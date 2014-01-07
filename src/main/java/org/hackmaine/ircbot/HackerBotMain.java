package org.hackmaine.ircbot;

import java.io.IOException;
import java.util.zip.ZipException;

import org.hackmaine.ircbot.events.InitializeEvent;
import org.hackmaine.ircbot.eventsystem.EventAnnotationParser;
import org.hackmaine.ircbot.plugins.ModLoader;
import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.NickAlreadyInUseException;

public class HackerBotMain {
	public static HackerBot bot;
	
	public static void main(String[] args) {
		ModLoader loader = new ModLoader();
		try {
			loader.loadPlugins();
		} catch (ZipException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		loader.parsePlugins();
		
		bot = new HackerBot();
		
		EventAnnotationParser.raiseEvent(new InitializeEvent(bot));
		
		try {
			bot.connect("irc.freenode.net");
		} catch (NickAlreadyInUseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IrcException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bot.joinChannel("#MaineHackerClubTest");
	}
	
}
