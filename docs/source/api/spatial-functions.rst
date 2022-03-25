=================
Spatial functions
=================

st_area
*******

.. function:: st_area(col)

    Compute the area of a geometry.

    :param col: Geometry
    :type col: Column
    :rtype: Column: DoubleType

    :example:

.. tabs::
   .. code-tab:: py

    >>> df = spark.createDataFrame([{'wkt': 'POLYGON ((30 10, 40 40, 20 40, 10 20, 30 10))'}])
    >>> df.select(st_area('wkt')).show()
    +------------+
    |st_area(wkt)|
    +------------+
    |       550.0|
    +------------+

   .. code-tab:: scala

    >>> val df = List(("POLYGON ((30 10, 40 40, 20 40, 10 20, 30 10))")).toDF("wkt")
    >>> df.select(st_area($"wkt")).show()
    +------------+
    |st_area(wkt)|
    +------------+
    |       550.0|
    +------------+

   .. code-tab:: sql

    >>> SELECT st_area("POLYGON ((30 10, 40 40, 20 40, 10 20, 30 10))")
    +------------+
    |st_area(wkt)|
    +------------+
    |       550.0|
    +------------+

.. note:: Results of this function are always expressed in the original units of the input geometry.

st_perimeter
************

.. function:: st_perimeter(col)

    Compute the perimeter length of a geometry.

    :param col: Geometry
    :type col: Column
    :rtype: Column: DoubleType

    :example:

.. tabs::
   .. code-tab:: py

    >>> df = spark.createDataFrame([{'wkt': 'POLYGON ((30 10, 40 40, 20 40, 10 20, 30 10))'}])
    >>> df.select(st_perimeter('wkt')).show()
    +-----------------+
    |   st_length(wkt)|
    +-----------------+
    |96.34413615167959|
    +-----------------+

   .. code-tab:: scala

    >>> val df = List(("POLYGON ((30 10, 40 40, 20 40, 10 20, 30 10))")).toDF("wkt")
    >>> df.select(st_perimeter($"wkt")).show()
    +-----------------+
    |   st_length(wkt)|
    +-----------------+
    |96.34413615167959|
    +-----------------+

   .. code-tab:: sql

    >>> SELECT st_perimeter("POLYGON ((30 10, 40 40, 20 40, 10 20, 30 10))")
    +-----------------+
    |   st_length(wkt)|
    +-----------------+
    |96.34413615167959|
    +-----------------+


.. note:: Results of this function are always expressed in the original units of the input geometry.

.. note:: Alias for :ref:`st_length`.

st_length
************

.. function:: st_length(col)

    Compute the length of a geometry.

    :param col: Geometry
    :type col: Column
    :rtype: Column: DoubleType

    :example:

.. tabs::
   .. code-tab:: py

    >>> df = spark.createDataFrame([{'wkt': 'POLYGON ((30 10, 40 40, 20 40, 10 20, 30 10))'}])
    >>> df.select(st_length('wkt')).show()
    +-----------------+
    |   st_length(wkt)|
    +-----------------+
    |96.34413615167959|
    +-----------------+

   .. code-tab:: scala

    >>> val df = List(("POLYGON ((30 10, 40 40, 20 40, 10 20, 30 10))")).toDF("wkt")
    >>> df.select(st_length($"wkt")).show()
    +-----------------+
    |   st_length(wkt)|
    +-----------------+
    |96.34413615167959|
    +-----------------+

   .. code-tab:: sql

    >>> SELECT st_length("POLYGON ((30 10, 40 40, 20 40, 10 20, 30 10))")
    +-----------------+
    |   st_length(wkt)|
    +-----------------+
    |96.34413615167959|
    +-----------------+


.. note:: Results of this function are always expressed in the original units of the input geometry.

.. note:: Alias for :ref:`st_perimeter`.


st_convexhull
*************

.. function:: st_convexhull(col)

    Compute the convex hull of a geometry or multi-geometry object.

    :param col: Geometry
    :type col: Column
    :rtype: Column

    :example:

