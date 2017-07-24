package com.darichey.discord.limiter;

import com.darichey.discord.CommandContext;
import sx.blah.discord.handle.obj.IChannel;

/**
 * Limits execution of a command to a set of channels.
 */
public class ChannelLimiter extends IDLimiter {

	public ChannelLimiter(IChannel... channels) {
		super(channels);
	}

	public ChannelLimiter(long... ids) {
		super(ids);
	}

	@Override
	public long getID(CommandContext ctx) {
		return ctx.getChannel().getLongID();
	}
}
