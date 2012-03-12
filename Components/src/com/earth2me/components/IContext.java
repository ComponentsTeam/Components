package com.earth2me.components;

import com.earth2me.components.storage.CommonSettings;
import com.earth2me.components.plugin.ComponentsPlugin;
import com.earth2me.components.storage.YamlStore;
import java.util.logging.Logger;
import org.bukkit.Server;


/**
 * Provides a standard context for all components and plugins.
 *
 * @author Zenexer
 */
public interface IContext
{
	/**
	 * Gets the plugin containing this context.
	 *
	 * @return the plugin containing this context.
	 */
	ComponentsPlugin getPlugin();

	/**
	 * Gets the server associated with this instance.
	 *
	 * @return a server object representing the CraftBukkit server.
	 */
	Server getServer();

	/**
	 * Gets a mutable collection of settings common to all components.
	 *
	 * @return a data store of common settings.
	 */
	YamlStore<CommonSettings> getCommonSettings();

	/**
	 * Gets a logger for reporting notices, warnings, and errors.
	 *
	 * @return a logger appropriate for the context.
	 */
	Logger getLogger();
}
