package project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


class TextMenu {
    private Map<String, Command> commands;
    public TextMenu(){ commands=new HashMap<>(); }
    public void addCommand(Command c){ commands.put(c.getKey(),c);}
    private void printMenu(){
        for(Command com : commands.values()){
            String line=String.format("%4s : %s", com.getKey(), com.getDescription());
            System.out.println(line);
        }
    }
    public void show(){
        Scanner scanner=new Scanner(System.in);
        while(true){
            printMenu();
            System.out.printf("Input the option: ");
            String key=scanner.nextLine();
            Command com=commands.get(key);
            if (com==null){
                System.out.println("Invalid Option");
                continue; }
            com.execute();
        }
    }
}

class RunExample extends Command {
    private Controler ctr;
    public RunExample(String key, String desc,Controler ctr){
        super(key, desc);
        this.ctr=ctr;
    }
    @Override
    public void execute() {
        try{
            ctr.all_steps(); }
        catch (MyException | IOException e) {
            e.getMessage();
        } //here you must treat the exceptions that can not be solved in the controller
    }
}




class Main {
    public static void main(String[] args) throws IOException, MyException {
        int a = 5;
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

        MyIHeap<Integer,Value> heap1 = new MyHeap();
        String filePath1 = "F:\\proiecte facultate\\IdeaProjects\\lab 3-7\\src\\project\\out1.txt";
       // C:\\Users\\Pop\\IdeaProjects\\lab 3-7\\src\\project\\out.txt
        MyIFileTable myFileTable1 =  new MyFileTable<String,BufferedReader>();
        MyIStack stk1 = new MyStack<IStmt>();
        MyIDictionary<String,Value> symtbl1 = new MyDictionary<>();
        MyIList<String> ot1 = new MyList();
        PrgState program1 = new PrgState(stk1,symtbl1,ot1,myFileTable1,heap1,ex12);
        Repository repo1 = new Repository(program1,filePath1);
        Controler controler1 = new Controler(repo1);
        View ui1 = new View(controler1);


        MyIHeap<Integer,Value> heap2 = new MyHeap();
        String filePath2 = "F:\\proiecte facultate\\IdeaProjects\\lab 3-7\\src\\project\\out2.txt";
        MyIFileTable myFileTable2 =  new MyFileTable<String,BufferedReader>();
        MyIStack stk2 = new MyStack<IStmt>();
        MyIDictionary<String,Value> symtbl2 = new MyDictionary<>();
        MyIList<String> ot2 = new MyList();
        PrgState program2 = new PrgState(stk2,symtbl2,ot2,myFileTable2,heap2,ex3);
        Repository repo2 = new Repository(program2,filePath2);
        Controler controler2 = new Controler(repo2);
        View ui2 = new View(controler2);


        String filePath3 = "F:\\proiecte facultate\\IdeaProjects\\lab 3-7\\src\\project\\out3.txt";
        MyIHeap<Integer,Value> heap3 = new MyHeap();
        MyIFileTable myFileTable3 =  new MyFileTable<String,BufferedReader>();
        MyIStack stk3 = new MyStack<IStmt>();
        MyIDictionary<String,Value> symtbl3 = new MyDictionary<>();
        MyIList<String> ot3 = new MyList();
        PrgState program3 = new PrgState(stk3,symtbl3,ot3,myFileTable3,heap3,ex2);
        Repository repo3 = new Repository(program3,filePath3);
        Controler controler3 = new Controler(repo3);
        View ui3 = new View(controler3);


        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample("1",ex12.toString(),controler1));
        menu.addCommand(new RunExample("2",ex2.toString(),controler2));
        menu.addCommand(new RunExample("3",ex3.toString(),controler3));
        menu.show();

        //ui.execute();
        int q = 3;
        //repo.logPrgStateExec();





    }
}