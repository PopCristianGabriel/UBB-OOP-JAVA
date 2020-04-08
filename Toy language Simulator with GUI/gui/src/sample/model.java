package sample;
import java.io.*;

import java.lang.reflect.Array;
import java.time.chrono.IsoChronology;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

interface Value {
    boolean equals(Object another);

    Type getType();
}




interface Exp {
    Value eval(MyIDictionary<String, Value> tbl,MyIHeap<Integer,Value> hp) throws MyException;
    String get_id();
    Type typecheck(MyIDictionary<String,Type> typeEnv) throws MyException;
}

interface Type{
    Value defaultValue();
}

interface IStmt {
    PrgState execute(PrgState state) throws MyException, IOException;
    MyIDictionary<String,Type> typecheck(MyIDictionary<String,Type> typeEnv) throws MyException;

//which is the execution method for a statement.
}




interface MyIStack<T> {
    T pop();
    void push(T v);

    boolean isEmpty();
     Object get_copy();

    Stack<T>get_stck();

}

interface MyIHeap<I extends Number, V> {
public int allocate(Value v);
public boolean is_defined(Integer i);
public Set<Integer> get_all_keys();
public Value get_value(int adress);
public Value lookup(Integer key);
public void update(Integer key, Value v);

void setContent(HashMap<Integer,Value> content);
HashMap<Integer,Value> getContent();

}

interface MyIDictionary<K,V>{
   void put(K key,V value);
   V getValue(K key);
   void remove(K key);
    void setContent(Hashtable<String,Value> content);
    boolean isDefined(K id);

    void update(K key, V value);

    V lookup(K id);
    Hashtable<K,V> getContent();
    Set<K> get_all_keys();
}

interface MyIList<V>{
    void add(V value);
    void remove(V value);
    ArrayList<V> getLst();
}


interface MyIFileTable<K,V>{
    void put(K key,V value) throws FileNotFoundException, MyException;
    V getValue(K key);
    void remove(K key);
    public Set<K> get_all_keys();
    boolean isDefined(K id);

    void update(K key, V value);

    V lookup(K id);
}

class pair{
    private int integer;
    private ArrayList<Integer> integerList;

    pair(int integer, ArrayList<Integer> integerList){
        this.integer = integer;
        this.integerList = integerList;
    }
    pair(){
        this.integerList = new ArrayList<>();
    }

    int get_length_of_semaphore(){
        return this.integer;
    }

    void remove_from_list(int id){
        for(int i = 0 ; i < this.integerList.size(); i++) {
            if(this.integerList.get(i) == id){
                this.integerList.remove(i);
                return;
            }
        }
    }


    ArrayList<Integer> get_list_of_threads_awaiting(){
        return this.integerList;
    }

    int how_many_threads_wait(){
        return this.integerList.size();
    }

    boolean this_thrad_awaits(int identifier){
        return this.integerList.contains(identifier);
    }


    void add_thread_to_wait(int identifier){
        this.integerList.add(identifier);
    }

}

interface MyISemaphore<K,V>{
    int allocate(V value);
    void put(K key, V value);
    V get_value(K key);
    public Set<K> get_all_keys();
    void update(K key, V value);
        }



interface MyIBarrier<K,V>{
    int allocate(V value);
    void put(K key, V value);
    V get_value(K key);
    void update(K key, V value);
    public Set<K> get_all_keys();

}

class MySemaphore implements MyISemaphore<Integer,pair> {

    private HashMap<Integer, pair> semaphore;
    private int newFreeLocation;

    MySemaphore() {
        this.semaphore = new HashMap<>();
        this.newFreeLocation = 0;
    }


    @Override
    public int allocate(pair value) {
        this.put(this.newFreeLocation, value);
        this.newFreeLocation++;
        return this.newFreeLocation - 1;
    }

    @Override
    public void put(Integer key, pair value) {
        this.semaphore.put(key, value);
    }

    @Override
    public pair get_value(Integer key) {
        return this.semaphore.get(key);
    }

    @Override
    public void update(Integer key, pair value) {
        this.semaphore.put(key, value);
    }

    @Override
    public Set<Integer> get_all_keys() {
        return this.semaphore.keySet();
    }

}
class MyBarrier implements MyIBarrier<Integer,pair>{
    private HashMap<Integer,pair> barrier;
    private int newFreeLocation;
    MyBarrier(){
        this.barrier = new HashMap<>();
        this.newFreeLocation = 0;
    }


    @Override
    public int allocate(pair value) {
        this.put(this.newFreeLocation,value);
        this.newFreeLocation++;
        return this.newFreeLocation-1;
    }

    @Override
    public void put(Integer key, pair value) {
        this.barrier.put(key,value);
    }

    @Override
    public pair get_value(Integer key) {
        return this.barrier.get(key);
    }

    @Override
    public void update(Integer key, pair value) {
        this.barrier.put(key,value);
    }

    @Override
    public Set<Integer> get_all_keys() {
        return this.barrier.keySet();
    }
}


class MyHeap implements MyIHeap<Integer, Value> {
    private HashMap<Integer,Value> heap;
    private int nextFree;
    MyHeap(){
        this.heap = new HashMap<Integer,Value>();
        this.nextFree = 1;
    }

