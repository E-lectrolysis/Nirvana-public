package electrolysis.nirvanabot.commands;

import electrolysis.nirvanabot.NirvanaBot;
import electrolysis.nirvanabot.itemgen.ItemGenerator;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;


public class ItemSearch extends Command{

    private ItemGenerator itemGenerator = new ItemGenerator();

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        String prefix = NirvanaBot.getDefaultPrefix();
        if (event.getAuthor().isBot()) return;

        // We don't want to respond to other bot accounts, including ourself
        Message message = event.getMessage();

        String content = message.getContentRaw();
        String sentMessage = "";
        String search = "";

        MessageChannel channel = event.getChannel();
        //man, my comments are so good
        if (content.indexOf(prefix + "item") == 0) { //checks for item command

            if (content.contains(prefix + "item ") && !(content.equals(prefix + "item"))) { //error checking to see if people typed stuff after
                search = content.substring(content.indexOf(" ") + 1).toLowerCase();
                System.out.println(search);
                search = search.replace(" ", "%20"); //replace url spacey thing so it works lol

                try {
                    sentMessage = itemGenerator.searchItem(search); //geneerate thingyyyyy
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (content.equals(prefix + "item")){ //roasts people for improper command
                sentMessage = "ur bad put in an actual search or something";
            } else {
                return;
            }
            //send message
            channel.sendMessage(sentMessage).queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)


        }
    }

}
