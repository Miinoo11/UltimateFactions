package de.miinoo.factions.events;

import de.miinoo.factions.model.Faction;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class FactionPlayerJoinEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    Player player;
    Faction faction;

    /**
     * @param player who creates the faction
     * @param faction the player joined in
     */
    public FactionPlayerJoinEvent(Player player, Faction faction) {
        this.player = player;
        this.faction = faction;
    }

    public Player getPlayer() {
        return player;
    }

    public Faction getFaction() {
        return faction;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
