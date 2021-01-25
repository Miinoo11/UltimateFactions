package de.miinoo.factions.events;

import de.miinoo.factions.model.Faction;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class FactionDisbandEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    Player player;
    Faction faction;

    /**
     * @param player who disbanded the faction
     * @param faction which gets disbanded
     */
    public FactionDisbandEvent(Player player, Faction faction) {
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
