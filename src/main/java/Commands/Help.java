package Commands;

import Listener.BotListener;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.entities.MessageEmbed.Field;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.Button;
import net.dv8tion.jda.api.interactions.components.Component;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Help implements PMBCommand{

    private ArrayList<PMBCommand> commands;

    private BotListener listener;

    private final int sizePerPage = 2;
    private int page = 0;
    private final int buttonCooldown = 30;


    public Help(BotListener listener){
        this.listener = listener;
    }
    @Override
    public void onStart(MessageReceivedEvent event, String[] messageParts) {
        page = 0;

        Message message = getEmbedHelpMessage();
        event.getChannel().sendMessage(message).queue();

    }

    @Override
    public void onReact(Message message, ButtonClickEvent event) {
        OffsetDateTime date = message.getTimeCreated();
        if(OffsetDateTime.now().isAfter(date.plusSeconds(buttonCooldown))){
            event.deferEdit().queue();
            return;
        }
        String id = event.getButton().getId();
        id = id.toLowerCase();
        String[] idParts = id.split(" ");


        if(idParts.length < 2) {
            return;
        }
        switch (idParts[1]) {
            case "prev":
                page -= 1;
                event.editMessage(getEmbedHelpMessage()).queue();
                break;
            case "next":
                page += 1;
                event.editMessage(getEmbedHelpMessage()).queue();
                break;
            default:
                Exception e = new Exception("Behavior for " + id + " Button does not exist");
                e.printStackTrace();
        }

    }

    private Message getEmbedHelpMessage(){
        commands = listener.getCommands();
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder = embedBuilder.setTitle("Commands - Page " + (page+1) );
        for(int index = 0; index < sizePerPage; index++){
            int i = index + page*sizePerPage;
            if(i >= commands.size()){
                break;
            }
            PMBCommand command = commands.get(i);
            embedBuilder = embedBuilder.addField(listener.getPrefix() + command.getCommandUsage(),
                    "Description: " + command.getCommandDetails(), false);
        }
        MessageEmbed messageEmbed = embedBuilder.build();

        MessageBuilder message = new MessageBuilder();
        Button prevButton = Button.primary("Help prev", "<");
        Button nextButton = Button.primary("Help next", ">");
        List<ActionRow> actionRows = new ArrayList<>();
        if(isPageValid(page - 1) && isPageValid(page + 1)){
            message.setActionRows(ActionRow.of(prevButton, nextButton));
        }else if(isPageValid(page - 1)){
            message.setActionRows(ActionRow.of(prevButton));
        } else if(isPageValid(page + 1)){
            message.setActionRows(ActionRow.of(nextButton));

        }



        message.setEmbeds(messageEmbed);
        return message.build();
    }

    private boolean isPageValid(int page){
        return page*sizePerPage < commands.size() && page*sizePerPage >= 0;
    }
    @Override
    public String getCommandName() {
        return "help";
    }

    @Override
    public String getCommandDetails() {
        return "The help command will give a list of possible commands and how to use them";
    }

    @Override
    public String getCommandUsage() {
        return "help";
    }
}
