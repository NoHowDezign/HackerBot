package org.hackmaine.ircbot;

import org.hackmaine.ircbot.commandsystem.CommandAnnotationParser;
import org.hackmaine.ircbot.events.ConnectEvent;
import org.hackmaine.ircbot.events.DisconnectEvent;
import org.hackmaine.ircbot.events.InviteEvent;
import org.hackmaine.ircbot.events.MessageEvent;
import org.hackmaine.ircbot.eventsystem.EventAnnotationParser;
import org.jibble.pircbot.PircBot;

public class HackerBot extends PircBot {
	
	public HackerBot() {
		this.setName("MaineHackerBotTe");
	}
	
	public void onMessage(String channel, String sender, String login, String hostname, String message) {
		if(message.startsWith(".")) {
			String[] initialParams = message.split(" ");
			String[] params = new String[initialParams.length - 1];
			for(int i = 1; i == initialParams.length - 1; i++) {
				params[i - 1] = initialParams[i];
			}
			
			CommandAnnotationParser.raiseEvent(initialParams[0].replace(".", ""), channel, params, HackerBotMain.bot);
			System.out.println("Recieved a command.");
		} else {
			EventAnnotationParser.raiseEvent(new MessageEvent(this, channel, sender, login, hostname, message));
		}
	}
	
	public void onConnect() {
		EventAnnotationParser.raiseEvent(new ConnectEvent());
	}
	
	public void onDisconnect() {
		EventAnnotationParser.raiseEvent(new DisconnectEvent());
	}
	
	public void onInvite(String targetNick, String sourceNick, String sourceLogin, String sourceHostname, String channel) {
		EventAnnotationParser.raiseEvent(new InviteEvent(targetNick, sourceNick, sourceLogin, sourceHostname, channel));
	}
	
	public void replyToChannel(String channel, String reply) {
		sendMessage(channel, reply);
	}
	
	public void runAction(String channel, String command) {
		this.sendAction(channel, command);
	}
	
}
