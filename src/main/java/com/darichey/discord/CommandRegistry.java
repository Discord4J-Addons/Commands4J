package com.darichey.discord;

import sx.blah.discord.api.IDiscordClient;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class CommandRegistry {
	private static Map<IDiscordClient, CommandRegistry> registeries = new HashMap<>();

	HashMap<Object, HashMap<String, Method>> commands = new HashMap<>();
	public String prefix = "!";

	public static CommandRegistry getRegistryForClient(IDiscordClient client) {
		if (!registeries.containsKey(client)) {
			registeries.put(client, new CommandRegistry());
			client.getDispatcher().registerListener(new CommandListener());
		}
		return registeries.get(client);
	}

	public void register(Object commandHandler) {
		for (Method method : commandHandler.getClass().getMethods()) {
			if (method.isAnnotationPresent(Command.class)) {
				Command annotation = method.getAnnotation(Command.class);
				String name = annotation.name();

				if (!commands.containsKey(commandHandler)) {
					commands.put(commandHandler, new HashMap<>());
				}

				if (!commands.get(commandHandler).containsKey(name)) {
					commands.get(commandHandler).put(name, method);
				} else {
					throw new IllegalArgumentException("Attempt to register two commands with the same name: " + name);
				}
			}
		}
	}
}
