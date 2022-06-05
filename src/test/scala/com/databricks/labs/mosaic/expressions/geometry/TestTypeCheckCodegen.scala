package com.databricks.labs.mosaic.expressions.geometry

import com.databricks.labs.mosaic.core.geometry.api.GeometryAPI.{ESRI, JTS}
import com.databricks.labs.mosaic.core.index.{BNGIndexSystem, H3IndexSystem}
import com.databricks.labs.mosaic.functions.MosaicContext
import com.databricks.labs.mosaic.test.SparkCodeGenSuite
import org.scalatest.flatspec.AnyFlatSpec

class TestTypeCheckCodegen extends AnyFlatSpec with TypeCheckBehaviors with SparkCodeGenSuite {

    "ST_GeometryType" should "return correct types for wkt format for any index system and any geometry API" in {
        it should behave like wktTypesCodegen(MosaicContext.build(H3IndexSystem, ESRI), spark)
        it should behave like wktTypesCodegen(MosaicContext.build(H3IndexSystem, JTS), spark)
        it should behave like wktTypes(MosaicContext.build(H3IndexSystem, ESRI), spark)
        it should behave like wktTypes(MosaicContext.build(H3IndexSystem, JTS), spark)
        it should behave like wktTypesCodegen(MosaicContext.build(BNGIndexSystem, ESRI), spark)
        it should behave like wktTypesCodegen(MosaicContext.build(BNGIndexSystem, JTS), spark)
        it should behave like wktTypes(MosaicContext.build(BNGIndexSystem, ESRI), spark)
        it should behave like wktTypes(MosaicContext.build(BNGIndexSystem, JTS), spark)
    }

    "ST_GeometryType" should "return correct types for hex format for any index system and any geometry API" in {
        it should behave like hexTypesCodegen(MosaicContext.build(H3IndexSystem, ESRI), spark)
        it should behave like hexTypesCodegen(MosaicContext.build(H3IndexSystem, JTS), spark)
        it should behave like hexTypes(MosaicContext.build(H3IndexSystem, ESRI), spark)
        it should behave like hexTypes(MosaicContext.build(H3IndexSystem, JTS), spark)
        it should behave like hexTypesCodegen(MosaicContext.build(BNGIndexSystem, ESRI), spark)
        it should behave like hexTypesCodegen(MosaicContext.build(BNGIndexSystem, JTS), spark)
        it should behave like hexTypes(MosaicContext.build(BNGIndexSystem, ESRI), spark)
        it should behave like hexTypes(MosaicContext.build(BNGIndexSystem, JTS), spark)
    }

}