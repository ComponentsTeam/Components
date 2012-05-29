package com.earth2me.components;

import com.earth2me.components.storage.IBackedStore;
import com.earth2me.components.storage.UserSurrogate;
import com.earth2me.components.storage.YamlStore;
import java.net.InetSocketAddress;
import java.util.*;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.InventoryView.Property;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.map.MapView;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;


/**
 * Wraps online, offline, and surrogate players.
 *
 * @author Zenexer
 */
public final class User implements Player
{
	private final transient Player online;
	private final transient OfflinePlayer stateless;
	private final transient IBackedStore<UserSurrogate> store;
	private final transient IContext context;
	
	private User(IContext context, OfflinePlayer stateless, Player online, IBackedStore<UserSurrogate> store)
	{
		this.context = context;
		this.stateless = stateless;
		this.online = online;
		this.store = store;
	}
	
	public IContext getContext()
	{
		return context;
	}
	
	public UserSurrogate getData()
	{
		return store.getData();
	}

	/**
	 * Gets the display name of the player.
	 * @return the display name of the player
	 */
	@Override
	public String getDisplayName()
	{
		return online.getDisplayName();
	}

	/**
	 * Sets the display name of the player.
	 * @param name the new display name of the player
	 */
	@Override
	public void setDisplayName(String name)
	{
		online.setDisplayName(name);
	}

	/**
	 * Gets the username that appears in the playerlist representing this player.
	 * @return the username
	 */
	@Override
	public String getPlayerListName()
	{
		return online.getPlayerListName();
	}

	/**
	 * Sets the username that appears in the playerlist representing this player.
	 * @param name the new username
	 */
	@Override
	public void setPlayerListName(String name)
	{
		online.setPlayerListName(name);
	}

	/**
	 * Sets where the player's compass should point.
	 * @param lctn the world coordinates to where the compass should point
	 */
	@Override
	public void setCompassTarget(Location lctn)
	{
		online.setCompassTarget(lctn);
	}

	/**
	 * Gets the current target of the player's compass.
	 * @return the target of the compass
	 */
	@Override
	public Location getCompassTarget()
	{
		return online.getCompassTarget();
	}

	/**
	 * Gets the IP address of the player.
	 * @return the IP address of the player
	 */
	@Override
	public InetSocketAddress getAddress()
	{
		return online.getAddress();
	}

	/**
	 * Sends a plaintext chat message to the player.
	 * @param text the text to send
	 */
	@Override
	public void sendRawMessage(String text)
	{
		online.sendRawMessage(text);
	}

	/**
	 * Kicks the player from the server.
	 * @param reason the reason for the kick
	 */
	@Override
	public void kickPlayer(String reason)
	{
		online.kickPlayer(reason);
	}

	/**
	 * Broadcasts a chat message to the server as if it came from the player.
	 * @param message the message body 
	 */
	@Override
	public void chat(String message)
	{
		online.chat(message);
	}

	/**
	 * Performs a command as if it came from the player.
	 * @param command the command to perform
	 * @return true if the command was handled; otherwise, false
	 */
	@Override
	public boolean performCommand(String command)
	{
		return online.performCommand(command);
	}

	/**
	 * Determines whether the player is currently sneaking.
	 * @return true if the player is sneaking; otherwise, false
	 */
	@Override
	public boolean isSneaking()
	{
		return online.isSneaking();
	}

	/**
	 * Forces the player in or out of a sneak state.
	 * @param sneaking true if the player is to be sneaking; otherwise, false
	 */
	@Override
	public void setSneaking(boolean sneaking)
	{
		online.setSneaking(sneaking);
	}

	/**
	 * Determines whether the player is currently sprinting.
	 * @return true if the player is sprinting; otherwise, false
	 */
	@Override
	public boolean isSprinting()
	{
		return online.isSprinting();
	}

	/**
	 * Forces the player in or out of a sprint state.
	 * @param sprinting true if the player is to be sprinting; otherwise, false
	 */
	@Override
	public void setSprinting(boolean sprinting)
	{
		online.setSprinting(sprinting);
	}

	/**
	 * Forces a save of the player's database file.
	 */
	@Override
	public void saveData()
	{
		online.saveData();
	}

	/**
	 * Forces the player's data to be reloaded from the filesystem.
	 */
	@Override
	public void loadData()
	{
		online.loadData();
	}

