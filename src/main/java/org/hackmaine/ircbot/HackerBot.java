package org.hackmaine.ircbot;

import org.hackmaine.ircbot.events.CommandEvent;
import org.hackmaine.ircbot.events.ConnectEvent;
import org.hackmaine.ircbot.events.DisconnectEvent;
import org.hackmaine.ircbot.events.InviteEvent;
import org.hackmaine.ircbot.events.MessageEvent;
import org.hackmaine.ircbot.eventsystem.EventAnnotationParser;
import org.jibble.pircbot.PircBot;

public class HackerBot extends PircBot {
	
	public HackerBot() {
		this.setName("MaineHackerBot");
	}
	
	public void onMessage(String channel, String sender, String login, String hostname, String message) {
		if(message.startsWith(".")) {
			EventAnnotationParser.raiseEvent(new CommandEvent(this, channel, sender, login, hostname, message));
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
