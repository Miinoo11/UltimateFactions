package de.miinoo.factions.factionchest;

import de.miinoo.factions.model.Faction;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class FactionChest implements ConfigurationSerializable {

    private UUID id;
    private final Faction faction;
    private List<ItemStack> items;

    public FactionChest(Faction faction) {
        this.id = UUID.randomUUID();
        this.faction = faction;
        this.items = new ArrayList<>();
    }

    public FactionChest(Map<String, Object> args) {
        id = UUID.fromString((String) args.get("id"));
        faction = (Faction) args.get("faction");
        items = (List<ItemStack>) args.get("items");
    }

    public UUID getId() {
        return id;
    }

    public Faction getFaction() {
        return faction;
    }

    public List<ItemStack> getItems() {
        return items;
    }

    public void addItem(ItemStack itemStack) {
        items.add(itemStack);
    }

    public void removeItem(ItemStack itemStack) {
        items.remove(itemStack);
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> results = new HashMap<>();
        results.put("id", id.toString());
        results.put("faction", faction);
        results.put("items", items);
        return results;
    }

}
