package net.postgis.geojson;

import com.fasterxml.jackson.databind.module.SimpleModule;
import net.postgis.geojson.deserializers.GeometryCollectionDeserializer;
import net.postgis.geojson.deserializers.GeometryDeserializer;
import net.postgis.geojson.deserializers.LineStringDeserializer;
import net.postgis.geojson.deserializers.MultiLineStringDeserializer;
import net.postgis.geojson.deserializers.MultiPointDeserializer;
import net.postgis.geojson.deserializers.MultiPolygonDeserializer;
import net.postgis.geojson.deserializers.PointDeserializer;
import net.postgis.geojson.deserializers.PolygonDeserializer;
import net.postgis.geojson.serializers.GeometrySerializer;
import net.postgis.jdbc.geometry.Geometry;
import net.postgis.jdbc.geometry.GeometryCollection;
import net.postgis.jdbc.geometry.LineString;
import net.postgis.jdbc.geometry.MultiLineString;
import net.postgis.jdbc.geometry.MultiPoint;
import net.postgis.jdbc.geometry.MultiPolygon;
import net.postgis.jdbc.geometry.Point;
import net.postgis.jdbc.geometry.Polygon;

/**
 * Module for loading serializers/deserializers.
 * 
 * @author Maycon Viana Bordin <mayconbordin@gmail.com>
 * @author fcv
 */
public class PostGISModule extends SimpleModule {
    private static final long serialVersionUID = 1L;

    public PostGISModule() {
        super("PostGISModule");

        addSerializer(Geometry.class, new GeometrySerializer());
        addDeserializer(Geometry.class, new GeometryDeserializer());

        addDeserializer(Point.class, new PointDeserializer());
        addDeserializer(MultiPoint.class, new MultiPointDeserializer());
        addDeserializer(LineString.class, new LineStringDeserializer());
        addDeserializer(Geometry.class, new GeometryDeserializer());
        addDeserializer(MultiLineString.class, new MultiLineStringDeserializer());
        addDeserializer(Polygon.class, new PolygonDeserializer());
        addDeserializer(MultiPolygon.class, new MultiPolygonDeserializer());
        addDeserializer(GeometryCollection.class, new GeometryCollectionDeserializer());
    }
}
