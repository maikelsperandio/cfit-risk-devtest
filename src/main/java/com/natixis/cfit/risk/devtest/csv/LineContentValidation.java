package com.natixis.cfit.risk.devtest.csv;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LineContentValidation implements LineValidator {
    private List<String> invalidLines;

    public LineContentValidation(Path file){
        try (Stream<String> stream = Files.lines(file)) {
            this.invalidLines = stream.collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isValid(String line) {
        String[] columns = line.split(";");
        if(columns.length >= 3){
            String column = columns[2];
            for (String invalidLine: invalidLines) {
                if(column.equals(invalidLine))
                    return false;
            }
        }
        return true;
    }
}
