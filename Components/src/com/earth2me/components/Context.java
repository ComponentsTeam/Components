package com.earth2me.components;

import com.earth2me.components.storage.CommonSettings;
import com.earth2me.components.plugin.ComponentsPlugin;
import com.earth2me.components.storage.YamlStore;
import java.util.logging.Logger;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Server;


/**
 * Provides a mutable standard context for all components and plugins.
 *
 * @author Zenexer
 */
public final class Context implements IContext
{
	@Getter
	private static transient IContext context;

	@Getter
	private final transient Server server;

	@Getter
	private final transient ComponentsPlugin plugin;

	@Getter
	@Setter
	private YamlStore<CommonSettings> commonSettings;

	/**
	 * Instantiates a new context with the default server.
	 *
	 * @param plugin the plugin containing this context.
	 */
	public Context(final ComponentsPlugin plugin)
	{
		this(plugin, Bukkit.getServer());
	}

	/**
	 * Instantiates a new context with the specified server.
	 *
	 * @param plugin the plugin containing this context.
	 * @param server the server to be used by this context.
	 */
	public Context(final ComponentsPlugin plugin, final Server server)
	{
		this.plugin = plugin;
		this.server = server;
	}

	/**
	 * Sets this as the active context.
	 */
	public void makeActive()
	{
		context = this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Logger getLogger()
	{
		return server.getLogger();
	}
}