    public int allocate(Value v){
        this.heap.put(nextFree,v);
        this.nextFree ++;
        return this.nextFree - 1;

    }

    @Override
    public boolean is_defined(Integer i) {
        return this.heap.containsKey(i);
    }

    public Set<Integer> get_all_keys() {
        return this.heap.keySet();
    }

    @Override
    public Value get_value(int adress) {
        return  this.heap.get(adress);
    }

    @Override
    public Value lookup(Integer key) {
        return this.heap.get(key);
    }

    @Override
    public void update(Integer key, Value v) {
        this.heap.put(key,v);
    }

    @Override
    public void setContent(HashMap<Integer, Value> content) {
        this.heap = (HashMap<Integer, Value>) content;
    }

    @Override
    public HashMap<Integer, Value> getContent() {
        return this.heap;
    }


}




class MyFileTable<K,V> implements MyIFileTable<K,V>{
    private Hashtable<K, V> hashTable;
    MyFileTable(){
        hashTable =  new Hashtable<K,V>();
    }
    public V lookup(K id){
        return this.hashTable.get(id);
    }

    public Set<K> get_all_keys(){
        return hashTable.keySet();
    }


    @Override
    public void put(K key, V value) throws FileNotFoundException, MyException {

            if (!this.isDefined(key)){

                    this.hashTable.put(key,value);


            }
        }


    @Override
    public V getValue(K key) {
        return hashTable.get(key);
    }

    @Override
    public void remove(K key) {
        hashTable.remove(key);
    }
    public boolean isDefined(K key){
        return this.hashTable.containsKey(key);

    }
    public void update(K key, V value) {
        if (this.isDefined(key)) {
            this.hashTable.put(key, value);
        }
    }

}

class MyList<V> implements MyIList<V>{
    private ArrayList<V> list;

    public MyList(){
        this.list = new ArrayList<V>();
    }

    @Override
    public void add(V value) {
        this.list.add(value);
    }

    @Override
    public void remove(V value) {
        this.list.remove(value);
    }

    public ArrayList<V> getLst(){
        return this.list;
    }
}

class MyDictionary<K,V> implements MyIDictionary<K,V>{
   private Hashtable<K, V> hashTable;

   public V lookup(K id){
       return this.hashTable.get(id);
   }

    @Override
    public Hashtable<K, V> getContent() {
        return this.hashTable;
    }



    public Set<K> get_all_keys(){
       return hashTable.keySet();
    }
   public MyDictionary(){
       hashTable = new Hashtable<K, V>();
    }

    @Override
    public void put(K key, V value) {
        hashTable.put(key,value);
    }

    @Override
    public V getValue(K key) {
        return hashTable.get(key);
    }

    @Override
    public void remove(K key) {
        hashTable.remove(key);
    }

    @Override
    public void setContent(Hashtable<String, Value> content) {
        this.hashTable = (Hashtable<K, V>) content;
    }

    public boolean isDefined(K key){
       return this.hashTable.containsKey(key);

   }
    public void update(K key, V value) {
        if (this.isDefined(key)) {
            this.hashTable.put(key, value);
        }
    }
}




class MyStack<T> implements MyIStack<T>{
    private Stack<T> st;

    MyStack(){
    this.st = new Stack<T>();
    }

    public Object get_copy(){
        return this.st.clone();

    }

    @Override
    public Stack<T> get_stck() {
        return this.st;
    }

    @Override
    public T pop() {
    return this.st.pop();
    }

    @Override
    public void push(T v) {
    this.st.push(v);
    }



    public boolean isEmpty(){
        return this.st.isEmpty();
    }

}

class StringValue implements  Value{
    String val;
    StringValue(String v){
        this.val = v;
    }
    String getVal(){return val;}

    @Override
    public String toString() {
        return "StringValue{" +
                "val='" + val + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object another){
        StringValue v = (StringValue)another;
        if (v.getVal() == val)
            return true;
        else
            return false;
    }

    @Override
    public Type getType() {
        return new StringType();
    }
}
class IntValue implements Value{
    int val;
    IntValue(int v){val=v;}

    int getVal() {return val;}

    @Override
    public String toString() {
        return "IntValue{" +
                "val=" + val +
                '}';
    }
    @Override
    public boolean equals(Object another){
        IntValue v = (IntValue)another;
        if (v.getVal() == this.val)
            return true;
        else
            return false;
    }
    public Type getType() { return new IntType();}
}



class BoolValue implements Value{

    boolean val;

    BoolValue(boolean v){
        val = v;
    }
    boolean getVal() {
        return val;
    }

    public Type getType() {
        return new BoolType();
    }

    @Override
    public String toString() {
        return "BoolValue{" +
                "val=" + val +
                '}';
    }
    @Override
    public boolean equals(Object another){
        BoolValue v = (BoolValue)another;
        if (v.getVal() == this.val)
            return true;
        else
            return false;
    }

}
class RefValue implements Value{
    int address;
    Type locationType;


    public RefValue(int adress, Type locationType){
        this.address = adress;
        this.locationType = locationType;
    }
    public int getAddress(){
        return this.address;
    }
    @Override
    public String toString() {
        return "RefValue{" +
                "address=" + address +
                ", locationType=" + locationType +
                '}';
    }

    int getAddr() {return address;}
    public Type getType() { return new RefType(locationType);}
}


