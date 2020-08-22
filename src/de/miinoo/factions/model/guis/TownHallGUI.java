package de.miinoo.factions.model.guis;

import de.miinoo.factions.api.item.Items;
import de.miinoo.factions.api.ui.gui.DependGUIItem;
import de.miinoo.factions.api.ui.gui.GUI;
import de.miinoo.factions.api.ui.gui.GUIArea;
import de.miinoo.factions.api.ui.gui.GUIItem;
import de.miinoo.factions.api.ui.ui.UIElement;
import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.api.xutils.XMaterial;
import de.miinoo.factions.configuration.messages.ErrorMessage;
import de.miinoo.factions.configuration.messages.OtherMessages;
import de.miinoo.factions.configuration.messages.SuccessMessage;
import de.miinoo.factions.model.RankPermission;
import de.miinoo.factions.util.ItemUtil;
import de.miinoo.factions.util.TimeUtil;
import de.miinoo.factions.configuration.messages.GUITags;
import de.miinoo.factions.model.Faction;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * @author Mino
 * 13.05.2020
 */
public class TownHallGUI extends GUI {

    private final BukkitTask updater;
    private final NumberFormat formatter = new DecimalFormat("#,###.00");

    public TownHallGUI(Player player, Faction faction) {
        super(player, "Townhall", 27);

        UIElement element, element1;
        int slot, slot1;

        addElement(0, new GUIArea(9, 3).fill(new GUIItem(Items.createItem(XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()).setDisplayName("§r").getItem())));

        addElement(slot = 11, element = new DependGUIItem(() -> Items.createItem(XMaterial.EMERALD.parseMaterial())
                .setDisplayName(GUITags.Townhall_Energy.getMessage())
                .setLore(GUITags.Townhall_Energy_Lore.getMessage()
                        .replace("%count%", TimeUtil.convertSeconds(faction.getEnergy()))).getItem(), () -> new EnergyGUI(player, faction).open()));

        addElement(15, new GUIItem(Items.createItem(XMaterial.GOLD_INGOT.parseMaterial())
                .setDisplayName(GUITags.Townhall_Bank.getMessage())
                .setLore(GUITags.Townhall_Bank_Lore2.getMessage()
                        .replace("%count%",faction.getBank() > 0 ? formatter.format(faction.getBank()) : "0.0")).getItem(), () -> new BankGUI(player, faction).open()));

        addElement(slot1 = getInventory().getSize() - 5, element1 = new DependGUIItem(() -> Items.createItem(XMaterial.VILLAGER_SPAWN_EGG.parseMaterial())
                .setDisplayName(GUITags.Townhall_Move.getMessage())
        .setLore(faction.getTownHall() != null ? GUITags.Townhall_Move_Lore.getMessage()
                .replace("%time%", TimeUtil.convertSeconds(faction.getTownHall().getMoveTime())) : "").getItem(), () -> {
            if(!faction.getRankOfPlayer(player.getUniqueId()).hasPermission(RankPermission.MANAGE_TOWNHALL)) {
                player.sendMessage(ErrorMessage.Player_Not_Permitted.getMessage());
                close();
                return;
            }
            if(!faction.getTownHall().canMoved()) {
                player.sendMessage(ErrorMessage.TownHall_Move_Error.getMessage());
                close();
                return;
            }
            if(!ItemUtil.hasAvailableSlot(player, 1)) {
                player.sendMessage(ErrorMessage.Inventory_Full_Error.getMessage());
                close();
                return;
            }
            close();
            Bukkit.getEntity(faction.getTownHall().getEntityUUID()).remove();
            faction.getTownHall().stopMoveTask();
            player.getInventory().addItem(Items.createItem(XMaterial.VILLAGER_SPAWN_EGG.parseMaterial())
                    .setDisplayName(OtherMessages.TownHall_DisplayName.getMessage().replace("%faction%", faction.getName())).getItem());
            player.sendMessage(SuccessMessage.Successfully_Egged_TownHall.getMessage());
        }));

        updater = new BukkitRunnable() {
            @Override
            public void run() {
                build(slot, element);
                build(slot1, element1);
            }
        }.runTaskTimer(FactionsSystem.getPlugin(), 20, 20);
    }

    @Override
    protected void onClose(Player player) {
        updater.cancel();
    }
}
