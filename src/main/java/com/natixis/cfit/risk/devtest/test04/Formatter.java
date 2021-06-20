package com.natixis.cfit.risk.devtest.test04;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


/**
 * Class responsible for formatting Row objects and write the output to a given output
 */
public class Formatter {

    private final Collection<Element> entries;
    private final Collection<Row> rows;

    public static void main(String[] args) throws IOException {
        long init = System.currentTimeMillis();
        //Sample Data preparation (Random data just for the sake of simplicity for this exercise)
        Random r = new Random();
        final Collection<Row> rows = IntStream.range(1, 235_000)
                .mapToObj(value -> new Row(r.nextInt(235_000))).collect(Collectors.toSet());

        final Collection<Element> elementList = IntStream.range(1, 1_000_000)
                .mapToObj(value -> new Element(r.nextInt(235_000))).collect(Collectors.toSet());

        //Sample use case. For the sake of simplicity in this exercise, it is initialized in the main method
        final Formatter formatter = new Formatter(elementList, rows);
        Collection<FormatterContent> result = formatter.makeIntersections();

        try (Writer writer = new PrintWriter(System.out)) {
            formatter.execute(writer, result);
        }
    }

    public Formatter(Collection<Element> entries, Collection<Row> rows) {
        this.entries = entries;
        this.rows = rows;
    }

    public Collection<FormatterContent> makeIntersections(){
        CopyOnWriteArraySet<Row> rowSet = new CopyOnWriteArraySet(rows);
        Collection<FormatterContent> result = new HashSet<>();
        entries.parallelStream().forEach(entry -> {
            rowSet.parallelStream().forEach(rw -> {
                if(rw.getId() == entry.getId()){
                    result.add(new FormatterContent(entry, rw));
                    rowSet.remove(rw);
                }
            });
        });
        return result;
    }

    public void execute(Writer writer, Collection<FormatterContent> result) throws IOException {
        result.forEach(res -> {
            try {
                makeFlow(res, writer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
//        for (Row row : this.rows) {
//            makeFlow(row, writer);
//        }
    }

    /**
     * <p>
     * <p>
     * Method that formats lines based on the content of the given {@code Row} and based on the content of the given
     * <p>
     * list of {@code Element}. The line is formatted as follow:
     *
     * </p>
     *
     * <p>
     * <p>
     * For each {@code Element id} that matches a {@code Row id}, a new line is written to the output. Each line is
     * <p>
     * separated by a ';'
     *
     * </p>
     */
    //void makeFlow(Row row, Writer output) throws IOException {
    void makeFlow(FormatterContent content, Writer output) throws IOException {
        //Extracts only the elements which id matches the row.getId()
//        final Collection<Element> matchingEntries = this.entries.parallelStream()
//                .filter(entry -> entry.getId() == row.getId()).collect(Collectors.toSet());
        Row row = content.getRow();
        Element element = content.getElement();

//        for (Element element : matchingEntries) {
            output.write(row.getId());
            output.write(";");
            output.write(element.getId());
            output.write(";");
            output.write(row.getContent());
            output.write(";");
            output.write(element.getContent());
            output.write(System.lineSeparator());
//        }

        //This call must remains here as it is
        ExternalClass.execute(this.entries, row);
    }


    static class Row {
        private final int id;
        private final String content;

        public Row(int value) {
            this.id = value;
            this.content = "Row " + value;
        }

        public int getId() {
            return id;
        }

        public String getContent() {
            return content;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            Row element = (Row) o;
            return id == element.id && Objects.equals(content, element.content);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, content);
        }
    }

    static class Element {
        private final int id;
        private final String content;

        public Element(int value) {
            this.id = value;
            this.content = "Data " + value;
        }

        public int getId() {
            return id;
        }

        public String getContent() {
            return content;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            Element element = (Element) o;
            return id == element.id && Objects.equals(content, element.content);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, content);
        }
    }

    static class ExternalClass {
        public static void execute(Collection<Element> elementList, Row row) {
            //do something that does not matter to the context of this exercise
        }
    }
}

class FormatterContent{
    private Formatter.Element element;
    private Formatter.Row row;

    public FormatterContent(Formatter.Element ele, Formatter.Row row){
        this.element = ele;
        this.row = row;
    }

    public Formatter.Element getElement(){
        return this.element;
    }

    public Formatter.Row getRow(){
        return this.row;
    }
}