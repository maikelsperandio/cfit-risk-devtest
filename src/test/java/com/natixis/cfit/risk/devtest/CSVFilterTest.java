package com.natixis.cfit.risk.devtest;

import com.natixis.cfit.risk.devtest.csv.FileProcessor;
import com.natixis.cfit.risk.devtest.csv.LineContentValidation;
import com.natixis.cfit.risk.devtest.csv.LineValidator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class CSVFilterTest {

    @Test
    public void test_FileWithInvalidDataWillNotBeProcessed(){
        try {
            Path source = Path.of("src/test/resources/source.csv");
            Path invalids = Path.of("src/test/resources/invalid_data.csv");

            LineValidator lineValidator = new LineContentValidation(invalids);
            FileProcessor processor = new FileProcessor(source, List.of(lineValidator));

            processor.process();
        }catch (Exception e){
            Assertions.fail("The test has been failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void test_LinesWithNoInvalidDataWouldBeProcessed(){
        try{
            Path invalids = Path.of("src/test/resources/invalid_data.csv");

            LineValidator lineValidator = new LineContentValidation(invalids);
            List<String> lines = List.of(
                    "9004A;Lorem ipsum dolor;A86;1000.125547;8524;-1411.1;Lorem ipsum",
                    "9004A;Lorem ipsum dolor;C88;1000.125547;8524;-1411.1;Lorem ipsum",
                    "9004A;Lorem ipsum dolor;D98;1000.125547;8524;-1411.1;Lorem ipsum",
                    "9004A;Lorem ipsum dolor;N28;1000.125547;8524;-1411.1;Lorem ipsum");
            List<String> validLines = new ArrayList<>();
            lines.forEach(line -> {
                if(lineValidator.isValid(line))
                    validLines.add(line);
            });

            Assertions.assertThat(validLines).isNotEmpty();
            Assertions.assertThat(validLines.size()).isEqualTo(4);
        }catch (Exception e){
            Assertions.fail("The test has been failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
