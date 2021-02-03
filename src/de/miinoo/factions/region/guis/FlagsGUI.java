package de.miinoo.factions.region.guis;

import de.miinoo.factions.api.item.Items;
import de.miinoo.factions.api.ui.gui.GUI;
import de.miinoo.factions.api.ui.gui.GUIItem;
import de.miinoo.factions.api.ui.gui.GUIList;
import de.miinoo.factions.api.ui.gui.GUIScrollBar;
import de.miinoo.factions.api.ui.ui.UIList;
import de.miinoo.factions.configuration.messages.GUITags;
import de.miinoo.factions.region.Flag;
import org.bukkit.entity.Player;

import java.util.Collection;

public class FlagsGUI extends GUI {

    public FlagsGUI(Player player, Collection<Flag> elements, GUI gui) {
        super(player, GUITags.Regions_Flags_Title.getMessage(),
                elements.size() > 45 ? 54  : (elements.size() >= 0 && elements.size() % 9 == 0 ? elements.size() + 9 : ((elements.size() / 9) + 2) * 9));

        UIList<Flag> list = new GUIList<Flag>(9, elements.size() > 54 ? 5 : size / 9, elements,
                value -> new GUIItem(Items.createItem(value.getMaterial()).setDisplayName(value.getName())
                        .setLore(value.isEnabled() ? GUITags.Enabled.getMessage() : GUITags.Disabled.getMessage()).getItem())).setOnClickListener((player2, list1, index, element, event) -> {
            element.toggleValue();
            return true;
        });

        addElement(0, list);
        if (elements.size() > 54) {
            addElement(46, new GUIScrollBar(GUIScrollBar.HORIZONTAL, 7, list,
                    new GUIItem(Items.createSkull("MHF_ArrowLeft").setDisplayName(GUITags.Previous.getMessage()).getItem()),
                    new GUIItem(Items.createSkull("MHF_ArrowRight").setDisplayName(GUITags.Next.getMessage()).getItem())));
        }

        addElement(size-5, new GUIItem(Items.createSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTMwZjQ1MzdkMjE0ZDM4NjY2ZTYzMDRlOWM4NTFjZDZmN2U0MWEwZWI3YzI1MDQ5YzlkMjJjOGM1ZjY1NDVkZiJ9fX0=")
                .setDisplayName(GUITags.Save.getMessage()).getItem(), gui::open));

        //addElement(size-5, new GUIItem(Items.createSkull(
        //        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTMwZjQ1MzdkMjE0ZDM4NjY2ZTYzMDRlOWM4NTFjZDZmN2U0MWEwZWI3YzI1MDQ5YzlkMjJjOGM1ZjY1NDVkZiJ9fX0=")
        //        .setDisplayName(GUITags.Save.getMessage()).getItem(), gui::open));
    }
}
