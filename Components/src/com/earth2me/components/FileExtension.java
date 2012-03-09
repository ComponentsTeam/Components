package com.earth2me.components;

import com.earth2me.components.resources.R;
import com.earth2me.annotations.Resource;
import java.io.File;
import java.io.IOException;


/**
 * Provides extra features for dealing with files, particularly in terms of
 * securely accessing them. This is a purely static class.
 *
 * @author Zenexer
 */
public final class FileExtension
{
	// Resources:
	private static final transient @Resource String ERROR_CREATING_FILE = "Unable to create file: %s";
	private static final transient @Resource String ERROR_MAKING_WRITABLE = "Unable to make file writable: %s";
	private static final transient @Resource String ERROR_SECURITY = "Bukkit is preventing the plugin from accessing necessary files.";
	private static final transient @Resource String ERROR_MKDIRS = "Could not create directory hierarchy: %s";

	private static final transient ThreadLocal<String> error = new ThreadLocal<String>();

	/**
	 * Prevents class from being instantiated. Do not call. Do not remove.
	 */
	private FileExtension()
	{
	}

	/**
	 * Should be called via tail invocation, to eliminate overhead of extra
	 * stack.  Sets last error and returns false.
	 *
	 * @param reason reason for last error, in the user's locale.
	 * @return false.
	 */
	private static boolean fail(final String reason)
	{
		error.set(reason);
		return false;
	}

	/**
	 * Gets the last error that occurred.  Speedier than exceptions, and
	 * still thread-safe.  Any method that uses a boolean to indicate success
	 * will set an error before returning false.
	 *
	 * @return an ID indicating the last error to occur, or null if no errors
	 * have occurred in the current thread.
	 */
	public static String getLastError()
	{
		return error.get();
	}

	/**
	 * Clears the error state for the current thread.  Should always be
	 * called after a success-indicating method returns false.
	 */
	public static void clearError()
	{
		error.remove();
	}

	/**
	 * Renames a file to a similar name so that it does not conflict with
	 * existing files. Assumes the file exists.
	 *
	 * @param file The existing file to rename.
	 */
	public static void replaceFile(File file)
	{
		for (long i = 0; i <= Long.MAX_VALUE && i != -1; i++)
		{
			final File dest = new File(file.getParentFile(), file.getName() + "." + i);
			if (!dest.exists())
			{
				file.renameTo(dest);
				break;
			}
		}
	}

	/**
	 * Safely creates a file. Assumes the file does not already exist.
	 *
	 * @param file The file to create.
	 * @return true if the file is created successfully; otherwise, false.
	 */
	public static boolean createWritableFile(final File file)
	{
		try
		{
			file.createNewFile();
		}
		catch (IOException ex)
		{
			return fail(R.string(ERROR_CREATING_FILE, file.getAbsolutePath()));
		}

		if (file.canWrite())
		{
			return true;
		}
		else if (!file.setWritable(true, true))
		{
			return fail(R.string(ERROR_MAKING_WRITABLE, file.getAbsolutePath()));
		}
		else if (!file.canWrite() && !file.setWritable(true, false))
		{
			return fail(R.string(ERROR_MAKING_WRITABLE, file.getAbsolutePath()));
		}
		else if (!file.canWrite())
		{
			return fail(R.string(ERROR_MAKING_WRITABLE, file.getAbsolutePath()));
		}
		else
		{
			return true;
		}
	}

	/**
	 * Ensures that a file exists and is writable, and performs any necessary
	 * operations to create this condition if it does not already exist.
	 *
	 * @param file the destination file to be checked; must be a file, not a
	 * directory.
	 * @return true if the operation is successful; otherwise, false.
	 */
	public static boolean ensureWritable(final File file)
	{
		// Run a security check first, so we don't go through this whole
		// process only to find that we don't have JVM access to the file.
		final SecurityManager security = System.getSecurityManager();
		if (security != null)
		{
			try
			{
				security.checkWrite(file.getAbsolutePath());
			}
			catch (SecurityException ex)
			{
				return fail(R.string(ERROR_SECURITY));
			}
		}

		final File parent = file.getParentFile();
		if (!parent.exists())
		{
			if (!parent.mkdirs())
			{
				return fail(R.string(ERROR_MKDIRS, parent.getAbsolutePath()));
			}
			else
			{
				return createWritableFile(file);
			}
		}
		else if (!parent.canWrite())
		{
			if (!parent.setWritable(true, true))
			{
				return fail(R.string(ERROR_MAKING_WRITABLE, parent.getAbsolutePath()));
			}
			else if (!parent.canWrite() && !parent.setWritable(true))
			{
				return fail(R.string(ERROR_MAKING_WRITABLE, parent.getAbsolutePath()));
			}
			else if (!parent.canWrite())
			{
				return fail(R.string(ERROR_MAKING_WRITABLE, parent.getAbsolutePath()));
			}
			else if (file.canWrite())
			{
				return createWritableFile(file);
			}
			else
			{
				return fail(R.string(ERROR_MAKING_WRITABLE, parent.getAbsolutePath()));
			}
		}

		if (file.exists())
		{
			if (!file.canWrite() || !file.isFile())
			{
				replaceFile(file);
				return createWritableFile(file);
			}
			else
			{
				return true;
			}
		}
		else
		{
			return createWritableFile(file);
		}
	}
}
