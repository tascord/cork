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

    public ObjectVariable(HashMap<String, GenericValue> value) {
        super(GenericType.OBJECT);
        this.value = value;
    }
    
    private HashMap<String, GenericValue> value = new HashMap<>();

    @Override
    public GenericValue GetIndex(GenericValue index) {
        return this.value.get(index.Stringify());
    }

    @Override
    public void SetIndex(GenericValue index, GenericValue value) {
        this.value.put(index.Stringify(), value);
    }

    @Override
    public String Stringify() {
        return "{Object}";
    }


}
