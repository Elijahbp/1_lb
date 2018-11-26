import library.Randomizer;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Parser {
    private String [] basicString;
    private ArrayList<String[]> probabilityMatrix;

    private final static int START_CHAR =0;
    private final static int COUNT_GENERATED_CHAR =1;
    private int typeGeneration;

    private final static int TYPE_GENERATION_LINE = 1;
    private final static int TYPE_GENERATION_BAES = 2;

    private int maxLengthProbability;
    private String nameOutputFile;

    public Parser(String path, String nameOutputFile) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
        this.basicString =  bufferedReader.readLine().split(" ");
        probabilityMatrix = new ArrayList<>();
        String bsrt= " ";
        while (true){
            bsrt=bufferedReader.readLine();
            if (bsrt != null) probabilityMatrix.add(bsrt.split(" "));
            else break;
        }
        if (probabilityMatrix.size() == 1){
            typeGeneration = TYPE_GENERATION_LINE;
        }else if(probabilityMatrix.size()>1){
            typeGeneration = TYPE_GENERATION_BAES;
            if (!countElementsCheck()) throw new Exception("Кол-во элементов строки вероятностей != кол-ву элементов");
        }
        this.nameOutputFile = nameOutputFile;
        parseData();
        convertData();
    }


    private void parseData(){
        maxLengthProbability=0;
        probabilityMatrix.forEach(strings -> {
            for (int i = 0; i < strings.length; i++) {
                if(maxLengthProbability < (strings[i].length()-2)){
                    maxLengthProbability = strings[i].length()-2;
                }
                strings[i] = strings[i].replace("0.","");
            }
        });

    }

    //TODO ВСЕ ПОМЕНЯТЬ!!!
    private void convertData (){
        List<List<Integer>> exData = new ArrayList<>();//лист - ради универсальности

        int startChar = Integer.parseInt(basicString[START_CHAR]);
        int countGeneratedChar = Integer.parseInt(basicString[COUNT_GENERATED_CHAR]);

        StringBuilder bufValue;
        for (int i=0;i<probabilityMatrix.size();i++) {
            Integer[] integers = new Integer[probabilityMatrix.get(i).length];
            for (int j = 0; j < probabilityMatrix.get(i).length; j++) {
                bufValue = new StringBuilder();
                bufValue.append(probabilityMatrix.get(i)[j]);
                for (int k= 0; k < maxLengthProbability-probabilityMatrix.get(i)[j].length(); k++) {
                    bufValue.append("0");
                }
                integers[j] = Integer.parseInt(bufValue.toString());
            }
            if (!convergenceCheck(integers)){
                System.out.println("Сумма вероятностей в строке: "+ (i+1) +" не равна 1");
            }
            exData.add(new ArrayList<>(Arrays.asList(integers)));

        }
        try {
            new Randomizer(startChar,countGeneratedChar, typeGeneration,exData
                    ,erectionInDegree(10,maxLengthProbability),nameOutputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean convergenceCheck(Integer[] bufData){
        int k=0;
        for (int i = 0; i < bufData.length; i++) {
            k+=bufData[i];
        }
        if(erectionInDegree(10,maxLengthProbability) == k) return true;
        else return false;
    }

    private boolean countElementsCheck(){
        for (String[] strings:probabilityMatrix) {
            if(strings.length != probabilityMatrix.size()){
                return false;
            }
        }
        return true;
    }

    private int erectionInDegree(int x, int measure){
        int a=1;
        for (int i = 0; i < measure; i++) {
            a*=x;
        }
        return a;
    }



}
