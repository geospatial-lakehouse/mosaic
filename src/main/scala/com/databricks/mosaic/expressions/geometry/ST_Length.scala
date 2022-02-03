package com.databricks.mosaic.expressions.geometry

import org.apache.spark.sql.catalyst.expressions.{Expression, ExpressionDescription, NullIntolerant, UnaryExpression}
import org.apache.spark.sql.catalyst.expressions.codegen.{CodegenContext, CodegenFallback, ExprCode}
import org.apache.spark.sql.types.{DataType, DoubleType}

import com.databricks.mosaic.codegen.format.ConvertToCodeGen
import com.databricks.mosaic.core.geometry.api.GeometryAPI

@ExpressionDescription(
  usage = "_FUNC_(expr1) - Returns the length measurement of the geometry.",
  examples = """
    Examples:
      > SELECT _FUNC_(a);
       15.2512
  """,
  since = "1.0"
)
case class ST_Length(inputGeom: Expression, geometryAPIName: String) extends UnaryExpression with NullIntolerant {

    def dataType: DataType = DoubleType

    def child: Expression = inputGeom

    override def nullSafeEval(input1: Any): Any = {
        val geometryAPI = GeometryAPI(geometryAPIName)
        val geom = geometryAPI.geometry(input1, inputGeom.dataType)
        geom.getLength
    }

    override def makeCopy(newArgs: Array[AnyRef]): Expression = {
        val asArray = newArgs.take(1).map(_.asInstanceOf[Expression])
        val res = ST_Length(asArray(0), geometryAPIName)
        res.copyTagsFrom(this)
        res
    }

    override protected def doGenCode(ctx: CodegenContext, ev: ExprCode): ExprCode =
        nullSafeCodeGen(
            ctx,
            ev,
            leftEval => {
                val geometryAPI = GeometryAPI.apply(geometryAPIName)
                val (inCode, geomInRef) = ConvertToCodeGen.readGeometryCode(ctx, leftEval, inputGeom.dataType, geometryAPI)

                geometryAPIName match {
                    case "OGC" => s"""
                                     |$inCode
                                     |${ev.value} = $geomInRef.getEsriGeometry().calculateLength2D();
                                     |""".stripMargin
                    case "JTS" => s"""
                                     |try {
                                     |$inCode
                                     |${ev.value} = $geomInRef.getLength();
                                     |} catch (Exception e) {
                                     | throw e;
                                     |}
                                     |""".stripMargin

                }
            }
        )
}