.. tabs::
   .. code-tab:: py

    >>> df = spark.createDataFrame([{'wkt': 'MULTIPOINT ((10 40), (40 30), (20 20), (30 10))'}])
    >>> df.select(st_convexhull('wkt')).show(1, False)
    +---------------------------------------------+
    |st_convexhull(wkt)                           |
    +---------------------------------------------+
    |POLYGON ((10 40, 20 20, 30 10, 40 30, 10 40))|
    +---------------------------------------------+

   .. code-tab:: scala

    >>> val df = List(("MULTIPOINT ((10 40), (40 30), (20 20), (30 10))")).toDF("wkt")
    >>> df.select(st_convexhull($"wkt")).show(false)
    +---------------------------------------------+
    |st_convexhull(wkt)                           |
    +---------------------------------------------+
    |POLYGON ((10 40, 20 20, 30 10, 40 30, 10 40))|
    +---------------------------------------------+

   .. code-tab:: sql

    >>> SELECT st_convexhull("MULTIPOINT ((10 40), (40 30), (20 20), (30 10))")
    +---------------------------------------------+
    |st_convexhull(wkt)                           |
    +---------------------------------------------+
    |POLYGON ((10 40, 20 20, 30 10, 40 30, 10 40))|
    +---------------------------------------------+


st_dump
*******

.. function:: st_dump(col)

    Explodes a multi-geometry into one row per constituent geometry.

    :param col: The input multi-geometry
    :type col: Column
    :rtype: Column

    :example:

.. tabs::
   .. code-tab:: py

    >>> df = spark.createDataFrame([{'wkt': 'MULTIPOINT ((10 40), (40 30), (20 20), (30 10))'}])
    >>> df.select(st_dump('wkt')).show(5, False)
    +-------------+
    |element      |
    +-------------+
    |POINT (10 40)|
    |POINT (40 30)|
    |POINT (20 20)|
    |POINT (30 10)|
    +-------------+

   .. code-tab:: scala

    >>> val df = List(("MULTIPOINT ((10 40), (40 30), (20 20), (30 10))")).toDF("wkt")
    >>> df.select(st_dump($"wkt")).show(false)
    +-------------+
    |element      |
    +-------------+
    |POINT (10 40)|
    |POINT (40 30)|
    |POINT (20 20)|
    |POINT (30 10)|
    +-------------+

   .. code-tab:: sql

    >>> SELECT st_dump("MULTIPOINT ((10 40), (40 30), (20 20), (30 10))")
    +-------------+
    |element      |
    +-------------+
    |POINT (10 40)|
    |POINT (40 30)|
    |POINT (20 20)|
    |POINT (30 10)|
    +-------------+


st_translate
************

.. function:: st_translate(geom, xd, yd)

    Translates `geom` to a new location using the distance parameters `xd` and `yd`.

    :param geom: Geometry
    :type geom: Column
    :param xd: Offset in the x-direction
    :type xd: Column (DoubleType)
    :param yd: Offset in the y-direction
    :type yd: Column (DoubleType)
    :rtype: Column

    :example:

.. tabs::
   .. code-tab:: py

    >>> df = spark.createDataFrame([{'wkt': 'MULTIPOINT ((10 40), (40 30), (20 20), (30 10))'}])
    >>> df.select(st_translate('wkt', lit(10), lit(-5))).show(1, False)
    +----------------------------------------------+
    |st_translate(wkt, 10, -5)                     |
    +----------------------------------------------+
    |MULTIPOINT ((20 35), (50 25), (30 15), (40 5))|
    +----------------------------------------------+

   .. code-tab:: scala

    >>> val df = List(("MULTIPOINT ((10 40), (40 30), (20 20), (30 10))")).toDF("wkt")
    >>> df.select(st_translate($"wkt", lit(10d), lit(-5d))).show(false)
    +----------------------------------------------+
    |st_translate(wkt, 10, -5)                     |
    +----------------------------------------------+
    |MULTIPOINT ((20 35), (50 25), (30 15), (40 5))|
    +----------------------------------------------+

   .. code-tab:: sql

    >>> SELECT st_translate("MULTIPOINT ((10 40), (40 30), (20 20), (30 10))", 10d, -5d)
    +----------------------------------------------+
    |st_translate(wkt, 10, -5)                     |
    +----------------------------------------------+
    |MULTIPOINT ((20 35), (50 25), (30 15), (40 5))|
    +----------------------------------------------+


