package com.earth2me.components;

import com.earth2me.components.storage.IBackedStore;
import com.earth2me.components.storage.UserSurrogate;
import com.earth2me.components.storage.YamlStore;
import com.earth2me.components.util.FileExtension;
import java.io.File;
import java.io.IOException;
import org.bukkit.OfflinePlayer;


public final class User
{
	private final transient IContext context;
	private final transient OfflinePlayer player;
	private final transient IBackedStore<UserSurrogate> store;
	
	private User(IContext context, OfflinePlayer player, boolean readonly)
		throws IOException
	{
		this.context = context;
		this.player = player;
		
		String fileName = FileExtension.escape(player.getName()) + ".yml";
		File file = new File(context.getPlugin().getDataFolder(), "db/users/" + fileName);
		
		this.store = new YamlStore<UserSurrogate>(file, UserSurrogate.createDefault(), readonly);
	}
	
	public UserSurrogate getData()
	{
		return store.getData();
	}
	
	public void close()
	{
	}
}
