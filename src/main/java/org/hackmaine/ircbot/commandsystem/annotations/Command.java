package org.hackmaine.ircbot.commandsystem.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Command {
	String helpText() default "The developer has not specified help text.";

	boolean requiresArgs() default false;
}
