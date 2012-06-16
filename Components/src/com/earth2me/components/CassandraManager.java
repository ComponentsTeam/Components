package com.earth2me.components;

import java.util.logging.Level;
import org.apache.cassandra.thrift.CassandraDaemon;
import org.bukkit.Bukkit;


/**
 * @author Zenexer
 */
public final class CassandraManager implements IClosable
{
	private CassandraDaemon daemon;
	
	public CassandraManager()
	{
	}
	
	public boolean start(final String[] args)
	{
		try
		{
			daemon = new CassandraDaemon();
			daemon.init(args);
			daemon.start();
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
		daemon.stop();
		daemon = null;
	}
}
