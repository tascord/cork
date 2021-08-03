package xyz.tascord.DataTypes.impl;

import java.util.ArrayList;

import xyz.tascord.DataTypes.GenericType;
import xyz.tascord.DataTypes.GenericValue;
import xyz.tascord.DataTypes.GenericVariable;
import xyz.tascord.DataTypes.interfaces.IGenericIndexable;

public class ArrayVariable extends GenericVariable implements IGenericIndexable {
    
    public ArrayVariable() {
        super(GenericType.CONTAINER);
    }

    public ArrayVariable(ArrayList<GenericValue> values) {
        super(GenericType.CONTAINER);
        this.value = values;
    }

    public ArrayVariable(GenericVariable value) {
        super(GenericType.CONTAINER);
        this.value.add(value);
    }

    private ArrayList<GenericValue> value = new ArrayList<>();

    @Override
    public GenericValue GetIndex(GenericValue raw_index) {
        if(raw_index.type != GenericType.NUMERICAL) throw new Error(String.format("Invalid index '%s'", raw_index.Stringify()));
        int index = (int) raw_index.GetValue();

        if(this.value.size() <= index) throw new Error(String.format("Index out of bounds '%s'", index));
        else {

            return this.value.get(index);

        }

    }

    @Override
    public void SetIndex(GenericValue raw_index, GenericValue value) {
        if(raw_index.type != GenericType.NUMERICAL) throw new Error(String.format("Invalid index '%s'", raw_index.Stringify()));
        int index = (int) raw_index.GetValue();

        if(this.value.size() <= index) throw new Error(String.format("Index out of bounds '%s'", index));
        else {

            this.value.set(index, value);

        }
    }

    public String Join() {
        StringBuilder builder = new StringBuilder();
        
        this.value.forEach(value -> {
            builder.append(value.Stringify());
        });

        return builder.toString();
    }

    @Override
    public String Stringify() {
        return "[Array]";
    }

}