st_scale
********

.. function:: st_scale(geom, xd, yd)

    Scales `geom` using the scaling factors `xd` and `yd`.

    :param geom: Geometry
    :type geom: Column
    :param xd: Scale factor in the x-direction
    :type xd: Column (DoubleType)
    :param yd: Scale factor in the y-direction
    :type yd: Column (DoubleType)
    :rtype: Column

    :example:

.. tabs::
   .. code-tab:: py

    >>> df = spark.createDataFrame([{'wkt': 'POLYGON ((30 10, 40 40, 20 40, 10 20, 30 10))'}])
    >>> df.select(st_scale('wkt', lit(0.5), lit(2))).show(1, False)
    +--------------------------------------------+
    |st_scale(wkt, 0.5, 2)                       |
    +--------------------------------------------+
    |POLYGON ((15 20, 20 80, 10 80, 5 40, 15 20))|
    +--------------------------------------------+

   .. code-tab:: scala

    >>> val df = List(("POLYGON ((30 10, 40 40, 20 40, 10 20, 30 10))")).toDF("wkt")
    >>> df.select(st_scale($"wkt", lit(0.5), lit(2.0))).show(false)
    +--------------------------------------------+
    |st_scale(wkt, 0.5, 2)                       |
    +--------------------------------------------+
    |POLYGON ((15 20, 20 80, 10 80, 5 40, 15 20))|
    +--------------------------------------------+

   .. code-tab:: sql

    >>> SELECT st_scale("POLYGON ((30 10, 40 40, 20 40, 10 20, 30 10))", 0.5d, 2.0d)
    +--------------------------------------------+
    |st_scale(wkt, 0.5, 2)                       |
    +--------------------------------------------+
    |POLYGON ((15 20, 20 80, 10 80, 5 40, 15 20))|
    +--------------------------------------------+


st_rotate
*********

.. function:: st_rotate(geom, td)

    Rotates `geom` using the rotational factor `td`.

    :param geom: Geometry
    :type geom: Column
    :param td: Rotation (in radians)
    :type td: Column (DoubleType)
    :rtype: Column

    :example:

.. tabs::
   .. code-tab:: py

    >>> from math import pi
    >>> df = spark.createDataFrame([{'wkt': 'POLYGON ((30 10, 40 40, 20 40, 10 20, 30 10))'}])
    >>> df.select(st_rotate('wkt', lit(pi))).show(1, False)
    +-------------------------------------------------------+
    |st_rotate(wkt, 3.141592653589793)                      |
    +-------------------------------------------------------+
    |POLYGON ((-30 -10, -40 -40, -20 -40, -10 -20, -30 -10))|
    +-------------------------------------------------------+

   .. code-tab:: scala

    >>> import math.Pi
    >>> val df = List(("POLYGON ((30 10, 40 40, 20 40, 10 20, 30 10))")).toDF("wkt")
    >>> df.select(st_rotate($"wkt", lit(Pi))).show(false)
    +-------------------------------------------------------+
    |st_rotate(wkt, 3.141592653589793)                      |
    +-------------------------------------------------------+
    |POLYGON ((-30 -10, -40 -40, -20 -40, -10 -20, -30 -10))|
    +-------------------------------------------------------+

   .. code-tab:: sql

    >>> SELECT st_rotate("POLYGON ((30 10, 40 40, 20 40, 10 20, 30 10))", pi())
    +-------------------------------------------------------+
    |st_rotate(wkt, 3.141592653589793)                      |
    +-------------------------------------------------------+
    |POLYGON ((-30 -10, -40 -40, -20 -40, -10 -20, -30 -10))|
    +-------------------------------------------------------+


st_centroid2D
*************

.. function:: st_centroid2D(col)

    Returns the x and y coordinates representing the centroid of the input geometry.

    :param col: Geometry
    :type col: Column
    :rtype: Column: StructType[x: DoubleType, y: DoubleType]

    :example:

