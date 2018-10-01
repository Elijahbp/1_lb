import library.Randomizer;

import java.awt.*;
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Parser {
    private String [] basicString;
    private String [] probabilityString;


    public Parser(String path) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
        this.basicString =  bufferedReader.readLine().split(" ");
        this.probabilityString = bufferedReader.readLine().split(" ");
        Stream.of(basicString).forEach(System.out::println);
        Stream.of(probabilityString).forEach(System.out::println);

        convertData();
//        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append(bufferedReader.readLine()).append("\n");
//        stringBuilder.append(bufferedReader.readLine()).append("\n");
//        System.out.println(stringBuilder.toString());
    }

    //ArrayList<Double>
    private void convertData (){

        int startChar = Integer.parseInt(basicString[0]);
        int countGeneratedChar = Integer.parseInt(basicString[1]);
        ArrayList<Double> probabilityData = new ArrayList<>();
        Stream.of(probabilityString).forEach(s -> probabilityData.add(Double.parseDouble(s)*100));
        new Randomizer(startChar,countGeneratedChar,probabilityData);
    }
}
