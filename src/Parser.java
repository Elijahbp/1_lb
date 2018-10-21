import library.Randomizer;

import java.awt.*;
import java.io.*;
import java.lang.reflect.Array;
import java.math.BigInteger;
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
        parseData();
        convertData();
    }

    //ArrayList<Double>
    private void convertData (){

        int startChar = Integer.parseInt(basicString[0]); //
        int countGeneratedChar = Integer.parseInt(basicString[1]); //TODO Сделать правильное определение
        ArrayList<Double> probabilityData = new ArrayList<>();
        Stream.of(probabilityString).forEach(s ->probabilityData.add(Double.parseDouble(s)*100));
        System.out.println(probabilityData.get(0).doubleValue());
        new Randomizer(startChar,countGeneratedChar,probabilityData);
    }
    private void parseData(){
        int maxLengthProbability=0;
        int[] exData = new int[probabilityString.length];
        for (int i = 0; i < probabilityString.length; i++) {
            if(maxLengthProbability < (probabilityString[i].length()-2)){
                maxLengthProbability = probabilityString[i].length()-2;
            }
            probabilityString[i] = probabilityString[i].replace("0.","");
        }

        for (int i = 0; i < probabilityString.length; i++) {
            probabilityString[i].concat("");


            exData[i] = exData[i].pow(maxLengthProbability);
            System.out.println(exData[i]);
        }
        System.out.println(maxLengthProbability);
    }
}
