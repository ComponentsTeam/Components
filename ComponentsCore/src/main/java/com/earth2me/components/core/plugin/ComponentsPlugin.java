package com.earth2me.components.core.plugin;

import com.earth2me.components.core.Context;
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
	private transient Context context;

	/**
	 * Called immediately after the plugin loads.
	 */
	@Override
	public void onLoad()
	{
		reportState("Loaded");
		super.onLoad();
	}

	@Override
	public void onEnable()
	{
		context.start();
		
		reportState("Enabled");
		super.onEnable();
	}

	@Override
	public void onDisable()
	{
		context.close();
		
		reportState("Disabled");
		super.onDisable();
	}

	/**
	 * Report when the plugin state has changed.
	 *
	 * @param action Loaded, Enabled, Disabled.
	 */
	private void reportState(String action)
	{
		final PluginDescriptionFile description = getDescription();
		getServer().getLogger().log(Level.INFO, "{0} {1}.", new Object[] { action, description.getFullName() });
	}
}
