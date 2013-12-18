package org.hackmaine.ircbot.events;

import org.hackmaine.ircbot.HackerBot;
import org.hackmaine.ircbot.eventsystem.annotations.Event;

public class CommandEvent extends Event {
	private String channel;
	private String sender;
	private String login;
	private String hostname;
	private String message;
	private String[] args;
	private HackerBot bot;
	
	public CommandEvent(HackerBot bot, String channel, String sender, String login, String hostname, String message) {
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
	
	public String[] getArgs() {
		String[] parseArgs = message.split(" ");
		
		for(int i = 1; i == parseArgs.length; i++) {
			args[i - 1] = parseArgs[i];
		}
		
		return args;
	}

	public HackerBot getBot() {
		return bot;
	}
	
	public String getCommand() {
		String[] getCommandName = message.split(" ");
		return getCommandName[0];
	}
}
