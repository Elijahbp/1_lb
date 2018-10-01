package library;

import java.util.*;
import java.util.stream.Collectors;

public class Randomizer {
    int startChar;
    int countChar;
    int countGeneratedChar;
    List<Double> listProbability;
    HashMap<Character,Double> listSymbols;
    List<Character> listGeneratedSymbols;

    public Randomizer(int startChar, int countGeneratedChar, List<Double> listProbability) {
        this.startChar = startChar;
        this.listProbability = listProbability;
        this.countChar = listProbability.size();
        this.countGeneratedChar = countGeneratedChar;
        listSymbols = new HashMap<>();
        listGeneratedSymbols = new ArrayList<>();

        Integer i= startChar;
        double border = 0;
        for (Double probabiity:listProbability) {
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
            int pr = random.nextInt(100);
                listGeneratedSymbols.add(listSymbols.entrySet().stream()
                        .filter(n->(n.getValue() >= pr)).findAny().get().getKey());
        }
        listGeneratedSymbols.stream().forEach(s->{
            System.out.println(s +";");
        });
    }

    private String statistic(){
        StringBuilder statistic = new StringBuilder();
        listSymbols.entrySet().stream().forEach(s->{
            double countRepeatChar = (int) listGeneratedSymbols.stream().filter(t -> s.getKey().equals(t)).count();
            statistic.append("Total count of a ").append(s.getKey()).append(" is a:").append(countRepeatChar)
                    .append(". The probability symbol falling out: ")
                    .append(countRepeatChar/listGeneratedSymbols.size()).append("\n");
        });
        return statistic.toString();
    }


}
