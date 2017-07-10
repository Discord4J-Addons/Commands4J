package com.darichey.discord;

import com.darichey.discord.limiter.Limiter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@SuppressWarnings("WeakerAccess")
public class Command {

	private final Consumer<CommandContext> onCalled;
	private final List<Limiter> limiters;

	private Command(Consumer<CommandContext> onCalled, List<Limiter> limiters) {
		this.onCalled = onCalled;
		this.limiters = limiters;
	}

	public Consumer<CommandContext> getOnCalled() {
		return onCalled;
	}

	public List<Limiter> getLimiters() {
		return limiters;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {

		private Consumer<CommandContext> onCalled;
		private final List<Limiter> limiters = new ArrayList<>();

		public Builder onCalled(Consumer<CommandContext> onCalled) {
			this.onCalled = onCalled;
			return this;
		}

		public Builder limiter(Limiter limiter) {
			this.limiters.add(limiter);
			return this;
		}

		public Command build() {
			return new Command(onCalled, limiters);
		}
	}
}
