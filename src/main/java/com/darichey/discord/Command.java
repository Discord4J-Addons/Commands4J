package com.darichey.discord;

import com.darichey.discord.limiter.Limiter;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

/**
 * A command to be executed in Discord. This holds a set of {@link Limiter}s that determine if the command can be executed in a given context as well
 * as a function to be called should all of the limiters pass.
 */
@SuppressWarnings("WeakerAccess")
public class Command {

	private final Consumer<CommandContext> onCalled;
	private final Set<Limiter> limiters;

	private Command(Consumer<CommandContext> onCalled, Set<Limiter> limiters) {
		this.onCalled = onCalled;
		this.limiters = limiters;
	}

	/**
	 * Gets the function that is executed when the command is called.
	 * @return The function that is executed when the command is called.
	 */
	public Consumer<CommandContext> getOnCalled() {
		return onCalled;
	}

	/**
	 * Gets the limiters which determine if the command can be executed in a given context.
	 * @return The limiters which determine if the command can be executed in a given context.
	 */
	public Set<Limiter> getLimiters() {
		return limiters;
	}

	/**
	 * Gets a builder that can be used to build a new Command.
	 * @return A new command builder.
	 */
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Used to build Command instances.
	 */
	public static class Builder {

		private Consumer<CommandContext> onCalled;
		private Set<Limiter> limiters = new HashSet<>();

		/**
		 * Sets the function that is executed when the command is called.
		 * @param onCalled The function that is executed when the command is called.
		 * @return The builder instance.
		 */
		public Builder onCalled(Consumer<CommandContext> onCalled) {
			this.onCalled = onCalled;
			return this;
		}

		/**
		 * Adds a limiter to the set of limiters for the command.
		 * @param limiter The limiter to add.
		 * @return The builder instance.
		 */
		public Builder limiter(Limiter limiter) {
			this.limiters.add(limiter);
			return this;
		}

		/**
		 * Sets all of the limiters that determine if the command can be executed in a given context.
		 * @param limiters The limiters to set.
		 * @return The builder instance.
		 */
		public Builder limiters(Set<Limiter> limiters) {
			this.limiters = limiters;
			return this;
		}

		/**
		 * Builds the command instance.
		 * @return The command instance.
		 */
		public Command build() {
			return new Command(onCalled, limiters);
		}
	}
}
