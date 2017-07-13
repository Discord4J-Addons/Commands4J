package com.darichey.discord.limiter;

import com.darichey.discord.CommandContext;
import org.apache.commons.lang3.ArrayUtils;
import sx.blah.discord.handle.obj.IIDLinkedObject;

import java.util.Arrays;

/**
 * A limiter that limits execution to a set of IDs.
 */
public abstract class IDLimiter implements Limiter {

	private final long[] ids;

	public IDLimiter(IIDLinkedObject... objects) {
		this(Arrays.stream(objects).mapToLong(IIDLinkedObject::getLongID).toArray());
	}

	public IDLimiter(long... ids) {
		this.ids = ids;
	}

	@Override
	public boolean check(CommandContext ctx) {
		return ArrayUtils.contains(ids, getID(ctx));
	}

	/**
	 * Gets the ID to check for this limiter. If the ID returned by this method is not in {@link #ids}, the check will not pass.
	 * @param ctx The context to get the ID from.
	 * @return The ID to check for this limiter.
	 */
	public abstract long getID(CommandContext ctx);
}
