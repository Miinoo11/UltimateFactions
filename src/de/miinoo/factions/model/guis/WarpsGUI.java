package de.miinoo.factions.model.guis;

import de.miinoo.factions.api.item.Items;
import de.miinoo.factions.api.ui.gui.*;
import de.miinoo.factions.api.ui.ui.UIList;
import de.miinoo.factions.api.xutils.XMaterial;
import de.miinoo.factions.configuration.messages.GUITags;
import de.miinoo.factions.model.Faction;
import de.miinoo.factions.model.FactionWarp;
import org.bukkit.entity.Player;

/**
 * @author Mino
 * 14.05.2020
 */
public class WarpsGUI extends GUI {

    public WarpsGUI(Player player, Faction faction) {
        super(player, "Warps", 27);

        addElement(0, new GUIArea(9, 3).fill(0, 0, 9, 1, new GUIItem(Items.createItem(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem()).setDisplayName(" ").getItem()))
                .fill(0, 2, 9, 3, new GUIItem(Items.createItem(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem()).setDisplayName("§r").getItem())));

        UIList<FactionWarp> list = new GUIList<FactionWarp>(9, 1, faction.getWarps(), factionWarp ->
                new GUIItem(Items.createItem(XMaterial.ENDER_PEARL.parseMaterial())
                        .setDisplayName(GUITags.Warps_Warp.getMessage().replace("%warp%", factionWarp.getName()))
                        .setLore(GUITags.Warps_Lore.getMessage().replace("%value%",
                                factionWarp.hasPassword() ? GUITags.Warps_Value_Yes.getMessage() : GUITags.Warps_Value_No.getMessage())).getItem()));

        addElement(9, list);

        if (faction.getWarps().size() > 9) {
            addElement(19, new GUIScrollBar(GUIScrollBar.HORIZONTAL, 7, list,
                    new GUIItem(Items.createSkull("MHF_ArrowLeft").setDisplayName(GUITags.Previous.getMessage()).getItem()),
                    new GUIItem(Items.createSkull("MHF_ArrowRight").setDisplayName(GUITags.Next.getMessage()).getItem())));
        }

    }
}