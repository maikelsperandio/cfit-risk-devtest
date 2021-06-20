package com.natixis.cfit.risk.devtest.test02;

import java.util.Set;
import java.util.stream.Collectors;

public class CharCounter {
    public int count(String str){
        if(str == null){
            return 0;
        };
        Set<Character> collect = str.chars().mapToObj(e -> (char) e).collect(Collectors.toSet());
        return collect.size();
    }
}
