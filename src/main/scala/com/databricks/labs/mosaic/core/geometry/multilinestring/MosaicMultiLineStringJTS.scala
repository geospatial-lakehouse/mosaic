package com.databricks.labs.mosaic.core.geometry.multilinestring

import com.databricks.labs.mosaic.core.geometry._
import com.databricks.labs.mosaic.core.geometry.linestring.{MosaicLineString, MosaicLineStringJTS}
import com.databricks.labs.mosaic.core.geometry.point.MosaicPoint
import com.databricks.labs.mosaic.core.types.model._
import com.databricks.labs.mosaic.core.types.model.GeometryTypeEnum.MULTILINESTRING
import com.esotericsoftware.kryo.io.Input
import org.locationtech.jts.geom._

import org.apache.spark.sql.catalyst.InternalRow

class MosaicMultiLineStringJTS(multiLineString: MultiLineString) extends MosaicGeometryJTS(multiLineString) with MosaicMultiLineString {

    override def toInternal: InternalGeometry = {
        val shells = for (i <- 0 until multiLineString.getNumGeometries) yield {
            val lineString = multiLineString.getGeometryN(i).asInstanceOf[LineString]
            lineString.getCoordinates.map(InternalCoord(_))
        }
        new InternalGeometry(MULTILINESTRING.id, shells.toArray, Array(Array(Array())))
    }

    override def getBoundary: MosaicGeometry = MosaicGeometryJTS(multiLineString.getBoundary)

    override def getShells: Seq[MosaicLineString] =
        for (i <- 0 until multiLineString.getNumGeometries) yield MosaicLineStringJTS(multiLineString.getGeometryN(i))

    override def asSeq: Seq[MosaicLineString] =
        for (i <- 0 until multiLineString.getNumGeometries)
            yield new MosaicLineStringJTS(multiLineString.getGeometryN(i).asInstanceOf[LineString])

    override def mapXY(f: (Double, Double) => (Double, Double)): MosaicGeometry = {
        MosaicMultiLineStringJTS.fromLines(asSeq.map(_.mapXY(f).asInstanceOf[MosaicLineStringJTS]))
    }

}

object MosaicMultiLineStringJTS extends GeometryReader {

    override def fromInternal(row: InternalRow): MosaicGeometry = {
        val internalGeom = InternalGeometry(row)
        val gf = new GeometryFactory()
        val lineStrings = for (shell <- internalGeom.boundaries) yield gf.createLineString(shell.map(_.toCoordinate))
        MosaicMultiLineStringJTS(gf.createMultiLineString(lineStrings))
    }

    def apply(geometry: Geometry): MosaicMultiLineStringJTS = {
        new MosaicMultiLineStringJTS(geometry.asInstanceOf[MultiLineString])
    }

    override def fromPoints(points: Seq[MosaicPoint], geomType: GeometryTypeEnum.Value = MULTILINESTRING): MosaicGeometry = {
        throw new UnsupportedOperationException("fromPoints is not intended for creating MultiLineStrings")
    }

    private def fromLines(lines: Seq[MosaicLineStringJTS]): MosaicMultiLineStringJTS = {
        val sr = lines.head.getSpatialReference
        val gf = new GeometryFactory()
        val geom = gf.createMultiLineString(lines.map(_.getGeom.asInstanceOf[LineString]).toArray)
        geom.setSRID(sr)
        MosaicMultiLineStringJTS(geom)
    }

    override def fromWKB(wkb: Array[Byte]): MosaicGeometry = MosaicGeometryJTS.fromWKB(wkb)

    override def fromWKT(wkt: String): MosaicGeometry = MosaicGeometryJTS.fromWKT(wkt)

    override def fromJSON(geoJson: String): MosaicGeometry = MosaicGeometryJTS.fromJSON(geoJson)

    override def fromHEX(hex: String): MosaicGeometry = MosaicGeometryJTS.fromHEX(hex)

    override def fromKryo(row: InternalRow): MosaicGeometry = {
        val kryoBytes = row.getBinary(1)
        val input = new Input(kryoBytes)
        MosaicGeometryJTS.kryo.readObject(input, classOf[MosaicMultiLineStringJTS])
    }

}