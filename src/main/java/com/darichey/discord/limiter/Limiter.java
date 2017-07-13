package com.darichey.discord.limiter;

import com.darichey.discord.CommandContext;

@FunctionalInterface
public interface Limiter {

	boolean check(CommandContext ctx);

	default void onFail(CommandContext ctx) {
		// NO-OP
	}
}
