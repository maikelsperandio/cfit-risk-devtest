package com.natixis.cfit.risk.devtest.test01;

import java.util.HashMap;
import java.util.Map;

public class MergeMaps {

    public Map<String, Long> merge(Map<String, Long> map1, Map<String, Long> map2){
        Map<String, Long> merged = new HashMap<>();
        merged.putAll(map1);

        map2.forEach( (k, v) -> {
            Long value = merged.remove(k);
            if(value != null){
                merged.put(k, v + value);
            }else{
                merged.put(k, v);
            }
        });

        return merged;
    }
}
