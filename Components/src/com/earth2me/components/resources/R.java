package com.earth2me.components.resources;


/**
 * A localized resource manager.
 *
 * @author Zenexer
 */
public final class R
{
	/**
	 * Gets a formated string resource.
	 *
	 * @param format the default English string resource; should have a {@code Resource} annotation.
	 * @param args The arguments, if any, to pass to the formatter.
	 * @return the locale-specific string resource, pre-formatted.
	 */
	public static String string(String format, Object... args)
	{
		// TODO Use format.getHashCode() to look up the localized format.
		return String.format(format, args);
	}
}
