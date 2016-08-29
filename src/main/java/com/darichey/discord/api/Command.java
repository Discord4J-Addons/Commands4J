package com.darichey.discord.api;

import sx.blah.discord.handle.obj.Permissions;

import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Command {
	public final String name;
	public final Options options;
	protected Consumer<CommandContext> onExecuted = context -> {};
	protected BiConsumer<CommandContext, FailureReason> onFailure = (context, failureReason) -> {};

	/**
	 * Initialize with the command's name and options.
	 * @param name
	 * @param options
	 */
	public Command(String name, Options options) {
		this.name = name;
		this.options = options;
	}

	/**
	 * Initialize with the command's name, and default options.
	 * @param name
	 */
	public Command(String name) {
		this(name, new Options());
	}

	/**
	 * The function to execute when the command is successful.
	 *
	 * @param function The function to execute
	 * @return This for chaining
	 */
	public Command onExecuted(Consumer<CommandContext> function) {
		this.onExecuted = function;
		return this;
	}

	/**
	 * The function to execute when the command fails.
	 *
	 * @param function The function to execute
	 * @return This for chaining
	 */
	public Command onFailure(BiConsumer<CommandContext, FailureReason> function) {
		this.onFailure = function;
		return this;
	}

	public static class Options {
		public Set<String> aliases = Collections.emptySet();
		public String description = "";
		public boolean caseSensitive = false;
		public boolean deleteCommand = false;
		public EnumSet<Permissions> requiredPermissions = EnumSet.noneOf(Permissions.class);

		/**
		 * @param aliases Alternative aliases for the command
		 * @return This for chaining
		 * @see #withAliases(String...)
		 */
		public Options withAliases(String... aliases) {
			Set<String> set = new HashSet<>();
			Collections.addAll(set, aliases);
			return withAliases(set);
		}

		/**
		 * @param aliases Alternative aliases for the command
		 * @return This for chaining
		 */
		public Options withAliases(Set<String> aliases) {
			this.aliases = aliases;
			return this;
		}

		/**
		 * @param description A description of the command
		 * @return This for chaining
		 */
		public Options withDescription(String description) {
			this.description = description;
			return this;
		}

		/**
		 * @param caseSensitive Whether the command detection should be case-sensitive
		 * @return This for chaining
		 */
		public Options caseSensitive(boolean caseSensitive) {
			this.caseSensitive = caseSensitive;
			return this;
		}

		/**
		 * @param deleteCommand Whether to delete the command-invoking message if successful
		 * @return This for chaining
		 */
		public Options deleteCommand(boolean deleteCommand) {
			this.deleteCommand = deleteCommand;
			return this;
		}

		/**
		 * @param requiredPermissions The required Discord permissions to use the command
		 * @return This for chaining
		 */
		public Options requirePermissions(EnumSet<Permissions> requiredPermissions) {
			this.requiredPermissions = requiredPermissions;
			return this;
		}
	}
}
