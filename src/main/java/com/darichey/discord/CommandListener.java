package com.darichey.discord;

import sx.blah.discord.api.events.IListener;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;

public class CommandListener implements IListener<MessageReceivedEvent> {
	@Override
	public void handle(MessageReceivedEvent event) {
		String content = event.getMessage().getContent();
		CommandRegistry registry = CommandRegistry.getRegistryForClient(event.getClient());
		if (content.startsWith(registry.prefix)) {
			String commandName = content.substring(1, content.contains(" ") ? content.indexOf(" ") : content.length());
			registry.commands.entrySet().stream()
					.forEach(entry -> {
						Object c = entry.getKey();
						Optional<Map.Entry<String, Method>> methodEntry = entry.getValue().entrySet().stream()
								.filter(stringMethodEntry -> stringMethodEntry.getKey().equalsIgnoreCase(commandName))
								.findFirst();

						if (methodEntry.isPresent()) {
							Method method = methodEntry.get().getValue();
							try {
								method.invoke(c, new CommandContext(event.getMessage()));
							} catch (IllegalAccessException | InvocationTargetException e) {
								e.printStackTrace();
							}
						}
					});

		}
	}
}
