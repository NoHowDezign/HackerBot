package org.hackmaine.ircbot;

import org.hackmaine.ircbot.events.InitializeEvent;
import org.hackmaine.ircbot.eventsystem.EventAnnotationParser;
import org.hackmaine.ircbot.plugins.PluginLoader;

public class HackerBotMain {
	public static HackerBot bot;
	
	public static void main(String[] args) {
		try {
			PluginLoader loader = new PluginLoader();
			loader.loadPlugins();
			loader.parsePlugins();
			
			bot = new HackerBot();
			
			EventAnnotationParser.raiseEvent(new InitializeEvent(bot));
			
			bot.connect("irc.freenode.net");
			
			bot.joinChannel("#MaineHackerClubTest");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
