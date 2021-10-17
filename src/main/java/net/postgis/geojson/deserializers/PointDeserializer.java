package net.postgis.geojson.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import net.postgis.geojson.GeometryTypes;
import net.postgis.jdbc.geometry.Point;

/**
 * @author fcv
 */
public class PointDeserializer extends AbstractGeometryDeserializer<Point> {

    public PointDeserializer() {
        super(Point.class, GeometryTypes.POINT);
    }

    @Override
    protected Point coordinatesToGeometry(String type, JsonNode coordinates, JsonParser jp) {
        return GeometryDeserializer.readNodeAsPoint(coordinates);
    }

}
