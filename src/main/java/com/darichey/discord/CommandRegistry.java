package com.darichey.discord;

import com.darichey.discord.limiter.Limiter;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.util.cache.LongMap;

import java.util.*;

@SuppressWarnings("WeakerAccess")
public class CommandRegistry {

	private final Map<String, Command> commands = new HashMap<>();
	private final LongMap<String> guildPrefixes = LongMap.newMap();
	private final Set<Limiter> defaultLimiters;
	private final String defaultPrefix;

	public CommandRegistry(String defaultPrefix) {
		this.defaultPrefix = defaultPrefix;
		this.defaultLimiters = new HashSet<>();
	}

	public CommandRegistry(String defaultPrefix, Set<Limiter> defaultLimiters) {
		this.defaultPrefix = defaultPrefix;
		this.defaultLimiters = defaultLimiters;
	}

	public CommandRegistry(String defaultPrefix, Limiter... defaultLimiters) {
		this(defaultPrefix);
		Collections.addAll(this.defaultLimiters, defaultLimiters);
	}

	public void call(String name, CommandContext ctx) {
		getCommand(name).ifPresent(cmd -> {
			for (Limiter limiter : defaultLimiters) {
				if (!limiter.check(ctx)) {
					limiter.onFail(ctx);
					return;
				}
			}

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

	public Optional<Command> getCommand(String name) {
		return Optional.ofNullable(commands.get(name));
	}

	public void overwritePrefix(IGuild guild, String prefix) {
		overwritePrefix(guild.getLongID(), prefix);
	}

	public void overwritePrefix(long id, String prefix) {
		guildPrefixes.put(id, prefix);
	}

	public Optional<String> getPrefixForGuild(IGuild guild) {
		return getPrefixForGuild(guild.getLongID());
	}

	public Optional<String> getPrefixForGuild(long id) {
		return Optional.ofNullable(guildPrefixes.get(id));
	}

	public String getEffectivePrefix(IGuild guild) {
		return getEffectivePrefix(guild.getLongID());
	}

	public String getEffectivePrefix(long id) {
		return getPrefixForGuild(id).orElse(defaultPrefix);
	}
}
