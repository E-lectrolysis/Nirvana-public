package electrolysis.nirvanabot.commands;

import electrolysis.nirvanabot.NirvanaBot;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;


public class Help extends Command{

    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
        String prefix = NirvanaBot.getDefaultPrefix();

        if (event.getAuthor().isBot()) return;
        // We don't want to respond to other bot accounts, including ourself
        Message message = event.getMessage();
        String content = message.getContentRaw().toLowerCase();

        //displayed help message thing
        String helpMessage = "**" + prefix + "help** - Gives you a list of all commands"
                + "\n**" + prefix + "item itemname** - Gives item stats"
                + "\n**/kill** - Kills yourself"
                + "\n**" + prefix + "request <parameters>** - requests a build. Type " + prefix + "help request for more info"
                + "\n**" + prefix + "e <amount> <currencyType>** - displays an amount in all currency forms";

        String helpBuildSearch = "**BUILD REQUEST COMMAND**"
                + "\n **Command:** " + prefix + "request param1, param2, param3, param4"
                + "\n\n **Parameters** (separated by comma with space \", \" any order, all optional but must include at least one)"
                + "\n ** - Weapon:** Name of weapon e.g Nirvana"
                + "\n ** - Element:** e: Followed by abbreviation of all elements you want e.g. e:tw searches for thunder/water builds (e:etwfa OR rainbow)"
                + "\n ** - Class:** Builds for specific class e.g Archer"
                + "\n ** - Playstyle:** Playstyle of build (spellspam, spell, melee, hybrid, poison, quake)"
                + "\n ** - Page:** p:# When there's multiple pages of builds, you can do this to go to specific page (e.g p:3). This must be the last parameter if you choose to include it"
                + "\n\n **Example commands**: "
                + "\n`"+prefix+"request warrior, e:f, melee`: Requests warrior fire melee"
                + "\n`"+prefix+"request e:rainbow, spellspam`: Requests rainbow spellspam for any class/weapon"
                + "\n`"+prefix+"request nirvana, spellspam`: Requests Nirvana spellspam"
                + "\n`"+prefix+"request e:w, p:2`: Requests page 2 of water builds"
                + "\n`"+prefix+"request e:twf, spellspam, nirvana`: Requests T-W-F spellspam build for Nirvana";



        //check for prefix
        if (content.equals(prefix + "help"))
        {
            MessageChannel channel = event.getChannel();
            channel.sendMessage(helpMessage).queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)
        } else if  (content.equals(prefix + "help request")) {

            MessageChannel channel = event.getChannel();
            channel.sendMessage(helpBuildSearch).queue();
        }
    }
}


