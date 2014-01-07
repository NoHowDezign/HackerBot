package org.hackmaine.ircbot.commandsystem.annotations;

import org.hackmaine.ircbot.HackerBot;
import org.hackmaine.ircbot.HackerBotMain;

public class CommandParams {
	private String[] params;
	
	public CommandParams(String[] commandParams) {
		this.params = commandParams;
	}
	
	public String[] getParams() {
		return params;
	}
	
	public HackerBot getHackerBot() {
		return HackerBotMain.bot;
	}
}
