package org.hackmaine.ircbot.eventsystem.annotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ReceiveEvent {
}
