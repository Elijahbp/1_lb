package library;

import mathLib.Algorithm;
import mathLib.LinearSystem;
import mathLib.MyEquation;

import java.util.ArrayList;
import java.util.Arrays;
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

    private ArrayList<ArrayList<Integer>> convertMatrix(List<List<Integer>> matrix){
        Integer[][] newMatrix = new Integer[matrix.size()-1][matrix.size()];
        for (int i = 0; i < newMatrix.length; i++) {
            for (int j = 0; j < newMatrix[i].length; j++) {
                newMatrix[i][j] = 1;
            }
        }

        for (int i = 0; i < newMatrix.length; i++) {
            for (int j = 0; j < newMatrix.length; j++) {
                if (i==j){
                    newMatrix[i][j] = 1 + matrix.get(matrix.size()-1).get(i) - matrix.get(i).get(j);
                }else {
                    newMatrix[i][j] = matrix.get(matrix.size()-1).get(i) - matrix.get(j).get(i);
                }
            }
        }
        for (int i = 0; i < newMatrix.length; i++) {
            newMatrix[i][newMatrix.length-1] = matrix.get(matrix.size()-1).get(i);
        }
        ArrayList<ArrayList<Integer>> matrixList = new ArrayList<>();
        for (int i = 0; i < newMatrix.length; i++) {
            matrixList.add(new ArrayList<>());
            matrixList.get(i).addAll(Arrays.asList(newMatrix[i]));
        }
        return matrixList;
    }
    //TODO СДелать определение безусловных вероятностей путем решения линейных уравнений!!!
    public HashMap<Character,Double> getHeadProbabilities(List<List<Integer>> matrixProbabilities){



        HashMap<Character,Double> hashMap = new HashMap();
        convertMatrix(matrixProbabilities);
        LinearSystem<Integer, MyEquation> list = generateSystem(convertMatrix(matrixProbabilities));
        Algorithm<Integer,MyEquation> algorithm = new Algorithm<>(list);
        algorithm.calculate();
        int i,j;
        double bufV = 0;
        Double[] x = new Double[list.size()+1];
        for(i = list.size() - 1; i >= 0; i--) {
            double sum = 0.0;
            for(j = list.size() - 1; j > i; j--) {
                sum += list.itemAt(i, j) * x[j];
            }
            x[i] = Math.abs((list.itemAt(i, list.size()) - sum )/ list.itemAt(i, j));
            bufV += x[i];
        }
        Double l = 0.0;
        x[x.length-1] = 1 - bufV;
        for (int k = 0; k < x.length; k++) {
            l+=Math.abs(x[k]);
            hashMap.put(listSymbols.get(k),l);
        }

        return hashMap;
    }





    public LinearSystem<Integer, MyEquation> generateSystem(ArrayList<ArrayList<Integer>> listProbability){
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