package com.earth2me.components.core;

import org.bukkit.OfflinePlayer;


public final class User
{
	private final transient IContext context;
	private final transient OfflinePlayer player;
	
	public User(IContext context, OfflinePlayer player)
	{
		this.context = context;
		this.player = player;
	}
}
