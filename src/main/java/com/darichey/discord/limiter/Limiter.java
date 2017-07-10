package com.darichey.discord.limiter;

import com.darichey.discord.CommandContext;

import java.util.function.Predicate;

@FunctionalInterface
public interface Limiter {

	boolean check(CommandContext ctx);

	default void onFail(CommandContext ctx) {
		// NO-OP
	}
}
