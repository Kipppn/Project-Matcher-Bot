package Commands;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Project implements PMBCommand{
    @Override
    public void onStart(MessageReceivedEvent event, String[] messageParts) {

    }

    @Override
    public String getCommandName() {
        return "project";
    }

    @Override
    public String getCommandDetails() {
        return null;
    }

    @Override
    public String getCommandUsage() {
        return null;
    }
}
