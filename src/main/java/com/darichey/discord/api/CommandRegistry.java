package com.darichey.discord.api;

import sx.blah.discord.api.IDiscordClient;

import java.util.*;

public class CommandRegistry {

	private static HashMap<IDiscordClient, CommandRegistry> registeries = new HashMap<>();

	public static CommandRegistry getRegistryForClient(IDiscordClient client) {
		throwExceptionIfPrefixIsInvalid();
		
		if (!registeries.containsKey(client)) {
			registeries.put(client, new CommandRegistry());
			client.getDispatcher().registerListener(new CommandDispatcher());
		}
		return registeries.get(client);
	}

	private List<Command> commands = new ArrayList<>();
	public static String prefix = "";

	/**
	 * Private constructor, has to be initialized in getRegistryForClient
	 */
	private CommandRegistry() { }
	
	private static void throwExceptionIfPrefixIsInvalid() {
		if (prefix == null || prefix.isEmpty()) throw new InvalidPrefixException("You can't have a null or empty prefix!");
	}

	public static void setPrefix(String newPrefix) {
		prefix = newPrefix;
		
		throwExceptionIfPrefixIsInvalid();
	}

	public static String getPrefix() {
		return prefix;
	}

	public void register(Command command) {
		if (!commands.stream().filter(cmd -> cmd.name.equalsIgnoreCase(command.name)).findFirst().isPresent()) {
			commands.add(command);
		} else {
			throw new IllegalArgumentException("Attempt to register two commands with the same name: " + command.name);
		}
	}

	public Optional<Command> getCommandByName(String name, boolean includeAlias) {
		return commands.stream().filter(c ->
			c.name.equalsIgnoreCase(name) || (includeAlias && c.options.aliases.contains(name))
		).findFirst();
	}

	public List<Command> getCommands() {
		return commands;
	}
	
	public static class InvalidPrefixException extends RuntimeException {
		
		public InvalidPrefixException(String desc) {
			super(desc);
		}
		
	}

}