.. tabs::
   .. code-tab:: py

    >>> df = spark.createDataFrame([{'wkt': 'POLYGON ((30 10, 40 40, 20 40, 10 20, 30 10))'}])
    >>> df.select(st_centroid2D('wkt')).show()
    +---------------------------------------+
    |st_centroid(wkt)                       |
    +---------------------------------------+
    |{25.454545454545453, 26.96969696969697}|
    +---------------------------------------+

   .. code-tab:: scala

   .. code-tab:: sql

st_centroid3D
*************

.. function:: st_centroid3D(col)

    Returns the x, y and z coordinates representing the centroid of the input geometry.

    :param col: Geometry
    :type col: Column
    :rtype: Column: StructType[x: DoubleType, y: DoubleType, z: DoubleType]


st_isvalid
**********

.. function:: st_isvalid(col)

    Returns `true` if the geometry is valid.

    :param col: Geometry
    :type col: Column
    :rtype: Column: BooleanType

    :example:

.. tabs::
   .. code-tab:: py

    >>> df = spark.createDataFrame([{'wkt': 'POLYGON ((30 10, 40 40, 20 40, 10 20, 30 10))'}])
    >>> df.select(st_isvalid('wkt')).show()
    +---------------+
    |st_isvalid(wkt)|
    +---------------+
    |           true|
    +---------------+

    >>> df = spark.createDataFrame([{
        'wkt': 'POLYGON((0 0, 10 0, 10 10, 0 10, 0 0), (15 15, 15 20, 20 20, 20 15, 15 15))'
        }])
    >>> df.select(st_isvalid('wkt')).show()
    +---------------+
    |st_isvalid(wkt)|
    +---------------+
    |          false|
    +---------------+

   .. code-tab:: scala

    >>> val df = List(("POLYGON ((30 10, 40 40, 20 40, 10 20, 30 10))")).toDF("wkt")
    >>> df.select(st_isvalid($"wkt")).show()
    +---------------+
    |st_isvalid(wkt)|
    +---------------+
    |           true|
    +---------------+

    >>> val df = List(("POLYGON((0 0, 10 0, 10 10, 0 10, 0 0), (15 15, 15 20, 20 20, 20 15, 15 15))")).toDF("wkt")
    >>> df.select(st_isvalid($"wkt")).show()
    +---------------+
    |st_isvalid(wkt)|
    +---------------+
    |          false|
    +---------------+

   .. code-tab:: sql

    >>> SELECT st_isvalid("POLYGON ((30 10, 40 40, 20 40, 10 20, 30 10))")
    +---------------+
    |st_isvalid(wkt)|
    +---------------+
    |           true|
    +---------------+

    >>> SELECT st_isvalid("POLYGON((0 0, 10 0, 10 10, 0 10, 0 0), (15 15, 15 20, 20 20, 20 15, 15 15))")
    +---------------+
    |st_isvalid(wkt)|
    +---------------+
    |          false|
    +---------------+

.. note:: Validity assertions will be dependent on the chosen geometry API.
    The assertions used in the ESRI geometry API (the default) follow the definitions in the
    "Simple feature access - Part 1" document (OGC 06-103r4) for each geometry type.

st_geometrytype
***************

.. function:: st_geometrytype(col)

    Returns the type of the input geometry ("POINT", "LINESTRING", "POLYGON" etc.).

    :param col: Geometry
    :type col: Column
    :rtype: Column: StringType

    :example:

.. tabs::
   .. code-tab:: py

    >>> df = spark.createDataFrame([{'wkt': 'POLYGON ((30 10, 40 40, 20 40, 10 20, 30 10))'}])
    >>> df.select(st_geometrytype('wkt')).show()
    +--------------------+
    |st_geometrytype(wkt)|
    +--------------------+
    |             POLYGON|
    +--------------------+

   .. code-tab:: scala

    >>> val df = List(("POLYGON ((30 10, 40 40, 20 40, 10 20, 30 10))")).toDF("wkt")
    >>> df.select(st_geometrytype($"wkt")).show()
    +--------------------+
    |st_geometrytype(wkt)|
    +--------------------+
    |             POLYGON|
    +--------------------+

   .. code-tab:: sql

    >>> SELECT st_geometrytype("POLYGON((0 0, 10 0, 10 10, 0 10, 0 0), (15 15, 15 20, 20 20, 20 15, 15 15))")
    +--------------------+
    |st_geometrytype(wkt)|
    +--------------------+
    |             POLYGON|
    +--------------------+


