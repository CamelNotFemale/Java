import au.com.bytecode.opencsv.CSVReader;

import au.com.bytecode.opencsv.CSVReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Parser {
    private CSVReader reader;

    public Parser() throws FileNotFoundException {
        reader = new CSVReader(new FileReader("src/main/resources/weather.csv"), ';' , '"' , 7);
    }
    public String[] getNextLine() throws IOException {
        String[] nextLine = reader.readNext();
        return nextLine;
    }
}
