package sample;

import java.util.ArrayList;

public class GUIRepo {
    ArrayList<IStmt> programStatements;
    GUIRepo(ArrayList<IStmt> programStatements){
        this.programStatements = programStatements;
    }

    public ArrayList<IStmt> get_program_statements(){
        return this.programStatements;
    }


}
