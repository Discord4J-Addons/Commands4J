package com.darichey.discord.api;

import sx.blah.discord.api.IDiscordClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("WeakerAccess")
public class CommandRegistry {

	private static HashMap<IDiscordClient, CommandRegistry> registries = new HashMap<>();

	/**
	 * Get the CommandRegistry associated with the client, or create a new one if not present.
	 *
	 * @param client The client object to associate with
	 * @return The CommandRegistry for the client
	 */
	public static CommandRegistry getForClient(IDiscordClient client) {
		if (!registries.containsKey(client)) {
			registries.put(client, new CommandRegistry());
			client.getDispatcher().registerListener(new CommandDispatcher());
		}
		return registries.get(client);
	}

	/**
	 * Private so you have to use {@link CommandRegistry#getForClient(IDiscordClient)}
	 */
	private CommandRegistry() {
	}

	private List<Command> commands = new ArrayList<>();
	private String prefix = "!";

	/**
	 * Register a command.
	 *
	 * @param command
	 */
	public void register(Command command) {
		if (!commands.stream().filter(cmd -> cmd.getName().equalsIgnoreCase(command.getName())).findFirst().isPresent()) {
			commands.add(command);
		} else {
			throw new IllegalArgumentException("Attempt to register two commands with the same name: " + command.getName());
		}
	}

	/**
	 * Registers an array of commands. Purely for convenience.
	 * @param commands Array/Varargs of commands to register
	 */
	public void registerAll(Command... commands) {
		for (Command command : commands) register(command);
	}

	/**
	 * Get a command by its name.
	 *
	 * @param name         The command name
	 * @param includeAlias If aliases can be used to search, otherwise it has to be the original name
	 * @return The command if present
	 */
	public Optional<Command> getCommandByName(String name, boolean includeAlias) {
		return commands.stream().filter(c ->
				c.getName().equalsIgnoreCase(name) || (includeAlias && c.getAliases().contains(name))
		).findFirst();
	}

	/**
	 * Get all commands registered.
	 *
	 * @return A list of commands
	 */
	public List<Command> getCommands() {
		return commands;
	}

	/**
	 * Set the prefix to use.
	 *
	 * @param prefix The new prefix
	 */
	public void setPrefix(String prefix) {
		if (prefix == null) throw new IllegalArgumentException("The new prefix cannot be null!");
		this.prefix = prefix;
	}

	/**
	 * @return The prefix
	 */
	public String getPrefix() {
		return this.prefix;
	}
}
