package com.darichey.discord.limiter;

import com.darichey.discord.CommandContext;

/**
 * Used to limit the execution of a command to a certain context.
 */
@FunctionalInterface
public interface Limiter {

	/**
	 * Determines if the command can be executed in the given context.
	 * @param ctx The context in which the command was executed.
	 * @return True if the command can be executed.
	 */
	boolean check(CommandContext ctx);

	/**
	 * Executed by the {@link com.darichey.discord.CommandRegistry} if a command is called and {@link #check(CommandContext)} returns false.
	 * @param ctx The context in which the limiter failed.
	 */
	default void onFail(CommandContext ctx) {
		// NO-OP
	}
}
