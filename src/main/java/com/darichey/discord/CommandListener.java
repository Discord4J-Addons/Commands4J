package com.darichey.discord;

import sx.blah.discord.api.events.IListener;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

/**
 * Listens for messages which start with the prefix specified by a {@link CommandRegistry} and attempts to parse and call the correct command.
 */
@SuppressWarnings("WeakerAccess")
public class CommandListener  implements IListener<MessageReceivedEvent> {

	private final CommandRegistry registry;

	public CommandListener(CommandRegistry registry) {
		this.registry = registry;
	}

	@Override
	public void handle(MessageReceivedEvent event) {
		String content = event.getMessage().getContent();
		String prefix = registry.getEffectivePrefix(event.getGuild().getLongID());

		if (content.startsWith(prefix)) {
			String prefixRemoved = content.substring(prefix.length());
			if (prefixRemoved.isEmpty()) return; // message was just the prefix.

			int spaceIndex = prefixRemoved.indexOf(" ");
			int subIndex = spaceIndex == -1 ? prefixRemoved.length() : spaceIndex;
			String name = prefixRemoved.substring(0, subIndex);

			String args = name.length() == prefixRemoved.length() ? "" : prefixRemoved.substring(name.length() + 1);

			registry.call(name, new CommandContext(event, name, args));
		}
	}
}
