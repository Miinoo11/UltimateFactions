package de.miinoo.factions.shop;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ShopItem implements ConfigurationSerializable {

    UUID id;
    ItemStack itemStack;
    String displayName;
    int amount;
    String lore;
    double price;
    double sell;
    boolean canSell;

    public ShopItem(ItemStack itemStack, String displayName, int amount, String lore, double price, double sell, boolean canSell) {
        this.id = UUID.randomUUID();
        this.itemStack = itemStack;
        this.displayName = displayName;
        this.amount = amount;
        this.lore = lore;
        this.price = price;
        this.sell = sell;
        this.canSell = canSell;
    }

    public ShopItem(Map<String, Object> args) {
        this.id = UUID.fromString((String) args.get("id"));
        this.itemStack = (ItemStack) args.get("itemStack");
        this.displayName = (String) args.get("displayName");
        this.amount = ((Number) args.get("amount")).intValue();
        this.lore = (String) args.get("lore");
        this.price = (double) args.get("price");
        this.sell = (double) args.get("sell");
        this.canSell = (boolean) args.get("canSell");
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> result = new HashMap<>();
        result.put("id", id.toString());
        result.put("itemStack", itemStack);
        result.put("displayName", displayName);
        result.put("amount", amount);
        result.put("lore", lore);
        result.put("price", price);
        result.put("sell", sell);
        result.put("canSell", canSell);
        return result;
    }

    public UUID getId() {
        return id;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getAmount() {
        return amount;
    }

    public String getLore() {
        return lore;
    }

    public double getPrice() {
        return price;
    }

    public double getSell() {
        return sell;
    }

    public boolean canSell() {
        return canSell;
    }
}
