package library;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Randomizer {
    private final static int TYPE_GENERATION_LINE = 1;
    private final static int TYPE_GENERATION_BAES = 2;


    int startChar;
    int countChar;
    int typeGeneration;
    int countGeneratedChar;
    int order;


    List<List<Integer>> listProbability;
    List<Character> listSymbols;
    List<Character> listGeneratedSymbols;

    HashMap<Character,Integer> hashMapForLinearType;
    ListSymbols_BaesL hashMapforBaesType;

    public Randomizer(int startChar, int countGeneratedChar,int typeGeneration, List<List<Integer>> listProbability
            ,int order, String nameFile) throws IOException {
        this.startChar = startChar;
        this.listProbability = listProbability;
        this.countChar = listProbability.size();
        this.countGeneratedChar = countGeneratedChar;
        this.typeGeneration = typeGeneration;
        this.order = order;

        listSymbols = new ArrayList<>();
        listGeneratedSymbols = new ArrayList<>();

        Integer stB= startChar;

        for (int j = 0; j < listProbability.get(0).size(); j++) {
            listSymbols.add((char) Integer.parseInt(stB.toString(),16));
            stB++;
        }
        if (typeGeneration == TYPE_GENERATION_LINE){
            generateBorderForLinear();
        }else if (typeGeneration == TYPE_GENERATION_BAES){
            hashMapforBaesType = new ListSymbols_BaesL(listSymbols);
            hashMapforBaesType.setValues(this.listProbability);
        }
        listSymbols.forEach(System.out::println);
        generateSymbols();
        System.out.println(statistic());
        outputFile(nameFile);
    }

    private void generateBorderForLinear(){
        hashMapForLinearType = new HashMap<>();
        int border = 0;
        for (int i = 0; i < listProbability.get(0).size(); i++) {
            border+=listProbability.get(0).get(i);
            hashMapForLinearType.put(listSymbols.get(i),border);
        }
    }


    private void outputFile(String nameFile) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(nameFile));
        listGeneratedSymbols.stream().forEach(character -> {
            try {
                writer.write(character+'\n');

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        writer.newLine();
        writer.write(statistic());
        writer.close();
    }


    private void generateSymbols(){
        Random random = new Random();
        if(typeGeneration == TYPE_GENERATION_LINE) {
            for (int i = 0; i < this.countGeneratedChar; i++) {
                int pr = random.nextInt(order);
                listGeneratedSymbols.add(hashMapForLinearType.entrySet().stream()
                        .filter(n -> (n.getValue() >= pr)).findAny().get().getKey());
            }
        }else if (typeGeneration == TYPE_GENERATION_BAES){
            //Т.к. матрицы подаваемые на вход являются квадратными, то для решения СЛАУ им будет добавляться столбец из нулей
            for (int i = 0; i < this.countGeneratedChar; i++) {
                int pr = random.nextInt(order);
                if (i == 0) {
                    //TODO место определения первых вероятностей путем решения СЛАУ, и определения границ из их корней
                    final double[] maxValue = {0};
                    HashMap<Character,Double> h =  hashMapforBaesType.getHeadProbabilities(listProbability);
                    h.forEach((character, aDouble) -> {
                        if(maxValue[0] <aDouble){
                            maxValue[0] = aDouble;
                        }
                    });
                    listGeneratedSymbols.add(h.entrySet().stream()
                            .filter(n->(n.getValue() >= random.nextDouble())).findAny().get().getKey());
                }else{
                    listGeneratedSymbols.add(hashMapforBaesType.getHashProbabilitys(listGeneratedSymbols.get(i-1)).entrySet().stream()
                            .filter(n->(n.getValue() >= pr)).findAny().get().getKey());
                }
            }
        }
    }

    private String statistic(){
        StringBuilder statistic = new StringBuilder();
        ArrayList<Double> probabilitys = new ArrayList();
        StringBuilder generatedSymbols = new StringBuilder();
        listGeneratedSymbols.forEach(character -> generatedSymbols.append(character));
        statistic.append(generatedSymbols.toString()).append("\n");
        listSymbols.stream().forEach(character->{
            double countRepeatChar = (int) listGeneratedSymbols.stream().filter(character::equals).count();
            probabilitys.add(countRepeatChar/listGeneratedSymbols.size());
            statistic.append("Total count of a ").append(character).append(" is a:").append(countRepeatChar)
                    .append(". The symbol frequency : ")
                    .append(countRepeatChar/listGeneratedSymbols.size()).append("\n");
        });
        double Entropy=0;
        for (Double v: probabilitys) {
            Entropy += v* lg(v);
        }
        Entropy *= -1;
        statistic.append("Entropy equals to: ").append(Entropy);
        return statistic.toString();
    }

    public static double lg(double x) {
        return Math.log(x)/Math.log(2.0);
    }

}
