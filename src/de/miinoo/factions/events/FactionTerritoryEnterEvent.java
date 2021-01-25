package de.miinoo.factions.events;

import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.model.Faction;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class FactionTerritoryEnterEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    Player player;
    Chunk chunk;

    public FactionTerritoryEnterEvent(Player player, Chunk chunk) {
        this.player = player;
        this.chunk = chunk;
    }

    public Player getPlayer() {
        return player;
    }

    public Chunk getChunk() {
        return chunk;
    }

    public Faction getFaction(Chunk chunk) {
        return FactionsSystem.getFactions().getFaction(chunk);
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
