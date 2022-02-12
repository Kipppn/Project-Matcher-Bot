package Listener;


import Commands.PMBCommand;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.ShutdownEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.interactions.commands.Command;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedList;

public class BotListener implements EventListener {

    private char prefix = '!';

    private ArrayList<PMBCommand> commands;


    public void addCommands(PMBCommand... commands){
        for(int i = 0; i < commands.length; i++){
            this.commands.add(commands[i]);
        }
    }

    @Override
    public void onEvent(@NotNull GenericEvent event) {
        if (event instanceof ReadyEvent) {
            System.out.println("***** BOT IS READY *****");
        }
        if (event instanceof ShutdownEvent) {
            System.out.println("***** BOT IS OFFLINE *****");
        }
        if (event instanceof MessageReceivedEvent) {
            commandRouter((MessageReceivedEvent) event);
        }

    }
    private void commandRouter(MessageReceivedEvent event){
        Message message = event.getMessage();
        String messageText = message.getContentRaw();
        String[] messageParts = messageText.split(" ");

        if(messageText.charAt(0) != prefix) {
            return;
        }

        String command = messageParts[0].substring(1);

        boolean commandFound = false;
        for(int i = 0; i < commands.size() && !commandFound; i++){
            PMBCommand currCommand = commands.get(i);
            if(currCommand.getCommandName().equalsIgnoreCase(command)){
                currCommand.onStart(event, messageParts);
                commandFound = true;
            }
        }

        if(!commandFound){
            message.reply("Unknown command. Use the " + prefix
                    + "help command to see all commands").queue();
        }
    }

    public char getPrefix(){
        return prefix;
    }
    public void setPrefix(char prefix){
        this.prefix = prefix;
    }


}
