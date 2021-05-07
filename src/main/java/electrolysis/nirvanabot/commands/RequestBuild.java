package electrolysis.nirvanabot.commands;

import electrolysis.nirvanabot.googlesheets.BuildSheetSearch;
import electrolysis.nirvanabot.NirvanaBot;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.ArrayList;

public class RequestBuild extends Command{

    @Override
    public void onMessageReceived(MessageReceivedEvent event){
        if (event.getAuthor().isBot()) return;


        //blah blah blah variables
        Message message = event.getMessage();
        BuildSheetSearch archive = new BuildSheetSearch();
        String content = message.getContentRaw().toLowerCase();
        String prefix = NirvanaBot.getDefaultPrefix();
        MessageChannel channel = message.getChannel();
        String output = "";

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



        //dang this is so good command
        if(content.indexOf(prefix + "request ") == 0 && !content.equalsIgnoreCase(prefix + "request")) {

            if(content.equalsIgnoreCase(prefix + "request gf") || content.equalsIgnoreCase(prefix + "request girlfriend")) {
                channel.sendMessage("I can't get you one of those.").queue();
                return;
            }

            if(content.equalsIgnoreCase(prefix + "request bf") || content.equalsIgnoreCase(prefix + "request boyfriend")) {
                channel.sendMessage("~~I heard <@118538445000933378> is single~~ I mean what").queue();
                return;
            }

            if(content.equalsIgnoreCase(prefix + "request a life")) {
                channel.sendMessage("I can't get you one of those.").queue();
                return;
            }


            //lets not have this in there for now or something
            //reenable if necessary
            /*if(!event.getGuild().getId().equals("476887176487895052")) {
                channel.sendMessage("This command is not available").queue();
                return;
            }*/

            //page number determine thingy
            int pageNum = 1;
            String page = "";
            if (content.contains("p:")) {
                page = content.substring(content.indexOf("p:"));
            }
            if(page.length() > 2) {
                pageNum = Integer.parseInt(page.substring(2));
                System.out.println(pageNum);
            }
            if(content.contains("p:")) {
                content = content.substring(content.indexOf(' ') + 1, content.indexOf(", p:"));
            } else {
                content = content.substring(content.indexOf(' ') + 1);
            }
            System.out.println(content);
            /*
            0 author
            1 class
            2 elements
            3 weapon
            4 playstyle
            5 build link
            6 notes
            7 last updated
            */
            try {
                //gets builds from the archive
                //builds
                ArrayList<String[]> builds = archive.getStuff(content, "Non-Mythics");
                ArrayList<String[]> mythicBuilds = archive.getStuff(content, "Mythics");
                ArrayList<String> outputs = new ArrayList<String>();

                if(builds.size() <= 0 && mythicBuilds.size() <= 0) { //if no builds is found
                    outputs.add("No builds were found. Someone make this person a build!");
                } else {
                    outputs.add("");
                    if(builds.size() > 0) { //puts non-mythic builds into msg
                        for (int i = 0; i < builds.size(); i++) {
                            //makes new msg for new page if it's too long for discord
                            if(output.length() + makeBuildMsg(builds, i).length() > 1900) {
                                output += "\nmore builds on next page! add p:" + (pageNum+1) + " as a parameter to go there";
                                outputs.add(output);
                                output = "";
                                i--;
                            } else {
                                if(i==0) {
                                    output += "__**Non-Mythic Builds**__\n";
                                }

                                output = output + makeBuildMsg(builds, i);
                            }
                        }
                    }
                    if(mythicBuilds.size() > 0) { //for mythics now
                        for (int i = 0; i < mythicBuilds.size(); i++) {
                            //makes new msg for new page if it's too long for discord
                            if(output.length() + makeBuildMsg(mythicBuilds, i).length() > 1900) {
                                output += "\nMore builds on next page! repeat your command with p:" + (pageNum+1) + " as the last parameter to go there";
                                outputs.add(output);
                                output = "";
                                i--;
                            } else {
                                if(i==0) {
                                    output += "\n\n__**Mythic Builds**__\n";
                                }
                                output = output + makeBuildMsg(mythicBuilds, i);
                            }

                        }
                    }
                    if(!output.equalsIgnoreCase("")) {
                        output += ("\n**End of List**");
                        outputs.add(output);
                    }
                }

                //if build doesn't exist
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

            } catch(Exception e) {
                e.printStackTrace();
            }

        }  else {


            if (content.equalsIgnoreCase(prefix + "request")) {
                channel.sendMessage(helpBuildSearch).queue();
            }

        }


    }

    private String makeBuildMsg(ArrayList<String[]> buildList, int i) {
        String a = "";

            a = a   + "\n**Author: **" + buildList.get(i)[0]
                    + "\n**Class: **" + buildList.get(i)[1]
                    + "\n**Elements: **" + buildList.get(i)[2]
                    + "\n**Weapon: **" + buildList.get(i)[3]
                    + "\n**Playstyle: **" + buildList.get(i)[4]
                    + "\n**Build Link: **" + buildList.get(i)[5]
                    + "\n**Additional Notes: **" + buildList.get(i)[6]
                    + "\n**Last Updated: **" + buildList.get(i)[7]
                    + "\n";
        return a;
    }
}
