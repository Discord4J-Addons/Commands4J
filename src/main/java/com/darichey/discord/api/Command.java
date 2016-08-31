package com.darichey.discord.api;

import sx.blah.discord.handle.obj.Permissions;

import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@SuppressWarnings("WeakerAccess")
public class Command {

	private final String name;
	private String description = "";
	private Set<String> aliases = Collections.emptySet();
	private boolean caseSensitive = false;
	private boolean deleteCommand = false;
	private EnumSet<Permissions> requiredPermissions = EnumSet.noneOf(Permissions.class);

	Consumer<CommandContext> onExecuted = context -> {};
	BiConsumer<CommandContext, FailureReason> onFailure = (context, failureReason) -> {};

	/**
	 * Initialize with the command's name
	 * @param name The name of the command
	 */
	public Command(String name) {
		this.name = name;
	}

	/**
	 * The function to execute when the command is successful.
	 *
	 * @param function The function to execute
	 * @return This command instance
	 */
	public Command onExecuted(Consumer<CommandContext> function) {
		this.onExecuted = function;
		return this;
	}

	/**
	 * The function to execute when the command fails.
	 *
	 * @param function The function to execute
	 * @return This command instance
	 */
	public Command onFailure(BiConsumer<CommandContext, FailureReason> function) {
		this.onFailure = function;
		return this;
	}

	/**
	 * An arbitrary value for the description of the command. This isn't used by the API.
	 * @param description The description.
	 * @return This command instance.
	 */
	public Command withDescription(String description) {
		this.description = description;
		return this;
	}

	/**
	 * Aliases that will also trigger this command (besides the name). No two commands may have the same alias.
	 * @param aliases The aliases.
	 * @return This command instance.
	 */
	public Command withAliases(Set<String> aliases) {
		this.aliases = aliases;
		return this;
	}

	/**
	 * Aliases that will also trigger this command (besides the name). No two commands may have the same alias.
	 * @param aliases The aliases.
	 * @return This command instance.
	 */
	public Command withAliases(String... aliases) {
		Collections.addAll(this.aliases, aliases);
		return this;
	}

	/**
	 * If true, the command will trigger regardless of case. (i.e. both !ping and !PiNg will trigger the command)
	 * @param caseSensitive Case sensitive or not.
	 * @return This command instance.
	 */
	public Command caseSensitive(boolean caseSensitive) {
		this.caseSensitive = caseSensitive;
		return this;
	}

	/**
	 * If true, the message that triggered this command will automatically be deleted.
	 * @param deleteCommand To delete or not.
	 * @return This command instance.
	 */
	public Command deleteCommand(boolean deleteCommand) {
		this.deleteCommand = deleteCommand;
		return this;
	}

	/**
	 * The set of permissions a person requires to execute this command. Failing to meet these requirements will result in {@link FailureReason#AUTHOR_MISSING_PERMISSIONS}
	 * @param requiredPermissions The required permissions.
	 * @return This commnad instance.
	 */
	public Command requirePermissions(EnumSet<Permissions> requiredPermissions) {
		this.requiredPermissions = requiredPermissions;
		return this;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Set<String> getAliases() {
		return aliases;
	}

	public boolean isCaseSensitive() {
		return caseSensitive;
	}

	public boolean deletesCommand() {
		return deleteCommand;
	}

	public EnumSet<Permissions> getRequiredPermissions() {
		return requiredPermissions;
	}

}
