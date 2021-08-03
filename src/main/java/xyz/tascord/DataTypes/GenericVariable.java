package xyz.tascord.DataTypes;


public class GenericVariable extends GenericValue {

    public GenericVariable(GenericType type) {
        super(type);
    }

    public GenericVariable Cast(GenericType type) {
        return this;
    }

    public void SetValue(GenericValue value) {
        ;
    }

}
