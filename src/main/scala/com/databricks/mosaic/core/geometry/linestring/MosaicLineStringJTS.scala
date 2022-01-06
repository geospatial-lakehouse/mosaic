package com.databricks.mosaic.core.geometry.linestring

import com.databricks.mosaic.core.geometry.point.{MosaicPoint, MosaicPointJTS}
import com.databricks.mosaic.core.geometry.{GeometryReader, MosaicGeometry, MosaicGeometryJTS}
import com.databricks.mosaic.core.types.model.GeometryTypeEnum.{LINEARRING, LINESTRING}
import com.databricks.mosaic.core.types.model.{GeometryTypeEnum, InternalCoord, InternalGeometry}
import org.apache.spark.sql.catalyst.InternalRow
import org.locationtech.jts.geom.{Geometry, GeometryFactory, LineString, LinearRing}

class MosaicLineStringJTS(lineString: LineString)
  extends MosaicGeometryJTS(lineString) with MosaicLineString {

  override def getHolePoints: Seq[Seq[MosaicPoint]] = Nil

  override def asSeq: Seq[MosaicPoint] = getBoundaryPoints

  override def getBoundaryPoints: Seq[MosaicPoint] = {
    MosaicLineStringJTS.getPoints(lineString)
  }

  override def toInternal: InternalGeometry = {
    val shell = lineString.getCoordinates.map(InternalCoord(_))
    new InternalGeometry(LINESTRING.id, Array(shell), Array(Array(Array())))
  }

  override def getBoundary: Seq[MosaicPoint] = getBoundaryPoints

  override def getHoles: Seq[Seq[MosaicPoint]] = Nil

  override def flatten: Seq[MosaicGeometry] = List(this)
}

object MosaicLineStringJTS extends GeometryReader {

  def getPoints(lineString: LineString): Seq[MosaicPoint] = {
    for (i <- 0 until lineString.getNumPoints)
      yield new MosaicPointJTS(lineString.getPointN(i))
  }

  override def fromInternal(row: InternalRow): MosaicGeometry = {
    val internalGeom = InternalGeometry(row)
    val gf = new GeometryFactory()
    val lineString = gf.createLineString(internalGeom.boundaries.head.map(_.toCoordinate))
    MosaicLineStringJTS(lineString)
  }

  def apply(geometry: Geometry): MosaicLineStringJTS = {
    GeometryTypeEnum.fromString(geometry.getGeometryType) match {
      case LINESTRING => new MosaicLineStringJTS(geometry.asInstanceOf[LineString])
      case LINEARRING => new MosaicLineStringJTS(
        new GeometryFactory().createLineString(
          geometry.asInstanceOf[LinearRing].getCoordinates
        )
      )
    }
  }

  override def fromPoints(points: Seq[MosaicPoint], geomType: GeometryTypeEnum.Value = LINESTRING): MosaicGeometry = {
    require(geomType.id == LINESTRING.id)
    val gf = new GeometryFactory()
    val lineString = gf.createLineString(points.map(_.coord).toArray)
    MosaicLineStringJTS(lineString)
  }

  override def fromWKB(wkb: Array[Byte]): MosaicGeometry = MosaicGeometryJTS.fromWKB(wkb)

  override def fromWKT(wkt: String): MosaicGeometry = MosaicGeometryJTS.fromWKT(wkt)

  override def fromJSON(geoJson: String): MosaicGeometry = MosaicGeometryJTS.fromJSON(geoJson)

  override def fromHEX(hex: String): MosaicGeometry = MosaicGeometryJTS.fromHEX(hex)

}