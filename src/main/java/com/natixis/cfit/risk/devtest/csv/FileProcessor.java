package com.natixis.cfit.risk.devtest.csv;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public class FileProcessor {

    private final Path source;
    //This dependence could be injected with the needed validations
    private List<LineValidator> validations;

    public FileProcessor(Path source, List<LineValidator> validates) {
        this.validations = validates;
        this.source = source;
    }

    public void process() throws IOException {

        FileConsumerFactory fileConsumerFactory = new FileConsumerFactory();
        try (Stream<String> stream = Files.lines(source)) {
            stream.forEach(line -> {
                //One option is to add some logic around this part of the code to skip the lines based on the
                //requirements, but feel free to add the logic wherever you think it the best location
                for(LineValidator rule : validations){
                    if(!rule.isValid(line)){
                        continue;
                    }
                    FileConsumer fileConsumer = fileConsumerFactory.createFileConsumer(line);
                    fileConsumer.consume(line);
                }
            });
        }
    }

    static class FileConsumerFactory {
        FileConsumer createFileConsumer(String line) {
            //Some logic here to create a valid file consumer.
            //For this example it only return a Default File Consumer
            //You don’t need to care about this method.
            return new DefaultFileConsumer();
        }
    }

    interface FileConsumer {
        void consume(String line);
    }

    static class DefaultFileConsumer implements FileConsumer {

        @Override
        public void consume(String line) {
            //Some code is done here, but it is not important for this exercise
            System.out.println(line);
        }
    }
}