class IntType implements Type{

    IntType(){}
    @Override
    public boolean equals(Object another){
        if (another instanceof IntType)
            return true;
        else
            return false;
    }

    @Override
    public String toString() {
        return "IntType{}";
    }

    @Override
    public Value defaultValue() {
      return new IntValue(0);
    }
}

class RefType implements Type{
    Type inner;
    RefType(Type inner) {this.inner=inner;}
    Type getInner() {return inner;}
    public boolean equals(Object another){
        if (another instanceof RefType)
            return inner.equals(((RefType) another).getInner());
        else
            return false;
    }
    @Override
    public String toString() { return "Ref(" +inner.toString()+")";}
    @Override
    public Value defaultValue() { return new RefValue(0,inner);}
}




class StringType implements Type{

    StringType() {

    }
    public boolean equals(Object another){
        return another instanceof StringType;
    }

    @Override
    public String toString() {
        return "StringType";
    }

    @Override
    public Value defaultValue() {
        return new StringValue("");
    }
}


class BoolType implements Type {

    BoolType(){}

    public boolean equals(Object another){
        if (another instanceof BoolType)
            return true;
        else
            return false;
    }
    @Override
    public String toString() { return "bool";}

    @Override
    public Value defaultValue() {
        return new BoolValue(false);
    }
}



class PrgState{
    MyISemaphore<Integer,pair> semaphoreTable;
    MyIStack<IStmt> exeStack;
    MyIDictionary<String, Value> symTable;
    MyIList<String> out;
    MyIFileTable<String, BufferedReader> myFileTable;
    MyIHeap<Integer,Value> heap;
    IStmt originalProgram; //optional field, but good to have
    static int id = 0;
    int actualId;

    @Override
    public String toString() {
        return String.valueOf(this.actualId);
    }

    PrgState(MyIStack<IStmt> stk, MyIDictionary<String,Value> symtbl,
             MyIList<String> ot, MyIFileTable<String, BufferedReader> myFileTable, MyIHeap<Integer,Value> heap, MyISemaphore<Integer,pair> semaphore,
             IStmt prg){

        this.semaphoreTable = semaphore;
        this.myFileTable = myFileTable;
        exeStack=stk;
        symTable=symtbl;
        out = ot;
        this.myFileTable = myFileTable;
        this.heap = heap;
        //originalProgram=deepCopy(prg);//recreate the entire original prg
        stk.push(prg);
        this.actualId = id;
        this.id++;
    }

    public MyISemaphore<Integer,pair> get_semaphore_table(){
        return this.semaphoreTable;
    }
    public boolean  isNotCompleted(){
        return !this.getStk().isEmpty();
    }

    public MyIStack<IStmt> getStk() {
        return this.exeStack;
    }

    public MyIDictionary<String,Value> getSymTable(){
        return (MyIDictionary<String, Value>) this.symTable;
    }

    public MyIList<String> getLst(){
        return this.out;
    }
    public MyIFileTable<String, BufferedReader> getFileTable(){
        return this.myFileTable;
    }
    public MyIStack<IStmt> get_stack_copy(){
        return (MyIStack<IStmt>) this.exeStack.get_copy();
    }
    public MyIHeap<Integer,Value> get_heap(){return this.heap;}

    public PrgState one_step() throws MyException, IOException {

        IStmt crtStmt = this.exeStack.pop();
        return crtStmt.execute(this);
    }


}

class CompStmt implements IStmt {
    IStmt first;
    IStmt snd;

    public CompStmt(IStmt v, IStmt v1) {
        this.first = v;
        this.snd = v1;
    }
    @Override
    public String toString() {
        return "Compound Statement : 1->"+first.toString() + "2->" + snd.toString()+")";
    }
    public PrgState execute(PrgState state) throws MyException{
        MyIStack<IStmt> stk=state.getStk();
        stk.push(snd);
        stk.push(first);

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        //MyIDictionary<String,Type> typEnv1 = first.typecheck(typeEnv);
        //MyIDictionary<String,Type> typEnv2 = snd.typecheck(typEnv1);
        //return typEnv2;
        return snd.typecheck(first.typecheck(typeEnv));
    }
}


class OpenRFile implements  IStmt{
    Exp exp;
    public OpenRFile(Exp e){this.exp = e;}

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> exeStack = state.getStk();
        MyIFileTable<String,BufferedReader> fileTable = state.getFileTable();
        String name = this.exp.get_id();
        Value v = this.exp.eval(state.getSymTable(), state.get_heap());
        StringValue sv = (StringValue)v;
        String ssv = sv.getVal();
        if (v.getType().equals(new StringType())){
            if(fileTable.isDefined(sv.getVal())) {
                throw new MyException("already defined expresion");
            }
            else{
                try{
                    BufferedReader in = new BufferedReader(new FileReader(sv.getVal()));
                    fileTable.put(name,in);
                } catch (FileNotFoundException e) {
                    throw new MyException("File Not Found ;_;");
                }

            }
        }
        else{
            throw new MyException("Incorect name");
        }
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }
}


class readFile implements IStmt{
    Exp exp;
    String var_name;

