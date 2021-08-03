package xyz.tascord.Parser;

public class Token {
    
    public enum TokenType {
        FUNCTION,
        LITERAL,
        END,
        CLOSE,
        FNCLOSE,
        OPEN,
        FNOPEN,
        PROP,
        VARDEC,
    }

    public TokenType type;
    public Object value;

    public Token(TokenType type) {
        this.type = type;
        this.value = null;
    }

    public Token(TokenType type, Object value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public String toString() {
        return "TOKEN{ type: " + this.type.toString() + ", value: " + String.valueOf(this.value) + " }";
    }

}
