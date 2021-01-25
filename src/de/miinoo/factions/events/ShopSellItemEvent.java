package de.miinoo.factions.events;

import de.miinoo.factions.shop.ShopItem;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ShopSellItemEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    Player player;
    ShopItem shopItem;

    /**
     * @param player who creates the faction
     * @param shopItem that was sold
     */
    public ShopSellItemEvent(Player player, ShopItem shopItem) {
        this.player = player;
        this.shopItem = shopItem;
    }

    public Player getPlayer() {
        return player;
    }

    public ShopItem getShopItem() {
        return shopItem;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