st_xmin
*******

.. function:: st_xmin(col)

    Returns the smallest x coordinate in the input geometry.

    :param col: Geometry
    :type col: Column
    :rtype: Column: DoubleType

    :example:

.. tabs::
   .. code-tab:: py

    >>> df = spark.createDataFrame([{'wkt': 'POLYGON ((30 10, 40 40, 20 40, 10 20, 30 10))'}])
    >>> df.select(st_xmin('wkt')).show()
    +-----------------+
    |st_minmaxxyz(wkt)|
    +-----------------+
    |             10.0|
    +-----------------+

   .. code-tab:: scala

    >>> val df = List(("POLYGON ((30 10, 40 40, 20 40, 10 20, 30 10))")).toDF("wkt")
    >>> df.select(st_xmin($"wkt")).show()
    +-----------------+
    |st_minmaxxyz(wkt)|
    +-----------------+
    |             10.0|
    +-----------------+

   .. code-tab:: sql

    >>> SELECT st_xmin("POLYGON ((30 10, 40 40, 20 40, 10 20, 30 10))")
    +-----------------+
    |st_minmaxxyz(wkt)|
    +-----------------+
    |             10.0|
    +-----------------+

st_xmax
*******

.. function:: st_xmax(col)

    Returns the largest x coordinate in the input geometry.

    :param col: Geometry
    :type col: Column
    :rtype: Column: DoubleType

    :example:

.. tabs::
   .. code-tab:: py

    >>> df = spark.createDataFrame([{'wkt': 'POLYGON ((30 10, 40 40, 20 40, 10 20, 30 10))'}])
    >>> df.select(st_xmax('wkt')).show()
    +-----------------+
    |st_minmaxxyz(wkt)|
    +-----------------+
    |             40.0|
    +-----------------+

   .. code-tab:: scala

    >>> val df = List(("POLYGON ((30 10, 40 40, 20 40, 10 20, 30 10))")).toDF("wkt")
    >>> df.select(st_xmax($"wkt")).show()
    +-----------------+
    |st_minmaxxyz(wkt)|
    +-----------------+
    |             40.0|
    +-----------------+

   .. code-tab:: sql

    >>> SELECT st_xmax("POLYGON ((30 10, 40 40, 20 40, 10 20, 30 10))")
    +-----------------+
    |st_minmaxxyz(wkt)|
    +-----------------+
    |             40.0|
    +-----------------+

st_ymin
*******

.. function:: st_ymin(col)

    Returns the smallest y coordinate in the input geometry.

    :param col: Geometry
    :type col: Column
    :rtype: Column: DoubleType

    :example:

.. tabs::
   .. code-tab:: py

    >>> df = spark.createDataFrame([{'wkt': 'POLYGON ((30 10, 40 40, 20 40, 10 20, 30 10))'}])
    >>> df.select(st_ymin('wkt')).show()
    +-----------------+
    |st_minmaxxyz(wkt)|
    +-----------------+
    |             10.0|
    +-----------------+

   .. code-tab:: scala

    >>> val df = List(("POLYGON ((30 10, 40 40, 20 40, 10 20, 30 10))")).toDF("wkt")
    >>> df.select(st_ymin($"wkt")).show()
    +-----------------+
    |st_minmaxxyz(wkt)|
    +-----------------+
    |             10.0|
    +-----------------+

   .. code-tab:: sql

    >>> SELECT st_ymin("POLYGON ((30 10, 40 40, 20 40, 10 20, 30 10))")
    +-----------------+
    |st_minmaxxyz(wkt)|
    +-----------------+
    |             10.0|
    +-----------------+

st_ymax
*******

