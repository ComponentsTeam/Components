package com.earth2me.components;

import com.earth2me.components.plugin.ComponentsPlugin;
import com.earth2me.components.storage.CommonSettings;
import com.earth2me.components.storage.IBackedStore;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.logging.Logger;
import org.apache.cassandra.thrift.*;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;


/**
 * Provides a mutable standard context for all components and plugins.
 *
 * @author Zenexer
 */
public final class Context implements IContext, IClosable
{
	private static transient IContext context;
	private final transient Server server;
	private final transient ComponentsPlugin plugin;
	private transient ScheduledThreadPoolExecutor scheduler;
	private transient IBackedStore<CommonSettings> commonSettings;

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
	
	public void start()
	{
		scheduler = new ScheduledThreadPoolExecutor(plugin.getConfig().getInt("core.thread-pool-size"));
	}

	@Override
	public void close()
	{
		scheduler.shutdown();
		scheduler = null;
	}
	
	public User getUser(OfflinePlayer player)
	{
		return new User(this, player);
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
		return getServer().getLogger();
	}

	/**
	 * @return the context
	 */
	public static IContext getContext()
	{
		return context;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IBackedStore<CommonSettings> getCommonSettings()
	{
		return commonSettings;
	}
	
	/**
	 * set the common settings.
	 * @param commonSettings 
	 */
	public void setCommonSettings(IBackedStore<CommonSettings> commonSettings)
	{
		this.commonSettings = commonSettings;
	}

	/**
	 * @return the server
	 */
	@Override
	public Server getServer()
	{
		return server;
	}

	/**
	 * @return the plugin
	 */
	@Override
	public ComponentsPlugin getPlugin()
	{
		return plugin;
	}

	@Override
	public void enqueueTask(Runnable task)
	{
		scheduler.execute(task);
	}
}
