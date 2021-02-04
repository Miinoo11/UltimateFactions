package de.miinoo.factions.region.guis;

import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.Regions;
import de.miinoo.factions.core.item.Items;
import de.miinoo.factions.core.ui.gui.GUI;
import de.miinoo.factions.core.ui.gui.GUIArea;
import de.miinoo.factions.core.ui.gui.GUIItem;
import de.miinoo.factions.hooks.xseries.XMaterial;
import de.miinoo.factions.configuration.messages.GUITags;
import de.miinoo.factions.configuration.messages.SuccessMessage;
import de.miinoo.factions.model.guis.ConfirmationGUI;
import de.miinoo.factions.region.Region;
import org.bukkit.entity.Player;

public class RegionInfoGUI extends GUI {

    private Regions regions = FactionsSystem.getRegions();

    public RegionInfoGUI(Player player, Region region, GUI gui) {
        super(player, GUITags.Regions_Info_Title.getMessage().replace("%region%", region.getName()), 27);

        addElement(0, new GUIArea(9, 3).fill(new GUIItem(Items.createItem(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem()).setDisplayName("§r").getItem())));

        addElement(11, new GUIItem(Items.createItem(XMaterial.ANVIL.parseMaterial())
                .setDisplayName(GUITags.Regions_Info_Edit.getMessage())
                .getItem(), () -> new RegionEditGUI(player, region).open()));

        addElement(15, new GUIItem(Items.createItem(XMaterial.TNT.parseMaterial())
                .setDisplayName(GUITags.Regions_Info_Delete.getMessage())
                .getItem(), () -> {
            new ConfirmationGUI(player, Items.createItem(XMaterial.PAPER.parseMaterial())
                    .setDisplayName(GUITags.Confirm_Description.getMessage())
                    .setLore(GUITags.Regions_Delete_Description.getMessage()
                            .replace("%name%", region.getName())).getItem(), () -> {
                player.sendMessage(SuccessMessage.Successfully_Deleted_Region.getMessage().replace("%name%", region.getName()));
                regions.removeRegion(region);
            }).open();
        }));

        addElement(getInventory().getSize() - 9, new GUIItem(Items.createBackArrow().setDisplayName(GUITags.Back.getMessage()).getItem(), () -> gui.open()));
    }
}
