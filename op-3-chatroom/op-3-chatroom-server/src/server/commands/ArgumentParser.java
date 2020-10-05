package server.commands;

import java.util.ArrayList;
import java.util.List;

public class ArgumentParser {

    public ArgumentParser(String argumentString) throws ArgumentParsingException {
        this.argumentString = argumentString.trim().toCharArray();
        this.arguments = new ArrayList<>();
    }

    public List<String> parse() {
        if(finished)
            return arguments;

        StringBuilder builder = new StringBuilder();
        for(char character: argumentString) {

            if(!isQuote && character == ' '){
                arguments.add(builder.toString());
                builder = new StringBuilder();
            }
            else if(character == '"') {
                isQuote = !isQuote;
            }
            else {
                builder.append(character);
            }
        }
        arguments.add(builder.toString());

        if(isQuote)
            throw new ArgumentParsingException();

        finished = true;
        return arguments;
    }

    private boolean isQuote;
    private boolean finished;
    private final char[] argumentString;
    private final List<String> arguments;
}
