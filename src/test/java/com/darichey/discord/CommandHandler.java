package com.darichey.discord;

import java.util.Arrays;

public class CommandHandler {
	@Command(name = "test")
	public void testCommand(CommandContext context) {
		System.out.println("Test command executed with args: " + Arrays.toString(context.getArgs()));
	}
}
