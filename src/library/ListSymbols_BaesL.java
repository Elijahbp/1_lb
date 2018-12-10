package library;

import mathLib.MethodGauss;

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

    private double[][] convertMatrix(List<List<Integer>> matrix){
        double[][] newMatrix = new double[matrix.size()-1][matrix.size()];


        for (int i = 0; i < newMatrix.length; i++) {
            for (int j = 0; j < newMatrix.length; j++) {
                if (i==j){
                    newMatrix[j][i] = 1 + matrix.get(matrix.size()-1).get(i) - matrix.get(i).get(j);
                }else {
                    newMatrix[j][i] = matrix.get(matrix.size()-1).get(i) - matrix.get(j).get(i);
                }
            }
        }
        for (int i = 0; i < newMatrix.length; i++) {
            newMatrix[i][newMatrix[i].length-1] = matrix.get(matrix.size()-1).get(i);
        }
        return newMatrix;
    }
    public HashMap<Character,Double> getHeadProbabilities(List<List<Integer>> matrixProbabilities){


        HashMap<Character,Double> hashMap = new HashMap();
        double[][] matrixA = convertMatrix(matrixProbabilities);
        double[] vectorB = new double[matrixA.length];


        for (int i = 0; i < matrixA.length; i++) {
            vectorB[i] = matrixA[i][matrixA.length];
        }
        double[] vectorX;
        vectorX = MethodGauss.method(matrixA,vectorB);

        double bufV=0;
        for (int i = 0; i < vectorX.length-1; i++) {
            bufV += vectorX[i];
        }
        double l = 0.0;
        vectorX[vectorX.length-1] = 1-bufV;
        for (int k = 0; k < vectorX.length; k++) {
            l+=Math.abs(vectorX[k]);
            hashMap.put(listSymbols.get(k),l);
        }
        return hashMap;
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