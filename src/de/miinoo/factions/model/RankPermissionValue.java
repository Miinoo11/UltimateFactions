package de.miinoo.factions.model;

import de.miinoo.factions.api.item.Items;
import org.bukkit.inventory.ItemStack;

/**
 * @author Mino
 * 06.04.2020
 */
public class RankPermissionValue {

    private RankPermission permission;
    private boolean value;

    public RankPermissionValue(RankPermission permission) {
        this.permission = permission;
    }

    public RankPermission getPermission() {
        return permission;
    }

    public boolean getValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    public void toggleValue() {
        value = !value;
    }

    public ItemStack getItem() {
        return Items.createItem(permission.getMaterial()).setDisplayName("§7" + permission.getName()).setLore(value ? "§aenabled" : "§cdisabled").If(value, builder -> builder.addGlow()).getItem();
    }
}
