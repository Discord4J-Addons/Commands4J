package com.darichey.discord.api;

import sx.blah.discord.handle.obj.IMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandContext {

	private final IMessage message;
	private final String name;
	private final String[] args;
	private final CommandRegistry registry;

	public CommandContext(IMessage message) {
		this.registry = CommandRegistry.getRegistryForClient(message.getClient());
		this.message = message;
		final int prefixLength = registry.getPrefix().length();
		final String messageContent = message.getContent();
		final int indexOfSpace = messageContent.indexOf(' ');
		this.name = messageContent.substring(prefixLength)
				.substring(0, indexOfSpace != -1 ? indexOfSpace : messageContent.length());
		List<String> list = new ArrayList<String>();
		Matcher m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(messageContent.replace(registry.getPrefix(), "").replace(name, ""));
		while (m.find()) {
			list.add(m.group(1));
		}
		String args[] = new String[list.size()];
		this.args = list.toArray(args);
	}

	public CommandContext(IMessage message, String name, String[] args) {
		this.registry = CommandRegistry.getRegistryForClient(message.getClient());
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
