package xyz.tascord.DataTypes.impl;

import java.util.ArrayList;

import xyz.tascord.DataTypes.GenericType;
import xyz.tascord.DataTypes.GenericValue;
import xyz.tascord.DataTypes.GenericVariable;
import xyz.tascord.DataTypes.interfaces.IGenericIndexable;

public class ArrayVairable extends GenericVariable implements IGenericIndexable {
    
    public ArrayVairable() {
        super(GenericType.CONTAINER);
    }

    public ArrayVairable(ArrayList<GenericVariable> values) {
        super(GenericType.CONTAINER);
        this.value = values;
    }

    public ArrayVairable(GenericVariable value) {
        super(GenericType.CONTAINER);
        this.value.add(value);
    }

    private ArrayList<GenericVariable> value = new ArrayList<>();

    @Override
    public GenericValue Index(GenericValue value) {
        if(value.type != GenericType.NUMERICAL) return null;
        int index = (int) value.GetValue();

        if(this.value.size() < index) return null;
        else {

            return this.value.get(index);

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