.. function:: st_ymax(col)

    Returns the largest y coordinate in the input geometry.

    :param col: Geometry
    :type col: Column
    :rtype: Column: DoubleType

    :example:

.. tabs::
   .. code-tab:: py

    >>> df = spark.createDataFrame([{'wkt': 'POLYGON ((30 10, 40 40, 20 40, 10 20, 30 10))'}])
    >>> df.select(st_xmax('wkt')).show()
    +-----------------+
    |st_minmaxxyz(wkt)|
    +-----------------+
    |             40.0|
    +-----------------+

   .. code-tab:: scala

    >>> val df = List(("POLYGON ((30 10, 40 40, 20 40, 10 20, 30 10))")).toDF("wkt")
    >>> df.select(st_xmax($"wkt")).show()
    +-----------------+
    |st_minmaxxyz(wkt)|
    +-----------------+
    |             40.0|
    +-----------------+

   .. code-tab:: sql

    >>> SELECT st_xmax("POLYGON ((30 10, 40 40, 20 40, 10 20, 30 10))")
    +-----------------+
    |st_minmaxxyz(wkt)|
    +-----------------+
    |             40.0|
    +-----------------+


st_zmin
*******

.. function:: st_zmin(col)

    Returns the smallest z coordinate in the input geometry.

    :param col: Geometry
    :type col: Column
    :rtype: Column: DoubleType

st_zmax
*******

.. function:: st_zmax(col)

    Returns the largest z coordinate in the input geometry.

    :param col: Geometry
    :type col: Column
    :rtype: Column: DoubleType


flatten_polygons
****************

.. function:: flatten_polygons(col)

    Explodes a MultiPolygon geometry into one row per constituent Polygon.

    :param col: MultiPolygon Geometry
    :type col: Column
    :rtype: Column: StringType

    :example:

.. tabs::
   .. code-tab:: py

    >>> df = spark.createDataFrame([
        {'wkt': 'MULTIPOLYGON (((30 20, 45 40, 10 40, 30 20)), ((15 5, 40 10, 10 20, 5 10, 15 5)))'}
        ])
    >>> df.select(flatten_polygons('wkt')).show(2, False)
    +------------------------------------------+
    |element                                   |
    +------------------------------------------+
    |POLYGON ((30 20, 45 40, 10 40, 30 20))    |
    |POLYGON ((15 5, 40 10, 10 20, 5 10, 15 5))|
    +------------------------------------------+

   .. code-tab:: scala

    >>> val df = List(("MULTIPOLYGON (((30 20, 45 40, 10 40, 30 20)), ((15 5, 40 10, 10 20, 5 10, 15 5)))")).toDF("wkt")
    >>> df.select(flatten_polygons($"wkt")).show(false)
    +------------------------------------------+
    |element                                   |
    +------------------------------------------+
    |POLYGON ((30 20, 45 40, 10 40, 30 20))    |
    |POLYGON ((15 5, 40 10, 10 20, 5 10, 15 5))|
    +------------------------------------------+

   .. code-tab:: sql

    >>>

point_index
***********

.. function:: point_index(lat, lng, resolution)

    Returns the `resolution` grid index associated with 
    the input `lat` and `lng` coordinates.

    :param lat: Latitude
    :type lat: Column: DoubleType
    :param lng: Longitude
    :type lng: Column: DoubleType
    :param resolution: Index resolution
    :type resolution: Column: Integer
    :rtype: Column: LongType

    :example:

.. tabs::
   .. code-tab:: py

    >>> df = spark.createDataFrame([{'lon': 30., 'lat': 10.}])
    >>> df.select(point_index('lat', 'lon', lit(10))).show(1, False)
    +----------------------------+
    |h3_point_index(lat, lon, 10)|
    +----------------------------+
    |623385352048508927          |
    +----------------------------+

   .. code-tab:: scala

    >>> val df = List((30.0, 10.0)).toDF("lon", "lat")
    >>> df.select(point_index($"lon", $"lat", lit(10))).show()
    +----------------------------+
    |h3_point_index(lat, lon, 10)|
    +----------------------------+
    |623385352048508927          |
    +----------------------------+

   .. code-tab:: sql

    >>> SELECT point_index(30d, 10d, 10)
    +----------------------------+
    |h3_point_index(lat, lon, 10)|
    +----------------------------+
    |623385352048508927          |
    +----------------------------+


