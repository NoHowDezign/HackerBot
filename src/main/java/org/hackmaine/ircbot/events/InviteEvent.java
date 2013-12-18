package org.hackmaine.ircbot.events;

import org.hackmaine.ircbot.eventsystem.annotations.Event;

public class InviteEvent extends Event {
	public String targetNick;
	public String sourceNick;
	public String sourceLogin;
	public String sourceHostname;
	public String channel;
	
	public InviteEvent(String targetNick, String sourceNick, String sourceLogin, String sourceHostname, String channel) {
		this.channel = channel;
		this.sourceHostname = sourceHostname;
		this.sourceLogin = sourceLogin;
		this.sourceNick = sourceNick;
		this.targetNick = targetNick;
	}
	
}
