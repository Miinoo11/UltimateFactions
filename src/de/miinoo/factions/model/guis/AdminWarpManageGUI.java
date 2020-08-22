package de.miinoo.factions.model.guis;

import de.miinoo.factions.api.item.Items;
import de.miinoo.factions.api.ui.gui.GUI;
import de.miinoo.factions.api.ui.gui.GUIArea;
import de.miinoo.factions.api.ui.gui.GUIItem;
import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.api.xutils.XMaterial;
import de.miinoo.factions.configuration.messages.GUITags;
import de.miinoo.factions.configuration.messages.SuccessMessage;
import de.miinoo.factions.model.Faction;
import de.miinoo.factions.model.FactionWarp;
import org.bukkit.entity.Player;

/**
 * @author Mino
 * 12.05.2020
 */
public class AdminWarpManageGUI extends GUI {

    public AdminWarpManageGUI(Player player, Faction faction, FactionWarp factionWarp) {
        super(player, "Manage Warp", 27);

        addElement(0, new GUIArea(9, 3).fill(new GUIItem(Items.createItem(XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()).setDisplayName("§r").getItem())));

        addElement(10, new GUIItem(Items.createItem(XMaterial.ENDER_PEARL.parseMaterial())
                .setDisplayName(GUITags.Admin_Warps_Warp_Teleport.getMessage())
                .setLore(GUITags.Admin_Warps_Warp_Teleport_Lore.getMessage()).getItem(), () -> {
            player.teleport(factionWarp.getLocation());
            player.sendMessage(SuccessMessage.Successfully_Teleported_Warp.getMessage().replace("%warp%", factionWarp.getName()));
        }));

        addElement(13, new GUIItem(Items.createItem(XMaterial.OAK_SIGN.parseMaterial())
                .setDisplayName(GUITags.Admin_Warps_Warp_Info.getMessage())
                .setLore(factionWarp.hasPassword() ? GUITags.Admin_Warps_Warp_Lore.getMessage().replace("%password%", factionWarp.getPassword())
                        : GUITags.Admin_Warps_Warp_Lore_No_Password.getMessage()).getItem()));

        addElement(16, new GUIItem(Items.createItem(XMaterial.TNT.parseMaterial())
                .setDisplayName(GUITags.Admin_Warps_Warp_Delete.getMessage())
                .setLore(GUITags.Admin_Warps_Warp_Delete_Lore.getMessage()).getItem(), () ->
                    new ConfirmationGUI(player, Items.createItem(XMaterial.PAPER.parseMaterial())
                            .setDisplayName(GUITags.Confirm_Description.getMessage())
                            .setLore(GUITags.Admin_Warps_Warp_Delete_Lore.getMessage()).getItem(), () -> {
                        faction.removeWarp(factionWarp);
                        player.sendMessage(SuccessMessage.Successfully_Deleted_Warp.getMessage().replace("%warp%", factionWarp.getName()));
                        FactionsSystem.getFactions().saveFaction(faction);
                    }).open()));
    }
}
