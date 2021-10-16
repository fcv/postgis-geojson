package net.postgis.geojson.serializers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import net.postgis.jdbc.geometry.Geometry;
import net.postgis.jdbc.geometry.GeometryCollection;
import net.postgis.jdbc.geometry.LineString;
import net.postgis.jdbc.geometry.LinearRing;
import net.postgis.jdbc.geometry.MultiLineString;
import net.postgis.jdbc.geometry.MultiPoint;
import net.postgis.jdbc.geometry.MultiPolygon;
import net.postgis.jdbc.geometry.Point;
import net.postgis.jdbc.geometry.Polygon;
import net.postgis.geojson.util.GeometryBuilder;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

/**
 *
 * @author mayconbordin
 */
public class GeometrySerializerTest {
    protected ObjectMapper mapper;
    
    @Before
    public void setUp() {
        mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule("MyModule");
        module.addSerializer(Geometry.class, new GeometrySerializer());
        mapper.registerModule(module);
    }
    
    @Test
    public void testSerializePoint() throws Exception {
        System.out.println("serializePoint");

        Point obj = new Point(125.6, 10.1);
        
        String actual = mapper.writeValueAsString(obj);
        
        String expected = "{\"type\": \"Point\",\"coordinates\": [125.6, 10.1, 0.0]}";
        JSONAssert.assertEquals(expected, actual, false);
    }
    
    @Test
    public void testSerializeLineString() throws Exception {
        System.out.println("serializeLineString");
        
        String expected = "{\"type\": \"LineString\",\"coordinates\": [ [100.0, 0.0, 0.0], [101.0, 1.0, 0.0] ]}";
        
        LineString obj = new LineString(new Point[] {
            new Point(100.0, 0.0), new Point(101.0, 1.0)
        });
        
        String actual = mapper.writeValueAsString(obj);
        JSONAssert.assertEquals(expected, actual, false);
    }
    
    @Test
    public void testSerializePolygon() throws Exception {
        System.out.println("serializePolygon");

        String expected = "{\"type\":\"Polygon\",\"coordinates\":"
                + "[[[100.0,0.0,0.0],[101.0,0.0,0.0],[101.0,1.0,0.0],"
                + "[100.0,1.0,0.0],[100.0,0.0,0.0]]]}";
        
        Polygon obj = GeometryBuilder.createPolygon(new Point[] {
            new Point(100.0, 0.0), new Point(101.0, 0.0), new Point(101.0, 1.0),
            new Point(100.0, 1.0), new Point(100.0, 0.0)
        });
        
        String actual = mapper.writeValueAsString(obj);
        JSONAssert.assertEquals(expected, actual, false);
    }
    
    @Test
    public void testSerializeMultiLineString() throws Exception {
        System.out.println("serializeMultiLineString");

        String expected = "{\"type\": \"MultiLineString\",\"coordinates\": "
                + "[[[100.0, 0.0, 0.0], [101.0, 0.0, 0.0], [101.0, 1.0, 0.0], [100.0, 1.0, 0.0], [100.0, 0.0, 0.0]]]}";
        
        MultiLineString obj = new MultiLineString(new LineString[] {
            new LineString(new Point[] {
            new Point(100.0, 0.0), new Point(101.0, 0.0), new Point(101.0, 1.0),
            new Point(100.0, 1.0), new Point(100.0, 0.0)
        })});
        
        String actual = mapper.writeValueAsString(obj);
        JSONAssert.assertEquals(expected, actual, false);
    }
    
    @Test
    public void testSerializeMultiPoint() throws Exception {
        System.out.println("serializeMultiPoint");
        
        String expected = "{\"type\": \"MultiPoint\",\"coordinates\": [ [100.0, 0.0, 0.0], [101.0, 1.0, 0.0] ]}";
        
        MultiPoint obj = new MultiPoint(new Point[] {
            new Point(100.0, 0.0), new Point(101.0, 1.0)
        });
        
        String actual = mapper.writeValueAsString(obj);
        JSONAssert.assertEquals(expected, actual, false);
    }
    
    @Test
    public void testSerializeMultiPolygon() throws Exception {
        System.out.println("serializeMultiPolygon");

        String expected = "{\"type\": \"MultiPolygon\",\"coordinates\": "
                + "[[[[102.0, 2.0, 0.0], [103.0, 2.0, 0.0], [103.0, 3.0, 0.0], [102.0, 3.0, 0.0], [102.0, 2.0, 0.0]]]," 
                + "[[[100.0, 0.0, 0.0], [101.0, 0.0, 0.0], [101.0, 1.0, 0.0], [100.0, 1.0, 0.0], [100.0, 0.0, 0.0]],"
                + "[[100.2, 0.2, 0.0], [100.8, 0.2, 0.0], [100.8, 0.8, 0.0], [100.2, 0.8, 0.0], [100.2, 0.2, 0.0]]]"
                + "]}";
        
        MultiPolygon obj = new MultiPolygon(new Polygon[] {
            new Polygon(new LinearRing[] {
                new LinearRing(new Point[] {
                    new Point(102.0, 2.0), new Point(103.0, 2.0), new Point(103.0, 3.0),
                    new Point(102.0, 3.0), new Point(102.0, 2.0)
                })
            }),
            
            new Polygon(new LinearRing[] {
                new LinearRing(new Point[] {
                    new Point(100.0, 0.0), new Point(101.0, 0.0), new Point(101.0, 1.0),
                    new Point(100.0, 1.0), new Point(100.0, 0.0)
                }),
                new LinearRing(new Point[] {
                    new Point(100.2, 0.2), new Point(100.8, 0.2), new Point(100.8, 0.8),
                    new Point(100.2, 0.8), new Point(100.2, 0.2)
                })
            }),
        });
        
        String actual = mapper.writeValueAsString(obj);
        JSONAssert.assertEquals(expected, actual, false);
    }
    
    @Test
    public void testSerializegGeometryCollection() throws Exception {
        System.out.println("serializeGeometryCollection");
        
        String expected = "{\"type\": \"GeometryCollection\",\"geometries\": ["
                + "{ \"type\": \"Point\", \"coordinates\": [100.0, 0.0, 0.0]},"
                + "{ \"type\": \"LineString\", \"coordinates\": [ [101.0, 0.0, 0.0], [102.0, 1.0, 0.0] ] }"
                + "]}";
        
        GeometryCollection obj = new GeometryCollection(new Geometry[]{
            new Point(100.0, 0.0), new LineString(new Point[] {
                new Point(101.0, 0.0), new Point(102.0, 1.0)
            })
        });
        
        String actual = mapper.writeValueAsString(obj);
        JSONAssert.assertEquals(expected, actual, false);
    }
    
}
