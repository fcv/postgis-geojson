package net.postgis.geojson.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import net.postgis.geojson.GeometryTypes;
import net.postgis.jdbc.geometry.Geometry;
import net.postgis.jdbc.geometry.GeometryCollection;
import net.postgis.jdbc.geometry.LineString;
import net.postgis.jdbc.geometry.MultiLineString;
import net.postgis.jdbc.geometry.MultiPoint;
import net.postgis.jdbc.geometry.MultiPolygon;
import net.postgis.jdbc.geometry.Point;
import net.postgis.jdbc.geometry.Polygon;

import java.io.IOException;

/**
 * Serializer for Geometry types.
 * 
 * @author Maycon Viana Bordin <mayconbordin@gmail.com>
 */
public class GeometrySerializer extends JsonSerializer<Geometry> {
    
    @Override
    public void serialize(Geometry geom, JsonGenerator json, SerializerProvider provider) 
            throws IOException, JsonProcessingException {
        json.writeStartObject();

        if (geom instanceof Point) {
            serializePoint((Point)geom, json);
        } else if (geom instanceof Polygon) {
            serializePolygon((Polygon)geom, json);
        } else if (geom instanceof LineString) {
            serializeLineString((LineString)geom, json);
        } else if (geom instanceof MultiPolygon) {
            serializeMultiPolygon((MultiPolygon)geom, json);
        } else if (geom instanceof MultiPoint) {
            serializeMultiPoint((MultiPoint)geom, json);
        } else if (geom instanceof MultiLineString) {
            serializeMultiLineString((MultiLineString)geom, json);
        } else if (geom instanceof GeometryCollection) {
            serializeGeometryCollection((GeometryCollection)geom, json);
        }

        json.writeEndObject();
    }
    
    protected void serializeGeometryCollection(GeometryCollection gc, JsonGenerator json) throws IOException {
        writeTypeField(GeometryTypes.GEOMETRY_COLLECTION, json);
        json.writeArrayFieldStart("geometries");
        
        for (Geometry geom : gc.getGeometries()) {
            serialize(geom, json, null);
        }
        
        json.writeEndArray();
    }
    
    protected void serializeMultiLineString(MultiLineString mls, JsonGenerator json) throws IOException {
        writeTypeField(GeometryTypes.MULTI_LINE_STRING, json);
        writeStartCoordinates(json);

        for (LineString ls : mls.getLines()) {
            json.writeStartArray();
            writePoints(json, ls.getPoints());
            json.writeEndArray();
        }

        writeEndCoordinates(json);
    }
    
    protected void serializeMultiPoint(MultiPoint mp, JsonGenerator json) throws IOException {
        writeTypeField(GeometryTypes.MULTI_POINT, json);
        writeStartCoordinates(json);
        writePoints(json, mp.getPoints());
        writeEndCoordinates(json);
    }
    
    protected void serializeMultiPolygon(MultiPolygon mp, JsonGenerator json) throws IOException {
        writeTypeField(GeometryTypes.MULTI_POLYGON, json);
        writeStartCoordinates(json);

        for (Polygon polygon : mp.getPolygons()) {
            json.writeStartArray();

            for (int i=0; i<polygon.numRings(); i++) {
                json.writeStartArray();
                writePoints(json, polygon.getRing(i).getPoints());
                json.writeEndArray();
            }

            json.writeEndArray();
        }

        writeEndCoordinates(json);
    }
    
    protected void serializeLineString(LineString ls, JsonGenerator json) throws IOException {
        writeTypeField(GeometryTypes.LINE_STRING, json);
        writeStartCoordinates(json);
        writePoints(json, ls.getPoints());
        writeEndCoordinates(json);
    }
    
    protected void serializePolygon(Polygon polygon, JsonGenerator json) throws IOException {
        writeTypeField(GeometryTypes.POLYGON, json);
        writeStartCoordinates(json);

        for (int i=0; i<polygon.numRings(); i++) {
            json.writeStartArray();
            writePoints(json, polygon.getRing(i).getPoints());
            json.writeEndArray();
        }

        writeEndCoordinates(json);
    }
    
    protected void serializePoint(Point point, JsonGenerator json) throws IOException {
        writeTypeField(GeometryTypes.POINT, json);
        writeStartCoordinates(json);
        if (point.dimension > 2) {
            writeNumbers(json, point.getX(), point.getY(), point.getZ());
        } else {
            writeNumbers(json, point.getX(), point.getY());
        }
        writeEndCoordinates(json);
    }

    protected void writeTypeField(String type, JsonGenerator json) throws IOException {
        json.writeStringField("type", type);
    }

    protected void writeStartCoordinates(JsonGenerator json) throws IOException {
        json.writeArrayFieldStart("coordinates");
    }

    protected void writeEndCoordinates(JsonGenerator json) throws IOException {
        json.writeEndArray();
    }

    protected void writeNumbers(JsonGenerator json, double...numbers) throws IOException {
        for (double number : numbers) {
            json.writeNumber(number);
        }
    }

    protected void writePoints(JsonGenerator json, Point[] points) throws IOException {
        for (Point point : points) {
            json.writeStartArray();
            if (point.dimension > 2) {
                writeNumbers(json, point.getX(), point.getY(), point.getZ());
            } else {
                writeNumbers(json, point.getX(), point.getY());
            }
            json.writeEndArray();
        }
    }
}