	@Override
	public void setSleepingIgnored(boolean sleepingIgnored)
	{
		online.setSleepingIgnored(sleepingIgnored);
	}

	@Override
	public boolean isSleepingIgnored()
	{
		return online.isSleepingIgnored();
	}

	@Override
	public void playNote(Location location, byte a, byte b)
	{
		online.playNote(location, a, b);
	}

	@Override
	public void playNote(Location location, Instrument instrument, Note note)
	{
		online.playNote(location, instrument, note);
	}

	@Override
	public void playEffect(Location location, Effect effect, int i)
	{
		online.playEffect(location, effect, i);
	}

	@Override
	public <T> void playEffect(Location location, Effect effect, T t)
	{
		online.playEffect(location, effect, t);
	}

	@Override
	public void sendBlockChange(Location location, Material material, byte b)
	{
		online.sendBlockChange(location, material, b);
	}

	@Override
	public boolean sendChunkChange(Location location, int i, int i1, int i2, byte[] bytes)
	{
		return online.sendChunkChange(location, i, i1, i2, bytes);
	}

	@Override
	public void sendBlockChange(Location location, int i, byte b)
	{
		online.sendBlockChange(location, i, b);
	}

	@Override
	public void sendMap(MapView mv)
	{
		online.sendMap(mv);
	}

	@Override
	@SuppressWarnings("deprecation")
	public void updateInventory()
	{
		online.updateInventory();
	}

	@Override
	public void awardAchievement(Achievement a)
	{
		online.awardAchievement(a);
	}

	@Override
	public void incrementStatistic(Statistic statistic)
	{
		online.incrementStatistic(statistic);
	}

	@Override
	public void incrementStatistic(Statistic statistic, int step)
	{
		online.incrementStatistic(statistic, step);
	}

	@Override
	public void incrementStatistic(Statistic statistic, Material material)
	{
		online.incrementStatistic(statistic, material);
	}

	@Override
	public void incrementStatistic(Statistic statistic, Material material, int step)
	{
		online.incrementStatistic(statistic, material, step);
	}

	@Override
	public void setPlayerTime(long ticks, boolean bln)
	{
		online.setPlayerTime(ticks, bln);
	}

	@Override
	public long getPlayerTime()
	{
		return online.getPlayerTime();
	}

	@Override
	public long getPlayerTimeOffset()
	{
		return online.getPlayerTimeOffset();
	}

	@Override
	public boolean isPlayerTimeRelative()
	{
		return online.isPlayerTimeRelative();
	}

	@Override
	public void resetPlayerTime()
	{
		online.resetPlayerTime();
	}

	@Override
	public void giveExp(int xp)
	{
		online.giveExp(xp);
	}

	@Override
	public float getExp()
	{
		return online.getExp();
	}

	@Override
	public void setExp(float xp)
	{
		online.setExp(xp);
	}

	@Override
	public int getLevel()
	{
		return online.getLevel();
	}

	@Override
	public void setLevel(int level)
	{
		online.setLevel(level);
	}

	@Override
	public int getTotalExperience()
	{
		return online.getTotalExperience();
	}

	@Override
	public void setTotalExperience(int totalXp)
	{
		online.setTotalExperience(totalXp);
	}

	@Override
	public float getExhaustion()
	{
		return online.getExhaustion();
	}

	@Override
	public void setExhaustion(float exhaustion)
	{
		online.setExhaustion(exhaustion);
	}

	@Override
	public float getSaturation()
	{
		return online.getSaturation();
	}

	@Override
	public void setSaturation(float saturation)
	{
		online.setSaturation(saturation);
	}

	@Override
	public int getFoodLevel()
	{
		return online.getFoodLevel();
	}

	@Override
	public void setFoodLevel(int level)
	{
		online.setFoodLevel(level);
	}

	@Override
	public Location getBedSpawnLocation()
	{
		return stateless.getBedSpawnLocation();
	}

	@Override
	public void setBedSpawnLocation(Location location)
	{
		online.setBedSpawnLocation(location);
	}

	@Override
	public boolean getAllowFlight()
	{
		return online.getAllowFlight();
	}

	@Override
	public void setAllowFlight(boolean allow)
	{
		online.setAllowFlight(allow);
	}

