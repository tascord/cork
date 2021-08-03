package xyz.tascord.Parser;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.ArrayList;

import xyz.tascord.Parser.Token.TokenType;

public class Parser {

    public Parser() {
        ;
    }

    public void Parse(String input) {

        ArrayList<Token> tokens = this.Tokenize(input);
        Traverse(tokens);

    }

    public ArrayList<Token> Tokenize(String input) {

        String buffer = "";
        ArrayList<Token> tokens = new ArrayList<>();

        // Tokenization
        CharacterIterator iterator = new StringCharacterIterator(input);
        while (iterator.current() != CharacterIterator.DONE) {

            char current = iterator.current();
            iterator.next();

            // Handle special characters
            switch (current) {

                case ')':
                    if (buffer.length() > 1)
                        tokens.add(new Token(TokenType.LITERAL, buffer));
                    tokens.add(new Token(TokenType.CLOSE));
                    buffer = "";
                    continue;

                case '}':
                    if (buffer.length() > 1)
                        tokens.add(new Token(TokenType.LITERAL, buffer));
                    tokens.add(new Token(TokenType.FNCLOSE));
                    buffer = "";
                    continue;

                case '(':
                    if (buffer.length() > 1)
                        tokens.add(new Token(TokenType.LITERAL, buffer));
                    tokens.add(new Token(TokenType.OPEN));
                    buffer = "";
                    continue;

                case '{':
                    if (buffer.length() > 1)
                        tokens.add(new Token(TokenType.LITERAL, buffer));
                    tokens.add(new Token(TokenType.FNOPEN));
                    buffer = "";
                    continue;

                case ';':
                    if (buffer.length() > 1)
                        tokens.add(new Token(TokenType.LITERAL, buffer));
                    tokens.add(new Token(TokenType.END));
                    buffer = "";
                    continue;

            }

            // Skip spaces
            if (current != ' ' && current != '\n') {
                buffer += current;
                continue;
            }

            switch (buffer) {

                case "fn":
                    tokens.add(new Token(TokenType.FUNCTION));
                    break;

                default:
                    continue;

            }

            buffer = "";

        }

        return tokens;

    }

    public void Traverse(ArrayList<Token> tokens) {

        Context ctxProgram = new Context();
        Context ctxCurrent = ctxProgram;

        tokens.forEach(token -> {

            if(!ctxCurrent.valid()) throw new Error("Invalid token type \"" + ctxCurrent.inputBuffer.get(ctxCurrent.inputBuffer.size() -1 ).type.toString() + "\". Expected \"" + ctxCurrent.expectedTypes.get(ctxCurrent.inputBuffer.size() - 1).possibilities.toString() + "\"");
            ctxCurrent.inputBuffer.add(token);

            if(token.type == TokenType.FUNCTION) {
                ctxCurrent.expectedTypes.add(new TokenOr(TokenType.LITERAL));
            }

        });

        System.out.println(ctxCurrent.toString());

    }       

}
