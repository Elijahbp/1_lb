package library;

import java.util.*;
import java.util.stream.Collectors;

public class Randomizer {
    int startChar;
    int countChar;
    int countGeneratedChar;
    int order;
    List<Integer> listProbability;
    HashMap<Character,Double> listSymbols;
    List<Character> listGeneratedSymbols;

    public Randomizer(int startChar, int countGeneratedChar, List<Integer> listProbability, int order) {
        this.startChar = startChar;
        this.listProbability = listProbability;
        this.countChar = listProbability.size();
        this.countGeneratedChar = countGeneratedChar;
        this.order = order ;
        listSymbols = new HashMap<>();
        listGeneratedSymbols = new ArrayList<>();

        Integer i= startChar;
        double border = 0;
        for (Integer probabiity:listProbability) {
            border += probabiity;
            listSymbols.put((char) Integer.parseInt(i.toString(),16),border);
            i++;
        }
        listSymbols.entrySet().forEach(System.out::println);
        generateSymbols();
        System.out.println(statistic());
    }



    private void generateSymbols(){
        Random random = new Random();

        for (int i = 0; i < this.countGeneratedChar; i++) {
            int pr = random.nextInt(order);
            listGeneratedSymbols.add(listSymbols.entrySet().stream()
                .filter(n->(n.getValue() >= pr)).findAny().get().getKey());
        }
//        listGeneratedSymbols.stream().forEach(s->{
//            System.out.println(s +";");
//        });
    }

    private String statistic(){
        StringBuilder statistic = new StringBuilder();
        ArrayList<Double> probabilitys = new ArrayList();
        listSymbols.entrySet().stream().forEach(s->{
            double countRepeatChar = (int) listGeneratedSymbols.stream().filter(t -> s.getKey().equals(t)).count();
            probabilitys.add(countRepeatChar/listGeneratedSymbols.size());
            statistic.append("Total count of a ").append(s.getKey()).append(" is a:").append(countRepeatChar)
                    .append(". The probability symbol falling out: ")
                    .append(countRepeatChar/listGeneratedSymbols.size()).append("\n");
        });

        double c=0;
        for (Double v: probabilitys) {
            c += v* lg(v);
        }
        c *= -1;
        statistic.append("Entropy equals to: ").append(c);
        return statistic.toString();
    }

    public static double lg(double x) {
        return Math.log(x)/Math.log(2.0);
    }

}
