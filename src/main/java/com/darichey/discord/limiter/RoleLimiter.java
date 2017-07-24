package com.darichey.discord.limiter;

import com.darichey.discord.CommandContext;
import org.apache.commons.lang3.ArrayUtils;
import sx.blah.discord.handle.obj.IIDLinkedObject;
import sx.blah.discord.handle.obj.IRole;

import java.util.Arrays;

/**
 * Limits execution of a command to a user with <b>ALL</b> of the given roles.
 */
public class RoleLimiter implements Limiter {

	private final long[] ids;

	public RoleLimiter(IRole... objects) {
		this(Arrays.stream(objects).mapToLong(IIDLinkedObject::getLongID).toArray());
	}

	public RoleLimiter(long... ids) {
		this.ids = ids;
	}

	@Override
	public boolean check(CommandContext ctx) {
		return ctx.getAuthor().getRolesForGuild(ctx.getGuild()).stream().anyMatch(role -> ArrayUtils.contains(ids, role.getLongID()));
	}
}
