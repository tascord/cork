package xyz.tascord;

import xyz.tascord.Parser.Parser;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Parser parser = new Parser();
        parser.Parse("fn main(args) {\nterm.print(\"Hello, World!\");\n}");

    }
}
