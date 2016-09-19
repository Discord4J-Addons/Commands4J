package com.darichey.discord;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.modules.IModule;

public class Commands4J implements IModule {
	@Override
	public boolean enable(IDiscordClient iDiscordClient) {
		return true;
	}

	@Override
	public void disable() {

	}

	@Override
	public String getName() {
		return "Commands4J";
	}

	@Override
	public String getAuthor() {
		return "Panda";
	}

	@Override
	public String getVersion() {
		return "1.1.0";
	}

	@Override
	public String getMinimumDiscord4JVersion() {
		return "2.6.0";
	}
}
