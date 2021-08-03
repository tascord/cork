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
    public GenericValue Index(GenericValue value) {

        if(value.type != GenericType.NUMERICAL) return null;
        int index = (int) value.GetValue();

        if(this.value.length() < index) return null;
        else {

            return new GenericValue(GenericType.STRING, String.valueOf(this.value.charAt(index)));

        }
        
    }

    @Override
    public void SetValue(GenericValue value) {
        this.value = value.Stringify();
    }
    
}
