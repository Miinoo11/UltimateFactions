package de.miinoo.factions.region.guis;

import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.core.item.Items;
import de.miinoo.factions.core.ui.gui.DependGUIItem;
import de.miinoo.factions.core.ui.gui.GUI;
import de.miinoo.factions.core.ui.gui.GUIArea;
import de.miinoo.factions.core.ui.gui.GUIItem;
import de.miinoo.factions.core.ui.input.AnvilInput;
import de.miinoo.factions.core.ui.input.GUIInput;
import de.miinoo.factions.hooks.xseries.XMaterial;
import de.miinoo.factions.configuration.messages.ErrorMessage;
import de.miinoo.factions.configuration.messages.GUITags;
import de.miinoo.factions.configuration.messages.SuccessMessage;
import de.miinoo.factions.region.Flag;
import de.miinoo.factions.region.Region;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class RegionEditGUI extends GUI {

    private String name;
    private List<Flag> flags;

    public RegionEditGUI(Player player, Region region) {
        super(player, GUITags.Regions_Info_Title.getMessage().replace("%region%", region.getName()), 27);

        addElement(0, new GUIArea(9, 3).fill(new GUIItem(Items.createItem(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem()).setDisplayName("§r").getItem())));

        name = region.getName();
        flags = region.getFlags();

        addElement(11, new DependGUIItem(() -> Items.createItem(XMaterial.NAME_TAG.parseMaterial()).setDisplayName(GUITags.Regions_Edit_Change_Name.getMessage()).getItem(),
                () -> GUIInput.get(player, region.getName(), input -> {
                    name = input;
                    close();
                }, AnvilInput.class)));

        List<String> lore = new ArrayList<>();
        for(Flag flag : flags) {
            if(flag.isEnabled()) {
                lore.add(GUITags.Regions_Edit_Flags_Lore_Format.getMessage().replace("%flag%", flag.getName()));
            }
        }

        addElement(15, new DependGUIItem(() -> Items.createItem(XMaterial.OAK_SIGN.parseMaterial())
                .setDisplayName(GUITags.Regions_Edit_Flags.getMessage())
                .setLore(lore)
                .getItem(), () -> new FlagsGUI(player, region.getFlags(), this).open()));

        addElement(22, new GUIItem(Items.createSkull(
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTMwZjQ1MzdkMjE0ZDM4NjY2ZTYzMDRlOWM4NTFjZDZmN2U0MWEwZWI3YzI1MDQ5YzlkMjJjOGM1ZjY1NDVkZiJ9fX0=")
                .setDisplayName(GUITags.Save.getMessage()).getItem(),() -> {

            if(name == null) {
                player.sendMessage(ErrorMessage.Region_Name_Empty.getMessage());
            } else {
                region.setName(name);
                FactionsSystem.getRegions().saveRegion(region);
                player.sendMessage(SuccessMessage.Successfully_Edited_Region.getMessage()
                        .replace("%region%", name));
                close();
            }
        }));
    }
}
