package com.darichey.discord.limiter;

import com.darichey.discord.CommandContext;
import sx.blah.discord.handle.obj.IVoiceChannel;

/**
 * Limits execution of a command to a set of voice channels. The executor of the command must be connected to one of the given voice channels.
 */
public class VoiceChannelLimiter extends IDLimiter {

	public VoiceChannelLimiter(IVoiceChannel... voiceChannels) {
		super(voiceChannels);
	}

	public VoiceChannelLimiter(long... ids) {
		super(ids);
	}

	@Override
	public long getID(CommandContext ctx) {
		return ctx.getAuthor().getVoiceStateForGuild(ctx.getGuild()).getChannel().getLongID();
	}
}
