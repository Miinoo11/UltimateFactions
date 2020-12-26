package de.miinoo.factions.shop;

import de.miinoo.factions.FactionsSystem;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class ShopCategory implements ConfigurationSerializable {

    UUID id;
    String name;
    String lore;
    ItemStack icon;
    List<ShopItem> items;

    public ShopCategory(String name, ItemStack icon, String lore) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.lore = lore;
        this.icon = icon;
        this.items = new ArrayList<>();
    }


    public ShopCategory(Map<String, Object> args) {
        this.id = UUID.fromString((String) args.get("id"));
        this.name = (String) args.get("name");
        this.lore = (String) args.get("lore");
        this.icon = (ItemStack) args.get("icon");
        this.items = (List<ShopItem>) args.get("items");
    }

    public void addItem(ShopItem item) {
        items.add(item);
    }

    public void removeItem(ShopItem item) {
        items.remove(item);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLore() {
        return lore;
    }

    public ItemStack getIcon() {
        return icon;
    }

    public List<ShopItem> getItems() {
        return items;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLore(String lore) {
        this.lore = lore;
    }

    public void setIcon(ItemStack icon) {
        this.icon = icon;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> result = new HashMap<>();
        result.put("id", id.toString());
        result.put("name", name);
        result.put("lore", lore);
        result.put("icon", icon);
        result.put("items", new ArrayList<>(items));
        return result;
    }
}
