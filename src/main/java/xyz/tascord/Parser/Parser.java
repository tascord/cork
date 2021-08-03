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
        boolean inString = false;
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
                
                case '"':
                    inString = !inString;
                    break;

            }

            // Skip spaces
            if ((current != ' ' && current != '\n') || inString) {
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

        for(Token token : tokens) {

            if(!ctxCurrent.valid()) throw new Error("Invalid token type \"" + ctxCurrent.inputBuffer.get(ctxCurrent.inputBuffer.size() -1 ).type.toString() + "\". Expected \"" + ctxCurrent.expectedTypes.get(ctxCurrent.inputBuffer.size() - 1).possibilities.toString() + "\"");
            ctxCurrent.inputBuffer.add(token);

            // Enter functions
            if(token.type == TokenType.FNOPEN) {
                // TODO: Find name
                ctxCurrent = new Context(ctxCurrent, "Todo lol");
            }

            // Exit functions
            else if(token.type == TokenType.FNOPEN) {
                ctxCurrent = ctxCurrent.parentScope;
            }

            else if(token.type == TokenType.FUNCTION) {
                ctxCurrent.expectedTypes.add(new TokenOr(TokenType.LITERAL)); // Name
                ctxCurrent.expectedTypes.add(new TokenOr(TokenType.OPEN)); // Open Arg
                ctxCurrent.expectedTypes.add(new TokenOr(TokenType.LITERAL, true)); // Arguments
                ctxCurrent.expectedTypes.add(new TokenOr(TokenType.CLOSE)); // Close Arg
                ctxCurrent.expectedTypes.add(new TokenOr(TokenType.FNOPEN)); // Open Fn
                ctxCurrent.expectedTypes.add(new TokenOr(TokenType.FNCLOSE)); // Close Fn
            }

        };

        System.out.println(ctxCurrent.toString());

    }       

}
