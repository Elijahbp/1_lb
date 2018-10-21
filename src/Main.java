import library.Randomizer;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
//        ArrayList<Double> arrayList = new ArrayList();
//        arrayList.add(0.1*100);
//        arrayList.add(0.15*100);
//        arrayList.add(0.05*100);
//        arrayList.add(0.2*100);
//        arrayList.add(0.5*100);
//        new Randomizer(50,1000000,arrayList);
        try {
            Parser parser = new Parser("kek.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}