package com.darichey.discord.limiter;

import com.darichey.discord.CommandContext;
import sx.blah.discord.handle.obj.IGuild;

public class GuildLimiter extends IDLimiter {

	public GuildLimiter(IGuild... guilds) {
		super(guilds);
	}

	public GuildLimiter(long... ids) {
		super(ids);
	}

	@Override
	public long getID(CommandContext ctx) {
		return ctx.getGuild().getLongID();
	}
}
