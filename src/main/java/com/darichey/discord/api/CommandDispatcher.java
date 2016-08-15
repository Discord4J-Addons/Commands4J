package com.darichey.discord.api;

import sx.blah.discord.api.events.IListener;
import sx.blah.discord.api.internal.DiscordUtils;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.handle.obj.Permissions;

import java.util.EnumSet;
import java.util.Optional;

public class CommandDispatcher implements IListener<MessageReceivedEvent> {

	@Override
	public void handle(MessageReceivedEvent event) {
		String content = event.getMessage().getContent();
		CommandRegistry registry = CommandRegistry.getRegistryForClient(event.getClient());
		if (content.startsWith(registry.prefix)) {
			String commandName = content.substring(1, content.contains(" ") ? content.indexOf(" ")  + 1 : content.length());
			Optional<Command> command = registry.getCommandByName(commandName, true);
			if (command.isPresent()) {
				if (command.get().options.caseSensitive && !commandName.equals(command.get().name)) return; // If it's case sensitive, check if the cases match

				CommandContext context = new CommandContext(event.getMessage());

				EnumSet<Permissions> requiredPermissions = command.get().options.requiredPermissions;
				boolean canExecute = event.getMessage().getChannel().getModifiedPermissions(event.getMessage().getAuthor()).containsAll(requiredPermissions);

				if (canExecute) {
					command.get().onExecuted.accept(context);
				} else {
					command.get().onFailure.accept(context, FailureReason.MISSING_PERMISSIONS);
				}
			}
		}
	}
}
