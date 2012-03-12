package com.earth2me.components;

import com.earth2me.components.storage.UserSurrogate;
import lombok.Delegate;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.Metadatable;
import org.bukkit.permissions.Permissible;


/**
 * Wraps online, offline, and surrogate players.
 *
 * @author Zenexer
 */
@SuppressWarnings("deprecation")
public final class User implements Player
{
	@Delegate(types =
	{
		Player.class,
		LivingEntity.class,
		Metadatable.class,
		Permissible.class,
	},
	excludes =
	{
		OfflinePlayer.class
	})
	private transient Player online;

	@Delegate
	private transient OfflinePlayer stateless;

	private transient UserSurrogate surrogate;
}
