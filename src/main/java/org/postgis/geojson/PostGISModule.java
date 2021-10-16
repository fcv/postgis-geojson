package org.postgis.geojson;

import com.fasterxml.jackson.databind.module.SimpleModule;
import net.postgis.jdbc.geometry.Geometry;
import org.postgis.geojson.deserializers.GeometryDeserializer;
import org.postgis.geojson.serializers.GeometrySerializer;

/**
 * Module for loading serializers/deserializers.
 * 
 * @author Maycon Viana Bordin <mayconbordin@gmail.com>
 */
public class PostGISModule extends SimpleModule {
    private static final long serialVersionUID = 1L;

    public PostGISModule() {
        super("PostGISModule");

        addSerializer(Geometry.class, new GeometrySerializer());
        addDeserializer(Geometry.class, new GeometryDeserializer());
    }
}
