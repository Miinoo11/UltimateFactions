package de.miinoo.factions.randomteleport;

/**
 * @author Miinoo_
 * 23.08.2020
 **/

import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.util.TeleportUtils;
import org.bukkit.entity.*;
import org.bukkit.*;

public class TeleportHandler
{
    private final FactionsSystem plugin;
    private final Player player;
    private final World world;
    private int xCoord;
    private int zCoord;
    private int xF;
    private int yF;
    private int zF;

    public TeleportHandler(final FactionsSystem plugin, final Player player, final World world, final int xCoord, final int zCoord) {
        this.xCoord = -1;
        this.zCoord = -1;
        this.plugin = plugin;
        this.player = player;
        this.world = world;
        this.xCoord = xCoord;
        this.zCoord = zCoord;
    }

    public void teleport() {
        final Location location = this.getLocation();
        if (location == null) {
            this.player.sendMessage(ChatColor.RED + "ERROR: Failed to find a safe teleport location!");
            return;
        }
        this.player.teleport(location);
        //this.player.sendMessage(ChatColor.DARK_AQUA + "Teleported to the location:");
        //this.player.sendMessage(ChatColor.DARK_AQUA + "X: " + ChatColor.AQUA + this.xF);
        //this.player.sendMessage(ChatColor.DARK_AQUA + "Y: " + ChatColor.AQUA + this.yF);
        //this.player.sendMessage(ChatColor.DARK_AQUA + "Z: " + ChatColor.AQUA + this.zF);
        //this.player.sendMessage(ChatColor.DARK_AQUA + "World: " + ChatColor.AQUA + this.world.getName());
        //this.player.sendMessage(" ");
    }

    public int getX() {
        return this.xF;
    }

    public int getY() {
        return this.yF;
    }

    public int getZ() {
        return this.zF;
    }

    private void set(final double x, final double y, final double z) {
        this.xF = (int)x;
        this.yF = (int)y;
        this.zF = (int)z;
    }

    protected Location getLocation() {
        int x = FactionsSystem.getRandom().nextInt(this.xCoord);
        int z = FactionsSystem.getRandom().nextInt(this.zCoord);
        x = this.randomizeType(x);
        z = this.randomizeType(z);
        final int y = 63;
        Location location = TeleportUtils.safeizeLocation(new Location(this.world, (double)x, (double)y, (double)z));
        if (location == null) {
            return null;
        }
        final String biome = location.getBlock().getBiome().toString();
        if (FactionsSystem.getSettings().wildDisabledBiomes().contains(biome)) {
            location = TeleportUtils.safeizeLocation(this.getLocation());
        }
        if (location == null) {
            return null;
        }
        this.set(location.getX(), location.getY(), location.getZ());
        return location;
    }

    protected int randomizeType(final int i) {
        final int j = this.plugin.getRandom().nextInt(2);
        switch (j) {
            case 0: {
                return -i;
            }
            case 1: {
                return i;
            }
            default: {
                return -1;
            }
        }
    }
}
