package de.miinoo.factions.listener;

import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.adapter.ServerVersion;
import de.miinoo.factions.adapter.adapters.FactionAdapter_v1_8_R1;
import de.miinoo.factions.api.item.Items;
import de.miinoo.factions.api.xutils.XMaterial;
import de.miinoo.factions.commands.subcommands.FillCommand;
import de.miinoo.factions.configuration.messages.ErrorMessage;
import de.miinoo.factions.util.ItemUtil;
import net.minecraft.server.v1_8_R1.ChestLock;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.craftbukkit.v1_8_R1.block.CraftBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author Miinoo_
 * 07.09.2020
 **/

public class FillListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Block block = event.getClickedBlock();
        Player player = event.getPlayer();

        if (block == null) {
            return;
        }
        if (block.getType() == Material.AIR) {
            return;
        }

        if (FillCommand.fillList.contains(player.getUniqueId())) {
            if (block.getType() == XMaterial.CHEST.parseMaterial() || block.getType() == XMaterial.ENDER_CHEST.parseMaterial() ||
                    block.getType() == XMaterial.TRAPPED_CHEST.parseMaterial()) {
                List<ItemStack> tnt = new ArrayList<>();
                if (block.getType() == XMaterial.CHEST.parseMaterial()) {
                    for (ItemStack itemStack : player.getInventory().getContents()) {
                        if (itemStack == null) {
                            continue;
                        }
                        if (itemStack.getType().equals(XMaterial.TNT.parseMaterial())) {
                            tnt.add(itemStack);
                            player.getInventory().remove(itemStack);
                        }
                    }
                    Chest chest = (Chest) block.getState();
                    for (ItemStack itemStack : tnt) {
                        for (ItemStack item2 : chest.getInventory().addItem(itemStack).values()) {
                            player.getInventory().addItem(item2);
                        }
                    }
                }
            } else {
                player.sendMessage(ErrorMessage.Fill_Block_Error.getMessage());
                return;
            }
        }
    }

}
