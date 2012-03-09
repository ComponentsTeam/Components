package com.earth2me.components.plugin;

import java.util.logging.Level;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;


/**
 * Provides a plugin for dealing with many small components in an efficient
 * manner.
 *
 * @author Zenexer
 */
public class ComponentsPlugin extends JavaPlugin
{
	/**
	 * Called immediately after the plugin loads.
	 */
	@Override
	public void onLoad()
	{
		super.onLoad();

		final PluginDescriptionFile description = getDescription();
		getServer().getLogger().log(Level.INFO, "Loaded {0}.", description.getFullName());
	}
}
