# postgis-geojson
[![Build Status](https://github.com/fcv/postgis-geojson/actions/workflows/maven.yml/badge.svg)](https://github.com/fcv/postgis-geojson/actions/workflows/maven.yml) [![Release](https://jitpack.io/v/fcv/postgis-geojson.svg)](https://jitpack.io/#fcv/postgis-geojson)

GeoJSON Jackson Serializers and Deserializers for PostGIS Geometry objects.

## GeoJSON Support

This library gives support for serialization/deserialization of all [Geometry Objects](http://geojson.org/geojson-spec.html#geometry-objects) defined
in the GeoJSON specification.

The relation between GeoJSON geometry objects and PostGIS objects is given below:

GeoJSON           | PostGIS
------------------| -------------
[Point](http://geojson.org/geojson-spec.html#point)| [Point](https://javadoc.io/doc/net.postgis/postgis-geometry/2021.1.0/net/postgis/jdbc/geometry/Point.html)
[MultiPoint](http://geojson.org/geojson-spec.html#multipoint)| [MultiPoint](https://javadoc.io/doc/net.postgis/postgis-geometry/2021.1.0/net/postgis/jdbc/geometry/MultiPoint.html)
[LineString](http://geojson.org/geojson-spec.html#linestring)| [LineString](https://javadoc.io/doc/net.postgis/postgis-geometry/2021.1.0/net/postgis/jdbc/geometry/LineString.html)
[MultiLineString](http://geojson.org/geojson-spec.html#multilinestring)| [MultiLineString](https://javadoc.io/doc/net.postgis/postgis-geometry/2021.1.0/net/postgis/jdbc/geometry/MultiLineString.html)
[Polygon](http://geojson.org/geojson-spec.html#polygon)| [Polygon](https://javadoc.io/doc/net.postgis/postgis-geometry/2021.1.0/net/postgis/jdbc/geometry/Polygon.html)
[MultiPolygon](http://geojson.org/geojson-spec.html#multipolygon)| [MultiPolygon](https://javadoc.io/doc/net.postgis/postgis-geometry/2021.1.0/net/postgis/jdbc/geometry/MultiPolygon.html)
[GeometryCollection](http://geojson.org/geojson-spec.html#geometry-collection)| [GeometryCollection](https://javadoc.io/doc/net.postgis/postgis-geometry/2021.1.0/net/postgis/jdbc/geometry/GeometryCollection.html)

## Installation

Add the JitPack repository to your `<repositories>` list in the `pom.xml` file:

```xml
<repository>
  <id>jitpack.io</id>
  <url>https://jitpack.io</url>
</repository>
```

Then add the dependency to your `pom.xml` file:

```xml
<dependency>
  <groupId>com.github.fcv</groupId>
  <artifactId>postgis-geojson</artifactId>
  <version>3.1</version>
</dependency>
```

For more information on how to build the library with other tools (Gradle, Sbt, Leiningen) see the [JitPack documentation](https://jitpack.io/docs/BUILDING/).

## Usage

First you need to register the library module within the `ObjectMapper` instance you are going to use:

```java
ObjectMapper mapper = new ObjectMapper();
mapper.registerModule(new PostGISModule());
```

The you can serialize objects:

```java
String json = mapper.writeValueAsString(new Point(125.6, 10.1));
```

And deserialize them:

```java
Point point = (Point) mapper.readValue(json, Geometry.class);
```