    public readFile(Exp exp, String var_name) {
        this.exp = exp;
        this.var_name = var_name;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, IOException {
        MyIDictionary<String,Value> symTbl= state.getSymTable();
        if(symTbl.isDefined(var_name) == true){
         if(symTbl.getValue(var_name).getType().equals(new IntType())){
             Value s = exp.eval(state.getSymTable(),state.get_heap());
             StringValue ss = (StringValue) s;
             if(state.getFileTable().isDefined(ss.getVal())) {
                 BufferedReader b = state.getFileTable().getValue(ss.getVal());
                 String read = b.readLine();
                 if (read != null) {
                     int readValue = Integer.parseInt(read);
                     Value toPut = new IntValue(readValue);
                     state.getSymTable().update(var_name, toPut);
                 }
                 else{
                     state.getSymTable().update(var_name,new IntValue(0));
                 }
             }
             else{
                 throw new MyException("could not open file");
             }
         }
         else{
             throw new MyException("var_name not an int");
         }
        }
        else{
            throw new MyException("incorrect variable name");
        }
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type t = this.exp.typecheck(typeEnv);
        if(t.equals(new StringType())){
            return typeEnv;
        }
        else{
            throw new MyException("nu e bun numele");
        }
    }

}


class closeRFile implements IStmt {
    Exp exp;

    closeRFile(Exp e) {
        this.exp = e;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, IOException {
        MyIFileTable<String, BufferedReader> fileTable = state.getFileTable();
        Value v = this.exp.eval(state.getSymTable(), state.get_heap());
        StringValue sv = (StringValue) v;
        if (sv.getType().equals(new StringType())) {
            try {
                BufferedReader in = fileTable.getValue(sv.getVal());
                in.close();
            } catch (IOException e) {
                throw new MyException("Invalid name");
            }
            fileTable.remove(sv.getVal());
        } else {
            throw new MyException("not good input");
        }
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type t = this.exp.typecheck(typeEnv);
        if (t.equals(new StringType())) {
            return typeEnv;
        } else {
            throw new MyException("Invalid name");
        }
    }
}

class PrintStmt implements IStmt{
    Exp exp;

    public PrintStmt(Exp v) {
        this.exp = v;
    }
    @Override
    public String toString(){ return "print(" +exp.toString()+")";
    }
    public PrgState execute(PrgState state) throws MyException{
        MyIList<String> out = state.getLst();
        Value v = this.exp.eval(state.getSymTable(),state.get_heap());
        String s = v.toString();
        out.add(exp.eval(state.getSymTable(),state.get_heap()).toString());
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        exp.typecheck(typeEnv);
        return typeEnv;
    }

}
class AssignStmt implements IStmt {
    String id;
    Exp exp;

    public AssignStmt(String v, Exp valueExp) {
        this.id = v;
        this.exp = valueExp;
    }


    @Override
    public String toString() {
        return id + "=" + exp.toString();
    }

    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stk = state.getStk();
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        Value val = exp.eval(symTbl, state.get_heap());
        if (symTbl.isDefined(id)) {
            Type typId = (symTbl.getValue(id)).getType();
            if (val.getType().equals(typId))
                symTbl.update(id, val);
            else
                throw new MyException("declared type of variable" + id + " and type of the assigned expression do not match");
        } else throw new MyException("the used variable" + id + " was not declared before");
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        {
            Type typevar = typeEnv.lookup(id);
            Type typexp = exp.typecheck(typeEnv);
            if (typevar.equals(typexp))
                return typeEnv;
            else
                throw new MyException("Assignment: right hand side and left hand side have different types ");
        }

    }
}
    class IfStmt implements IStmt{
        Exp exp;
        IStmt thenS;
        IStmt elseS;

        IfStmt(Exp e, IStmt t, IStmt el) {exp=e; thenS=t;elseS=el;}
        @Override
        public String toString(){ return "IF("+ exp.toString()+") THEN(" +thenS.toString()
                +")ELSE("+elseS.toString()+")";}
        public PrgState execute(PrgState state) throws MyException, IOException {
            Value v = exp.eval(state.getSymTable(),state.get_heap());
            if(v.getType().equals(new IntType())){
                throw new MyException("invalid expresion");
            }
            BoolValue b = (BoolValue)v;
            if(b.getVal() == true){
                thenS.execute(state);
            }
            else{
                elseS.execute(state);
            }

            return null;
        }

        @Override
        public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
            Type typexp=exp.typecheck(typeEnv);
            if (typexp.equals(new BoolType())) {
                thenS.typecheck(typeEnv);
                elseS.typecheck(typeEnv);
                return typeEnv;
            }
            else
                throw new MyException("The condition of IF has not the type bool");
        }

    }



    class VarDeclStmt implements IStmt{
        String name;
        Type typ;

        public VarDeclStmt(String v, Type type) {
            this.name = v;
            this.typ = type;
        }

        @Override
        public String toString() {
            return "VarDeclStmt{" +
                    "name='" + name + '\'' +
                    ", typ=" + typ +
                    '}';
        }

        @Override
        public PrgState execute(PrgState state) throws MyException {

            MyIDictionary<String, Value> symTable = state.getSymTable();
            if(symTable.isDefined(name) == true){
                throw  new MyException("variable already declared");
            }
            if(this.typ instanceof IntType) {
                IntType v = new IntType();
                symTable.put(name,v.defaultValue());
            }
            else if(this.typ instanceof BoolType){
                BoolType b = new BoolType();
                symTable.put(name,b.defaultValue());
            }
            else if(this.typ instanceof StringType){
                StringType s = new StringType();
                symTable.put(name,s.defaultValue());
            }
            else if(this.typ instanceof RefType){
                RefType r = new RefType(((RefType) this.typ).inner);
                symTable.put(name,r.defaultValue());
            }
            return null;
        }

        @Override
        public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
            typeEnv.put(name,typ);
            return typeEnv;
        }

    }

    class NopStmt implements IStmt{
        @Override
        public PrgState execute(PrgState state) throws MyException {
            return null;
        }

        @Override
        public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
            return typeEnv;
        }
    }




