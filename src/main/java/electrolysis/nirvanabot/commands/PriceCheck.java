package electrolysis.nirvanabot.commands;

import electrolysis.nirvanabot.NirvanaBot;
import electrolysis.nirvanabot.googlesheets.PriceCheckSearch;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.apache.commons.collections4.ArrayStack;

import java.util.ArrayList;


public class PriceCheck extends Command{

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {



        String prefix = NirvanaBot.getDefaultPrefix();

        PriceCheckSearch pc = new PriceCheckSearch();

        if (event.getAuthor().isBot()) return;

        Message message = event.getMessage();
        String content = message.getContentRaw();
        MessageChannel channel = message.getChannel();

        String output = "";

        int pageNum = 1;
        String page = "";
        if (content.contains("p:")) {
            page = content.substring(content.indexOf("p:"));
        } else {
            pageNum = 1;
        }
        if(page.length() > 2) {
            pageNum = Integer.parseInt(page.substring(2));
        }

        ArrayList<String> outputs = new ArrayList<String>();

        //if no extra parameters
        if(content.equalsIgnoreCase(prefix + "pc") || (content.indexOf(prefix + "pc ") == 0 && content.contains("p:"))) {
            ArrayList<String> currentOutput = new ArrayList<String>();
            //ooooo
            try {
                currentOutput = pc.listItems("Non-Mythics");

            } catch (Exception e) {
                e.printStackTrace();
            }

            output += "**Type " + prefix + "pc <itemname> to pricecheck an item. Available items are below.**\n\n";

            //adds non-mythics to list
            if (currentOutput.size() > 0) {
                output += "**Non-Mythics**\n";
                for (int i = 0; i < currentOutput.size(); i++) {
                    //length checker
                    if (output.length() + currentOutput.get(i).length() < 1900) {
                        if (i > 0) {
                            output += ", "; //comma best separator
                        }
                        output += currentOutput.get(i);
                    } else {
                        output += "\n\nmore items on next page! add p:" + (pageNum + 1) + " as a parameter to go there";
                        outputs.add(output);
                        output = "";
                        i--;
                    }

                }
            }
            try {
                currentOutput = pc.listItems("Mythics");

            } catch (Exception e) {
                e.printStackTrace();
            }
            //mythic check
            if (currentOutput.size() > 0) {
                output += "\n\n**Mythics**\n";
                for (int i = 0; i < currentOutput.size(); i++) {
                    //length checker
                    if (output.length() + currentOutput.get(i).length() < 1900) {
                        if (i > 0) {
                            output += ", ";
                        }
                        output += currentOutput.get(i);
                    } else {
                        output += "\n\nmore items on next page! add p:" + (pageNum + 1) + " as a parameter to go there";
                        outputs.add(output);
                        output = "";
                        i--;
                    }

                }
            }
            if(!output.equalsIgnoreCase("")) {
                output += ("\n\n**End of List**");
                outputs.add(output);
            }

            //pagenum checker
            if(outputs.size() == 1) {
                pageNum = 0;
                channel.sendMessage(outputs.get(pageNum)).queue();
            } else if(pageNum <= 0) {
                channel.sendMessage("Negative pages don't exist.").queue();
            } else if(pageNum < outputs.size()) { //sends accordingly to page num
                channel.sendMessage(outputs.get(pageNum)).queue();
            } else {
                channel.sendMessage("That page doesn't exist! You've gone too far!").queue();
            }

            //if there's a parameter
        } else if(content.indexOf(prefix + "pc ") == 0 && !content.equalsIgnoreCase(prefix + "pc")) {
            String[] pcedItem;
            try {
                pcedItem = pc.pcItem(content.substring(content.indexOf(" ") + 1));
            } catch (Exception e) {
                pcedItem = null;
            }
            /*
            0 item
            1 type
            2 tier
            3 price range
            4 Primary stats
            5 secondary stats
            6 demand
            7 notes
             */

            if(pcedItem!=null) {
                output += "**Price check for " + pcedItem[0] + "**";
                output += "\n\n**Type:** " + pcedItem[1];
                output += "\n**Tier:** " + pcedItem[2];
                output += "\n\n**Price Range ";
                if(!pcedItem[2].equals("Mythic")){
                    output+=("(LE): **");
                } else {
                    output+=("(STX of LE): **");
                }
                output +=  pcedItem[3];
                output += "\n\n**Primary Stats:** " + pcedItem[4];
                output += "\n**Secondary Stats:** " + pcedItem[5];
                output += "\n\n**Demand (1-5):** " + pcedItem[6];
                output += "\n\n**Additional Notes:** " + pcedItem[7];
                output += "\n\n**DISCLAIMER:** Please use common sense when estimating a price";

            } else {
                output = "No such item exists, or hasn't been pricechecked.";
            }

            channel.sendMessage(output).queue();
        }

    }
}
