package com.darichey.discord.limiter;

import com.darichey.discord.CommandContext;
import sx.blah.discord.handle.obj.IUser;

/**
 * Limits execution of a command to a set of users.
 */
public class UserLimiter extends IDLimiter {

	public UserLimiter(IUser... users) {
		super(users);
	}

	public UserLimiter(long... ids) {
		super(ids);
	}

	@Override
	public long getID(CommandContext ctx) {
		return ctx.getAuthor().getLongID();
	}
}
