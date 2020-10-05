package server.commands;

import server.Connection;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Command {

    private final Pattern commandPattern;
    public Command(String command) {
        this.commandPattern = Pattern.compile(String.format("^(/%s) (.*)$", command));
    }

    public boolean match(Connection connection, String command) {
        Matcher matcher = commandPattern.matcher(command);
        if(matcher.find()) {

            try {
                ArgumentParser parser = new ArgumentParser(matcher.group(2));
                handle(connection, parser.parse());
                return true;
            }
            catch (ArgumentParsingException exception) {
                return false;
            }
        }
        return false;
    }

    protected abstract void handle(Connection connection, List<String> arguments);


}
