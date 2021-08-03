package xyz.tascord.DataTypes.interfaces;

import xyz.tascord.DataTypes.GenericValue;

public interface IGenericOperable {
    
    public GenericValue Add(GenericValue value);
    public GenericValue Subtract(GenericValue value);
    public GenericValue Multiply(GenericValue value);
    public GenericValue Divide(GenericValue value);

}
