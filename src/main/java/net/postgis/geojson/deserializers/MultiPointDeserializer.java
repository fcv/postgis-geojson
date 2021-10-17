package net.postgis.geojson.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import net.postgis.geojson.GeometryTypes;
import net.postgis.jdbc.geometry.MultiPoint;

/**
 * @author fcv
 */
public class MultiPointDeserializer extends AbstractGeometryDeserializer<MultiPoint> {

    public MultiPointDeserializer() {
        super(MultiPoint.class, GeometryTypes.MULTI_POINT);
    }

    @Override
    protected MultiPoint coordinatesToGeometry(String type, JsonNode coordinates, JsonParser jp) {
        return new MultiPoint(GeometryDeserializer.readNodeAsPointArray(coordinates));
    }

}
