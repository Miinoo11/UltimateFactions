package de.miinoo.factions.model.guis;

import de.miinoo.factions.core.item.Items;
import de.miinoo.factions.core.ui.gui.GUI;
import de.miinoo.factions.core.ui.gui.GUIArea;
import de.miinoo.factions.core.ui.gui.GUIItem;
import de.miinoo.factions.hooks.xseries.XMaterial;
import de.miinoo.factions.configuration.messages.GUITags;
import de.miinoo.factions.configuration.messages.SuccessMessage;
import de.miinoo.factions.util.AdminUtil;
import org.bukkit.entity.Player;

/**
 * @author Mino
 * 03.05.2020
 */
public class AdminGUI extends GUI {

    public AdminGUI(Player player) {
        super(player, "Admin GUI", 27);

        addElement(0, new GUIArea(9, 3).fill(new GUIItem(Items.createItem(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem()).setDisplayName("§r").getItem())));

        addElement(11, new GUIItem(Items.createItem(XMaterial.DIAMOND.parseMaterial()).setDisplayName(GUITags.Toggle_Advanced_Permissions.getMessage())
                .setLore(GUITags.Admin_Get_Advanced_Permissions_Lore.getMessage()).getItem(), () ->{
            if(AdminUtil.advancedPermissions.contains(player)) {
                AdminUtil.advancedPermissions.remove(player);
                player.sendMessage(SuccessMessage.Successfully_Removed_Advanced_Permissions.getMessage());
            } else {
                AdminUtil.advancedPermissions.add(player);
                player.sendMessage(SuccessMessage.Successfully_Received_Advanced_Permissions.getMessage());
            }
            close();
        }));

        addElement(15, new GUIItem(Items.createItem(XMaterial.CAKE.parseMaterial()).setDisplayName(GUITags.Admin_Factions.getMessage()).setLore(GUITags.Info_Click_Lore.getMessage()).getItem(), () -> {
            new FactionsGUI(player, this).open();
        }));
    }
}
