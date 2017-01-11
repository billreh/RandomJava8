package net.tralfamadore;

import java.util.*;

/**
 * Class: MapUtils
 * Created by billreh on 1/11/17.
 */
public class MapUtils<K,V> {
    public Map<K,Set<V>> findCommonKeys(Map<K,V> map1, Map<K,V> ... maps) {
        Map<K,Set<V>> ret = new HashMap<>();
        Map<K,V> master = new HashMap<>();
        master.putAll(map1);
        for(Map<K,V> map : maps) {
            for(K key : map.keySet()) {
                if(master.containsKey(key)) {
                    if(!ret.containsKey(key))
                        ret.put(key, new HashSet<>());
                    ret.get(key).add(map.get(key));
                    ret.get(key).add(master.get(key));
                }
                master.put(key, map.get(key));
            }
        }
        return ret;
    }
}
