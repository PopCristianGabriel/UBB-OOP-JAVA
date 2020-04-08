package sample;


import java.io.IOException;

public class View {
    Controler controler;

    View(){}
    View(Controler controler){
        this.controler =  controler;
    }

    void execute(){
        try{
            this.controler.all_steps();
        } catch (MyException | IOException e) {
            System.out.print(e.toString());
        }
    }

}
