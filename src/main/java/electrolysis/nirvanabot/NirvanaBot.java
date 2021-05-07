/*
NirvanaBot
DiscordBot with wynn utilities
@author Electrolysis
 */

package electrolysis.nirvanabot;

import electrolysis.nirvanabot.commands.*;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;


public class NirvanaBot{

    public static String getDefaultPrefix() {
        return defaultPrefix;
    }

    private static String defaultPrefix = "n!";

    public static void main(String[] arguments) throws Exception
    {

        JDA api = new JDABuilder(AccountType.BOT).setToken("Token Here").build();
        api.getPresence().setGame(Game.playing("150 INT"));

        api.addEventListener(new ItemSearch());
        api.addEventListener(new Help());
        api.addEventListener(new Kill());
        api.addEventListener(new RequestBuild());
        api.addEventListener(new Emerald());
    }

}
