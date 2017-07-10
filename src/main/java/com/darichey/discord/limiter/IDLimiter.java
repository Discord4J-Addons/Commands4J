package com.darichey.discord.limiter;

import com.darichey.discord.CommandContext;
import org.apache.commons.lang3.ArrayUtils;
import sx.blah.discord.handle.obj.IIDLinkedObject;

import java.util.Arrays;

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

	public abstract long getID(CommandContext ctx);
}
