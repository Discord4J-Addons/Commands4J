package com.darichey.discord.api;

import sx.blah.discord.handle.obj.IMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The context of a command. Contains useful information about where and how a command was executed.
 */
public class CommandContext {

	private final IMessage message;
	private final String name;
	private final String[] args;
	private final CommandRegistry registry;

	public CommandContext(IMessage message) {
		this.registry = CommandRegistry.getRegistryForClient(message.getClient());
		this.message = message;
		final String content = message.getContent();
		this.name = content.substring(registry.getPrefix().length()).substring(0, content.contains(" ") ? content.indexOf(" ") : content.length() - 1);
		List<String> list = new ArrayList<>();
		Matcher m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(content.substring(registry.getPrefix().length() + name.length())); // Thanks @dec for regex
		while (m.find()) {
			list.add(m.group(1).replace("\"", ""));
		}
		this.args = list.toArray(new String[list.size()]);
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
