package net.postgis.geojson.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import net.postgis.geojson.GeometryTypes;
import net.postgis.jdbc.geometry.Polygon;

/**
 * @author fcv
 */
public class PolygonDeserializer extends AbstractGeometryDeserializer<Polygon> {

    public PolygonDeserializer() {
        super(Polygon.class, GeometryTypes.POLYGON);
    }

    @Override
    protected Polygon coordinatesToGeometry(String type, JsonNode coordinates, JsonParser jp) {
        return new Polygon(GeometryDeserializer.readNodeAsLinearRingArray(coordinates));
    }

}
