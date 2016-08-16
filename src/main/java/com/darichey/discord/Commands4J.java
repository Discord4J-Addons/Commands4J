package com.darichey.discord;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.Discord4J;

public class Commands4J {
	public static final Logger LOGGER = (Discord4J.LOGGER instanceof Discord4J.Discord4JLogger ? new Commands4JLogger(Commands4J.class.getName()) : LoggerFactory.getLogger(Commands4J.class));

	public static class Commands4JLogger extends Discord4J.Discord4JLogger {
		public Commands4JLogger(String name) {
			super(name);
		}
	}
}
