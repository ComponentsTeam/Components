package com.earth2me.components.storage;

import java.util.Collections;
import java.util.Map;


/**
 * Stores information about players.
 *
 * @author Zenexer
 */
public final class UserSurrogate implements IStorable
{
	@Override
	public String getId()
	{
		return "UserSurrogate";
	}
	
	public static UserSurrogate createDefault()
	{
		UserSurrogate userSurrogate = new UserSurrogate();
		userSurrogate.loadDefaults();
		return userSurrogate;
	}

	@Override
	public Map<String, Class<?>> getCustomClassTags()
	{
		return Collections.emptyMap();
	}
	
	public void loadDefaults()
	{
	}
}
