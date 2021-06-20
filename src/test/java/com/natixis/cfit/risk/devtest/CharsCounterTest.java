package com.natixis.cfit.risk.devtest;

import com.natixis.cfit.risk.devtest.test02.CharCounter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CharsCounterTest {

    @Test
    public void test_CountCharacteres(){
        CharCounter counter = new CharCounter();
        int qt = counter.count("Aa121qA28sa");
        Assertions.assertThat(qt).isEqualTo(7);
    }
}
