package com.darichey.discord;

import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.util.DiscordException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {
	static IDiscordClient client;
	static String TOKEN = "";

	public static void main(String[] args) {
		try (BufferedReader reader = new BufferedReader(new FileReader("token.txt"))){
			TOKEN = reader.readLine();
			client = new ClientBuilder().withToken(TOKEN).login();
			CommandRegistry.getRegistryForClient(client).register(new CommandHandler());
		} catch (IOException | DiscordException e) {
			e.printStackTrace();
		}
	}
}
