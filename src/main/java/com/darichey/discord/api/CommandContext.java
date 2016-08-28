package com.darichey.discord.api;

import sx.blah.discord.handle.obj.IMessage;

public class CommandContext {

	private final IMessage message;
	private final String name;
	private final String[] args;
	private final CommandRegistry registry;

	public CommandContext(CommandRegistry registry, IMessage message) {
		this.registry = registry;
		this.message = message;
		final int prefixLength = registry.getPrefix().length();
		final String messageContent = message.getContent();
		final int indexOfSpace = messageContent.indexOf(' ');
		this.name = messageContent.substring(prefixLength)
				.substring(0, indexOfSpace != -1 ? indexOfSpace : messageContent.length());
		this.args = indexOfSpace < messageContent.length() - 1
				? messageContent.substring(indexOfSpace + 1).split("\\s+")
				: new String[0];
	}

	public CommandContext(CommandRegistry registry, IMessage message, String name, String[] args) {
		this.registry = registry;
		this.message = message;
		this.name = name;
		this.args = args;
	}

	/**
	 * @return The message object
	 */
	public IMessage getMessage() {
		return this.message;
	}

	/**
	 * @return The command's name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @return The arguments, or an empty array if there aren't any
	 */
	public String[] getArgs() {
		return this.args;
	}

	/**
	 * @return The CommandRegistry
	 */
	public CommandRegistry getRegistry() {
		return this.registry;
	}
}
