package com.earth2me.components.core;

import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.cassandra.thrift.ThriftServer;
import org.bukkit.Bukkit;


/**
 * @author Zenexer
 */
public final class CassandraManager implements AutoCloseable
{
	private transient ThriftServer server;

	public CassandraManager()
	{
	}

	public boolean start(final InetAddress iface, final int port)
	{
		try
		{
			server = new ThriftServer(InetAddress.getLoopbackAddress(), port);
			server.start();
		}
		catch (Throwable ex)
		{
			Bukkit.getLogger().log(Level.SEVERE, "Error starting Cassandra daemon.", ex);
			return false;
		}

		return true;
	}

	@Override
	public void close()
	{
		if (server != null)
		{
			try
			{
				if (server.isRunning())
				{
					server.stop();
				}
			}
			catch (Exception ex)
			{
				Bukkit.getLogger().warning(ex.toString());
			}

			server = null;
		}
	}
}
