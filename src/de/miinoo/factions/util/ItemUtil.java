package de.miinoo.factions.util;

import de.miinoo.factions.adapter.ServerVersion;
import de.miinoo.factions.hooks.xseries.XMaterial;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mino
 * 10.05.2020
 */
public class ItemUtil {

    public static int getAmount(Inventory inventory, Material material) {
        int amount = 0;
        for (ItemStack item : inventory.getContents()) {
            if (item != null && item.getType() == material) {
                amount += item.getAmount();
            }
        }
        return amount;
    }

    public static int removeItems(Inventory inventory, Material type, int amount) {
        ItemStack[] contents = inventory.getContents();
        int removed = 0;
        for (int i = 0; i < contents.length; i++) {
            ItemStack item = contents[i];
            if (item != null && item.getType() == type) {
                if (amount > item.getAmount()) {
                    amount -= item.getAmount();
                    contents[i] = null;
                    removed += amount;
                } else {
                    item.setAmount(item.getAmount() - amount);
                    removed += amount;
                    break;
                }
            }
        }
        if (removed > 0) {
            inventory.setContents(contents);
        }
        return removed;
    }

    public static boolean hasAvailableSlot(Player player, int howmanyclear) {
        if (ServerVersion.is1_8_X() || ServerVersion.isLegacy()) {
            Inventory inv = player.getInventory();
            int check = 0;
            for (ItemStack item : inv.getContents()) {
                if (item == null) {
                    check++;
                }
            }
            return check >= howmanyclear;
        } else {
            Inventory inv = player.getInventory();
            int check = 0;
            for (ItemStack item : inv.getStorageContents()) {
                if (item == null) {
                    check++;
                }
            }
            return check >= howmanyclear;
        }
    }

    public static boolean hasAvailableSlot(Inventory inv, int howmanyclear) {
        if (ServerVersion.is1_8_X() || ServerVersion.isLegacy()) {
            int check = 0;
            for (ItemStack item : inv.getContents()) {
                if (item == null) {
                    check++;
                }
            }
            return check >= howmanyclear;
        } else {
            int check = 0;
            for (ItemStack item : inv.getStorageContents()) {
                if (item == null) {
                    check++;
                }
            }
            return check >= howmanyclear;
        }
    }

    public static boolean takeItem(Inventory inventory, ItemStack stack, int amount) {
        if (inventory == null)
            return false;
        if (stack == null)
            return false;
        int toTake = amount;
        for (int i = 0; i < inventory.getContents().length; i++) {
            ItemStack current = inventory.getItem(i);
            if (current != null && current.isSimilar(stack)) {
                if (toTake - current.getAmount() > 0) {
                    //System.out.println("Stack ist kleiner: "+toTake+">"+current.getAmount());
                    toTake -= current.getAmount();
                    inventory.setItem(i, null);
                } else if (toTake - current.getAmount() < 0) {
                    //System.out.println("Stack ist groeßer: "+toTake+">"+current.getAmount());
                    current.setAmount(current.getAmount() - toTake);
                    break;
                } else {
                    //System.out.println("Stack ist gleich: "+toTake+"="+current.getAmount());
                    inventory.setItem(i, null);
                    return true;
                }
            }
        }
        return false;
    }

    public static List<ItemStack> getItemsAsList(Inventory inv) {
        List<ItemStack> items = new ArrayList<>();

        for(int i = 0; i < inv.getSize(); i++) {
            if(inv.getItem(i) != null && inv.getItem(i).getType() != XMaterial.AIR.parseMaterial()) {
                items.add(inv.getItem(i));
            }
        }
        return items;
    }

}
