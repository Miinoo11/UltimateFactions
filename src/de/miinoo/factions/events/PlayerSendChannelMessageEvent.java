package de.miinoo.factions.events;

import de.miinoo.factions.model.Faction;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerSendChannelMessageEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    Player player;
    String channel;
    String message;

    /**
     * @param player which sends the message
     * @param channel the channel the message was send in
     * @param message that was sent
     */
    public PlayerSendChannelMessageEvent(Player player, String channel, String message) {
        this.player = player;
        this.channel = channel;
        this.message = message;
    }

    public Player getPlayer() {
        return player;
    }

    public String getChannel() {
        return channel;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}