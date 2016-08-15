package com.darichey.discord.api;

import sx.blah.discord.handle.obj.IMessage;

public class CommandContext {

	private final IMessage message;
	private final String name;
	private final String[] args;

	public CommandContext(IMessage message) {
		this.message = message;
		this.name = message.getContent().substring(1).substring(0, message.getContent().contains(" ") ? message.getContent().indexOf(" ") + 1 : message.getContent().length() - 1);
		this.args = message.getContent().substring(message.getContent().contains(" ") ? message.getContent().indexOf(" ") + 1 : message.getContent().length() - 1).split("\\s+");
	}

	public CommandContext(IMessage message, String name, String[] args) {
		this.message = message;
		this.name = name;
		this.args = args;
	}

	public IMessage getMessage() {
		return this.message;
	}

	public String getName() {
		return this.name;
	}

	public String[] getArgs() {
		return this.args;
	}
}
