package com.darichey.discord;

import sx.blah.discord.api.events.IListener;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class CommandListener  implements IListener<MessageReceivedEvent> {

	private final String prefix;
	private final CommandRegistry registry;

	public CommandListener(String prefix, CommandRegistry registry) {
		this.prefix = prefix;
		this.registry = registry;
	}

	@Override
	public void handle(MessageReceivedEvent event) {
		String content = event.getMessage().getContent();
		if (content.startsWith(prefix)) {
			String prefixRemoved = content.substring(prefix.length());
			String name = prefixRemoved.substring(0, prefixRemoved.indexOf(" "));

			registry.call(name, new CommandContext(event, prefix, name, prefixRemoved.substring(name.length() + 1)));
		}
	}
}
