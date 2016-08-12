package com.darichey.discord;

import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.util.DiscordException;

public class Main {
	static IDiscordClient client;

	public static void main(String[] args) {
		try {
			client = new ClientBuilder().withToken("this didn't happen").login();
			CommandRegistry.getRegistryForClient(client).register(new CommandHandler());
		} catch (DiscordException e) {
			e.printStackTrace();
		}
	}
}
