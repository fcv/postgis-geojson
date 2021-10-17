package net.postgis.geojson.deserializers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import net.postgis.jdbc.geometry.Geometry;
import net.postgis.jdbc.geometry.GeometryCollection;
import net.postgis.jdbc.geometry.LineString;
import net.postgis.jdbc.geometry.MultiLineString;
import net.postgis.jdbc.geometry.MultiPoint;
import net.postgis.jdbc.geometry.MultiPolygon;
import net.postgis.jdbc.geometry.Point;
import net.postgis.jdbc.geometry.Polygon;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 *
 * @author mayconbordin
 */
public class GeometryDeserializerTest {
    protected ObjectMapper mapper;
    
    @Before
    public void setUp() {
        mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule("MyModule");
        module.addDeserializer(Geometry.class, new GeometryDeserializer());
        mapper.registerModule(module);
    }
    
    @Test
    public void testDeserialize2DPoint() throws Exception {
        String json = "{\"type\": \"Point\",\"coordinates\": [125.6, 10.1]}";
        
        Point p = (Point) mapper.readValue(json, Geometry.class);
        
        assertNotNull(p);
        assertEquals(125.6, p.getX(), 0);
        assertEquals(10.1, p.getY(), 0);
        assertEquals(0.0, p.getZ(), 0);
        assertEquals(2, p.dimension);
    }

    @Test
    public void testDeserialize3DPoint() throws Exception {
        String json = "{\"type\": \"Point\",\"coordinates\": [125.6, 10.1, 2.4]}";

        Point p = (Point) mapper.readValue(json, Geometry.class);

        assertNotNull(p);
        assertEquals(125.6, p.getX(), 0);
        assertEquals(10.1, p.getY(), 0);
        assertEquals(2.4, p.getZ(), 0);
        assertEquals(3, p.dimension);
    }
    
    @Test
    public void testDeserializeLineString() throws Exception {
        String json = "{\"type\": \"LineString\",\"coordinates\": [ [100.0, 0.0], [101.0, 1.0] ]}";
        
        LineString p = (LineString) mapper.readValue(json, Geometry.class);
        
        assertNotNull(p);
        assertEquals(2, p.numPoints());
        assertEquals(100.0, p.getPoint(0).getX(), 0.0);
        assertEquals(0.0, p.getPoint(0).getY(), 0.0);
        assertEquals(101.0, p.getPoint(1).getX(), 0.0);
        assertEquals(1.0, p.getPoint(1).getY(), 0.0);
    }
    
    @Test
    public void testDeserializePolygon() throws Exception {
        String json = "{\"type\": \"Polygon\",\"coordinates\": "
                + "[[[100.0, 0.0], [101.0, 0.0], [101.0, 1.0], [100.0, 1.0], [100.0, 0.0]]]}";
        
        Polygon p = (Polygon) mapper.readValue(json, Geometry.class);
        
        assertNotNull(p);
        assertEquals(1, p.numRings());
        assertEquals(5, p.numPoints());
        
        assertEquals(100.0, p.getRing(0).getPoint(0).getX(), 0.0);
        assertEquals(0.0, p.getRing(0).getPoint(0).getY(), 0.0);
        
        assertEquals(101.0, p.getRing(0).getPoint(1).getX(), 0.0);
        assertEquals(0.0, p.getRing(0).getPoint(1).getY(), 0.0);
        
        assertEquals(101.0, p.getRing(0).getPoint(2).getX(), 0.0);
        assertEquals(1.0, p.getRing(0).getPoint(2).getY(), 0.0);
        
        assertEquals(100.0, p.getRing(0).getPoint(3).getX(), 0.0);
        assertEquals(1.0, p.getRing(0).getPoint(3).getY(), 0.0);
        
        assertEquals(100.0, p.getRing(0).getPoint(4).getX(), 0.0);
        assertEquals(0.0, p.getRing(0).getPoint(4).getY(), 0.0);
    }
    
    @Test
    public void testDeserializeMultiLineString() throws Exception {
        String json = "{\"type\": \"MultiLineString\",\"coordinates\": "
                + "[[[100.0, 0.0], [101.0, 0.0], [101.0, 1.0], [100.0, 1.0], [100.0, 0.0]]]}";
        
        MultiLineString p = (MultiLineString) mapper.readValue(json, Geometry.class);
        
        assertNotNull(p);
        assertEquals(1, p.numLines());
        assertEquals(5, p.numPoints());
        
        assertEquals(100.0, p.getLine(0).getPoint(0).getX(), 0.0);
        assertEquals(0.0, p.getLine(0).getPoint(0).getY(), 0.0);
        
        assertEquals(101.0, p.getLine(0).getPoint(1).getX(), 0.0);
        assertEquals(0.0, p.getLine(0).getPoint(1).getY(), 0.0);
        
        assertEquals(101.0, p.getLine(0).getPoint(2).getX(), 0.0);
        assertEquals(1.0, p.getLine(0).getPoint(2).getY(), 0.0);
        
        assertEquals(100.0, p.getLine(0).getPoint(3).getX(), 0.0);
        assertEquals(1.0, p.getLine(0).getPoint(3).getY(), 0.0);
        
        assertEquals(100.0, p.getLine(0).getPoint(4).getX(), 0.0);
        assertEquals(0.0, p.getLine(0).getPoint(4).getY(), 0.0);
    }
    
