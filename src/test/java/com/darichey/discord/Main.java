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

public class Main {
	static IDiscordClient client;
	static String TOKEN = "";

	public static void main(String[] args) {
		try (BufferedReader reader = new BufferedReader(new FileReader("token.txt"))){
			TOKEN = reader.readLine();
			client = new ClientBuilder().withToken(TOKEN).login();
			CommandRegistry.setPrefix("--");
			Command test = new Command("ping", new Command.Options().withAliases("pings", "pinger"))
					.onExecuted(context -> {
						System.out.println(Arrays.toString(context.getArgs()));
						System.out.println(context.getName());
					})
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
