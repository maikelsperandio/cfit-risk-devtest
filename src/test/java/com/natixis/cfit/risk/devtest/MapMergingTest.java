package com.natixis.cfit.risk.devtest;

import com.natixis.cfit.risk.devtest.test01.MergeMaps;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class MapMergingTest {

    @Test
    public void test_MergeTwoMaps(){
        try{
            Map<String, Long> map1 = new HashMap<>();
            map1.put("A", 1l);
            map1.put("B", 1l);

            Map<String, Long> map2 = new HashMap<>();
            map2.put("A", 1l);
            map2.put("B", 1000l);
            map2.put("C", 1000l);

            Map<String, Long> mergedMap = new MergeMaps().merge(map1, map2);

            Assertions.assertThat(mergedMap.size()).isEqualTo(3);
            Assertions.assertThat(mergedMap.get("A")).isEqualTo(2);
            Assertions.assertThat(mergedMap.get("C")).isEqualTo(1000);
        }catch (Exception e){
            Assertions.fail("The test has been failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
