package com.darichey.discord.limiter;

import com.darichey.discord.CommandContext;
import sx.blah.discord.handle.obj.IVoiceChannel;

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