class New implements IStmt{
    private String var_name;
    private Exp expression;

    New(String var_name, Exp expression){
        this.var_name = var_name;
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "New{" +
                "var_name='" + var_name + '\'' +
                ", expression=" + expression +
                '}';
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, IOException {

        MyIDictionary<String, Value> symTable = state.getSymTable();
        if(!symTable.isDefined(this.var_name)){
           throw new MyException("variable not defined");
        }
        Value v = this.expression.eval(symTable,state.get_heap());
        RefValue r = (RefValue) symTable.getValue(var_name);

        if(!(v.getType().equals(r.locationType))){
            throw new MyException("incorrect type");
        }

        MyIHeap<Integer,Value> heap = state.get_heap();
        int location = heap.allocate(v);
        Value vv = new IntValue(location);
        symTable.update(var_name,new RefValue(location, r.locationType));
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typevar = typeEnv.lookup(this.var_name);
        Type typexp = this.expression.typecheck(typeEnv);
        if (typevar.equals(new RefType(typexp)))
            return typeEnv;
        else
            throw new MyException("NEW stmt: right hand side and left hand side have different types ");
    }
}







class ArithExp implements Exp{
    Exp e1;
    Exp e2;
    int op; //1-plus, 2-minus, 3-star, 4-divide

    public ArithExp(int op,Exp e1, Exp e2) {
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;
    }

    @Override
    public String toString() {
        return "ArithExp{" +
                "e1=" + e1 +
                ", e2=" + e2 +
                ", op=" + op +
                '}';
    }

    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Integer,Value> hp) throws MyException{
        Value v1,v2;
        v1= e1.eval(tbl,hp);
        if (v1.getType().equals(new IntType())) {
            v2 = e2.eval(tbl,hp);
            if (v2.getType().equals(new IntType())) {
                IntValue i1 = (IntValue)v1;
                IntValue i2 = (IntValue)v2;
                int n1,n2;
                n1= ((IntValue) v1).getVal();
                n2 = ((IntValue) v2).getVal();
                if (op==1) return new IntValue(n1+n2);
                if (op ==2) return new IntValue(n1-n2);
                if(op==3) return new IntValue(n1*n2);
                if(op==4)
                    if(n2==0) throw new MyException("division by zero");
                    else return new IntValue(n1/n2);
            }else
                throw new MyException("second operand is not an integer");
        }else
            throw new MyException("first operand is not an integer");
        return v1;
    }

    @Override
    public String get_id() {
        return null;
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typ1, typ2;
        typ1=e1.typecheck(typeEnv);
        typ2=e2.typecheck(typeEnv);
        if (typ1.equals(new IntType())) {
            if (typ2.equals(new IntType())) {
                return new IntType();
            } else
            throw new MyException("second operand is not an integer");
        }else
        throw new MyException("first operand is not an integer");
    }
}


class ValueExp implements Exp{
    Value e;

    public ValueExp(Value v) {
        this.e = v;
    }

    @Override
    public String toString() {
        return "ValueExp{" +
                "e=" + e +
                '}';
    }

    public Value eval(MyIDictionary<String, Value> tbl,MyIHeap<Integer,Value> hp) throws MyException{return e;}

    @Override
    public String get_id() {
        return null;
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return e.getType();
    }
}


class CompareExp implements Exp {
    Exp e1, e2;
    String operand;

    CompareExp(Exp e1, Exp e2, String operand) {
        this.e1 = e1;
        this.e2 = e2;
        this.operand = operand;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl,MyIHeap<Integer,Value> hp) throws MyException {
        Value v1 = e1.eval(tbl,hp);
        Value v2 = e2.eval(tbl,hp);
        IntValue v1Int = (IntValue) v1;
        IntValue v2Int = (IntValue) v2;
        if (this.operand == "<") {
            if (v1Int.getVal() < v2Int.getVal()) {
                return new BoolValue(true);
            } else {
                return new BoolValue(false);
            }

        }
        if (this.operand == "<=") {
            if (v1Int.getVal() <= v2Int.getVal()) {
                return new BoolValue(true);
            } else {
                return new BoolValue(false);
            }

        }
        if (this.operand == ">") {
            if (v1Int.getVal() > v2Int.getVal()) {
                return new BoolValue(true);
            } else {
                return new BoolValue(false);
            }

        }
        if(this.operand == ">="){
            if(v1Int.getVal() >= v2Int.getVal()){
                return new BoolValue(true);
            }
            else{
                return new BoolValue(false);
            }

        }
        if(this.operand == "=="){
            if(v1Int.getVal() == v2Int.getVal()){
                return new BoolValue(true);
            }
            else{
                return new BoolValue(false);
            }

        }
        if(this.operand == "!="){
            if(v1Int.getVal() != v2Int.getVal()){
                return new BoolValue(true);
            }
            else{
                return new BoolValue(false);
            }

        }
    return new BoolValue(false);
    }


