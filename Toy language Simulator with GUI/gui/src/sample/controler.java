package sample;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

interface IControler{




    void all_steps() throws MyException, IOException;
    void oneStepForAllPrg(ArrayList<PrgState> programs);
    ArrayList<PrgState> removeCompletedPrg(ArrayList<PrgState> inPrgList);
}

class Controler implements IControler{
    Repository repository;
    ExecutorService executor;
    Controler(){}
    Controler(Repository repository){
        this.repository = repository;
    }

    Map<Integer, Value> unsafeGarbageCollector(List<Integer> symTableAddr, Map<Integer, Value>
            heap) {
        return heap.entrySet().stream().filter(e -> symTableAddr.contains(e.getKey())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
    List<Integer> getAddrFromSymTable(Collection<Value> symTableValues, Collection<Value> heapValues) {
        return Stream.concat(
                heapValues.stream()
                        .filter(v->v instanceof RefValue)
                        .map(v-> {RefValue v1 = (RefValue)v; return v1.getAddress();}),
                symTableValues.stream()
                        .filter(v->v instanceof RefValue)
                        .map(v-> {RefValue v1 = (RefValue)v; return v1.getAddress();})
        ).collect(Collectors.toList());
    }



    @Override
    public void all_steps() throws MyException, IOException {
        executor = Executors.newFixedThreadPool(2);
        //remove the completed programs
        ArrayList<PrgState> prgList=removeCompletedPrg(repository.getPrgList());

            prgList.stream().forEach(state->state.get_heap().setContent((HashMap<Integer, Value>) unsafeGarbageCollector(
                    getAddrFromSymTable(state.getSymTable().getContent().values(),state.get_heap().getContent().values()),
                    state.get_heap().getContent())));
            oneStepForAllPrg(prgList);
            //remove the completed programs
            // prgList=removeCompletedPrg(repository.getPrgList());

        executor.shutdownNow();
        //HERE the repository still contains at least one Completed Prg
        // and its List<PrgState> is not empty. Note that oneStepForAllPrg calls the method
        //setPrgList of repository in order to change the repository

        // update the repository state
       // repository.setPrgList(prgList);
    }

    @Override
    public void oneStepForAllPrg(ArrayList<PrgState> programs){
        programs.forEach(prg-> {
            try {
                repository.logPrgStateExec(prg);
            } catch (MyException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        ArrayList<Callable<PrgState>> callList = (ArrayList<Callable<PrgState>>) programs.stream()
                .map((PrgState p) -> (Callable<PrgState>)(() -> {return p.one_step();}))
                .collect(Collectors.toList());
        ArrayList<PrgState> newPrgList = new ArrayList<PrgState>();
        try {
             newPrgList = (ArrayList<PrgState>) executor.invokeAll(callList). stream()
                    . map(future -> { try { return future.get();}
                    catch(InterruptedException | ExecutionException e) {
                        System.out.println(e.getMessage());
                    }

                        return null;
                    }).filter(p -> p!=null)
                                .collect(Collectors.toList());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        programs.addAll(newPrgList);
        programs.forEach(prg -> {
            try {
                repository.logPrgStateExec(prg);
            } catch (MyException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        repository.setPrgList(programs);


    }


    /*@Override
    public void all_steps() throws MyException, IOException {
        PrgState state = this.repository.getCrtPrg();
        MyIStack<IStmt> stk= state.getStk();
        this.repository.logPrgStateExec();
        while(!stk.isEmpty()) {
        PrgState newProgramState = one_step(state);
        stk = newProgramState.getStk();
            state.get_heap().setContent((HashMap<Integer, Value>) unsafeGarbageCollector(
                    getAddrFromSymTable(state.getSymTable().getContent().values(),state.get_heap().getContent().values()),
                    state.get_heap().getContent()));
        this.repository.logPrgStateExec();


        }
    }*/

    @Override
    public ArrayList<PrgState> removeCompletedPrg(ArrayList<PrgState> inPrgList) {
        return (ArrayList<PrgState>) inPrgList.stream()
                .filter(p -> p.isNotCompleted())
                .collect(Collectors.toList());
    }
}