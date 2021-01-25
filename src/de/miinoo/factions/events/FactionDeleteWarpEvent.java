package de.miinoo.factions.events;

import de.miinoo.factions.model.Faction;
import de.miinoo.factions.model.FactionWarp;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class FactionDeleteWarpEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    Player player;
    Faction faction;
    FactionWarp warp;
    /**
     * @param player who deletes the warp
     * @param faction the warp was removed by
     * @param warp that was deleted
     */
    public FactionDeleteWarpEvent(Player player, Faction faction, FactionWarp warp) {
        this.player = player;
        this.faction = faction;
        this.warp = warp;
    }

    public Player getPlayer() {
        return player;
    }

    public Faction getFaction() {
        return faction;
    }

    public FactionWarp getWarp() {
        return warp;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
