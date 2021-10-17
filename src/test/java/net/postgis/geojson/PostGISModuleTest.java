package net.postgis.geojson;

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
 * @author fcv
 */
public class PostGISModuleTest {

    protected ObjectMapper mapper;

    @Before
    public void setUp() {
        mapper = new ObjectMapper();
        SimpleModule module = new PostGISModule();
        mapper.registerModule(module);
    }

    @Test
    public void testDeserializeNestedPoint() throws Exception {
        String json = "{\"point\": {\"type\": \"Point\",\"coordinates\": [125.6, 10.1, 2.4]}}";

        NestedPoint bean = mapper.readValue(json, NestedPoint.class);

        assertEquals(new Point(125.6, 10.1, 2.4), bean.point);
    }

    static class NestedPoint {
        private Point point;
        public Point getPoint() {
            return point;
        }
        public void setPoint(Point point) {
            this.point = point;
        }
    }

    @Test
    public void testDeserializeNestedPointAsGeometry() throws Exception {
        String json = "{\"point\": {\"type\": \"Point\",\"coordinates\": [125.6, 10.1, 2.4]}}";

        NestedPointAsGeometry bean = mapper.readValue(json, NestedPointAsGeometry.class);

        assertEquals(new Point(125.6, 10.1, 2.4), bean.point);
    }

    static class NestedPointAsGeometry {
        private Geometry point;
        public Geometry getPoint() {
            return point;
        }
        public void setPoint(Geometry point) {
            this.point = point;
        }
    }

    @Test
    public void testDeserializeNestedLineString() throws Exception {
        String json = "{\"lineString\": {\"point\": {\"type\": \"Point\",\"coordinates\": [125.6, 10.1, 2.4]}}}";

        NestedLineString bean = mapper.readValue(json, NestedLineString.class);

        LineString ls = bean.lineString;

        assertNotNull(ls);
        assertEquals(2, ls.numPoints());
        assertEquals(100.0, ls.getPoint(0).getX(), 0.0);
        assertEquals(0.0, ls.getPoint(0).getY(), 0.0);
        assertEquals(101.0, ls.getPoint(1).getX(), 0.0);
        assertEquals(1.0, ls.getPoint(1).getY(), 0.0);
    }

    static class NestedLineString {
        private LineString lineString;
        public LineString getLineString() {
            return lineString;
        }
        public void setLineString(LineString lineString) {
            this.lineString = lineString;
        }
    }

