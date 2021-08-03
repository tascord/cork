package xyz.tascord.DataTypes.impl;

import java.util.ArrayList;

import xyz.tascord.DataTypes.GenericType;
import xyz.tascord.DataTypes.GenericVariable;
import xyz.tascord.DataTypes.interfaces.IGenericCallable;

public class FunctionVariable extends GenericVariable implements IGenericCallable {

    private IGenericCallable body = null;

    public FunctionVariable() {
        super(GenericType.FUNCTION);
    }

    public FunctionVariable(IGenericCallable body) {
        super(GenericType.FUNCTION);
        this.body = body;
    }

    @Override
    public void Call(ArrayList<GenericVariable> arguments) {
        body.Call(arguments);
    }

    @Override
    public String Stringify() {
        return "(FUNCTION)";
    }
    
}
