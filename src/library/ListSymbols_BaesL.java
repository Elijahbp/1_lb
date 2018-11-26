package library;

import mathLib.Algorithm;
import mathLib.LinearSystem;
import mathLib.MyEquation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListSymbols_BaesL {
    /*TODO
    1)  сделать список символов
    2)  сделать метод добавления значений в hashmap  +
    3)  сделать метод возвращения значение символа +
     */

    private HashMap<Character,HashMap<Character,Integer>> headHashMap;
    private List<Character> listSymbols;

    public ListSymbols_BaesL(List<Character> listSymbols) {
        this.listSymbols = listSymbols;
        headHashMap = new HashMap<>();
        listSymbols.forEach(headSymbol -> {
            headHashMap.put(headSymbol,new HashMap<>());
        });
    }


    public void setValues(List<List<Integer>> probabilities){
        int i=0;
        for (char headSymbol:listSymbols) {
            setBorders(headSymbol,probabilities.get(i));
            i++;
        }
    }


    //TODO СДелать определение безусловных вероятностей путем решения линейных уравнений!!!
    public HashMap<Character,Double> getHeadProbabilities(List<List<Integer>> listProbabilities){

        HashMap<Character,Double> hashMap = new HashMap();

        LinearSystem<Integer, MyEquation> list = generateSystem(listProbabilities);
        Algorithm<Integer,MyEquation> algorithm = new Algorithm<>(list);
        algorithm.calculate();
        int i,j;
        Double[] x = new Double[list.size()];
        for(i = list.size() - 1; i >= 0; i--) {
            double sum = 0.0;
            for(j = list.size() - 1; j > i; j--) {
                sum += list.itemAt(i, j) * x[j];
            }
            x[i] = (list.itemAt(i, list.size()) - sum) / list.itemAt(i, j);
        }
        Double l = 0.0;
        for (int k = 0; k < x.length; k++) {
            l+=Math.abs(x[k]);
            hashMap.put(listSymbols.get(k),l);
        }

        return hashMap;
    }

    public LinearSystem<Integer, MyEquation> generateSystem(List<List<Integer>> listProbability){
        LinearSystem<Integer, MyEquation> list = new LinearSystem<Integer, MyEquation>();
        for (int i = 0; i < listProbability.size(); i++){
            MyEquation eq = new MyEquation(listProbability.get(i));
            list.push(eq);
        }
        return list;
    }


    private void setBorders(char forSymbol,List<Integer> probabilities){
        int border = 0;
        HashMap<Character,Integer> bufHashMap = new HashMap();
        for (int i = 0; i < probabilities.size(); i++) {
            border+=probabilities.get(i);
            bufHashMap.put(listSymbols.get(i),border);
        }
        headHashMap.get(forSymbol).putAll(bufHashMap);
    }

    public HashMap<Character,Integer> getHashProbabilitys(char forSymbol){
        return headHashMap.get(forSymbol);
    }

    public Integer getValueSymbols(char headSymbol, char secondSymbol){
        return headHashMap.get(headSymbol).get(secondSymbol);
    }


}