package xyz.tascord.Globals;

import java.util.ArrayList;

import xyz.tascord.DataTypes.GenericType;
import xyz.tascord.DataTypes.GenericValue;
import xyz.tascord.DataTypes.GenericVariable;
import xyz.tascord.DataTypes.impl.FunctionVariable;
import xyz.tascord.DataTypes.impl.ObjectVariable;
import xyz.tascord.DataTypes.interfaces.IGenericCallable;

public class Term extends ObjectVariable {

    public Term() { 

        this.SetIndex(new GenericValue(GenericType.STRING, "print"), new FunctionVariable(
            new IGenericCallable(){
            
                public void Call(ArrayList<GenericVariable> arguments) {

                    System.out.println(String.join(" ", arguments.stream().map(argument -> argument.toString()).toList()));

                }

            }
        ));

    }

    @Override
    public String Stringify() {
        return "{TermObject}";
    }

}
