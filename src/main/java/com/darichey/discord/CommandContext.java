package com.darichey.discord;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;

import java.util.Arrays;
import java.util.List;

/**
 * Provides information about the context in which a command was executed.
 */
public class CommandContext {

	private final IDiscordClient client;
	private final String name;
	private final List<String> args;
	private final IMessage message;
	private final IGuild guild;
	private final IChannel channel;
	private final IUser author;

	CommandContext(MessageReceivedEvent event, String name, String args) {
		this.client = event.getClient();
		this.name = name;
		this.args = Arrays.asList(args.split("\\s+"));
		this.message = event.getMessage();
		this.guild = event.getGuild();
		this.channel = event.getChannel();
		this.author = event.getAuthor();
	}

	/**
	 * Gets the client that received the message this context was built from.
	 * @return The client that received the message this context was built from.
	 */
	public IDiscordClient getClient() {
		return client;
	}

	/**
	 * Gets the name of the command that was executed.
	 * @return The name of the command that was executed.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the arguments of the command that was executed.
	 * @return The arguments of the command that was executed.
	 */
	public List<String> getArgs() {
		return args;
	}

	/**
	 * Gets the message that this context was built from.
	 * @return The message that this context was built from.
	 */
	public IMessage getMessage() {
		return message;
	}

	/**
	 * Gets the guild in which the message that this context was built from was received.
	 * @return The guild in which the message that this context was built from was received.
	 */
	public IGuild getGuild() {
		return guild;
	}

	/**
	 * Gets the channel in which the message that this context was built from was received.
	 * @return The channel in which the message that this context was built from was received.
	 */
	public IChannel getChannel() {
		return channel;
	}

	/**
	 * Gets the author of the message that this context was built from.
	 * @return The author of the message that this context was built from.
	 */
	public IUser getAuthor() {
		return author;
	}
}
