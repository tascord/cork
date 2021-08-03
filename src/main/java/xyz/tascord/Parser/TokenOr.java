package xyz.tascord.Parser;

import java.util.ArrayList;

import xyz.tascord.Parser.Token.TokenType;

public class TokenOr {
    
    public ArrayList<TokenType> possibilities = new ArrayList<>();
    public boolean repeat = false;

    public TokenOr(TokenType type) {
        this.possibilities.add(type);
    }

    public TokenOr(ArrayList<TokenType> types) {
        this.possibilities = types;
    }

    public TokenOr(TokenType type, boolean repeat) {
        this.possibilities.add(type);
        this.repeat = repeat;
    }

    public TokenOr(ArrayList<TokenType> types, boolean repeat) {
        this.possibilities = types;
        this.repeat = repeat;
    }

    public String toString() {
        return "TokenOr[" + String.join("or", this.possibilities.stream().map(token -> token.toString()).toList()) + "]";
    }

}
