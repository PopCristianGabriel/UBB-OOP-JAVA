package sample;

public class HeapObject{
    public Integer key;
    public Value value;


    HeapObject(){}
    HeapObject(Integer key, Value value) {
        this.key = key;
        this.value = value;
    }

    Integer get_gey(){
        return this.key;
    }

    Value get_value(){
        return this.value;
    }


}