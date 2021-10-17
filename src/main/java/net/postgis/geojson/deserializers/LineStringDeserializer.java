package net.postgis.geojson.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import net.postgis.geojson.GeometryTypes;
import net.postgis.jdbc.geometry.LineString;

/**
 * @author fcv
 */
public class LineStringDeserializer extends AbstractGeometryDeserializer<LineString> {

    public LineStringDeserializer() {
        super(LineString.class, GeometryTypes.LINE_STRING);
    }

    @Override
    protected LineString coordinatesToGeometry(String type, JsonNode coordinates, JsonParser jp) {
        return GeometryDeserializer.readNodeAsLineString(coordinates);
    }

}
