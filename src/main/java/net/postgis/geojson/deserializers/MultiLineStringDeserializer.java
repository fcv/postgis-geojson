package net.postgis.geojson.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import net.postgis.geojson.GeometryTypes;
import net.postgis.jdbc.geometry.MultiLineString;

/**
 * @author fcv
 */
public class MultiLineStringDeserializer extends AbstractGeometryDeserializer<MultiLineString> {

    public MultiLineStringDeserializer() {
        super(MultiLineString.class, GeometryTypes.MULTI_LINE_STRING);
    }

    @Override
    protected MultiLineString coordinatesToGeometry(String type, JsonNode coordinates, JsonParser jp) {
        return new MultiLineString(GeometryDeserializer.readNodeAsLineStringArray(coordinates));
    }

}
