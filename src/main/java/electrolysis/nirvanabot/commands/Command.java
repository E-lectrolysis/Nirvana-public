package electrolysis.nirvanabot.commands;


import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.managers.GuildController;


abstract public class Command extends ListenerAdapter {

    @Override
    abstract public void onMessageReceived(MessageReceivedEvent e);

}
