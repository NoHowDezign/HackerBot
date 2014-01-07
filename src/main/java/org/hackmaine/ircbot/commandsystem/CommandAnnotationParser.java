package org.hackmaine.ircbot.commandsystem;

import java.lang.reflect.Method;

import org.hackmaine.ircbot.HackerBot;
import org.hackmaine.ircbot.commandsystem.annotations.Command;
import org.hackmaine.ircbot.commandsystem.annotations.CommandParams;

public class CommandAnnotationParser {

	public static void raiseEvent(final CommandParams params, final HackerBot bot) {
        new Thread() {
            @Override
            public void run() {
                raise(params, bot);
            }
        }.start();
    }

    private static void raise(final CommandParams params, final HackerBot bot) {
    	Object[] passToClass = new Object[1];
        for (Class<?> handler : CommandHandlerRegistry.getHandlers()) {
            Method[] methods = handler.getMethods();

            for(int i = 0; i < methods.length; ++i) {
                Command eventReceiver = methods[i].getAnnotation(Command.class);
                if (eventReceiver != null) {
                    Class<?>[] methodParams = methods[i].getParameterTypes();
                    
                	System.out.println("Method name: " + methods[i].getName());
                	
                	if(!params.getParams()[0].replace(".", "").equals(methods[i].getName())) {
                		continue;
                	}
                	
                	if(!params.getClass().getSimpleName()
                            .equals(methodParams[0].getSimpleName()))
                        continue;
                	if(!bot.getClass().getSimpleName()
                			.equals(methodParams[1].getSimpleName()))
                		continue;
                	
                	passToClass[0] = params;
                	passToClass[1] = bot;
                    
                    try {
                        methods[i].invoke(handler.newInstance(), passToClass);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
