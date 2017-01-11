package net.tralfamadore;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertTrue;

/**
 * Class: TestMapUtils
 * Created by billreh on 1/11/17.
 */
public class TestMapUtils {
    @Test
    public void testMapUtils() {
        Map<String,String> map1 = new HashMap<>();
        Map<String,String> map2 = new HashMap<>();
        Map<String,String> map3 = new HashMap<>();

        map1.put("Key1", "Value1");
        map1.put("Key2", "Value2");
        map1.put("Key3", "Value3");

        map2.put("Key3", "Value4");
        map2.put("Key4", "Value5");
        map2.put("Key5", "Value6");

        map3.put("Key5", "Value7");
        map3.put("Key6", "Value8");
        map3.put("Key1", "Value9");

        MapUtils<String,String> mapUtils = new MapUtils<>();
        //noinspection unchecked
        Map<String,Set<String>> result = mapUtils.findCommonKeys(map1, map2, map3);

        assertTrue(result.containsKey("Key1"));
        assertTrue(result.get("Key1").contains("Value1"));
        assertTrue(result.get("Key1").contains("Value9"));

        assertTrue(result.containsKey("Key3"));
        assertTrue(result.get("Key3").contains("Value3"));
        assertTrue(result.get("Key3").contains("Value4"));

        assertTrue(result.containsKey("Key5"));
        assertTrue(result.get("Key5").contains("Value6"));
        assertTrue(result.get("Key5").contains("Value7"));
    }
}