    @Override
    public String get_id() {
        return null;
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type t1,t2;
        t1 = e1.typecheck(typeEnv);
        t2 = e2.typecheck(typeEnv);
        if(t1.equals(new IntType())){
            if(t2.equals(new IntType())){
                return new IntType();
            }
            else{
                throw new MyException("second operand is not integer");
            }
        }else{
            throw new MyException("first operand is not integer");
        }
    }
}


class LogicExp implements Exp{
    Exp e1;
    Exp e2;
    int op; // 1 - and 2 - or;

    public Value eval(MyIDictionary<String, Value> tbl,MyIHeap<Integer,Value> hp) throws MyException{
        Value v1,v2;
        v1= e1.eval(tbl,hp);
        if (v1.getType().equals(new BoolType())) {
            v2 = e2.eval(tbl,hp);
            if (v2.getType().equals(new BoolType())) {
                BoolValue b1 = (BoolValue)v1;
                BoolValue b2 = (BoolValue)v2;
                boolean n1,n2;
                n1= ((BoolValue) v1).getVal();
                n2 = ((BoolValue) v2).getVal();
                if (op==1) return new BoolValue(n1 && n2);
                if (op ==2) return new BoolValue(n1 || n2);

            }else
                throw new MyException("second operand is not a boolean");
        }else
            throw new MyException("first operand is not a boolean");
        return v1;





    }

    @Override
    public String get_id() {
        return null;
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type t1,t2;
        t1 = e1.typecheck(typeEnv);
        t2 = e2.typecheck(typeEnv);
        if(t1.equals(new BoolType())){
            if(t2.equals(new BoolType())){
                return new BoolType();
            }
            else{
                throw new MyException("second operand is not boolean");
            }
        }else{
            throw new MyException("first operand is not boolean");
        }

    }
}


class VarExp implements Exp{
    public String id;

    public VarExp(String v) {
        this.id = v;
    }
    @Override
    public String get_id(){
        return this.id;
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv.lookup(id);
    }

    @Override
    public String toString() {
        return "VarExp{" +
                "id='" + id + '\'' +
                '}';
    }

