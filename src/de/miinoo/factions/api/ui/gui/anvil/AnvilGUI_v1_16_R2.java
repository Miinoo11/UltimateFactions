package de.miinoo.factions.api.ui.gui.anvil;

import de.miinoo.factions.api.ui.UIs;
import de.miinoo.factions.api.ui.ui.AnvilUI;
import de.miinoo.factions.api.ui.ui.UIElement;
import de.miinoo.factions.api.ui.ui.UIItem;
import net.minecraft.server.v1_16_R2.*;
import org.apache.commons.lang.Validate;
import org.bukkit.craftbukkit.v1_16_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class AnvilGUI_v1_16_R2 implements AnvilUI {
    private Player player;
    private Inventory inventory;
    private Map<Integer, UIItem> items;

    public AnvilGUI_v1_16_R2(Player player) {
        this.player = player;
        items = new HashMap<>();
    }

    @Override
    public void open() {
        UIs.getUIManager().open(player, this);
        EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
        ContainerAnvil container = new AnvilGUI_v1_16_R2.AnvilContainer(entityPlayer);
        inventory = container.getBukkitView().getTopInventory();
        for (Map.Entry<Integer, UIItem> entry : items.entrySet()) {
            UIItem item = entry.getValue();
            if (!item.isEnabled(player)) {
                continue;
            }
            item.lock(inventory);
            inventory.setItem(entry.getKey(), item.getItem());
        }
        entityPlayer.playerConnection.sendPacket(new PacketPlayOutOpenWindow(container.windowId, Containers.ANVIL, new ChatMessage("Repairing")));
        entityPlayer.activeContainer = container;
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
            element.call(player, new org.bukkit.inventory.ItemStack[][] { new ItemStack[] { inventory.getItem(slot) } });
        }
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    private final class AnvilContainer extends ContainerAnvil {

        public AnvilContainer(EntityPlayer entity) {
            super(entity.nextContainerCounter(), entity.inventory,
                    ContainerAccess.at(entity.world, new BlockPosition(0, 0, 0)));
            this.checkReachable = false;
            setTitle(new ChatMessage("Repairing"));
        }

        @Override
        public void e() {
            super.e();
            this.levelCost.set(0);
        }

        @Override
        public void b(EntityHuman entityhuman) {
        }

        @Override
        protected void a(EntityHuman entityhuman, World world, IInventory iinventory) {
        }

        public int getContainerId() {
            return windowId;
        }

    }
}
