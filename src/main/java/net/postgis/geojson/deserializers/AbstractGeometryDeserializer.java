package net.postgis.geojson.deserializers;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import net.postgis.jdbc.geometry.Geometry;

import java.io.IOException;

import static java.util.Objects.requireNonNull;

/**
 * @author fcv
 */
public abstract class AbstractGeometryDeserializer<T extends Geometry> extends JsonDeserializer<T> {

    private final Class<? super T> targetType;
    private final String supportedGeoType;

    public AbstractGeometryDeserializer(Class<? super T> targetType, String supportedGeoType) {
        this.targetType = requireNonNull(targetType);
        this.supportedGeoType = requireNonNull(supportedGeoType);
    }

    @Override
    public T deserialize(JsonParser jp, DeserializationContext dc)
            throws IOException, JsonProcessingException {
        String fieldName;
        String type = null;
        
        while (true) {
            fieldName = jp.nextFieldName();

            if (fieldName == null) {
                return null;
            } else if (fieldName.equals("type")) {
                type = jp.nextTextValue();
                if (!supportedGeoType.equals(type)) {
                    throw new JsonParseException(
                            String.format("type field is expected to be \"%s\" when deserializing to a %s" +
                                    ", was \"%s\" instead", supportedGeoType, targetType.getName(), type),
                            jp.getCurrentLocation());
                }
            } else if (fieldName.equals("coordinates")) {
                JsonNode node = jp.readValueAsTree();
                JsonNode coordinates = node.get("coordinates");
                
                return coordinatesToGeometry(type, coordinates, jp);
            } else if (fieldName.equals("geometries")) {
                JsonNode node = jp.readValueAsTree();
                JsonNode geometries = node.get("geometries");
                
                return coordinatesToGeometry(type, geometries, jp);
            }
        }
    }
    
    abstract protected T coordinatesToGeometry(String type, JsonNode coordinates, JsonParser jp)
            throws JsonParseException;

}
