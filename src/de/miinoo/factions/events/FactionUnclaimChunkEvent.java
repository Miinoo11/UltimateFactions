package de.miinoo.factions.events;

import de.miinoo.factions.model.Faction;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class FactionUnclaimChunkEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    Player player;
    Faction faction;

    /**
     * @param player who unclaimed a chunk
     * @param faction the chunk is owned by
     *
     */
    public FactionUnclaimChunkEvent(Player player, Faction faction) {
        this.player = player;
        this.faction = faction;
    }

    public FactionUnclaimChunkEvent(Faction faction) {
        this.faction = faction;
    }

    public Player getPlayer() {
        return player;
    }

    public Faction getFaction() {
        return faction;
    }

    /**
     * @return chunk the faction has unclaimed
     */
    public Chunk getChunk() {
        return player.getLocation().getChunk();
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