polyfill
********

.. function:: polyfill(geometry, resolution)

    Returns the set of grid indices covering the input `geometry` at `resolution`.

    :param geometry: Geometry
    :type geometry: Column
    :param resolution: Index resolution
    :type resolution: Column: Integer
    :rtype: Column: ArrayType[LongType]

    :example:

.. tabs::
   .. code-tab:: py

    >>> df = spark.createDataFrame([{
        'wkt': 'MULTIPOLYGON (((30 20, 45 40, 10 40, 30 20)), ((15 5, 40 10, 10 20, 5 10, 15 5)))'
        }])
    >>> df.select(polyfill('wkt', lit(0))).show(1, False)
    +------------------------------------------------------------+
    |h3_polyfill(wkt, 0)                                         |
    +------------------------------------------------------------+
    |[577586652210266111, 578360708396220415, 577269992861466623]|
    +------------------------------------------------------------+

   .. code-tab:: scala

    >>> val df = List(("MULTIPOLYGON (((30 20, 45 40, 10 40, 30 20)), ((15 5, 40 10, 10 20, 5 10, 15 5)))")).toDF("wkt")
    >>> df.select(polyfill($"wkt", lit(0))).show(false)
    +------------------------------------------------------------+
    |h3_polyfill(wkt, 0)                                         |
    +------------------------------------------------------------+
    |[577586652210266111, 578360708396220415, 577269992861466623]|
    +------------------------------------------------------------+

   .. code-tab:: sql

    >>> SELECT polyfill("MULTIPOLYGON (((30 20, 45 40, 10 40, 30 20)), ((15 5, 40 10, 10 20, 5 10, 15 5)))", 0)
    +------------------------------------------------------------+
    |h3_polyfill(wkt, 0)                                         |
    +------------------------------------------------------------+
    |[577586652210266111, 578360708396220415, 577269992861466623]|
    +------------------------------------------------------------+


mosaicfill
**********

.. function:: mosaicfill(geometry, resolution)

    Generates:
    - a set of core indices that are fully contained by `geometry`; and
    - a set of border indices and sub-polygons that are partially contained by the input.

    Outputs an array of chip structs for each input row.

    :param geometry: Geometry
    :type geometry: Column
    :param resolution: Index resolution
    :type resolution: Column: Integer
    :rtype: Column: ArrayType[MosaicType]

    :example:

.. tabs::
   .. code-tab:: py

    >>> df = spark.createDataFrame([{'wkt': 'MULTIPOLYGON (((30 20, 45 40, 10 40, 30 20)), ((15 5, 40 10, 10 20, 5 10, 15 5)))'}])
    >>> df.select(mosaicfill('wkt', lit(0))).printSchema()
    root
     |-- h3_mosaicfill(wkt, 0): mosaic (nullable = true)
     |    |-- chips: array (nullable = true)
     |    |    |-- element: mosaic_chip (containsNull = true)
     |    |    |    |-- is_core: boolean (nullable = true)
     |    |    |    |-- h3: long (nullable = true)
     |    |    |    |-- wkb: binary (nullable = true)


    >>> df.select(mosaicfill('wkt', lit(0))).show()
    +---------------------+
    |h3_mosaicfill(wkt, 0)|
    +---------------------+
    | {[{false, 5774810...|
    +---------------------+

   .. code-tab:: scala

    >>> val df = List(("MULTIPOLYGON (((30 20, 45 40, 10 40, 30 20)), ((15 5, 40 10, 10 20, 5 10, 15 5)))")).toDF("wkt")
    >>> df.select(mosaicfill($"wkt", lit(0))).printSchema
    root
     |-- h3_mosaicfill(wkt, 0): mosaic (nullable = true)
     |    |-- chips: array (nullable = true)
     |    |    |-- element: mosaic_chip (containsNull = true)
     |    |    |    |-- is_core: boolean (nullable = true)
     |    |    |    |-- h3: long (nullable = true)
     |    |    |    |-- wkb: binary (nullable = true)

    >>> df.select(mosaicfill($"wkt", lit(0))).show()
    +---------------------+
    |h3_mosaicfill(wkt, 0)|
    +---------------------+
    | {[{false, 5774810...|
    +---------------------+

   .. code-tab:: sql

    >>> SELECT mosaicfill("MULTIPOLYGON (((30 20, 45 40, 10 40, 30 20)), ((15 5, 40 10, 10 20, 5 10, 15 5)))", 0)
    +---------------------+
    |h3_mosaicfill(wkt, 0)|
    +---------------------+
    | {[{false, 5774810...|
    +---------------------+


mosaic_explode
**************

.. function:: mosaicfill(geometry, resolution)

    Returns the set of Mosaic chips covering the input `geometry` at `resolution`.

    In contrast to :ref:`mosaic_fill`, `mosaic_explode` generates one result row per chip.

    :param geometry: Geometry
    :type geometry: Column
    :param resolution: Index resolution
    :type resolution: Column: Integer
    :rtype: Column: MosaicType

    :example:

.. tabs::
   .. code-tab:: py

    >>> df = spark.createDataFrame([{'wkt': 'MULTIPOLYGON (((30 20, 45 40, 10 40, 30 20)), ((15 5, 40 10, 10 20, 5 10, 15 5)))'}])
    >>> df.select(mosaic_explode('wkt', lit(0))).show()
    +-----------------------------------------------+
    |is_core|                h3|                 wkb|
    +-------+------------------+--------------------+
    |  false|577481099093999615|[01 03 00 00 00 0...|
    |  false|578044049047420927|[01 03 00 00 00 0...|
    |  false|578782920861286399|[01 03 00 00 00 0...|
    |  false|577023702256844799|[01 03 00 00 00 0...|
    |  false|577938495931154431|[01 03 00 00 00 0...|
    |  false|577586652210266111|[01 06 00 00 00 0...|
    |  false|577269992861466623|[01 03 00 00 00 0...|
    |  false|578360708396220415|[01 03 00 00 00 0...|
    +-------+------------------+--------------------+

   .. code-tab:: scala

    >>> val df = List(("MULTIPOLYGON (((30 20, 45 40, 10 40, 30 20)), ((15 5, 40 10, 10 20, 5 10, 15 5)))")).toDF("wkt")
    >>> df.select(mosaic_explode($"wkt", lit(0))).show()
    +-----------------------------------------------+
    |is_core|                h3|                 wkb|
    +-------+------------------+--------------------+
    |  false|577481099093999615|[01 03 00 00 00 0...|
    |  false|578044049047420927|[01 03 00 00 00 0...|
    |  false|578782920861286399|[01 03 00 00 00 0...|
    |  false|577023702256844799|[01 03 00 00 00 0...|
    |  false|577938495931154431|[01 03 00 00 00 0...|
    |  false|577586652210266111|[01 06 00 00 00 0...|
    |  false|577269992861466623|[01 03 00 00 00 0...|
    |  false|578360708396220415|[01 03 00 00 00 0...|
    +-------+------------------+--------------------+

   .. code-tab:: sql

    >>> SELECT mosaic_explode("MULTIPOLYGON (((30 20, 45 40, 10 40, 30 20)), ((15 5, 40 10, 10 20, 5 10, 15 5)))", 0)
    +-----------------------------------------------+
    |is_core|                h3|                 wkb|
    +-------+------------------+--------------------+
    |  false|577481099093999615|[01 03 00 00 00 0...|
    |  false|578044049047420927|[01 03 00 00 00 0...|
    |  false|578782920861286399|[01 03 00 00 00 0...|
    |  false|577023702256844799|[01 03 00 00 00 0...|
    |  false|577938495931154431|[01 03 00 00 00 0...|
    |  false|577586652210266111|[01 06 00 00 00 0...|
    |  false|577269992861466623|[01 03 00 00 00 0...|
    |  false|578360708396220415|[01 03 00 00 00 0...|
    +-------+------------------+--------------------+
