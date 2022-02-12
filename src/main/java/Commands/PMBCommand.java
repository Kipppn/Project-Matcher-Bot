package Commands;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;

public interface PMBCommand {
    void onStart(MessageReceivedEvent event, String[] messageParts);

    String getCommandName();

    String getCommandDetails();

    String getCommandUsage();

    default void onReact(Message message, ButtonClickEvent event){
        return;
    }

}
