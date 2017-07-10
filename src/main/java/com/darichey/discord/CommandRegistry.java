package com.darichey.discord;

import com.darichey.discord.limiter.Limiter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CommandRegistry {

	private final Map<String, Command> commands = new HashMap<>();

	public void call(String name, CommandContext ctx) {
		get(name).ifPresent(cmd -> {
			for (Limiter limiter : cmd.getLimiters()) {
				if (!limiter.check(ctx)) {
					limiter.onFail(ctx);
					return;
				}
			}

			cmd.getOnCalled().accept(ctx);
		});
	}

	public void register(String name, Command command) {
		commands.put(name, command);
	}

	public Optional<Command> get(String name) {
		return Optional.ofNullable(commands.get(name));
	}
}
