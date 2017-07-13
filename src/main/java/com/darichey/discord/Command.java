package com.darichey.discord;

import com.darichey.discord.limiter.Limiter;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

@SuppressWarnings("WeakerAccess")
public class Command {

	private final Consumer<CommandContext> onCalled;
	private final Set<Limiter> limiters;

	private Command(Consumer<CommandContext> onCalled, Set<Limiter> limiters) {
		this.onCalled = onCalled;
		this.limiters = limiters;
	}

	public Consumer<CommandContext> getOnCalled() {
		return onCalled;
	}

	public Set<Limiter> getLimiters() {
		return limiters;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {

		private Consumer<CommandContext> onCalled;
		private Set<Limiter> limiters = new HashSet<>();

		public Builder onCalled(Consumer<CommandContext> onCalled) {
			this.onCalled = onCalled;
			return this;
		}

		public Builder limiter(Limiter limiter) {
			this.limiters.add(limiter);
			return this;
		}

		public Builder limiters(Set<Limiter> limiters) {
			this.limiters = limiters;
			return this;
		}

		public Command build() {
			return new Command(onCalled, limiters);
		}
	}
}
