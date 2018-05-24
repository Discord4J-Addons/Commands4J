package com.darichey.discord;

import com.darichey.discord.limiter.Limiter;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.util.cache.LongMap;

import java.util.*;

/**
 * The point of access for calling commands. This can hold default limiters which are automatically applied to all commands as well as guild-specific
 * prefixes.
 */
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

	/**
	 * Calls a command with the given name, if it exists.
	 * @param name The name of the command to call.
	 * @param ctx The context to give to the command function.
	 */
	public void call(String name, CommandContext ctx) {
		getCommand(name).ifPresent(cmd -> call(cmd, ctx));
	}

	private void call(Command cmd, CommandContext ctx) {
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
	}

	/**
	 * Associates a command to a name and optional aliases.
	 * @param command The command to register.
	 * @param name The name to associate with the command.
	 * @param aliases The aliases to associate with the command. (Or none)
	 */
	public void register(Command command, String name, String... aliases) {
		register(command, name);
		for (String alias : aliases) {
			register(command, alias);
		}
	}
	private void register(Command command, String name) {
		if (commands.containsKey(name)) throw new IllegalArgumentException("Attempt to register two commands with the same name: " + name);
		commands.put(name, command);
	}

	/**
	 * Optionally returns the command associated with the given name;
	 * @param name The name to look up.
	 * @return The command associated with the given name.
	 */
	public Optional<Command> getCommand(String name) {
		return Optional.ofNullable(commands.get(name));
	}

	/**
	 * Sets a guild-specific prefix.
	 * @param guild The guild to set the prefix in.
	 * @param prefix The prefix to set.
	 */
	public void overwritePrefix(IGuild guild, String prefix) {
		overwritePrefix(guild.getLongID(), prefix);
	}

	/**
	 * Sets a guild-specific prefix.
	 * @param id The id of the guild to set the prefix in.
	 * @param prefix The prefix to set.
	 */
	public void overwritePrefix(long id, String prefix) {
		guildPrefixes.put(id, prefix);
	}

	/**
	 * Optionally returns the guild-specific prefix for the given guild.
	 * @param guild The guild to look up.
	 * @return The guild-specific prefix for the given guild.
	 */
	public Optional<String> getPrefixForGuild(IGuild guild) {
		return getPrefixForGuild(guild.getLongID());
	}

	/**
	 * Optionally returns the guild-specific prefix for the given guild.
	 * @param id The id of the guild to look up.
	 * @return The guild-specific prefix for the given guild.
	 */
	public Optional<String> getPrefixForGuild(long id) {
		return Optional.ofNullable(guildPrefixes.get(id));
	}

	/**
	 * Gets the prefix for the given guild. This is either the guild-specific prefix or the default prefix if one is not set.
	 * @param guild The guild to look up.
	 * @return The guild-specific prefix for this guild or the default prefix.
	 */
	public String getEffectivePrefix(IGuild guild) {
		if (guild == null) return defaultPrefix;
		else return getEffectivePrefix(guild.getLongID());
	}

	/**
	 * Gets the prefix for the given guild. This is either the guild-specific prefix or the default prefix if one is not set.
	 * @param id The id of the guild to look up.
	 * @return The guild-specific prefix for this guild or the default prefix.
	 */
	public String getEffectivePrefix(long id) {
		return getPrefixForGuild(id).orElse(defaultPrefix);
	}
}
