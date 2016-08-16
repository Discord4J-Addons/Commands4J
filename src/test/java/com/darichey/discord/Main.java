package com.darichey.discord;

import com.darichey.discord.api.Command;
import com.darichey.discord.api.CommandRegistry;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Main {
	static IDiscordClient client;
	static String TOKEN = "";

	public static void main(String[] args) {
		try (BufferedReader reader = new BufferedReader(new FileReader("token.txt"))){
			TOKEN = reader.readLine();
			client = new ClientBuilder().withToken(TOKEN).login();

			Command test = new Command("ping", new Command.Options().withAliases("testAlias", "testAlias1").deleteCommand(true))
					.onExecuted(context ->
						sendMessage(context.getMessage().getChannel(), "Pong!")
					)
					.onFailure((context, reason) -> {

					});

			CommandRegistry.getRegistryForClient(client).register(test);

		} catch (IOException | DiscordException e) {
			e.printStackTrace();
		}
	}

	public static void sendMessage(IChannel channel, String message) {
		try {
			channel.sendMessage(message);
		} catch (MissingPermissionsException | DiscordException | RateLimitException e) {
			e.printStackTrace();
		}
	}
}