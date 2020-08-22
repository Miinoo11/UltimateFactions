package de.miinoo.factions.model.guis;

import de.miinoo.factions.api.item.Items;
import de.miinoo.factions.api.ui.gui.*;
import de.miinoo.factions.api.ui.ui.UIList;
import de.miinoo.factions.configuration.messages.GUITags;
import de.miinoo.factions.model.RankPermissionValue;
import org.bukkit.entity.Player;

import java.util.Collection;

/**
 * @author Mino
 * 06.04.2020
 */
public class PermissionsGUI extends GUI {

    public PermissionsGUI(Player player, Collection<RankPermissionValue> elements, GUI gui) {
        super(player, "Permissions",
                elements.size() > 45 ? 54  : (elements.size() >= 0 && elements.size() % 9 == 0 ? elements.size() + 9 : ((elements.size() / 9) + 2) * 9));

        UIList<RankPermissionValue> list = new GUIList<>(9, elements.size() > 54 ? 5 : size / 9, elements,
                value -> new GUIItem(value.getItem())).setOnClickListener((player2, list1, index, element, event) -> {
            element.toggleValue();
            return true;
        });
        addElement(0, list);
        if (elements.size() > 54) {
            addElement(46, new GUIScrollBar(GUIScrollBar.HORIZONTAL, 7, list,
                    new GUIItem(Items.createHead("MHF_ArrowLeft").setDisplayName(GUITags.Back.getMessage()).getItem()),
                    new GUIItem(Items.createHead("MHF_ArrowRight").setDisplayName(GUITags.Next.getMessage()).getItem())));
        }
        addElement(size-5, new GUIItem(Items.createHead(
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTMwZjQ1MzdkMjE0ZDM4NjY2ZTYzMDRlOWM4NTFjZDZmN2U0MWEwZWI3YzI1MDQ5YzlkMjJjOGM1ZjY1NDVkZiJ9fX0=")
                .setDisplayName(GUITags.Save.getMessage()).getItem(), gui::open));
    }

}