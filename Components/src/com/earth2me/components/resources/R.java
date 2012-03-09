package com.earth2me.components.resources;

import com.earth2me.components.IContext;
import com.earth2me.components.util.FileExtension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;


/**
 * A localized resource manager.
 *
 * @author Zenexer
 */
public final class R
{
	private static transient Map<Integer, String> resources = Collections.emptyMap();

	/**
	 * Marks the class as static. Do not call. Do not remove.
	 */
	private R()
	{
	}

	private static String readLine(final BufferedReader reader)
	{
		try
		{
			return reader.readLine();
		}
		catch (Throwable ex)
		{
			return null;
		}
	}

	private static void processResourceLine(final IContext context, final String line)
	{
		if (line.isEmpty() || line.startsWith("#"))
		{
			return;
		}

		String[] tokens = line.split("\\s", 2);
		if (tokens.length != 2)
		{
			return;
		}

		try
		{
			final int id = Integer.parseInt(tokens[0]);
			final String value = tokens[1];
			resources.put(id, value);
		}
		catch (RuntimeException ex)
		{
			context.getLogger().log(Level.WARNING, "Unable to parse line with ID {0} in locale file.", tokens[0]);
		}
	}

	/**
	 * Initializes the resource manager against the provided context.
	 *
	 * @param context the context used to obtain the locale.
	 */
	public static void initialize(final IContext context)
	{
		// Determine the locale file.
		final String locale = context.getCommonSettings().getData().getLocale();
		final File file = FileExtension.getResource("localization/" + locale + ".locale");

		// Ensure that the locale file exists.
		if (file == null || !file.exists())
		{
			resources = Collections.emptyMap();
			context.getLogger().log(Level.WARNING, "Your locale file doesn't exist.  Ensure that you have set a valid locale.");
			return;
		}

		resources = new HashMap<Integer, String>();

		// Open the locale file for reading.
		BufferedReader reader;
		try
		{
			reader = new BufferedReader(new FileReader(file));
		}
		catch (Throwable ex)
		{
			context.getLogger().log(Level.WARNING, "Unable to read locale file, probably due to lack of read permissions.");
			return;
		}

		// Read the locale file, line-by-line.
		try
		{
			for (String line; (line = readLine(reader)) != null;)
			{
				processResourceLine(context, line);
			}
		}
		finally
		{
			// Attempt to close the locale file.
			try
			{
				reader.close();
			}
			catch (Throwable ex)
			{
				context.getLogger().log(Level.WARNING, "Error closing locale file after reading.  This normally shouldn't be an issue by itself, but it is a sign that something more serious is wrong.");
			}
		}
	}

	/**
	 * Gets a formated string resource.
	 *
	 * @param format the default English string resource; should have a {@code Resource}
	 * annotation.
	 * @param args The arguments, if any, to pass to the formatter.
	 * @return the locale-specific string resource, pre-formatted.
	 */
	public static String string(String format, Object... args)
	{
		// TODO Use format.getHashCode() to look up the localized format.
		return String.format(format, args);
	}
}
