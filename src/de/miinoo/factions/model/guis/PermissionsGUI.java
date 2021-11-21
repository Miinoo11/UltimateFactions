package de.miinoo.factions.model.guis;

import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.core.item.Items;
import de.miinoo.factions.core.ui.gui.*;
import de.miinoo.factions.core.ui.ui.UIList;
import de.miinoo.factions.configuration.messages.GUITags;
import de.miinoo.factions.hooks.xseries.XMaterial;
import de.miinoo.factions.model.RankPermissionValue;
import org.bukkit.entity.Player;

import java.util.Collection;

/**
 * @author Mino
 * 06.04.2020
 */
public class PermissionsGUI extends GUI {

    public PermissionsGUI(Player player, Collection<RankPermissionValue> elements, GUI gui) {
        super(player, "Permissions",45);

        addElement(0, new GUIArea(9, 5).fill(new GUIItem(Items.createItem(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem()).setDisplayName("§r").getItem())));

        UIList<RankPermissionValue> list = new GUIList<>(9, 3, elements,
                value -> new GUIItem(value.getItem())).setOnClickListener((player2, list1, index, element, event) -> {
            element.toggleValue();
            return true;
        });

        addElement(9, list);

        if (elements.size() > 18) {
            addElement(size - 7, new GUIScrollBar(GUIScrollBar.HORIZONTAL, 5, list,
                    new GUIItem(Items.createSkull("MHF_ArrowLeft").setDisplayName(GUITags.Previous.getMessage()).getItem()),
                    new GUIItem(Items.createSkull("MHF_ArrowRight").setDisplayName(GUITags.Next.getMessage()).getItem())));
        }

        addElement(size - 5, new GUIItem(Items.createSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTMwZjQ1MzdkMjE0ZDM4NjY2ZTYzMDRlOWM4NTFjZDZmN2U0MWEwZWI3YzI1MDQ5YzlkMjJjOGM1ZjY1NDVkZiJ9fX0=")
                        .setDisplayName(GUITags.Save.getMessage()).getItem(), gui::open));

        //addElement(size-5, new GUIItem(Items.createSkull(
        //        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTMwZjQ1MzdkMjE0ZDM4NjY2ZTYzMDRlOWM4NTFjZDZmN2U0MWEwZWI3YzI1MDQ5YzlkMjJjOGM1ZjY1NDVkZiJ9fX0=")
        //        .setDisplayName(GUITags.Save.getMessage()).getItem(), gui::open));
    }

}