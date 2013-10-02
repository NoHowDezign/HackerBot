package org.hackmaine.ircbot;

import java.util.*;

public class EventHandlerRegistry {    
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
