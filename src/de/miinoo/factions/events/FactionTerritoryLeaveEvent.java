package de.miinoo.factions.events;


import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class FactionTerritoryLeaveEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    Player player;
    Chunk chunk;

    public FactionTerritoryLeaveEvent(Player player, Chunk chunk) {
        this.player = player;
        this.chunk = chunk;
    }

    public Player getPlayer() {
        return player;
    }

    public Chunk getChunk() {
        return chunk;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
