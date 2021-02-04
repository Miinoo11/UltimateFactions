package de.miinoo.factions.events;

import de.miinoo.factions.region.Region;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class RegionEnterEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    Player player;
    Region region;

    public RegionEnterEvent(Player player, Region region) {
        this.player = player;
        this.region = region;
    }

    public Player getPlayer() {
        return player;
    }

    public Region getRegion() {
        return region;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
