package de.miinoo.factions.events;

import de.miinoo.factions.model.Faction;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class FactionChangeNameEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    Player player;
    Faction faction;
    String oldName;
    String newName;

    /**
     * @param player who changed the name
     * @param faction that changed the name
     *
     */
    public FactionChangeNameEvent(Player player, Faction faction, String oldName, String newName) {
        this.player = player;
        this.faction = faction;
        this.oldName = oldName;
        this.newName = newName;
    }

    public Player getPlayer() {
        return player;
    }

    public Faction getFaction() {
        return faction;
    }

    public String getNewName() {
        return newName;
    }

    public String getOldName() {
        return oldName;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
