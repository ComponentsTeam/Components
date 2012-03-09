package com.earth2me.components;

import com.earth2me.components.storage.YamlStore;
import org.bukkit.Server;


/**
 * Provides a standard context for all components and plugins.
 *
 * @author Zenexer
 */
public interface IContext
{
	/**
	 * Gets the server associated with this instance.
	 *
	 * @return a server object representing the CraftBukkit server.
	 */
	Server getServer();

	/**
	 * Gets a mutable collection of settings common to all components.
	 * @return a data store of common settings.
	 */
	YamlStore<CommonSettings> getCommonSettings();
}
