package org.hackmaine.ircbot;

import org.hackmaine.ircbot.events.ConnectEvent;
import org.hackmaine.ircbot.events.DisconnectEvent;
import org.hackmaine.ircbot.events.InviteEvent;
import org.hackmaine.ircbot.events.KickEvent;
import org.hackmaine.ircbot.events.MessageEvent;
import org.jibble.pircbot.PircBot;

public class HackerBot extends PircBot {
	
	public HackerBot() {
		this.setName("MaineHackerBot");
	}
	
	public void onMessage(String channel, String sender, String login, String hostname, String message) {
		EventAnnotationParser.raiseEvent(new MessageEvent(channel, sender, login, hostname, message));
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
	
}
