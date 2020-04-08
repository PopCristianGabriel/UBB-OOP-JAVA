package sample;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Stack;


public class Controller {

    private Controler myControler;
    private GUIRepo guiRepo;
    private boolean hasBeenSelected;
    Controller(){}
    Controller( Controler myControler, GUIRepo guiRepo ){

        this.myControler = myControler;
        this.guiRepo = guiRepo;
        this.hasBeenSelected = false;
    }





    @FXML
    private Pane nrStsText;

    @FXML
    private ListView<String> OutListView;

    @FXML
    private ListView<String> FileTableListView;

    @FXML
    private ListView<IStmt> ProgramStateListView;

    @FXML
    private TextField NrStatements;

    @FXML
    private TableView<HeapObject> heapTableView;

    @FXML
    private TableColumn<HeapObject, String> AdressHeap;

    @FXML
    private TableColumn<HeapObject, String> ValueHeap;

    @FXML
    private TableView<SymTableObject> SymTableTableView;

    @FXML
    private TableColumn<SymTableObject, String> SymTableVariableName;

    @FXML
    private TableColumn<SymTableObject, String> SymTableValue;


    @FXML
    private ListView<Integer> threadsListView;

    @FXML
    private ListView<String> StackListView;

    @FXML
    private Button buttonRunOneStep;


    @FXML
    private TableView<SemaphoreTableObject> SemaphoreTableView;

    @FXML
    private TableColumn<SemaphoreTableObject, String> barrierNameCollum;

    @FXML
    private TableColumn<SemaphoreTableObject, String> barrierValuesCollum;


    @FXML
    void runOneStep(ActionEvent event) throws IOException, MyException {


        if(this.threadsListView.getItems().size() == 0){
            return;
        }

        this.myControler.all_steps();
    //this.myControler.oneStepForAllPrg(this.myControler.repository.getPrgList());
    System.out.println("one step done");
        this.show_detailes_automatically();
        ArrayList<PrgState> prgList = this.myControler.removeCompletedPrg(this.myControler.repository.getPrgList());
        this.myControler.repository.setPrgList(prgList);
        this.show_all_threads();




    }

    @FXML
    private ListView<IStmt> ProgramsListView;

    @FXML
    private Button ChooseButton;



    @FXML
    void show_program_states(){
        this.ProgramStateListView.refresh();
        ArrayList<Integer> programStates = new ArrayList<Integer>();

        for(int i = 0; i < (this.myControler.repository.getPrgList().size()); i++){
            programStates.add(i);
        }
        ObservableList<Integer> programs = FXCollections.observableList(programStates);
        threadsListView.setItems(programs);

    }



        void show_semaphore_table(){
        PrgState state = this.myControler.repository.getPrgList().get(this.threadsListView.getSelectionModel().getSelectedIndex());
        MyISemaphore<Integer, pair> barrierTable = state.get_semaphore_table();

        ArrayList<SemaphoreTableObject> semaphoreTableList = new ArrayList<>();
        Set<Integer> keys = barrierTable.get_all_keys();

        for(Integer key:keys){
            SemaphoreTableObject obj = new SemaphoreTableObject(key.toString(),barrierTable.get_value(key).get_list_of_threads_awaiting());
            semaphoreTableList.add(obj);
        }
        ObservableList<SemaphoreTableObject> semaphoreTableList2 = FXCollections.observableList(semaphoreTableList);
        this.SemaphoreTableView.setItems(semaphoreTableList2);
        this.barrierNameCollum.setCellValueFactory(r->new ReadOnlyStringWrapper(r.getValue().get_name().toString()));
        this.barrierValuesCollum.setCellValueFactory(r->new ReadOnlyStringWrapper(r.getValue().get_value().toString()));
    }

    @FXML
    void show_out(){
        MyIList<String> out = this.myControler.repository.getPrgList().get(0).getLst();
        ObservableList<String> out2 = FXCollections.observableList(out.getLst());
        this.OutListView.setItems(out2);
    }


    private void show_symTable() {
        PrgState state = this.myControler.repository.getPrgList().get(this.threadsListView.getSelectionModel().getSelectedIndex());
        MyIDictionary<String, Value> symTable = state.getSymTable();

        ArrayList<SymTableObject> symList = new ArrayList<>();
        Set<String> keys = symTable.get_all_keys();

        for(String key:keys){
            SymTableObject obj = new SymTableObject(key,symTable.lookup(key));
            symList.add(obj);
        }
        ObservableList<SymTableObject> symList2 = FXCollections.observableList(symList);
        this.SymTableTableView.setItems(symList2);


        this.SymTableVariableName.setCellValueFactory(r->new ReadOnlyStringWrapper(r.getValue().get_name()));
        this.SymTableValue.setCellValueFactory(r->new ReadOnlyStringWrapper(r.getValue().get_value().toString()));

    }