	@Override
	public void hidePlayer(Player player)
	{
		online.hidePlayer(player);
	}

	@Override
	public void showPlayer(Player player)
	{
		online.showPlayer(player);
	}

	@Override
	public boolean canSee(Player player)
	{
		return online.canSee(player);
	}

	@Override
	public boolean isFlying()
	{
		return online.isFlying();
	}

	@Override
	public void setFlying(boolean flying)
	{
		online.setFlying(flying);
	}

	@Override
	public String getName()
	{
		return stateless.getName();
	}

	@Override
	public PlayerInventory getInventory()
	{
		return online.getInventory();
	}

	@Override
	public boolean setWindowProperty(Property property, int value)
	{
		return online.setWindowProperty(property, value);
	}

	@Override
	public InventoryView getOpenInventory()
	{
		return online.getOpenInventory();
	}

	@Override
	public InventoryView openInventory(Inventory inventory)
	{
		return online.openInventory(inventory);
	}

	@Override
	public InventoryView openWorkbench(Location location, boolean bln)
	{
		return online.openWorkbench(location, bln);
	}

	@Override
	public InventoryView openEnchanting(Location location, boolean bln)
	{
		return online.openEnchanting(location, bln);
	}

	@Override
	public void openInventory(InventoryView iv)
	{
		online.openInventory(iv);
	}

	@Override
	public void closeInventory()
	{
		online.closeInventory();
	}

	@Override
	public ItemStack getItemInHand()
	{
		return online.getItemInHand();
	}

	@Override
	public void setItemInHand(ItemStack is)
	{
		online.setItemInHand(is);
	}

	@Override
	public ItemStack getItemOnCursor()
	{
		return online.getItemOnCursor();
	}

	@Override
	public void setItemOnCursor(ItemStack is)
	{
		online.setItemOnCursor(is);
	}

	@Override
	public boolean isSleeping()
	{
		return online.isSleeping();
	}

	@Override
	public int getSleepTicks()
	{
		return online.getSleepTicks();
	}

	@Override
	public GameMode getGameMode()
	{
		return online.getGameMode();
	}

	@Override
	public void setGameMode(GameMode gm)
	{
		online.setGameMode(gm);
	}

	@Override
	public boolean isBlocking()
	{
		return online.isBlocking();
	}

	@Override
	public int getHealth()
	{
		return online.getHealth();
	}

	@Override
	public void setHealth(int i)
	{
		online.setHealth(i);
	}

	@Override
	public int getMaxHealth()
	{
		return online.getMaxHealth();
	}

	@Override
	public double getEyeHeight()
	{
		return online.getEyeHeight();
	}

	@Override
	public double getEyeHeight(boolean bln)
	{
		return online.getEyeHeight(bln);
	}

	@Override
	public Location getEyeLocation()
	{
		return online.getEyeLocation();
	}

	@Override
	public List<Block> getLineOfSight(HashSet<Byte> hs, int i)
	{
		return online.getLineOfSight(hs, i);
	}

	@Override
	public Block getTargetBlock(HashSet<Byte> hs, int i)
	{
		return online.getTargetBlock(hs, i);
	}

	@Override
	public List<Block> getLastTwoTargetBlocks(HashSet<Byte> hs, int i)
	{
		return online.getLastTwoTargetBlocks(hs, i);
	}

	@Override
	@SuppressWarnings("deprecation")
	public Egg throwEgg()
	{
		return online.throwEgg();
	}

	@Override
	@SuppressWarnings("deprecation")
	public Snowball throwSnowball()
	{
		return online.throwSnowball();
	}

	@Override
	public Arrow shootArrow()
	{
		return online.shootArrow();
	}

	@Override
	public <T extends Projectile> T launchProjectile(Class<? extends T> type)
	{
		return online.launchProjectile(type);
	}

	@Override
	public int getRemainingAir()
	{
		return online.getRemainingAir();
	}

	@Override
	public void setRemainingAir(int i)
	{
		online.setRemainingAir(i);
	}

	@Override
	public int getMaximumAir()
	{
		return online.getMaximumAir();
	}

	@Override
	public void setMaximumAir(int i)
	{
		online.setMaximumAir(i);
	}

	@Override
	public void damage(int i)
	{
		online.damage(i);
	}

	@Override
	public void damage(int i, Entity entity)
	{
		online.damage(i, entity);
	}

