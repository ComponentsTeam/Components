package com.earth2me.components.core.storage;

import java.util.Collections;
import java.util.Map;


/**
 * Stores settings common to all components.
 *
 * @author Zenexer
 */
public final class CommonSettings implements IStorable
{
	private String locale;

	@Override
	public String getId()
	{
		return "CommonSettings";
	}

	@Override
	public Map<String, Class<?>> getCustomClassTags()
	{
		return Collections.emptyMap();
	}

	/**
	 * Gets the locale of the server.
	 * 
	 * @return the locale
	 */
	public String getLocale()
	{
		return locale;
	}

	/**
	 * Sets the locale of the server.
	 * 
	 * @param locale the locale to set
	 */
	public void setLocale(String locale)
	{
		this.locale = locale;
	}
}