    @FXML
    void show_heap(){

        MyIHeap<Integer,Value> heap = this.myControler.repository.getPrgList().get(0).get_heap();
        Set<Integer> keys2 = heap.get_all_keys();
        ArrayList<HeapObject> heapList = new ArrayList<>();
        for(Integer key : keys2){
            HeapObject o = new HeapObject(key,heap.lookup(key));
            heapList.add(o);
        }
        ObservableList<HeapObject> heapObservable = FXCollections.observableList(heapList);
        this.heapTableView.setItems(heapObservable);

        this.AdressHeap.setCellValueFactory(r->new ReadOnlyStringWrapper(r.getValue().get_gey().toString()));
       this.ValueHeap.setCellValueFactory(r->new ReadOnlyStringWrapper(r.getValue().get_value().toString()));


    }


    void show_all_threads(){

        ArrayList<Integer> programsId = new ArrayList<>();

        for(PrgState program : this.myControler.repository.getPrgList()){
            programsId.add(program.actualId);
        }


        ObservableList<Integer> programs = FXCollections.observableList(programsId);
        this.threadsListView.setItems(programs);
    }



    @FXML
    void choose_program(ActionEvent event) throws IOException, MyException {

        int selectedIndex = this.ProgramsListView.getSelectionModel().getSelectedIndex();
        MyIHeap<Integer,Value> heap1 = new MyHeap();
        String filePath1 = "F:\\proiecte facultate\\IdeaProjects\\gui\\src\\sample\\out1.txt";
        // C:\\Users\\Pop\\IdeaProjects\\lab 3-7\\src\\project\\out.txt
        MyIFileTable myFileTable1 =  new MyFileTable<String,BufferedReader>();
        MyIStack stk1 = new MyStack<IStmt>();
        MyIDictionary<String,Value> symtbl1 = new MyDictionary<>();
        MyIList<String> ot1 = new MyList();
        MyISemaphore<Integer,pair> semaphoreTable = new MySemaphore();
        PrgState program1 = new PrgState(stk1,symtbl1,ot1,myFileTable1,heap1,semaphoreTable ,this.guiRepo.get_program_statements().get(selectedIndex));

        this.myControler.repository.set_file_path(filePath1);
        this.myControler.repository.add_program(program1);

        MyIDictionary<String,Type> typeEnv = new MyDictionary<String,Type>();
        this.guiRepo.get_program_statements().get(selectedIndex).typecheck(typeEnv);



        this.show_all_threads();



    }


    void show_detailes_automatically() throws IOException {
        this.show_all_states();
        this.show_heap();
        this.show_out();
        this.show_stack();
        this.show_all_threads();
          this.show_fileTable();
          this.show_symTable();
          this.show_semaphore_table();
    }


    @FXML
    void show_detailes(ActionEvent event) throws IOException {
        this.show_all_states();
        this.show_heap();
        this.show_out();
        this.show_stack();
        this.show_all_threads();
        this.show_fileTable();
        this.show_symTable();
    }

    private void show_fileTable() {
        PrgState state = this.myControler.repository.getPrgList().get(this.threadsListView.getSelectionModel().getSelectedIndex());
        MyIFileTable<String, BufferedReader> myFileTable = state.getFileTable();
        ArrayList<String> fileTable = new ArrayList<>();
        Set<String> keys = myFileTable.get_all_keys();
        for(String key:keys){
            fileTable.add(key);
        }

        ObservableList<String> fileTable2 = FXCollections.observableList(fileTable);
        this.FileTableListView.setItems(fileTable2);
    }

    private void show_all_states(){
        this.NrStatements.setText(String.valueOf(this.myControler.repository.getPrgList().size()));
    }

    private void show_stack() throws IOException {

        PrgState state = this.myControler.repository.getPrgList().get(this.threadsListView.getSelectionModel().getSelectedIndex());

        MyIStack<IStmt> stack = (MyIStack<IStmt>) state.getStk();
        Stack<IStmt> exeStack = new Stack();
        exeStack = (Stack<IStmt>) stack.get_stck().clone();


        ArrayList<String> stackList = new ArrayList<>();

        while(!exeStack.isEmpty()){
            String item = exeStack.pop().toString();
            stackList.add(item);
        }
        BufferedWriter writer = new BufferedWriter(
                new FileWriter("F:\\proiecte facultate\\IdeaProjects\\gui\\src\\sample\\out1.txt", true));
        for(String item : stackList) {
            writer.newLine();
            writer.write(item);
        }
        ObservableList<String> StackList2 = FXCollections.observableList(stackList);
        this.StackListView.setItems(StackList2);

    }


    @FXML
    private Button showAllButton;

    @FXML
    void show_all_programs(ActionEvent event) {


        ObservableList<IStmt> programs = FXCollections.observableList(this.guiRepo.get_program_statements());
        ProgramsListView.setItems(programs);

    }

}