	@Override
	public int getMaximumNoDamageTicks()
	{
		return online.getMaximumNoDamageTicks();
	}

	@Override
	public void setMaximumNoDamageTicks(int i)
	{
		online.setMaximumNoDamageTicks(i);
	}

	@Override
	public int getLastDamage()
	{
		return online.getLastDamage();
	}

	@Override
	public void setLastDamage(int i)
	{
		online.setLastDamage(i);
	}

	@Override
	public int getNoDamageTicks()
	{
		return online.getNoDamageTicks();
	}

	@Override
	public void setNoDamageTicks(int i)
	{
		online.setNoDamageTicks(i);
	}

	@Override
	public Player getKiller()
	{
		return online.getKiller();
	}

	@Override
	public boolean addPotionEffect(PotionEffect pe)
	{
		return online.addPotionEffect(pe);
	}

	@Override
	public boolean addPotionEffect(PotionEffect pe, boolean bln)
	{
		return online.addPotionEffect(pe, bln);
	}

	@Override
	public boolean addPotionEffects(Collection<PotionEffect> clctn)
	{
		return online.addPotionEffects(clctn);
	}

	@Override
	public boolean hasPotionEffect(PotionEffectType pet)
	{
		return online.hasPotionEffect(pet);
	}

	@Override
	public void removePotionEffect(PotionEffectType pet)
	{
		online.removePotionEffect(pet);
	}

	@Override
	public Collection<PotionEffect> getActivePotionEffects()
	{
		return online.getActivePotionEffects();
	}

	@Override
	public Location getLocation()
	{
		return online.getLocation();
	}

	@Override
	public void setVelocity(Vector vector)
	{
		online.setVelocity(vector);
	}

	@Override
	public Vector getVelocity()
	{
		return online.getVelocity();
	}

	@Override
	public World getWorld()
	{
		return online.getWorld();
	}

	@Override
	public boolean teleport(Location lctn)
	{
		return online.teleport(lctn);
	}

	@Override
	public boolean teleport(Location lctn, TeleportCause tc)
	{
		return online.teleport(lctn, tc);
	}

	@Override
	public boolean teleport(Entity entity)
	{
		return online.teleport(entity);
	}

	@Override
	public boolean teleport(Entity entity, TeleportCause tc)
	{
		return online.teleport(entity, tc);
	}

	@Override
	public List<Entity> getNearbyEntities(double d, double d1, double d2)
	{
		return online.getNearbyEntities(d, d1, d2);
	}

	@Override
	public int getEntityId()
	{
		return online.getEntityId();
	}

	@Override
	public int getFireTicks()
	{
		return online.getFireTicks();
	}

	@Override
	public int getMaxFireTicks()
	{
		return online.getMaxFireTicks();
	}

	@Override
	public void setFireTicks(int i)
	{
		online.setFireTicks(i);
	}

	@Override
	public void remove()
	{
		online.remove();
	}

	@Override
	public boolean isDead()
	{
		return online.isDead();
	}

	@Override
	public Server getServer()
	{
		return context.getServer();
	}

	@Override
	public Entity getPassenger()
	{
		return online.getPassenger();
	}

	@Override
	public boolean setPassenger(Entity entity)
	{
		return online.setPassenger(entity);
	}

	@Override
	public boolean isEmpty()
	{
		return online.isEmpty();
	}

	@Override
	public boolean eject()
	{
		return online.eject();
	}

	@Override
	public float getFallDistance()
	{
		return online.getFallDistance();
	}

	@Override
	public void setFallDistance(float f)
	{
		online.setFallDistance(f);
	}

	@Override
	public void setLastDamageCause(EntityDamageEvent ede)
	{
		online.setLastDamageCause(ede);
	}

	@Override
	public EntityDamageEvent getLastDamageCause()
	{
		return online.getLastDamageCause();
	}

	@Override
	public UUID getUniqueId()
	{
		return online.getUniqueId();
	}

	@Override
	public int getTicksLived()
	{
		return online.getTicksLived();
	}

	@Override
	public void setTicksLived(int i)
	{
		online.setTicksLived(i);
	}

	@Override
	public void playEffect(EntityEffect ee)
	{
		online.playEffect(ee);
	}

	@Override
	public EntityType getType()
	{
		return online.getType();
	}

