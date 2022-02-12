package Commands;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public interface PMBCommand {
    void onStart(MessageReceivedEvent message, String[] messageParts);

    String getCommandName();

}
