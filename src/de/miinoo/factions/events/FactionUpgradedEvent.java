package de.miinoo.factions.events;

import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.model.Faction;
import de.miinoo.factions.model.FactionLevel;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class FactionUpgradedEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    Player player;
    Faction faction;
    int oldLevel;
    int newLevel;

    /**
     * @param player who levels the faction up
     * @param faction that leveled up
     *
     */
    public FactionUpgradedEvent(Player player, Faction faction, int oldLevel) {
        this.player = player;
        this.faction = faction;
        this.oldLevel = oldLevel;

        newLevel = oldLevel + 1;
    }

    public Player getPlayer() {
        return player;
    }

    public Faction getFaction() {
        return faction;
    }

    public int getOldLevel() {
        return oldLevel;
    }

    public int getNewLevel() {
        return newLevel;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
