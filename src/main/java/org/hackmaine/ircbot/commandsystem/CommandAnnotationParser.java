package org.hackmaine.ircbot.commandsystem;

import java.lang.reflect.Method;

import org.hackmaine.ircbot.HackerBot;
import org.hackmaine.ircbot.commandsystem.annotations.Command;
import org.hackmaine.ircbot.commandsystem.annotations.LongCommandParameter;

public class CommandAnnotationParser {

	public static void raiseEvent(final String cmdName, final String currentChannel,
			final String[] params, final HackerBot bot) {
        new Thread() {
            @Override
            public void run() {
                raise(cmdName, currentChannel, params, bot);
            }
        }.start();
    }

    private static void raise(String cmdName, String channel, String[] params, HackerBot bot) {
    	//System.out.println("Command raised!");
    	for (Class<?> handler : CommandHandlerRegistry.getHandlers()) {
            Method[] methods = handler.getMethods();
            //System.out.println("Got methods!");

            for (int i = 0; i < methods.length; ++i) {
                Command commAnnotation = methods[i].getAnnotation(Command.class);
                //System.out.println("Getting annotations!");
                if(methods[i].getName().equals(cmdName)) {
                	if(commAnnotation != null) {
                    	//System.out.println("Got annotation CommandLogic!");
                        Class<?>[] methodParams = methods[i].getParameterTypes();
                        
                        //Construct the argument object to pass to the class & add the bot as the first arg
                        Object[] args = new Object[methodParams.length];
                        args[0] = bot;
                        
                        if(methodParams.length > 1) {
                        	if(!params.equals(null)) {
                        		try {
									if(methods[i].getDeclaringClass().getMethod(methods[i].getName(), HackerBot.class, LongCommandParameter.class) 
											!= null && params.length != methodParams.length - 1) {
										//Here we bind the LongCommandParam statically - if you have this, you do not have any other params
										LongCommandParameter commandParameter = new LongCommandParameter();
										commandParameter.setLongParameter(params);
										
										args[1] = commandParameter;
									} else {
										//This cycles through the args and binds values to them
										for(int amountOfArgs = 1; amountOfArgs == params.length - 1; amountOfArgs++) {
											if(args[amountOfArgs].equals(null)) {
												bot.replyToChannel(channel, commAnnotation.helpText());
												break;
											} else {
												String argumentToPassToMod = params[amountOfArgs - 1];
												args[amountOfArgs] = argumentToPassToMod;
											}
										}
									}
								} catch (NoSuchMethodException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (SecurityException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
                        	} else {
                        		bot.replyToChannel(channel, commAnnotation.helpText());
                            	break;
                        	}
                        } else {
                        	System.out.println("All we need is a bot, bot bot.");
                        }
                        
                        try {
                            methods[i].invoke(handler.newInstance(), args);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                	}
                }
            }
        }
    }
}
