package Commands;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Search implements PMBCommand{
    @Override
    public void onStart(MessageReceivedEvent message, String[] messageParts) {

    }

    @Override
    public String getCommandName() {
        return "search";
    }
}
