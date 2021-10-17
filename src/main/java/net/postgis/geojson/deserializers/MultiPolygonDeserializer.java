package net.postgis.geojson.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import net.postgis.geojson.GeometryTypes;
import net.postgis.jdbc.geometry.MultiPolygon;

/**
 * @author fcv
 */
public class MultiPolygonDeserializer extends AbstractGeometryDeserializer<MultiPolygon> {

    public MultiPolygonDeserializer() {
        super(MultiPolygon.class, GeometryTypes.MULTI_POLYGON);
    }

    @Override
    protected MultiPolygon coordinatesToGeometry(String type, JsonNode coordinates, JsonParser jp) {
        return new MultiPolygon(GeometryDeserializer.readNodeAsPolygonArray(coordinates));
    }

}
