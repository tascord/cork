package xyz.tascord.DataTypes;

public class GenericValue {

    public final GenericType type;
    private final Object value;

    public GenericValue(GenericType type) {
        this.type = type;
        this.value = null;
    }

    public GenericValue(GenericType type, Object value) {
        this.type = type;
        this.value = value;
    }

    public Object GetValue() {
        return value;
    }

    public String Stringify() {
        return "null";
    }

}
