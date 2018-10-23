import library.Randomizer;

import java.awt.*;
import java.io.*;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Parser {
    private String [] basicString;
    private String [] probabilityString;
    private int maxLengthProbability;

    public Parser(String path) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
        this.basicString =  bufferedReader.readLine().split(" ");
        this.probabilityString = bufferedReader.readLine().split(" ");
        Stream.of(basicString).forEach(System.out::println);
        Stream.of(probabilityString).forEach(System.out::println);
        parseData();
        convertData();
    }


    private void parseData(){
        maxLengthProbability=0;

        for (int i = 0; i < probabilityString.length; i++) {
            if(maxLengthProbability < (probabilityString[i].length()-2)){
                maxLengthProbability = probabilityString[i].length()-2;
            }
            probabilityString[i] = probabilityString[i].replace("0.","");
        }



    }
    private void convertData (){
        ArrayList<Integer> exData = new ArrayList<>();
        int startChar = Integer.parseInt(basicString[0]); //
        int countGeneratedChar = Integer.parseInt(basicString[1]); //TODO Сделать правильное определение

        StringBuilder bufValue;
        for (int i = 0; i < probabilityString.length; i++) {
            bufValue = new StringBuilder();
            bufValue.append(probabilityString[i]);
            for (int j = 0; j < maxLengthProbability-probabilityString[i].length(); j++) {
                bufValue.append("0");
            }
            exData.add(Integer.parseInt(bufValue.toString()));
        }
        if (convergenceCheck(exData)){
            new Randomizer(startChar,countGeneratedChar,exData,erectionInDegree(10,maxLengthProbability));
        }else {
            System.out.println("Сумма вероятностей не равно ....");
        }
    }

    private boolean convergenceCheck(ArrayList<Integer> exData){
        if(erectionInDegree(10,maxLengthProbability) == exData.stream().mapToInt(Integer::shortValue).sum())
            return true;
        else return false;
    }


    private int erectionInDegree(int x, int measure){
        int a=1;
        for (int i = 0; i < measure; i++) {
            a*=x;
        }
        return a;
    }

}
