package org.hackmaine.ircbot.commandsystem;

import java.util.*;

public class CommandHandlerRegistry {    
    @SuppressWarnings("rawtypes")
	private static List<Class> handlers = new ArrayList<Class>();

    @SuppressWarnings("rawtypes")
	public static void register(Class clazz) {
        handlers.add(clazz);
    }

    @SuppressWarnings("rawtypes")
	public static List<Class> getHandlers() {
        return handlers;
    }
}