    @Test
    public void testDeserializeNestedPolygon() throws Exception {
        String json = "{\"polygon\": {\"type\": \"Polygon\",\"coordinates\": "
                + "[[[100.0, 0.0], [101.0, 0.0], [101.0, 1.0], [100.0, 1.0], [100.0, 0.0]]]}}";

        NestedPolygon bean = mapper.readValue(json, NestedPolygon.class);

        Polygon p = bean.polygon;

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

    static class NestedPolygon {
        private Polygon polygon;
        public Polygon getPolygon() {
            return polygon;
        }
        public void setPolygon(Polygon polygon) {
            this.polygon = polygon;
        }
    }

    @Test
    public void testDeserializeNestedMultiLineString() throws Exception {
        String json = "{\"multiLineString\": {\"type\": \"MultiLineString\",\"coordinates\": "
                + "[[[100.0, 0.0], [101.0, 0.0], [101.0, 1.0], [100.0, 1.0], [100.0, 0.0]]]}}";
        NestedMultiLineString bean = mapper.readValue(json, NestedMultiLineString.class);

        MultiLineString mls = bean.multiLineString;

        assertNotNull(mls);
        assertEquals(1, mls.numLines());
        assertEquals(5, mls.numPoints());

        assertEquals(100.0, mls.getLine(0).getPoint(0).getX(), 0.0);
        assertEquals(0.0, mls.getLine(0).getPoint(0).getY(), 0.0);

        assertEquals(101.0, mls.getLine(0).getPoint(1).getX(), 0.0);
        assertEquals(0.0, mls.getLine(0).getPoint(1).getY(), 0.0);

        assertEquals(101.0, mls.getLine(0).getPoint(2).getX(), 0.0);
        assertEquals(1.0, mls.getLine(0).getPoint(2).getY(), 0.0);

        assertEquals(100.0, mls.getLine(0).getPoint(3).getX(), 0.0);
        assertEquals(1.0, mls.getLine(0).getPoint(3).getY(), 0.0);

        assertEquals(100.0, mls.getLine(0).getPoint(4).getX(), 0.0);
        assertEquals(0.0, mls.getLine(0).getPoint(4).getY(), 0.0);
    }

    static class NestedMultiLineString {
        private MultiLineString multiLineString;
        public MultiLineString getMultiLineString() {
            return multiLineString;
        }
        public void setMultiLineString(MultiLineString multiLineString) {
            this.multiLineString = multiLineString;
        }
    }

    @Test
    public void testDeserializeNestedMultiPoint() throws Exception {
        String json = "{\"multiPoint\": {\"type\": \"MultiPoint\",\"coordinates\": [ [100.0, 0.0], [101.0, 1.0] ]}}";
        NestedMultiPoint bean = mapper.readValue(json, NestedMultiPoint.class);

        MultiPoint mp = bean.multiPoint;

        assertNotNull(mp);
        assertEquals(2, mp.numPoints());
        assertEquals(100.0, mp.getPoint(0).getX(), 0.0);
        assertEquals(0.0, mp.getPoint(0).getY(), 0.0);
        assertEquals(101.0, mp.getPoint(1).getX(), 0.0);
        assertEquals(1.0, mp.getPoint(1).getY(), 0.0);
    }

    static class NestedMultiPoint {
        private MultiPoint multiPoint;
        public MultiPoint getMultiPoint() {
            return multiPoint;
        }
        public void setMultiPoint(MultiPoint multiPoint) {
            this.multiPoint = multiPoint;
        }
    }

    @Test
    public void testDeserializeNestedMultiPolygon() throws Exception {
        String json = "{\"multiPolygon\": {\"type\": \"MultiPolygon\",\"coordinates\": "
                + "[[[[102.0, 2.0], [103.0, 2.0], [103.0, 3.0], [102.0, 3.0], [102.0, 2.0]]],"
                + "[[[100.0, 0.0], [101.0, 0.0], [101.0, 1.0], [100.0, 1.0], [100.0, 0.0]],"
                + "[[100.2, 0.2], [100.8, 0.2], [100.8, 0.8], [100.2, 0.8], [100.2, 0.2]]]"
                + "]}}";
        NestedMultiPolygon bean = mapper.readValue(json, NestedMultiPolygon.class);

        MultiPolygon mp = bean.multiPolygon;

        assertNotNull(mp);
        assertEquals(2, mp.numPolygons());
        assertEquals(1, mp.getPolygon(0).numRings());
        assertEquals(2, mp.getPolygon(1).numRings());
        assertEquals(15, mp.numPoints());

        assertEquals(102.0, mp.getPolygon(0).getRing(0).getPoint(0).getX(), 0.0);
        assertEquals(2.0, mp.getPolygon(0).getRing(0).getPoint(0).getY(), 0.0);

        assertEquals(103.0, mp.getPolygon(0).getRing(0).getPoint(1).getX(), 0.0);
        assertEquals(2.0, mp.getPolygon(0).getRing(0).getPoint(1).getY(), 0.0);

        assertEquals(103.0, mp.getPolygon(0).getRing(0).getPoint(2).getX(), 0.0);
        assertEquals(3.0, mp.getPolygon(0).getRing(0).getPoint(2).getY(), 0.0);

        assertEquals(102.0, mp.getPolygon(0).getRing(0).getPoint(3).getX(), 0.0);
        assertEquals(3.0, mp.getPolygon(0).getRing(0).getPoint(3).getY(), 0.0);

        assertEquals(102.0, mp.getPolygon(0).getRing(0).getPoint(4).getX(), 0.0);
        assertEquals(2.0, mp.getPolygon(0).getRing(0).getPoint(4).getY(), 0.0);
    }

    static class NestedMultiPolygon {
        private MultiPolygon multiPolygon;
        public MultiPolygon getMultiPolygon() {
            return multiPolygon;
        }
        public void setMultiPolygon(MultiPolygon multiPolygon) {
            this.multiPolygon = multiPolygon;
        }
    }

    @Test
    public void testDeserializeNestedGeometryCollection() throws Exception {
        String json = "{\"geometryCollection\": \"type\": \"GeometryCollection\",\"geometries\": ["
                + "{ \"type\": \"Point\", \"coordinates\": [100.0, 0.0]},"
                + "{ \"type\": \"LineString\", \"coordinates\": [ [101.0, 0.0], [102.0, 1.0] ] }"
                + "]}";
        NestedGeometryCollection bean = mapper.readValue(json, NestedGeometryCollection.class);

        GeometryCollection gc = bean.geometryCollection;

        assertNotNull(gc);
        assertEquals(2, gc.numGeoms());

        assertEquals("Point", gc.getGeometries()[0].getClass().getSimpleName());
        assertEquals(100.0, ((Point)gc.getGeometries()[0]).getX(), 0);
        assertEquals(0.0, ((Point)gc.getGeometries()[0]).getY(), 0);

        assertEquals("LineString", gc.getGeometries()[1].getClass().getSimpleName());
        assertEquals(101.0, (gc.getGeometries()[1]).getPoint(0).getX(), 0);
        assertEquals(0.0, (gc.getGeometries()[1]).getPoint(0).getY(), 0);
        assertEquals(102.0, (gc.getGeometries()[1]).getPoint(1).getX(), 0);
        assertEquals(1.0, (gc.getGeometries()[1]).getPoint(1).getY(), 0);
    }

    static class NestedGeometryCollection {
        private GeometryCollection geometryCollection;
        public GeometryCollection getGeometryCollection() {
            return geometryCollection;
        }
        public void setGeometryCollection(GeometryCollection geometryCollection) {
            this.geometryCollection = geometryCollection;
        }
    }
}