    @Test
    public void testDeserializeMultiPoint() throws Exception {
        String json = "{\"type\": \"MultiPoint\",\"coordinates\": [ [100.0, 0.0], [101.0, 1.0] ]}";
        
        MultiPoint p = (MultiPoint) mapper.readValue(json, Geometry.class);
        
        assertNotNull(p);
        assertEquals(2, p.numPoints());
        assertEquals(100.0, p.getPoint(0).getX(), 0.0);
        assertEquals(0.0, p.getPoint(0).getY(), 0.0);
        assertEquals(101.0, p.getPoint(1).getX(), 0.0);
        assertEquals(1.0, p.getPoint(1).getY(), 0.0);
    }
    
    @Test
    public void testDeserializeMultiPolygon() throws Exception {
        String json = "{\"type\": \"MultiPolygon\",\"coordinates\": "
                + "[[[[102.0, 2.0], [103.0, 2.0], [103.0, 3.0], [102.0, 3.0], [102.0, 2.0]]]," 
                + "[[[100.0, 0.0], [101.0, 0.0], [101.0, 1.0], [100.0, 1.0], [100.0, 0.0]],"
                + "[[100.2, 0.2], [100.8, 0.2], [100.8, 0.8], [100.2, 0.8], [100.2, 0.2]]]"
                + "]}";
        
        MultiPolygon p = (MultiPolygon) mapper.readValue(json, Geometry.class);
        
        assertNotNull(p);
        assertEquals(2, p.numPolygons());
        assertEquals(1, p.getPolygon(0).numRings());
        assertEquals(2, p.getPolygon(1).numRings());
        assertEquals(15, p.numPoints());
        
        assertEquals(102.0, p.getPolygon(0).getRing(0).getPoint(0).getX(), 0.0);
        assertEquals(2.0, p.getPolygon(0).getRing(0).getPoint(0).getY(), 0.0);
        
        assertEquals(103.0, p.getPolygon(0).getRing(0).getPoint(1).getX(), 0.0);
        assertEquals(2.0, p.getPolygon(0).getRing(0).getPoint(1).getY(), 0.0);
        
        assertEquals(103.0, p.getPolygon(0).getRing(0).getPoint(2).getX(), 0.0);
        assertEquals(3.0, p.getPolygon(0).getRing(0).getPoint(2).getY(), 0.0);
        
        assertEquals(102.0, p.getPolygon(0).getRing(0).getPoint(3).getX(), 0.0);
        assertEquals(3.0, p.getPolygon(0).getRing(0).getPoint(3).getY(), 0.0);
        
        assertEquals(102.0, p.getPolygon(0).getRing(0).getPoint(4).getX(), 0.0);
        assertEquals(2.0, p.getPolygon(0).getRing(0).getPoint(4).getY(), 0.0);
    }
    
    @Test
    public void testDeserializegGeometryCollection() throws Exception {
        String json = "{\"type\": \"GeometryCollection\",\"geometries\": ["
                + "{ \"type\": \"Point\", \"coordinates\": [100.0, 0.0]},"
                + "{ \"type\": \"LineString\", \"coordinates\": [ [101.0, 0.0], [102.0, 1.0] ] }"
                + "]}";
        
        GeometryCollection p = (GeometryCollection) mapper.readValue(json, Geometry.class);
        
        assertNotNull(p);
        assertEquals(2, p.numGeoms());
        
        assertEquals("Point", p.getGeometries()[0].getClass().getSimpleName());
        assertEquals(100.0, ((Point)p.getGeometries()[0]).getX(), 0);
        assertEquals(0.0, ((Point)p.getGeometries()[0]).getY(), 0);
        
        assertEquals("LineString", p.getGeometries()[1].getClass().getSimpleName());
        assertEquals(101.0, ((LineString)p.getGeometries()[1]).getPoint(0).getX(), 0);
        assertEquals(0.0, ((LineString)p.getGeometries()[1]).getPoint(0).getY(), 0);
        assertEquals(102.0, ((LineString)p.getGeometries()[1]).getPoint(1).getX(), 0);
        assertEquals(1.0, ((LineString)p.getGeometries()[1]).getPoint(1).getY(), 0);
    }
    
}
