package com.darichey.discord;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;

public class CommandContext {

	private final IMessage message;
	private final String[] args;

	public CommandContext(IMessage message) {
		this.message = message;
		this.args = message.getContent().substring(message.getContent().contains(" ") ? message.getContent().indexOf(" ") + 1 : message.getContent().length() - 1).split("\\s+");
	}

	public CommandContext(IMessage message, String[] args) {
		this.message = message;
		this.args = args;
	}

	public IMessage getMessage() {
		return this.message;
	}

	public String[] getArgs() {
		return this.args;
	}
}
