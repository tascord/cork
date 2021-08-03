package xyz.tascord.Parser;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.Arrays;

import xyz.tascord.DataTypes.GenericType;
import xyz.tascord.DataTypes.GenericValue;
import xyz.tascord.DataTypes.GenericVariable;
import xyz.tascord.Parser.Token.TokenType;
import xyz.tascord.DataTypes.impl.*;

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
                        tokens.addAll(FormatLiteral(buffer));

                    tokens.add(new Token(TokenType.CLOSE));
                    buffer = "";
                    continue;

                case '}':
                    if (buffer.length() > 1)
                        tokens.addAll(FormatLiteral(buffer));

                    tokens.add(new Token(TokenType.FNCLOSE));
                    buffer = "";
                    continue;

                case '(':
                    if (buffer.length() > 1)
                        tokens.addAll(FormatLiteral(buffer));

                    tokens.add(new Token(TokenType.OPEN));
                    buffer = "";
                    continue;

                case '{':
                    if (buffer.length() > 1)
                        tokens.addAll(FormatLiteral(buffer));

                    tokens.add(new Token(TokenType.FNOPEN));
                    buffer = "";
                    continue;

                case ';':
                    if (buffer.length() > 1)
                        tokens.addAll(FormatLiteral(buffer));

                    tokens.add(new Token(TokenType.END));
                    buffer = "";
                    continue;

                case '=':
                    if (buffer.length() > 1)
                        tokens.addAll(FormatLiteral(buffer));

                    tokens.add(new Token(TokenType.OPERATOR));
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

                case "let":
                    tokens.add(new Token(TokenType.VARDEC));
                    break;

                default:
                    continue;

            }

            buffer = "";

        }

        return tokens;

    }

    public ArrayList<Token> FormatLiteral(String input) {

        ArrayList<Token> tokens = new ArrayList<>();

        String buffer = "";

        boolean string = false;
        boolean index = false;

        for (char c : input.toCharArray()) {

            if (c == '"') {
                string = !string;
            }

            else if (c == '.') {

                tokens.add(new Token(index ? TokenType.INDEX : TokenType.LITERAL, buffer));
                buffer = "";

                index = true;
                continue;

            }

            buffer += c;

        }

        if (buffer.length() > 0)
            tokens.add(new Token((!string && index) ? TokenType.INDEX : TokenType.LITERAL, buffer));

        return tokens;

    }

    public void Traverse(ArrayList<Token> tokens) {

        Context ctxProgram = new Context();
        Context ctxCurrent = ctxProgram;

        for (int i = 0; i < tokens.size(); i++) {

            Token token = tokens.get(i);

            if (!ctxCurrent.valid())
                throw new Error("Invalid token type \""
                        + ctxCurrent.inputBuffer.get(ctxCurrent.inputBuffer.size() - 1).type.toString()
                        + "\". Expected \""
                        + ctxCurrent.expectedTypes.get(ctxCurrent.inputBuffer.size() - 1).possibilities.toString()
                        + "\"");
            ctxCurrent.inputBuffer.add(token);

            // Enter functions
            if (token.type == TokenType.FNOPEN) {
                // TODO: Find name & Add support for calling (convert context to function?)
                ctxCurrent = new Context(ctxCurrent, "Todo lol");
                continue;
            }

            // Exit functions
            else if (token.type == TokenType.FNCLOSE) {
                ctxCurrent = ctxCurrent.parentScope;
                continue;
            }

            // Function gunk
            else if (token.type == TokenType.FUNCTION) {
                ctxCurrent.expectedTypes.add(new TokenOr(TokenType.LITERAL)); // Name
                ctxCurrent.expectedTypes.add(new TokenOr(TokenType.OPEN)); // Open Arg
                ctxCurrent.expectedTypes.add(new TokenOr(TokenType.LITERAL, true)); // Arguments
                ctxCurrent.expectedTypes.add(new TokenOr(TokenType.CLOSE)); // Close Arg
                ctxCurrent.expectedTypes.add(new TokenOr(TokenType.FNOPEN)); // Open Fn
                ctxCurrent.expectedTypes.add(new TokenOr(TokenType.FNCLOSE)); // Close Fn
            }

            // Handle Literals
            else if (token.type == TokenType.LITERAL || token.type == TokenType.INDEX) {

                // Either wait to be assigned, called or indexed
                TokenOr or = new TokenOr(Arrays.asList(TokenType.OPERATOR, TokenType.OPEN, TokenType.INDEX));
                ctxCurrent.expectedTypes.add(or);

            }

            // Handle lots of things lol
            else if (token.type == TokenType.END) {

                Token previousInstruction = null;
                int previousInstructionIndex = 0;

                for (int j = i - 1; j >= 0; j--) {
                    if (tokens.get(j).type == TokenType.END) {
                        previousInstruction = tokens.get(j + 1);
                        previousInstructionIndex = j;
                        break;
                    }
                }

                if (previousInstruction == null) {
                    previousInstruction = tokens.get(0);
                    previousInstructionIndex = 0;
                }

                final Token instruction = previousInstruction;
                final int instructionIndex = previousInstructionIndex;

                // Do things to variables
                if (previousInstruction.type == TokenType.LITERAL) {
                    ctxCurrent.body.add(new IRawCall() {

                        @Override
                        public void Call(Context context) {

                            GenericVariable variable = context.getVariable(instruction.toString());

                            int i = instructionIndex;
                            while (tokens.get(i).type != TokenType.END) {

                                Token token = tokens.get(i);
                                switch (token.type) {

                                    case INDEX:

                                        if (variable.type == GenericType.OBJECT) {
                                            GenericValue value = ((ObjectVariable) variable).GetIndex(
                                                    new GenericValue(GenericType.STRING, token.value.toString()));

                                            switch (value.type) {
                                                case CONTAINER:
                                                    variable = (ArrayVariable) value;
                                                    break;

                                                case FUNCTION:
                                                    variable = (FunctionVariable) value;
                                                    break;

                                                case NUMERICAL:
                                                    variable = (NumericalVariable) value;
                                                    break;

                                                case OBJECT:
                                                    variable = (ObjectVariable) value;
                                                    break;

                                                case STRING:
                                                    variable = (StringVariable) value;
                                                    break;

                                            }
                                        }

                                        break;

                                    case OPEN:
                                        ArrayList<Token> arguments = new ArrayList<Token>() {{ add(token); }};
                                        ((FunctionVariable) variable).Call(arguments);
                                        break;

                                    case OPERATOR:
                                        break;

                                    default:
                                        break;

                                }

                            }

                        }

                    });
                }

            }

            else if (token.type == TokenType.OPEN || token.type == TokenType.CLOSE) {
                ;
            } // Only really here to make cork pretty

            else
                throw new Error(String.format("Unhandled token %1$s at index %2$s", token.toString(),
                        ctxCurrent.inputBuffer.size() - 1));

        }

        System.out.println(ctxCurrent.toString());

    }

}
