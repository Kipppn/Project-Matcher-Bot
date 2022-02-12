package Commands;

import Listener.BotListener;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class SetPrefix implements PMBCommand{
    BotListener listener;
    public SetPrefix(BotListener listener){
        this.listener = listener;
    }
    @Override
    public void onStart(MessageReceivedEvent event, String[] messageParts) {
        if(messageParts.length < 2){
            return;
        }
        char prefix = messageParts[1].charAt(0);
        listener.setPrefix(prefix);
        event.getMessage().reply("Prefix set to \"" + prefix + "\"").queue();
    }

    @Override
    public String getCommandName() {
        return "setprefix";
    }

    @Override
    public String getCommandDetails() {
        return "sets the prefix of each command to another one letter character";
    }

    @Override
    public String getCommandUsage() {
        return "setprefix <new prefix>";
    }
}
