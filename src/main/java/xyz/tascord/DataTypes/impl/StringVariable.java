package xyz.tascord.DataTypes.impl;

import xyz.tascord.DataTypes.GenericType;
import xyz.tascord.DataTypes.GenericValue;
import xyz.tascord.DataTypes.GenericVariable;
import xyz.tascord.DataTypes.interfaces.IGenericIndexable;
import xyz.tascord.DataTypes.interfaces.IGenericOperable;

public class StringVariable extends GenericVariable implements IGenericIndexable, IGenericOperable {

    public StringVariable() {
        super(GenericType.STRING);
    }

    public StringVariable(String value) {
        super(GenericType.STRING);
        this.value = value;
    }

    private String value;

    @Override
    public Object GetValue() {
        return value;
    }

    @Override
    public String Stringify() {
        return value;
    }

    @Override
    public GenericVariable Add(GenericValue value) {
        StringVariable result = new StringVariable();
        GenericValue resultValue = new GenericValue(GenericType.STRING, this.Stringify() + value.Stringify());
        result.SetValue(resultValue);
        return result;
    }

    @Override
    public GenericVariable Subtract(GenericValue value) {
        return null;
    }

    @Override
    public GenericVariable Multiply(GenericValue value) {
        return null;
    }

    @Override
    public GenericVariable Divide(GenericValue value) {
        return null;
    }

    @Override
    public GenericValue GetIndex(GenericValue raw_index) {

        if(raw_index.type != GenericType.NUMERICAL) throw new Error(String.format("Invalid index '%s'", raw_index.Stringify()));
        int index = (int) raw_index.GetValue();

        if(this.value.length() < index) throw new Error(String.format("Index out of bounds '%s'", index));
        else {

            return new GenericValue(GenericType.STRING, String.valueOf(this.value.charAt(index)));

        }
        
    }

    @Override
    public void SetIndex(GenericValue raw_index, GenericValue value) {
        if(raw_index.type != GenericType.NUMERICAL) throw new Error(String.format("Invalid index '%s'", raw_index.Stringify()));
        int index = (int) raw_index.GetValue();

        if(this.value.length() < index) throw new Error(String.format("Index out of bounds '%s'", index));
        else {

            this.value = this.value.substring(0, index - 1) + value.Stringify() + this.value.substring(index + 1);

        }
    }

    @Override
    public void SetValue(GenericValue value) {
        this.value = value.Stringify();
    }
    
}
