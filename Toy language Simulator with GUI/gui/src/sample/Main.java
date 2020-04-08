package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {

    public ArrayList<IStmt> get_programs_statements(){
        IStmt ex1= new CompStmt(new VarDeclStmt("v",new IntType()),
                new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(2))), new PrintStmt(new
                        VarExp("v"))));
        IStmt ex2 = new CompStmt( new VarDeclStmt("a",new IntType()),
                new CompStmt(new VarDeclStmt("b",new IntType()),
                        new CompStmt(new AssignStmt("a", new ArithExp(1,new ValueExp(new IntValue(2)),new
                                ArithExp(3,new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                                new CompStmt(new AssignStmt("b",new ArithExp(1,new VarExp("a"), new
                                        ValueExp(new IntValue(1)))), new PrintStmt(new VarExp("b"))))));



        IStmt ex3 = new CompStmt(new VarDeclStmt("a",new BoolType()),
                new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                new CompStmt(new IfStmt(new VarExp("a"),new AssignStmt("v",new ValueExp(new
                                        IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new
                                        VarExp("v"))))));

        IStmt ex4 = new CompStmt(new VarDeclStmt("varf",new StringType()),
                new CompStmt(new AssignStmt("varf",new ValueExp(new StringValue("C:\\Users\\Pop\\IdeaProjects\\lab 3-7\\src\\project\\test.txt"))),
                        new CompStmt(new OpenRFile(new VarExp("varf")),
                                new CompStmt(new VarDeclStmt("varc",new IntType()),
                                        new CompStmt(new readFile(new ValueExp( new StringValue("varf")),"varc"),
                                                new CompStmt(new PrintStmt(new VarExp("varc")),
                                                        new CompStmt(new readFile(new ValueExp(new StringValue("varf")),"varc"),
                                                                new CompStmt(new PrintStmt(new VarExp("varc")),
                                                                        new closeRFile(new ValueExp(new StringValue("varf")))       )      )        ))))));

        IStmt ex5 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new New("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a",new RefType(new RefType(new IntType()))),
                                new CompStmt(new New("a",new VarExp("v")),
                                        new CompStmt(new PrintStmt(new VarExp("v")),new PrintStmt(new VarExp("a")))))));


        IStmt ex6 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new New("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a",new RefType(new RefType(new IntType()))),
                                new CompStmt(new New("a",new VarExp("v")),
                                        new CompStmt(new PrintStmt(new rH(new rH(new VarExp("a")))),new PrintStmt(new VarExp("a")))))));


        IStmt ex7 = new CompStmt(new VarDeclStmt("v",new RefType(new IntType())),
                new CompStmt(new New("v",new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a",new RefType(new RefType(new IntType()))),
                                new CompStmt(new New("a",new VarExp("v")),
                                        new CompStmt(new PrintStmt(new rH(new VarExp("v"))),
                                                new PrintStmt(new ArithExp(1,new rH(new rH(new VarExp("a"))),new ValueExp(new IntValue(5)))))))));
        IStmt ex8 = new CompStmt(new VarDeclStmt("v",new RefType(new IntType())),
                new CompStmt(new New("v",new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a",new RefType(new RefType(new IntType()))),
                                new CompStmt(new New("a",new VarExp("v")),
                                        new CompStmt(new PrintStmt(new rH(new VarExp("v"))),
                                                new wH("v",new ValueExp(new IntValue(25))))))));

        IStmt ex9 = new CompStmt(new VarDeclStmt("v",new RefType(new IntType())),
                new CompStmt(new New("v",new ValueExp(new IntValue(20))),
                        new CompStmt(new PrintStmt(new rH(new VarExp("v"))),
                                new CompStmt(new wH("v",new ValueExp(new IntValue(30))),
                                        new PrintStmt(new ArithExp(1,new rH(new VarExp("v")),new ValueExp(new IntValue(5))))))));

        IStmt ex10 = new CompStmt(new VarDeclStmt("v",new IntType()),
                new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(4))),
                        new CompStmt(new While(new CompareExp(new VarExp("v"),new ValueExp(new IntValue(0)),">"), new CompStmt(new PrintStmt(new VarExp("v")),new AssignStmt("v",new ArithExp(2,new VarExp("v"),new ValueExp(new IntValue(1)))))),
                                new PrintStmt(new VarExp("v")))));

        IStmt ex11 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new New("v",new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a",new RefType(new RefType(new IntType()))),
                                new CompStmt(new New("a",new VarExp("v")),
                                        new CompStmt(new New("v",new ValueExp(new IntValue(30))),
                                                new PrintStmt(new rH(new rH(new VarExp("a")))))))));


        IStmt ex12 = new CompStmt(new VarDeclStmt("v",new IntType()),
                new CompStmt(new VarDeclStmt("a",new RefType(new IntType())),
                        new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(10))),
                                new CompStmt(new New("a",new ValueExp(new IntValue(20))),
                                        new CompStmt(new forkStmt(new CompStmt(new wH("a",new ValueExp(new IntValue(30))),
                                                new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(32))),
                                                        new CompStmt(new PrintStmt(new VarExp("v")),
                                                                new PrintStmt(new rH(new VarExp("a"))))))),
                                                new CompStmt(new PrintStmt(new VarExp("v")),new PrintStmt(new rH(new VarExp("a")))))))));



       /* IStmt fork1 = new CompStmt(new await("cnt"),
                new CompStmt(new wH("v1",new ArithExp(3,new rH(new VarExp("v1")),new ValueExp(new IntValue(10)))),
                        new PrintStmt(new rH(new VarExp("v1")))));

        IStmt fork2 = new CompStmt(new await("cnt"),
                new CompStmt(new wH("v2",new ArithExp(3,new rH(new VarExp("v2")),new ValueExp(new IntValue(10)))),
                        new CompStmt(new wH("v2",new ArithExp(3,new rH(new VarExp("v2")),new ValueExp(new IntValue(10)))),
                                new PrintStmt(new rH(new VarExp("v2"))))));

        IStmt ex13 = new CompStmt(new VarDeclStmt("v1",new RefType(new IntType())),
                new CompStmt(new New("v1",new ValueExp(new IntValue(2))),
                        new CompStmt(new VarDeclStmt("v2",new RefType(new IntType())),
                                new CompStmt(new New("v2",new ValueExp(new IntValue(3))),
                                        new CompStmt(new VarDeclStmt("v3",new RefType(new IntType())),
                                                new CompStmt(new New("v3",new ValueExp(new IntValue(4))),
                                                        new CompStmt(new newBarrier("cnt",new rH(new VarExp("v2"))),
                                                                new CompStmt(new forkStmt(fork1),
                                                                        new CompStmt(new forkStmt(fork2),
                                                                                new CompStmt(new await("cnt"),
                                                                                        new PrintStmt(new rH(new VarExp("v3")))))))))))));*/

       IStmt fork1 = new CompStmt(new aquire(new VarExp("cnt")),
               new CompStmt(new wH("v1",new ArithExp(3,new rH(new VarExp("v1")),new ValueExp(new IntValue(10)))),
                       new CompStmt(new PrintStmt(new rH(new VarExp("v1"))),new release(new VarExp("cnt")))));

       IStmt fork2 = new CompStmt(new aquire(new VarExp("cnt")),
               new CompStmt(new wH("v1",new ArithExp(3,new rH(new VarExp("v1")),new ValueExp(new IntValue(10)))),
                       new CompStmt(new wH("v1",new ArithExp(3,new rH(new VarExp("v1")),new ValueExp(new IntValue(2)))),
                               new CompStmt(new PrintStmt(new rH(new VarExp("v1"))),
                                       new release(new VarExp("cnt"))))));


       IStmt ex13 = new CompStmt(new VarDeclStmt("v1",new RefType(new IntType())),
               new CompStmt(new VarDeclStmt("cnt",new IntType()),
                       new CompStmt(new New("v1",new ValueExp(new IntValue(1))),
                               new CompStmt(new CreateSemaphore(new VarExp("cnt" ),new rH(new VarExp("v1"))),
                                       new CompStmt(new forkStmt(fork1),
                                               new CompStmt(new forkStmt(fork2),
                                                       new CompStmt(new aquire(new VarExp("cnt")),
                                                               new CompStmt(new PrintStmt(new ArithExp(2,new rH(new VarExp("v1")),new ValueExp(new IntValue(1)))),
                                                                       new release(new VarExp("cnt"))))))))));


        ArrayList<Case> cases = new ArrayList<>();
        IStmt stc1 = new CompStmt(new PrintStmt(new VarExp("a")),new PrintStmt(new VarExp("b")));
        Exp ec1 = new ArithExp(3,new VarExp("b"),new VarExp("c"));
        Case c1 = new Case("case",ec1,stc1);

        IStmt stc2 = new CompStmt(new PrintStmt(new ValueExp(new IntValue(100))),new PrintStmt(new ValueExp(new IntValue(200))));
        Exp ec2 = new ValueExp(new IntValue(10));
        Case c2 = new Case("case",ec2,stc2);

        IStmt stc3= new PrintStmt(new ValueExp(new IntValue(300)));
        Exp ec3 = new ArithExp(3,new VarExp("b"),new VarExp("c"));
        Case c3 = new Case("default",ec3,stc3);

        cases.add(c1);
        cases.add(c2);
        cases.add(c3);

        IStmt ex14 = new CompStmt(new VarDeclStmt("a",new IntType()),
                new CompStmt(new VarDeclStmt("b",new IntType()),
                        new CompStmt(new VarDeclStmt("c",new IntType()),
                                new CompStmt(new AssignStmt("a",new ValueExp(new IntValue(1))),
                                        new CompStmt(new AssignStmt("b",new ValueExp(new IntValue(2))),
                                                new CompStmt(new AssignStmt("c",new ValueExp(new IntValue(5))),
                                                       new CompStmt( new Switch(new ArithExp(3,new VarExp("a"),new ValueExp(new IntValue(10))),cases),
                                                               new PrintStmt(new ValueExp(new IntValue(300))))))))));

        ArrayList<IStmt> listOfPrograms = new ArrayList();
        listOfPrograms.add(ex1);
        listOfPrograms.add(ex2);
        listOfPrograms.add(ex3);
        listOfPrograms.add(ex4);
        listOfPrograms.add(ex5);
        listOfPrograms.add(ex6);
        listOfPrograms.add(ex7);
        listOfPrograms.add(ex8);
        listOfPrograms.add(ex9);
        listOfPrograms.add(ex10);
        listOfPrograms.add(ex11);
        listOfPrograms.add(ex12);
        listOfPrograms.add(ex13);
        listOfPrograms.add(ex14);

        return listOfPrograms;
    }

    int windowToShow = 1;


    @Override
    public void start(Stage primaryStage) throws Exception{



            FXMLLoader loader = new FXMLLoader(getClass().getResource("ex.fxml"));
            String filePath1 = "C:\\Users\\Pop\\IdeaProjects\\lab 3-7\\src\\project\\out1.txt";
            Repository repo = new Repository();
            Controler myController = new Controler(repo);
            GUIRepo guiRepo = new GUIRepo(this.get_programs_statements());
            Controller ctrl = new Controller(myController, guiRepo);
            loader.setController(ctrl);
            Parent root = loader.load();

            primaryStage.setTitle("Analysis");
            primaryStage.setScene(new Scene(root, 600, 400));
            primaryStage.show();



            Stage PrimaryStage2 = new Stage();
            FXMLLoader loader2 = new FXMLLoader(getClass().getResource("programsSample.fxml"));

            loader2.setController(ctrl);
            Parent root2 = loader2.load();

        PrimaryStage2.setTitle("Analysis");
        PrimaryStage2.setScene(new Scene(root2, 600, 400));
        PrimaryStage2.show();


    }


    public static void main(String[] args) {



        launch(args);
    }
}
