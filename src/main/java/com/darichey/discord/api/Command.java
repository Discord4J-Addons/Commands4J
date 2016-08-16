package com.darichey.discord.api;

import sx.blah.discord.handle.obj.Permissions;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Command {
	protected Consumer<CommandContext> onExecuted = context -> {};
	protected BiConsumer<CommandContext, FailureReason> onFailure = (context, failureReason) -> {};

	public final String name;
	public final Options options;

	public Command(String name, Options options) {
		this.name = name;
		this.options = options;
	}

	public Command(String name) {
		this(name, new Options());
	}

	public Command onExecuted(Consumer<CommandContext> function) {
		this.onExecuted = function;
		return this;
	}

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

		public Options withAliases(String... aliases) {
			Set<String> set = new HashSet<>();
			Collections.addAll(set, aliases);
			return withAliases(set);
		}

		public Options withAliases(Set<String> aliases) {
			this.aliases = aliases;
			return this;
		}

		public Options withDescription(String description) {
			this.description = description;
			return this;
		}

		public Options caseSensitive(boolean caseSensitive) {
			this.caseSensitive = caseSensitive;
			return this;
		}

		public Options deleteCommand(boolean deleteCommand) {
			this.deleteCommand = deleteCommand;
			return this;
		}

		public Options requirePermissions(EnumSet<Permissions> requiredPermissions) {
			this.requiredPermissions = requiredPermissions;
			return this;
		}
	}
}
