package sample;

public class SymTableObject {
    String name;
    Value value;

    SymTableObject(){}
    SymTableObject(String name, Value value){
        this.name = name;
        this.value = value;
    }

    String get_name(){
        return this.name;
    }

    Value get_value(){
        return this.value;
    }

}
