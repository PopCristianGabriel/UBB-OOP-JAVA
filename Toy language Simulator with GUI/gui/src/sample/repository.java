package sample;



import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.Stack;

interface IRepository{
    ArrayList<PrgState> getPrgList();
    void logPrgStateExec(PrgState state) throws MyException, IOException;
    void setPrgList(ArrayList<PrgState> list);
}


class Repository implements IRepository {
    public ArrayList<PrgState> programs;
    public String filePath;


    Repository() throws IOException {
        this.programs = new ArrayList<PrgState>();


    }
    void add_program(PrgState program){
        this.programs.add(program);
    }
    void set_file_path(String filePath) throws IOException {
        this.filePath = filePath;
        BufferedWriter writer = new BufferedWriter(
                new FileWriter(filePath, false));
        writer.close();
    }


    Repository(PrgState program, String filePath) throws IOException {
        this.programs = new ArrayList<PrgState>();
        this.programs.add(program);
        this.filePath = filePath;
        BufferedWriter writer = new BufferedWriter(
                new FileWriter(filePath, false));
        writer.close();


    }


    @Override
    public ArrayList<PrgState> getPrgList() {
        return this.programs;
    }

    @Override
    public void logPrgStateExec(PrgState state) throws MyException, IOException {
        BufferedWriter writer = new BufferedWriter(
                new FileWriter(this.filePath, true));

        MyIStack<IStmt> stack = (MyIStack<IStmt>) state.getStk();
        Stack<IStmt> exeStack = new Stack();
        exeStack = (Stack<IStmt>) stack.get_stck().clone();
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIList<String> out = state.getLst();
        MyIHeap<Integer,Value> heap = state.get_heap();

        writer.write("Program: ");
        writer.write(state.toString());
        writer.newLine();
        writer.write("Stack");
        while (exeStack.isEmpty() == false) {
            IStmt statement = exeStack.pop();

            writer.newLine();
            writer.write(statement.toString());
        }

        writer.newLine();
        writer.write("SymTable");
        writer.newLine();
        Set<String> keys = symTable.get_all_keys();
        for (String key:keys){
            writer.write(key);
            writer.write(" ");
            writer.write(String.valueOf(symTable.lookup(key)));
            writer.newLine();
        }
        writer.newLine();
        writer.write("Heap");
        writer.newLine();
        Set<Integer> keys2 = heap.get_all_keys();
        for(Integer key : keys2){
            writer.write(new String(String.valueOf(key.intValue())));
            writer.write(" ");
            writer.write(String.valueOf(heap.lookup(key)));
            writer.newLine();
        }



        writer.write("Out");

        for(String v : out.getLst()){
            writer.newLine();
            writer.write(v);
        }
        writer.newLine();
        writer.newLine();
        writer.newLine();
        writer.close();
    }

    @Override
    public void setPrgList(ArrayList<PrgState> list) {
        this.programs = list;
    }
}