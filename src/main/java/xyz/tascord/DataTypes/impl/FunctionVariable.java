package xyz.tascord.DataTypes.impl;

import java.util.ArrayList;

import xyz.tascord.DataTypes.GenericType;
import xyz.tascord.DataTypes.GenericVariable;
import xyz.tascord.DataTypes.interfaces.IGenericCallable;

public class FunctionVariable extends GenericVariable implements IGenericCallable {

    public FunctionVariable() {
        super(GenericType.FUNCTION);
    }

    @Override
    public void Call(ArrayList<GenericVariable> arguments) {
        return;        
    }
    
}
