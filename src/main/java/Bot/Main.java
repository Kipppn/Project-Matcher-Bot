package Bot;

import Commands.*;
import Listener.*;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;

public class Main {
    public static void main(String[] args) throws LoginException{

        JDABuilder bot = JDABuilder.createLight(args[0], GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES);
        BotListener botListener = new BotListener();

        botListener.addCommands(
                new Find(),
                new Help(botListener),
                //new Project(),
                //new Search(),
                new SetPrefix(botListener));
                //new Top());
        bot.addEventListeners(botListener);


        bot.setStatus(OnlineStatus.ONLINE);
        bot.setActivity(Activity.playing("Matching Devs!"));
        bot.build();

    }
}
