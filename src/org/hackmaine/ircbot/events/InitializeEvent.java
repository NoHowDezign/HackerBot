package org.hackmaine.ircbot.events;

import org.hackmaine.ircbot.HackerBot;
import org.hackmaine.ircbot.eventsystem.annotations.Event;

public class InitializeEvent extends Event {
	private HackerBot hackerBot;
	
	public InitializeEvent(HackerBot bot) {
		this.hackerBot = bot;
	}
	
	public HackerBot getBot() {
		return hackerBot;
	}
	
}
