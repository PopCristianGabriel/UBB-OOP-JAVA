package sample;

public class MyException extends Exception {
    String message;
    MyException(String message){
        this.message = message;
    }

    String get_error(){
        return this.message;
    }
}
