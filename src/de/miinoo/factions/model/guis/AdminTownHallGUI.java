package de.miinoo.factions.model.guis;

import de.miinoo.factions.api.item.Items;
import de.miinoo.factions.api.ui.gui.GUI;
import de.miinoo.factions.api.ui.gui.GUIArea;
import de.miinoo.factions.api.ui.gui.GUIItem;
import de.miinoo.factions.Factions;
import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.api.xutils.XMaterial;
import de.miinoo.factions.configuration.messages.ErrorMessage;
import de.miinoo.factions.configuration.messages.GUITags;
import de.miinoo.factions.configuration.messages.OtherMessages;
import de.miinoo.factions.configuration.messages.SuccessMessage;
import de.miinoo.factions.model.Faction;
import de.miinoo.factions.model.FactionChunk;
import de.miinoo.factions.util.ItemUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Mino
 * 15.05.2020
 */
public class AdminTownHallGUI extends GUI {

    private Factions factions = FactionsSystem.getFactions();

    public AdminTownHallGUI(Player player, Faction faction, GUI gui) {
        super(player, "Townhall options", 27);

        addElement(0, new GUIArea(9, 3).fill(new GUIItem(Items.createItem(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem()).setDisplayName("§r").getItem())));

        addElement(11, new GUIItem(Items.createItem(XMaterial.VILLAGER_SPAWN_EGG.parseMaterial())
                .setDisplayName(GUITags.Admin_TownHall_Get_Egg.getMessage())
                .setLore(GUITags.Admin_TownHall_Get_Egg_Lore1.getMessage(), GUITags.Admin_TownHall_Get_Egg_Lore2.getMessage())
                .getItem(), () -> {
            if (!ItemUtil.hasAvailableSlot(player, 1)) {
                player.sendMessage(ErrorMessage.Inventory_Full_Error.getMessage());
                close();
                return;
            }
            close();
            player.getInventory().addItem(Items.createItem(XMaterial.VILLAGER_SPAWN_EGG.parseMaterial())
                    .setDisplayName(OtherMessages.TownHall_DisplayName.getMessage().replace("%faction%", faction.getName())).getItem());
        }));

        addElement(15, new GUIItem(Items.createItem(XMaterial.TNT.parseMaterial())
                .setDisplayName(GUITags.Admin_TownHall_Remove.getMessage())
                .setLore(GUITags.Admin_TownHall_Remove_Lore.getMessage()).getItem(), () -> {
            Entity townhall = null;
            for (FactionChunk c : faction.getClaimed()) {
                for (Entity e : c.getBukkitChunk().getEntities()) {
                    if (e.getUniqueId().equals(faction.getTownHall().getEntityUUID())) {
                        townhall = e;
                    }
                }
            }
            if (faction.townHallExists() && townhall != null) {
                townhall.remove();
            }
            faction.getTownHall().stopMoveTask();
            faction.removeTownHall();
            factions.saveFaction(faction);
            player.sendMessage(SuccessMessage.Successfully_Removed_TownHall.getMessage());
            close();
        }));

        addElement(getInventory().getSize() - 9, new GUIItem(Items.createBackArrow().setDisplayName(GUITags.Back.getMessage()).getItem(), () -> gui.open()));
    }
}
