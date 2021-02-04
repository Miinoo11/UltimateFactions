package de.miinoo.factions.core.ui.gui.anvil;

import de.miinoo.factions.core.ui.UIs;
import de.miinoo.factions.core.ui.ui.UIElement;
import de.miinoo.factions.core.ui.ui.UIItem;
import de.miinoo.factions.core.ui.ui.AnvilUI;
import net.minecraft.server.v1_14_R1.*;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_14_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Miinoo_
 * 12.08.2020
 **/

public class AnvilGUI_v1_14_R1 implements AnvilUI {

    private Player player;
    private Inventory inventory;
    private Map<Integer, UIItem> items;

    private static final boolean IS_ONE_FOURTEEN = Bukkit.getBukkitVersion().contains("1.14.4");

    public AnvilGUI_v1_14_R1(Player player) {
        this.player = player;
        items = new HashMap<>();
    }

    private int getRealNextContainerId(Player player) {
        return ((CraftPlayer) player).getHandle().nextContainerCounter();
    }

    public static int getNextContainerId(Player player, Object container) {
        if(IS_ONE_FOURTEEN) {
            return ((AnvilContainer1_14_4_R1) container).getContainerId();
        } else {
            return ((AnvilContainer) container).getContainerId();
        }
    }

    @Override
    public void open() {
        if(Bukkit.getVersion().contains("1.14") && !Bukkit.getVersion().contains("1.14.4")) {
            UIs.getUIManager().open(player, this);
            EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
            ContainerAnvil container = new AnvilGUI_v1_14_R1.AnvilContainer(entityPlayer);
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
        } else if(IS_ONE_FOURTEEN) {
            UIs.getUIManager().open(player, this);
            EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
            ContainerAnvil container = new AnvilContainer1_14_4_R1(player, getRealNextContainerId(player), "Repairing");
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
            super(getRealNextContainerId(player), ((CraftPlayer)player).getHandle().inventory, ContainerAccess.at(((CraftWorld) player.getWorld()).getHandle(), new BlockPosition(0, 0, 0)));
            this.checkReachable = false;
            setTitle(new ChatMessage("Repairing"));
        }

        @Override
        public void e() {
            super.e();

            this.levelCost.a(0);
        }

        public int getContainerId() {
            return windowId;
        }

    }
}