	@Override
	public boolean isInsideVehicle()
	{
		return online.isInsideVehicle();
	}

	@Override
	public boolean leaveVehicle()
	{
		return online.leaveVehicle();
	}

	@Override
	public Entity getVehicle()
	{
		return online.getVehicle();
	}

	@Override
	public void setMetadata(String string, MetadataValue mv)
	{
		online.setMetadata(string, mv);
	}

	@Override
	public List<MetadataValue> getMetadata(String string)
	{
		return online.getMetadata(string);
	}

	@Override
	public boolean hasMetadata(String string)
	{
		return online.hasMetadata(string);
	}

	@Override
	public void removeMetadata(String string, Plugin plugin)
	{
		online.removeMetadata(string, plugin);
	}

	@Override
	public boolean isPermissionSet(String string)
	{
		return online.isPermissionSet(string);
	}

	@Override
	public boolean isPermissionSet(Permission prmsn)
	{
		return online.isPermissionSet(prmsn);
	}

	@Override
	public boolean hasPermission(String string)
	{
		return online.hasPermission(string);
	}

	@Override
	public boolean hasPermission(Permission prmsn)
	{
		return online.hasPermission(prmsn);
	}

	@Override
	public PermissionAttachment addAttachment(Plugin plugin, String string, boolean bln)
	{
		return online.addAttachment(plugin, string, bln);
	}

	@Override
	public PermissionAttachment addAttachment(Plugin plugin)
	{
		return online.addAttachment(plugin);
	}

	@Override
	public PermissionAttachment addAttachment(Plugin plugin, String string, boolean bln, int i)
	{
		return online.addAttachment(plugin, string, bln, i);
	}

	@Override
	public PermissionAttachment addAttachment(Plugin plugin, int i)
	{
		return online.addAttachment(plugin, i);
	}

	@Override
	public void removeAttachment(PermissionAttachment pa)
	{
		online.removeAttachment(pa);
	}

	@Override
	public void recalculatePermissions()
	{
		online.recalculatePermissions();
	}

	@Override
	public Set<PermissionAttachmentInfo> getEffectivePermissions()
	{
		return online.getEffectivePermissions();
	}

	@Override
	public boolean isOp()
	{
		return stateless.isOp();
	}

	@Override
	public void setOp(boolean bln)
	{
		stateless.setOp(bln);
	}

	@Override
	public boolean isConversing()
	{
		return online.isConversing();
	}

	@Override
	public void acceptConversationInput(String string)
	{
		online.acceptConversationInput(string);
	}

	@Override
	public boolean beginConversation(Conversation c)
	{
		return online.beginConversation(c);
	}

	@Override
	public void abandonConversation(Conversation c)
	{
		online.abandonConversation(c);
	}

	@Override
	public void abandonConversation(Conversation c, ConversationAbandonedEvent cae)
	{
		online.abandonConversation(c, cae);
	}

	@Override
	public void sendMessage(String string)
	{
		online.sendMessage(string);
	}

	@Override
	public void sendMessage(String[] strings)
	{
		online.sendMessage(strings);
	}

	@Override
	public boolean isOnline()
	{
		return stateless.isOnline();
	}

	@Override
	public boolean isBanned()
	{
		return stateless.isBanned();
	}

	@Override
	public void setBanned(boolean bln)
	{
		stateless.setBanned(bln);
	}

	@Override
	public boolean isWhitelisted()
	{
		return stateless.isWhitelisted();
	}

	@Override
	public void setWhitelisted(boolean bln)
	{
		stateless.setWhitelisted(bln);
	}

	@Override
	public Player getPlayer()
	{
		return stateless.getPlayer();
	}

	@Override
	public long getFirstPlayed()
	{
		return stateless.getFirstPlayed();
	}

	@Override
	public long getLastPlayed()
	{
		return stateless.getLastPlayed();
	}

	@Override
	public boolean hasPlayedBefore()
	{
		return stateless.hasPlayedBefore();
	}

	@Override
	public Map<String, Object> serialize()
	{
		return stateless.serialize();
	}

	@Override
	public void sendPluginMessage(Plugin plugin, String string, byte[] bytes)
	{
		online.sendPluginMessage(plugin, string, bytes);
	}

	@Override
	public Set<String> getListeningPluginChannels()
	{
		return online.getListeningPluginChannels();
	}
}
