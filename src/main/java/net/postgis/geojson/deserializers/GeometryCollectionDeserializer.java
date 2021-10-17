package net.postgis.geojson.deserializers;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import net.postgis.geojson.GeometryTypes;
import net.postgis.jdbc.geometry.GeometryCollection;

import static net.postgis.geojson.deserializers.GeometryDeserializer.readNodeAsGeometryArray;

/**
 * @author fcv
 */
public class GeometryCollectionDeserializer extends AbstractGeometryDeserializer<GeometryCollection> {

    public GeometryCollectionDeserializer() {
        super(GeometryCollection.class, GeometryTypes.GEOMETRY_COLLECTION);
    }

    @Override
    protected GeometryCollection coordinatesToGeometry(String type, JsonNode coordinates, JsonParser jp) throws JsonParseException {
        return new GeometryCollection(readNodeAsGeometryArray(coordinates, jp));
    }

}