    public Value eval(MyIDictionary<String, Value> tbl,MyIHeap<Integer,Value> hp) throws MyException
    {return tbl.lookup(id);}
 }

 class rH implements Exp{
    public Exp e;

    rH(Exp e){
        this.e = e;
    }

     @Override
     public String toString() {
         return "rH{" +
                 "e=" + e +
                 '}';
     }

     @Override
     public Value eval(MyIDictionary<String, Value> tbl,MyIHeap<Integer,Value> hp) throws MyException {
        RefValue v;
         try {
              v = (RefValue) this.e.eval(tbl,hp);
         }catch (RuntimeException e){
             throw new MyException("not a ref value");
         }
         if(!hp.is_defined(v.address)){
             throw new MyException("invalid adressing");
         }
         return hp.get_value(v.address);
     }

     @Override
     public String get_id() {
         return null;
     }

     @Override
     public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
         Type typ=e.typecheck(typeEnv);
         if (typ instanceof RefType) {
             RefType reft =(RefType) typ;
             return reft.getInner();
         } else
             throw new MyException("the rH argument is not a Ref Type");
     }

 }


 class wH implements IStmt{
     public String var_name;
     public Exp exp;

     wH(String var_name,Exp exp){
         this.var_name = var_name;
         this.exp = exp;
     }

     @Override
     public String toString() {
         return "wH{" +
                 "var_name='" + var_name + '\'' +
                 ", exp=" + exp +
                 '}';
     }

     @Override
     public PrgState execute(PrgState state) throws MyException, IOException {
         if(!state.getSymTable().isDefined(this.var_name)){
             throw new MyException("undifined variable");
         }
         if(!(state.getSymTable().getValue(this.var_name) instanceof RefValue)){
             throw new MyException("not a ref type");
         }
         RefValue v = (RefValue) state.getSymTable().getValue(this.var_name);
         if(!(state.get_heap().is_defined(v.address))){
             throw new MyException("undefined");
         }
         Value vv = this.exp.eval(state.getSymTable(),state.get_heap());
         if(!(state.get_heap().get_value(v.address).getType().equals(vv.getType()))){
             throw new MyException("incorrect value");
         }
         state.get_heap().update(v.address,vv);

         return null;
     }

     @Override
     public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
         Type typevar = typeEnv.lookup(this.var_name);
         Type typexp = this.exp.typecheck(typeEnv);
         if (typevar.equals(new RefType(typexp)))
             return typeEnv;
         else
             throw new MyException("WH stmt: right hand side and left hand side have different types ");
     }
 }

 class While implements IStmt {
     Exp exp;
     IStmt statement;

     While(Exp exp, IStmt statement) {
         this.exp = exp;
         this.statement = statement;
     }

     @Override
     public PrgState execute(PrgState state) throws MyException, IOException {
         BoolValue b = (BoolValue) this.exp.eval(state.getSymTable(), state.get_heap());
         MyIStack s = state.getStk();
         if (b.getVal() == true) {
             s.push(this);
             s.push(this.statement);

         }

         return null;
     }

     @Override
     public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
         Type t = this.exp.typecheck(typeEnv);
         if (t.equals(new BoolType())) {
             return typeEnv;
         } else {
             throw new MyException("not boolean condition in while");
         }
     }
 }

     abstract class Command {
         private String key, description;

         public Command(String key, String description) {
             this.key = key;
             this.description = description;
         }

         public abstract void execute();

         public String getKey() {
             return key;
         }

         public String getDescription() {
             return description;
         }
     }

     class ExitCommand extends Command {
         public ExitCommand(String key, String desc) {
             super(key, desc);
         }

         @Override
         public void execute() {
             System.exit(0);
         }
     }


     class forkStmt implements IStmt {
         IStmt program;

         forkStmt(IStmt program) {
             this.program = program;
         }

         @Override
         public String toString() {
             return "forkStmt{" +
                     "program=" + program.toString() +
                     '}';
         }

         @Override
         public PrgState execute(PrgState state) throws MyException, IOException {
             MyIFileTable myFileTable1 = new MyFileTable<String, BufferedReader>();
             MyIStack stk1 = new MyStack<IStmt>();
             MyIDictionary<String, Value> sym = new MyDictionary<String, Value>();
             MyIDictionary<String, Value> symtbl = state.getSymTable();
             MyISemaphore semaphoreTable = state.get_semaphore_table();
             sym.setContent((Hashtable<String, Value>) symtbl.getContent().clone());
             MyIList<String> ot1 = new MyList();
             myFileTable1 = state.getFileTable();
             MyIHeap heap1 = state.get_heap();
             ot1 = state.getLst();


             PrgState thread = new PrgState(stk1, sym, ot1, myFileTable1, heap1,semaphoreTable, this.program);
             return thread;
         }

         @Override
         public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
             this.program.typecheck(typeEnv);
             return typeEnv;
         }
     }

     class newBarrier implements IStmt{

        private String varName;
        private Exp exp;

        newBarrier(String varName, Exp exp){
            this.varName = varName;
            this.exp = exp;
        }

         @Override
         public String toString() {
             return "newBarrier{" +
                     "varName='" + varName + '\'' +
                     ", exp=" + exp +
                     '}';
         }

         @Override
         public PrgState execute(PrgState state) throws MyException, IOException {
             Value number = exp.eval(state.getSymTable(), state.get_heap());
             IntValue nr = (IntValue)number;
             Integer nr2 = nr.getVal();
             ArrayList<Integer> emptyList = new ArrayList<>();
             pair newPair = new pair(nr2,emptyList);
             int entry = state.get_semaphore_table().allocate(newPair);

             if(state.getSymTable().lookup(this.varName) == null){
                 state.getSymTable().put(this.varName,new IntValue(entry));

             }
             else{
                 state.get_semaphore_table().update(entry,newPair);
             }


            return null;
         }

         @Override
         public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
             return typeEnv;
         }
     }


     /*class await implements IStmt{


        String barrierName;

        await(String barrierName){
            this.barrierName = barrierName;
        }

         @Override
         public String toString() {
             return "await{" +
                     "barrierName='" + barrierName + '\'' +
                     '}';
         }

         @Override
         public PrgState execute(PrgState state) throws MyException, IOException {
            Value foundIndex = state.getSymTable().lookup(barrierName);
            if(foundIndex == null){
                throw new MyException("invalid barrier name");
            }
             IntValue index = (IntValue) foundIndex;
            Integer index2 = index.getVal();
            pair pairFromBarrierTable = state.get_barrier_table().get_value(index2);
            if(pairFromBarrierTable == null){
                throw new MyException("invalid index idk");
            }

            int howManyThreadsAwait = pairFromBarrierTable.how_many_threads_wait();
            int howManyThreadsShouldWait = pairFromBarrierTable.get_length_of_barrier();

            if(howManyThreadsShouldWait > howManyThreadsAwait){
                if(pairFromBarrierTable.this_thrad_awaits(state.actualId)){
                    IStmt await = new await(this.barrierName);
                    state.getStk().push(await);
                }
                else{
                    pairFromBarrierTable.add_thread_to_wait(state.actualId);
                    IStmt await = new await(this.barrierName);
                    state.getStk().push(await);
                }
            }

            else{
                //do nothing
            }




             return null;
         }

         @Override
         public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
             return typeEnv;
         }
     }
*/

     class Case{
        String type;
        Exp condition;
        IStmt toExec;

        Case(String type,Exp exp, IStmt istmt){
            this.type = type;
            this.condition = exp;
            this.toExec = istmt;
        }

         @Override
         public String toString() {
             return "Case{" +
                     "type='" + type + '\'' +
                     ", condition=" + condition +
                     ", toExec=" + toExec +
                     '}';
         }

         String get_type(){
            return this.type;
        }
        Exp get_cond(){
            return this.condition;
        }
        IStmt get_exec(){
            return this.toExec;
         }


     }

     class Switch implements IStmt{
            Exp condition;
            ArrayList<Case> cases = new ArrayList<>();

         Switch(Exp cond, ArrayList<Case> cases){
                this.condition = cond;
                this.cases = cases;
            }

         @Override
         public String toString() {
             return "Switch{" +
                     "condition=" + condition +
                     ", cases=" + cases +
                     '}';
         }

         @Override
         public PrgState execute(PrgState state) throws MyException, IOException {
             IntValue v1 = (IntValue) this.condition.eval(state.getSymTable(),state.get_heap());
             for (Case c : this.cases){
                 if(c.get_type() == "default"){
                     state.getStk().push(c.toExec);
                     return null;
                 }
                IntValue  v2 = (IntValue) c.get_cond().eval(state.getSymTable(),state.get_heap());

                 if(v1.getVal() == v2.getVal()){
                     state.getStk().push(c.toExec);
                     return null;
                 }
             }

             return null;
         }

         @Override
         public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
             int i = 5;
             System.out.println(i);
           /*  for (Case c : this.cases){
                 for(Case c2 : this.cases){
                     if(){

                         throw new MyException("invalid types in conditions");
                     }
                 }
             }*/

             for(Case c : this.cases){
                 c.toExec.typecheck(typeEnv);
             }
             return typeEnv;
         }

     }




     class CreateSemaphore implements IStmt{
         VarExp name;
         Exp n;
         static  ReentrantLock lock = new ReentrantLock();
         CreateSemaphore(VarExp name, Exp n){
             this.name = name;
             this.n = n;
         }

         @Override
         public String toString() {
             return "CreateSemaphore{" +
                     "name=" + name +
                     ", n=" + n +
                     '}';
         }

         @Override
         public PrgState execute(PrgState state) throws MyException, IOException {
             lock.lock();
             int number = 0;

             try {
                 Value v = this.n.eval(state.getSymTable(), state.get_heap());
                 IntValue v2 = (IntValue) v;
                 number = v2.getVal();
             }catch(MyException e){
                 lock.unlock();
                 System.out.println(e.get_error());
                 return null;
             }

             ArrayList<Integer> emptyList = new ArrayList<>();
             pair newPair = new pair(number,emptyList);
             int entry = state.get_semaphore_table().allocate(newPair);

             if(state.getSymTable().lookup(this.name.id) == null){
                 lock.unlock();
                 throw new MyException("not declared cnt");

             }
             else{
                 state.get_semaphore_table().update(entry,newPair);
             }




             lock.unlock();
             return null;
         }

         @Override
         public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {

             int j = 5;
             System.out.println(j);

           try{
               IntType i = (IntType) this.n.typecheck(typeEnv);
           }catch (MyException e){
               System.out.println("invalid 1");
               return null;
           }

           try{
               IntType i = (IntType) this.name.typecheck(typeEnv);
           } catch (MyException e) {
               System.out.println("invalid 1");
               return null;
           }
             return typeEnv;
         }
     }


     class aquire implements IStmt{
        VarExp cnt;

        aquire(VarExp cnt){
            this.cnt = cnt;
        }

         @Override
         public String toString() {
             return "aquire{" +
                     "cnt=" + cnt +
                     '}';
         }

         @Override
         public PrgState execute(PrgState state) throws MyException, IOException {
             Value index = state.getSymTable().lookup(cnt.id);
             if(index == null){
                 throw new MyException("invalid aquire");
             }
             IntValue index2 = (IntValue) index;
             int index3 = ((IntValue) index).getVal();

             pair pairFromSemaphoreTable = state.get_semaphore_table().get_value(index3);
             if(pairFromSemaphoreTable == null){
                 throw new MyException("invalid index idk");
             }

             if(pairFromSemaphoreTable.get_length_of_semaphore() > pairFromSemaphoreTable.how_many_threads_wait()){
                 if(!pairFromSemaphoreTable.this_thrad_awaits(state.actualId)){
                     pairFromSemaphoreTable.add_thread_to_wait(state.actualId);
                 }
             }
             else{
                 state.getStk().push(new aquire(this.cnt));
             }








             return null;
         }

         @Override
         public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {

            int j = 5;
            System.out.println(j);
            try{
                IntType i = (IntType)this.cnt.typecheck(typeEnv);
            }catch (MyException e){
                System.out.println("error 3");
                return null;
            }

             return typeEnv;
         }
     }



     class release implements IStmt{
         VarExp cnt;

         release(VarExp cnt){
             this.cnt = cnt;
         }

         @Override
         public String toString() {
             return "release{" +
                     "cnt=" + cnt +
                     '}';
         }

         @Override
         public PrgState execute(PrgState state) throws MyException, IOException {
             Value index = state.getSymTable().lookup(cnt.id);
             if(index == null){
                 throw new MyException("invalid release");
             }
             IntValue index2 = (IntValue) index;
             int index3 = ((IntValue) index).getVal();

             pair pairFromSemaphoreTable = state.get_semaphore_table().get_value(index3);
             if(pairFromSemaphoreTable == null){
                 throw new MyException("invalid index idk");
             }

             if(pairFromSemaphoreTable.this_thrad_awaits(state.actualId)){
                 pairFromSemaphoreTable.remove_from_list(state.actualId);
             }

             return null;
         }

         @Override
         public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
             return typeEnv;
         }
     }








