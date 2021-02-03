package de.miinoo.factions.events;

import de.miinoo.factions.model.Faction;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class FactionChangeDescriptionEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    Player player;
    Faction faction;
    String oldDesc;
    String newDesc;

    /**
     * @param player who changed the description
     * @param faction that changed the description
     *
     */
    public FactionChangeDescriptionEvent(Player player, Faction faction, String oldDesc, String newDesc) {
        this.player = player;
        this.faction = faction;
        this.oldDesc = oldDesc;
        this.newDesc = newDesc;
    }

    public Player getPlayer() {
        return player;
    }

    public Faction getFaction() {
        return faction;
    }

    public String getNewDesc() {
        return newDesc;
    }

    public String getOldDesc() {
        return oldDesc;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
