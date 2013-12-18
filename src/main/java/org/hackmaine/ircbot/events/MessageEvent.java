package org.hackmaine.ircbot.events;

import org.hackmaine.ircbot.HackerBot;
import org.hackmaine.ircbot.eventsystem.annotations.Event;

public class MessageEvent extends Event {
	private String channel;
	private String sender;
	private String login;
	private String hostname;
	private String message;
	private HackerBot bot;
	
	public MessageEvent(HackerBot bot, String channel, String sender, String login, String hostname, String message) {
		this.bot = bot;
		this.channel = channel;
		this.sender = sender;
		this.login = login;
		this.hostname = hostname;
		this.message = message;
	}
	
	public String getChannel() {
		return channel;
	}
	
	public String getSender() {
		return sender;
	}
	
	public String getLogin() {
		return login;
	}
	
	public String getHostname() {
		return hostname;
	}
	
	public String getMessage() {
		return message;
	}
	
	public HackerBot getBot() {
		return bot;
	}
}
