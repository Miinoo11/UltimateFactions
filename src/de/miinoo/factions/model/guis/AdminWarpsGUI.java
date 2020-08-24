package de.miinoo.factions.model.guis;

import de.miinoo.factions.api.item.Items;
import de.miinoo.factions.api.ui.gui.GUI;
import de.miinoo.factions.api.ui.gui.GUIItem;
import de.miinoo.factions.api.ui.gui.GUIList;
import de.miinoo.factions.api.ui.gui.GUIScrollBar;
import de.miinoo.factions.api.ui.ui.UIList;
import de.miinoo.factions.api.xutils.XMaterial;
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

    public AdminWarpsGUI(Player player, Faction faction, Collection<FactionWarp> elements) {
        super(player, "Warps", elements.size() > 45 ? 54 : (elements.size() >= 0 && elements.size() % 9 == 0 ? elements.size() + 9 : ((elements.size() / 9) + 2) * 9));

        UIList<FactionWarp> list = new GUIList<FactionWarp>(9, elements.size() > 54 ? 5 : size / 9, elements, factionWarp ->
                new GUIItem(Items.createItem(XMaterial.ENDER_EYE.parseMaterial()).setDisplayName(GUITags.Admin_Warps_Warp.getMessage().replace("%warp%", factionWarp.getName()))
                        .setLore(GUITags.Info_Click_Lore.getMessage()).getItem(), () -> new AdminWarpManageGUI(player, faction, factionWarp).open()));

        addElement(0, list);

        if (elements.size() > 54) {
            addElement(46, new GUIScrollBar(GUIScrollBar.HORIZONTAL, 7, list,
                    new GUIItem(Items.createSkull("MHF_ArrowLeft").setDisplayName(GUITags.Back.getMessage()).getItem()),
                    new GUIItem(Items.createSkull("MHF_ArrowRight").setDisplayName(GUITags.Next.getMessage()).getItem())));
        }
    }
}
