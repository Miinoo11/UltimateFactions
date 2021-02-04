package de.miinoo.factions.region.guis;

import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.core.item.Items;
import de.miinoo.factions.core.ui.gui.*;
import de.miinoo.factions.core.ui.ui.UIList;
import de.miinoo.factions.hooks.xseries.XMaterial;
import de.miinoo.factions.configuration.messages.GUITags;
import de.miinoo.factions.region.Region;
import org.bukkit.entity.Player;

public class RegionsGUI extends GUI {

    public RegionsGUI(Player player) {
        super(player, GUITags.Regions_Title.getMessage(), 27);

        addElement(0, new GUIArea(9, 3).fill(new GUIItem(Items.createItem(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem()).setDisplayName("§r").getItem())));

        UIList<Region> list = new GUIList<>(9, 1, FactionsSystem.getRegions().getRegions(),
                region -> new GUIItem(Items.createItem(XMaterial.WOODEN_AXE.parseMaterial())
                        .setDisplayName(GUITags.Regions_Region.getMessage().replace("%name%", region.getName()))
                        .getItem(), () -> new RegionInfoGUI(player, region, this).open()));

        addElement(9, list);

        if (FactionsSystem.getRegions().getRegions().size() > 9) {
            addElement(19, new GUIScrollBar(GUIScrollBar.HORIZONTAL, 7, list,
                    new GUIItem(Items.createSkull("MHF_ArrowLeft").setDisplayName(GUITags.Previous.getMessage()).getItem()),
                    new GUIItem(Items.createSkull("MHF_ArrowRight").setDisplayName(GUITags.Next.getMessage()).getItem())));
        }
    }
}
