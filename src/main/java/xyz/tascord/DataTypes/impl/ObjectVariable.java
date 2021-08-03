package xyz.tascord.DataTypes.impl;

import java.util.HashMap;

import xyz.tascord.DataTypes.GenericType;
import xyz.tascord.DataTypes.GenericValue;
import xyz.tascord.DataTypes.GenericVariable;
import xyz.tascord.DataTypes.interfaces.IGenericIndexable;

public class ObjectVariable extends GenericVariable implements IGenericIndexable {

    public ObjectVariable() {
        super(GenericType.OBJECT);
    }
    
    private HashMap<String, GenericValue> value = new HashMap<>();

    @Override
    public GenericValue Index(GenericValue value) {
        return this.value.get(value.Stringify());
    }

    @Override
    public String Stringify() {
        return "{Object}";
    }

}
