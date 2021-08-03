package xyz.tascord.Parser;

import java.util.ArrayList;
import java.util.HashMap;

import xyz.tascord.DataTypes.GenericVariable;
import xyz.tascord.Parser.Token.TokenType;

public class Context {
  
    public Context() { ; }
    public Context(Context parentScope, String contextName) {
        this.parentScope = parentScope;
        this.contextName = contextName;
    }

    public Context parentScope = null;
    public String contextName = "Cork.Body";
    public HashMap<String, GenericVariable> scopedVariables = new HashMap<>();

    public ArrayList<Token> inputBuffer = new ArrayList<>();
    public ArrayList<TokenOr> expectedTypes = new ArrayList<>();
 
    public ArrayList<AbstractCall> body = new ArrayList<>();

    public Boolean valid() {

        int index = this.expectedTypes.size() - 1; 

        if(index == -1) return true;
        if(expectedTypes.size() - 2 < index) return true;

        System.out.println(index + ") " + this.inputBuffer.toString() + ", " + this.expectedTypes.toString());
        TokenOr expectedType = expectedTypes.get(index);

        TokenType currentType = this.inputBuffer.get(this.inputBuffer.size() - 1).type;
        if(!expectedType.repeat) this.expectedTypes.remove(index);

        boolean validity = expectedTypes.get(index).possibilities.contains(currentType);
        
        if(!validity && expectedType.repeat) {
            this.expectedTypes.remove(index);
            return valid();
        } 

        return validity;

    }
    
    public String toString() {

        return "Context{" +
               "parentScope: " + (parentScope == null ? "null" : parentScope.contextName) + ", " +  
               "contextName: " + contextName + ", " +
               "scopedVariables: " + scopedVariables.toString() + ", " + 
               "inputBuffer: " + inputBuffer.toString() + ", " +
               "expectedTypes: " + this.expectedTypes.toString() + ", " + 
               "body: " + this.body.toString() + "}";   

    }

}
