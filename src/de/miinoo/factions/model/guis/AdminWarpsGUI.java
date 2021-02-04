package de.miinoo.factions.model.guis;

import de.miinoo.factions.core.item.Items;
import de.miinoo.factions.core.ui.gui.*;
import de.miinoo.factions.core.ui.ui.UIList;
import de.miinoo.factions.hooks.xseries.XMaterial;
import de.miinoo.factions.configuration.messages.GUITags;
import de.miinoo.factions.model.Faction;
import de.miinoo.factions.model.FactionWarp;
import org.bukkit.entity.Player;

import java.util.Collection;

/**
 * @author Mino
 * 12.05.2020
 */
public class AdminWarpsGUI extends GUI {

    public AdminWarpsGUI(Player player, Faction faction, Collection<FactionWarp> elements, GUI gui) {
        super(player, "Warps", 45);

        addElement(0, new GUIArea(9, 5).fill(new GUIItem(Items.createItem(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem()).setDisplayName("§r").getItem())));

        UIList<FactionWarp> list = new GUIList<FactionWarp>(9, 2, elements, factionWarp ->
                new GUIItem(Items.createItem(XMaterial.ENDER_EYE.parseMaterial()).setDisplayName(GUITags.Admin_Warps_Warp.getMessage().replace("%warp%", factionWarp.getName()))
                        .setLore(GUITags.Info_Click_Lore.getMessage()).getItem(), () -> new AdminWarpManageGUI(player, faction, factionWarp, this).open()));

        addElement(9, list);

        if (elements.size() > 17) {
            addElement(size - 6, new GUIScrollBar(GUIScrollBar.HORIZONTAL, 3, list,
                    new GUIItem(Items.createSkull("MHF_ArrowLeft").setDisplayName(GUITags.Previous.getMessage()).getItem()),
                    new GUIItem(Items.createSkull("MHF_ArrowRight").setDisplayName(GUITags.Next.getMessage()).getItem())));
        }

        addElement(getInventory().getSize() - 9, new GUIItem(Items.createBackArrow().setDisplayName(GUITags.Back.getMessage()).getItem(), () -> gui.open()));
    }
}
