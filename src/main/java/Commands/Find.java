package Commands;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Find implements PMBCommand{
    @Override
    public void onStart(MessageReceivedEvent event, String[] messageParts) {

    }

    @Override
    public String getCommandName() {
        return "find";
    }

    @Override
    public String getCommandDetails() {
        return "The find command recommends you either projects or developers to work on your project. You can either ";
    }

    @Override
    public String getCommandUsage() {
        return "find [project/dev]";
    }
}
