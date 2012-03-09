package com.earth2me.components;

import com.earth2me.components.storage.YamlStore;
import lombok.Data;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Server;


/**
 * Provides a mutable standard context for all components and plugins.
 *
 * @author Zenexer
 */
@Data
public final class Context implements IContext
{
	@Getter
	private final transient Server server;
	private YamlStore<CommonSettings> commonSettings;

	/**
	 * Instantiates a new context with the default server.
	 */
	public Context()
	{
		this(Bukkit.getServer());
	}

	/**
	 * Instantiates a new context with the specified server.
	 *
	 * @param server the server to be used by this context.
	 */
	public Context(final Server server)
	{
		this.server = server;
	}
}
