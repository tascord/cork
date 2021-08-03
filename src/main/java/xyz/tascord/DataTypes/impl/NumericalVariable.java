package xyz.tascord.DataTypes.impl;

import xyz.tascord.DataTypes.GenericType;
import xyz.tascord.DataTypes.GenericValue;
import xyz.tascord.DataTypes.GenericVariable;
import xyz.tascord.DataTypes.interfaces.IGenericOperable;

public class NumericalVariable extends GenericVariable implements IGenericOperable {

    public NumericalVariable() {
        super(GenericType.NUMERICAL);
    }

    public NumericalVariable(double value) {
        super(GenericType.NUMERICAL);
        this.value = value;
    }

    private double value;
    private boolean invalid = false;
    public static String NaN = "NaN";

    @Override
    public Object GetValue() {
        return invalid ? NaN : invalid;
    }

    @Override
    public String Stringify() {
        return invalid ? NaN : String.valueOf(value);
    }

    @Override
    public GenericValue Add(GenericValue value) {
        if (value.type != GenericType.NUMERICAL)
            return null;
        return new GenericValue(GenericType.NUMERICAL, this.value + ((int) value.GetValue()));
    }

    @Override
    public GenericValue Subtract(GenericValue value) {
        if (value.type != GenericType.NUMERICAL)
            return null;
        return new GenericValue(GenericType.NUMERICAL, this.value - ((int) value.GetValue()));
    }

    @Override
    public GenericValue Multiply(GenericValue value) {
        if (value.type != GenericType.NUMERICAL)
            return null;
        return new GenericValue(GenericType.NUMERICAL, this.value * ((int) value.GetValue()));
    }

    @Override
    public GenericValue Divide(GenericValue value) {
        if (value.type != GenericType.NUMERICAL)
            return null;
        return new GenericValue(GenericType.NUMERICAL, this.value / ((int) value.GetValue()));
    }

    @Override
    public GenericVariable Cast(GenericType type) {

        switch (type) {

            case NUMERICAL:
                return this;

            case STRING:
                return new StringVariable(String.valueOf(this.value));

            case CONTAINER:
                return new ArrayVariable(this);

            default:
                return null;

        }

    }

}
