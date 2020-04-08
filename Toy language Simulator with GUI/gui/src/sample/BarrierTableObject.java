package sample;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class BarrierTableObject {
    String name;
    ArrayList<Integer> threads;

    BarrierTableObject(){}
    BarrierTableObject(String name, ArrayList<Integer> threads){
        this.name = name;
        this.threads = threads;
    }

    String get_name(){
        return this.name;
    }

    ArrayList<Integer>  get_value(){
        return this.threads;
    }
}
