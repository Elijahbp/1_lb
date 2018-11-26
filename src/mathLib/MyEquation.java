package mathLib;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

public class MyEquation implements Gauss<Integer, MyEquation> {

    private List<Integer> equation = new ArrayList<Integer>();

    public List<Integer> getEquation(){
        return equation;
    }

    public MyEquation() {
    }

    public MyEquation(List<Integer> integers) {
        equation.addAll(integers);
    }

    public void generate(int size){
        if (size < 2) size = 2;
        this.equation.clear();
        for (int i = 0; i < size; i++){

        }
    }


    @Override
    public int size(){
        return equation.size();
    }
    @Override
    public void addEquation(MyEquation item){
        ListIterator<Integer> i = equation.listIterator();
        ListIterator<Integer> j = item.getEquation().listIterator();
        for(; i.hasNext() && j.hasNext();){
            Integer a = i.next();
            Integer b = j.next();
            i.set(a + b);
        }
    }

    @Override
    public void mul(Integer coefficient){
        for(ListIterator<Integer> i = equation.listIterator(); i.hasNext();){
            Integer next = i.next();
            i.set(next * coefficient);
        }
    }
    @Override
    public Integer findCoefficient(Integer a, Integer b){
        if (a == 0) return 1;
        return -b/a;
    }
    @Override
    public Integer at(int index){
        return equation.get(index);
    }
}