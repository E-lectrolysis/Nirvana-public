package electrolysis.nirvanabot.commands;

import electrolysis.nirvanabot.NirvanaBot;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class Emerald extends Command{

    private static final double E = 64.0;

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;

        Message message = event.getMessage();
        String content = message.getContentRaw();
        MessageChannel channel = message.getChannel();

        final String msg1 = "Use this command with a number to convert it to other units of currency" +
                "\n**Syntax:** n!e amount (unit)" +
                "\n\n**Units Available :**" +
                "\ne (emeralds)" +
                "\neb (emerald blocks)" +
                "\nle (liquid emeralds)" +
                "\ns (stacks of liquid emeralds)" +
                "\nzhight (zhight money)";

        final String error = "The correct format is:" +
                "\n" + NirvanaBot.getDefaultPrefix() + "e <amt> <unit>";

        if(content.indexOf(NirvanaBot.getDefaultPrefix() + "e") == 0) {
            if(content.equals(NirvanaBot.getDefaultPrefix() + "e")) {
                channel.sendMessage(msg1).queue();
            } else if(content.indexOf(NirvanaBot.getDefaultPrefix() + "e ") != 0) {
                return;
            } else {
                String[] parts = content.split(" ");
                if(parts.length != 3) {
                    if(parts.length == 4) { //zhight money checker
                        if(parts[2].equalsIgnoreCase("zhight") && parts[3].equalsIgnoreCase("money")) {
                            channel.sendMessage(makeMsg(parts)).queue();
                        } else {
                            channel.sendMessage(error).queue();
                        }
                    } else {
                        channel.sendMessage(error).queue(); //when the noobs use the command improperly
                    }
                } else {
                    channel.sendMessage(makeMsg(parts)).queue();
                }
            }
        }

    }

    private String makeMsg(String[] parts) {

        try {
            BigDecimal amt = new BigDecimal(parts[1]);
            StringBuilder finalMsg;
            BigDecimal emeralds[] = new BigDecimal[4];
            String currency = parts[2].toUpperCase();
            String[] units = {"**E**", "**EB**", "**LE**", "**STX**"};
            String zhightUnit = "**ZHIGHT MONEY**";

            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(20); //oof

            boolean isZhight = false;

            int modifier;

            if (currency.equalsIgnoreCase("E") || currency.equalsIgnoreCase("EMERALDS")) {
                modifier = 0;
            } else if (currency.equalsIgnoreCase("EB")) {
                modifier = 1;
            } else if (currency.equalsIgnoreCase("LE")) {
                modifier = 2;
            } else if (currency.equalsIgnoreCase("S") || currency.equalsIgnoreCase("STX") || currency.equalsIgnoreCase("STACKS")) {
                modifier = 3;
            } else if (currency.equalsIgnoreCase("zhight")) {
                modifier = 2;
                isZhight = true;
            }
            else {
                return "You didn't input a valid currency";
            }

            if(isZhight) {
                finalMsg = new StringBuilder(amt + " " + zhightUnit + " is: " +
                        "\n");
                amt = amt.divide(BigDecimal.valueOf(8.0));
            } else {
                finalMsg = new StringBuilder(amt + " " + units[modifier] + " is: " +
                        "\n");
            }

        /*
        0 e
        1 eb
        2 le
        3 stx
         */
            for (int i = 0; i < emeralds.length; i++) {
                emeralds[i] = new BigDecimal(amt.toString());
                emeralds[i] = emeralds[i].multiply(BigDecimal.valueOf(Math.pow(E, modifier - i)));
            }


            for (int i = 0; i < units.length; i++) {
                finalMsg.append("\n").append(df.format(emeralds[i])).append(" ").append(units[i]);
            }

            //zhight money lmao
            finalMsg.append("\n\n").append(df.format(emeralds[2].multiply(BigDecimal.valueOf(8.0)))).append(" ").append(zhightUnit);


            if (finalMsg.length() > 2000) {
                finalMsg = new StringBuilder("dude your numbers are too large");
            }

            return finalMsg.toString();
        } catch(Exception e) {
            return "You are doing something wrong";
        }

    }
}
