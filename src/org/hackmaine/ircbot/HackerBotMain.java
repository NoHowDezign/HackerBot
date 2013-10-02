package org.hackmaine.ircbot;

import java.io.IOException;

import org.hackmaine.ircbot.plugins.ModLoader;
import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.NickAlreadyInUseException;

public class HackerBotMain {
	
	public static void main(String[] args) throws NickAlreadyInUseException, IOException, IrcException {
		ModLoader loader = new ModLoader();
		loader.loadPlugins();
		loader.parsePlugins();
		
		HackerBot bot = new HackerBot();
		bot.connect("irc.freenode.net");
		bot.joinChannel("#MaineHackerClub");
	}
	
}
