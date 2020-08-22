package de.miinoo.factions.util;

import de.miinoo.factions.adapter.ServerVersion;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

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
        if(ServerVersion.getServerVersion().equals(ServerVersion.VERSION_1_8_R3) || ServerVersion.getServerVersion().equals(ServerVersion.VERSION_1_8_R1)) {
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


}
