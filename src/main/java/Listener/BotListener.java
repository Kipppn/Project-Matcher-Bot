package Listener;


import Commands.PMBCommand;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageReaction;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.ShutdownEvent;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.interactions.commands.Command;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class BotListener implements EventListener {

    private char prefix = '!';

    private ArrayList<PMBCommand> commands = new ArrayList<>();


    public void addCommands(PMBCommand... commands){
        this.commands.addAll(Arrays.asList(commands));
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
            //((MessageReceivedEvent) event).getChannel().sendMessage("Test").queue();
            commandRouter((MessageReceivedEvent) event);
        }

        if(event instanceof ButtonClickEvent){
            buttonRouter((ButtonClickEvent) event);
        }

    }
    private void commandRouter(MessageReceivedEvent event){
        Message message = event.getMessage();
        String messageText = message.getContentRaw();
        String[] messageParts = messageText.split(" ");

        if(messageText.length() < 1 || messageText.charAt(0) != prefix) {
            return;
        }

        String command = messageParts[0].substring(1);

        boolean commandFound = false;
        PMBCommand foundCommand = getCommand(command);

        if(foundCommand == null){
            message.reply("Unknown command. Use the " + prefix
                    + "help command to see all commands").queue();
        }else {
            foundCommand.onStart(event, messageParts);
        }
    }

    private void buttonRouter(ButtonClickEvent event){

        Message message = event.getMessage();
        String id = event.getButton().getId();
        String[] idParts = id.split(" ");

        if(!message.getAuthor().isBot() || idParts.length < 2){
            return;
        }

        event.getChannel().sendMessage(event.getButton().getId());
        PMBCommand foundCommand = getCommand(idParts[0]);
        if(foundCommand == null){
            message.getChannel().sendMessage("Error: Something is wrong");
        }else {
            foundCommand.onReact(message, event);
        }
    }

    public PMBCommand getCommand(String commandName){
        for(int i = 0; i < commands.size(); i++){
            PMBCommand currCommand = commands.get(i);
            if(currCommand.getCommandName().equalsIgnoreCase(commandName)){
                return currCommand;
            }
        }
        return null;
    }

    public char getPrefix(){
        return prefix;
    }
    public void setPrefix(char prefix){
        this.prefix = prefix;
    }

    public ArrayList<PMBCommand> getCommands(){
        return commands;
    }


}
