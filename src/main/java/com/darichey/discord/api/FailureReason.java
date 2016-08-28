package com.darichey.discord.api;

public enum FailureReason {

	/**
	 * If the author of the command-invoker is missing the Discord permissions required by
	 * {@link com.darichey.discord.api.Command.Options#requiredPermissions}
	 */
	AUTHOR_MISSING_PERMISSIONS,

	/**
	 * If the bot is missing Discord permissions (to send a message, etc.).
	 */
	BOT_MISSING_PERMISSIONS
}
