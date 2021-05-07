package electrolysis.nirvanabot.commands;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.managers.GuildController;

public class Kill extends Command {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;

        GuildController suicideMachine = new GuildController(event.getGuild()); //lol

        Message message = event.getMessage();
        String content = message.getContentRaw();
        MessageChannel channel = message.getChannel();

        //dang this is so good command
        if(message.getContentRaw().equalsIgnoreCase("/kill")) {
            try {
                channel.sendMessage(message.getAuthor().getName() + " committed die").queue();
            } catch (Exception e){
                channel.sendMessage("lmao you're not allowed to kill urself").queue();

            }
        }

    }
}
