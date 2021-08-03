package xyz.tascord.DataTypes.interfaces;

import xyz.tascord.DataTypes.GenericValue;

public interface IGenericIndexable {
    
    public GenericValue GetIndex(GenericValue index);
    public void SetIndex(GenericValue index, GenericValue value);

}
