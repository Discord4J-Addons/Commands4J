package com.darichey.discord.api;

import sx.blah.discord.api.events.IListener;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RequestBuffer;

import java.util.EnumSet;
import java.util.Optional;

class CommandDispatcher implements IListener<MessageReceivedEvent> {

	@Override
	public void handle(MessageReceivedEvent event) {
		String content = event.getMessage().getContent();
		CommandRegistry registry = CommandRegistry.getRegistryForClient(event.getClient());
		if (content.startsWith(registry.getPrefix())) {
			String commandName = content.substring(1, content.contains(" ") ? content.indexOf(" ") : content.length());
			Optional<Command> command = registry.getCommandByName(commandName, true);
			if (command.isPresent()) {
				if (command.get().isCaseSensitive() && !commandName.equals(command.get().getName())) return; // If it's case sensitive, check if the cases match

				CommandContext context = new CommandContext(event.getMessage());

				EnumSet<Permissions> requiredPermissions = command.get().getRequiredPermissions();
				boolean hasPermission = event.getMessage().getChannel().getModifiedPermissions(event.getMessage().getAuthor()).containsAll(requiredPermissions);
				if (hasPermission) {
					command.get().onExecuted.accept(context);
					if (command.get().deletesCommand()) {
						RequestBuffer.request(() -> {
							try {
								event.getMessage().delete();
							} catch (MissingPermissionsException e) {
								command.get().onFailure.accept(context, FailureReason.BOT_MISSING_PERMISSIONS);
							} catch (DiscordException e) {
								e.printStackTrace();
							}
						});
					}
				} else {
					command.get().onFailure.accept(context, FailureReason.AUTHOR_MISSING_PERMISSIONS);
				}
			}
		}
	}
}
