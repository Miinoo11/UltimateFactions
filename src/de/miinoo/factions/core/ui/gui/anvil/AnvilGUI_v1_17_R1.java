package de.miinoo.factions.core.ui.gui.anvil;

import de.miinoo.factions.core.ui.UIs;
import de.miinoo.factions.core.ui.ui.AnvilUI;
import de.miinoo.factions.core.ui.ui.UIElement;
import de.miinoo.factions.core.ui.ui.UIItem;
import net.minecraft.core.BlockPosition;
import net.minecraft.network.chat.ChatMessage;
import net.minecraft.network.protocol.game.PacketPlayOutOpenWindow;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.IInventory;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.inventory.ContainerAccess;
import net.minecraft.world.inventory.ContainerAnvil;
import net.minecraft.world.inventory.Containers;
import org.apache.commons.lang.Validate;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class AnvilGUI_v1_17_R1 implements AnvilUI {
    private Player player;
    private Inventory inventory;
    private Map<Integer, UIItem> items;

    public AnvilGUI_v1_17_R1(Player player) {
        this.player = player;
        items = new HashMap<>();
    }

    @Override
    public void open() {
        UIs.getUIManager().open(player, this);
        EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
        ContainerAnvil container = new AnvilContainer(entityPlayer);
        inventory = container.getBukkitView().getTopInventory();
        for (Map.Entry<Integer, UIItem> entry : items.entrySet()) {
            UIItem item = entry.getValue();
            if (!item.isEnabled(player)) {
                continue;
            }
            item.lock(inventory);
            inventory.setItem(entry.getKey(), item.getItem());
        }
        entityPlayer.b.sendPacket(new PacketPlayOutOpenWindow(container.j, Containers.h, new ChatMessage("Repairing")));
        entityPlayer.bV = container;
        entityPlayer.initMenu(entityPlayer.bV);
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
            element.call(player, new ItemStack[][] { new ItemStack[] { inventory.getItem(slot) } });
        }
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    private final class AnvilContainer extends ContainerAnvil {

        public AnvilContainer(EntityPlayer entity) {
            super(entity.nextContainerCounter(), entity.getInventory(),
                    ContainerAccess.at(entity.getWorld(), new BlockPosition(0, 0, 0)));
            this.checkReachable = false;
            setTitle(new ChatMessage("Repairing"));
        }

        @Override
        public void i() {
            super.i();
            this.w.set(0);
        }

        @Override
        public void b(EntityHuman entityhuman) {
        }

        @Override
        protected void a(EntityHuman player, IInventory container) {}

        public int getContainerId() {
            return this.j;
        }

    }
}
