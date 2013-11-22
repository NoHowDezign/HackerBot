package org.hackmaine.ircbot.eventsystem;

import java.lang.reflect.Method;

import org.hackmaine.ircbot.eventsystem.annotations.Event;
import org.hackmaine.ircbot.eventsystem.annotations.ReceiveEvent;

public class EventAnnotationParser {

	public static void raiseEvent(final Event event) {
        new Thread() {
            @Override
            public void run() {
                raise(event);
            }
        }.start();
    }

    private static void raise(final Event event) {
        for (Class<?> handler : EventHandlerRegistry.getHandlers()) {
            Method[] methods = handler.getMethods();

            for (int i = 0; i < methods.length; ++i) {
                ReceiveEvent eventReceiver = methods[i].getAnnotation(ReceiveEvent.class);
                if (eventReceiver != null) {
                    Class<?>[] methodParams = methods[i].getParameterTypes();

                    if (methodParams.length < 1)
                        continue;

                    if (!event.getClass().getSimpleName()
                            .equals(methodParams[0].getSimpleName()))
                        continue;
                    
                    try {
                        methods[i].invoke(handler.newInstance(), event);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
