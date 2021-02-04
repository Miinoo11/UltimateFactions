package de.miinoo.factions.core.ui.gui.anvil;

import de.miinoo.factions.core.ui.UIs;
import de.miinoo.factions.core.ui.ui.AnvilUI;
import de.miinoo.factions.core.ui.ui.UIElement;
import de.miinoo.factions.core.ui.ui.UIItem;
import net.minecraft.server.v1_12_R1.*;
import org.apache.commons.lang.Validate;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Miinoo_
 * 09.09.2020
 **/

public class AnvilGUI_v1_12_R1 implements AnvilUI {
    private Player player;
    private Inventory inventory;
    private Map<Integer, UIItem> items;

    public AnvilGUI_v1_12_R1(Player player) {
        this.player = player;
        items = new HashMap<>();
    }

    @Override
    public void open() {
        UIs.getUIManager().open(player, this);
        EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
        ContainerAnvil container = new AnvilGUI_v1_12_R1.AnvilContainer(entityPlayer);
        inventory = container.getBukkitView().getTopInventory();
        for (Map.Entry<Integer, UIItem> entry : items.entrySet()) {
            UIItem item = entry.getValue();
            if (!item.isEnabled(player)) {
                continue;
            }
            item.lock(inventory);
            inventory.setItem(entry.getKey(), item.getItem());
        }
        int count = entityPlayer.nextContainerCounter();
        entityPlayer.playerConnection.sendPacket(new PacketPlayOutOpenWindow(count, "minecraft:anvil", new ChatMessage("Repairing")));
        entityPlayer.activeContainer = container;
        entityPlayer.activeContainer.windowId = count;
        entityPlayer.activeContainer.addSlotListener(entityPlayer);
    }

    public void setItem(int slot, UIItem item) {
        Validate.notNull(item);
        items.put(slot, item);
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        event.setCancelled(true);
        int slot = event.getSlot();
        if (items.containsKey(slot)) {
            items.get(slot).onClick(player, slot, 0, event);
        }
    }

    @Override
    public void onClose(InventoryCloseEvent event) {
        for (Map.Entry<Integer, UIItem> entry : items.entrySet()) {
            UIElement element = entry.getValue();
            int slot = entry.getKey();
            element.call(player, new ItemStack[][]{new ItemStack[]{inventory.getItem(slot)}});
        }
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    private final class AnvilContainer extends ContainerAnvil {

        public AnvilContainer(EntityPlayer entity) {
            super(entity.inventory, entity.world, new BlockPosition(0, 0, 0), entity);
            this.checkReachable = false;
        }

        @Override
        public void e() {
            super.e();
            this.levelCost = 0;
        }
    }
}
