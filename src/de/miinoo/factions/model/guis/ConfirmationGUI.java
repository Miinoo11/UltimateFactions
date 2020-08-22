package de.miinoo.factions.model.guis;

import de.miinoo.factions.api.item.Items;
import de.miinoo.factions.api.ui.callback.Callback;
import de.miinoo.factions.api.ui.gui.GUI;
import de.miinoo.factions.api.ui.gui.GUIField;
import de.miinoo.factions.api.ui.gui.GUIItem;
import de.miinoo.factions.api.xutils.XMaterial;
import de.miinoo.factions.configuration.messages.GUITags;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * @author Mino
 * 10.04.2020
 */
public class ConfirmationGUI extends GUI {

    public ConfirmationGUI(Player player, ItemStack description, Callback action) {
        super(player, "Confirm", 9);

        addElement(0, new GUIField<Boolean>(9,1,
                value -> new GUIItem(Items.createItem(value ? XMaterial.GREEN_TERRACOTTA.parseMaterial() : XMaterial.RED_TERRACOTTA.parseMaterial())
                        .setDisplayName(value ? GUITags.Confirm.getMessage() : GUITags.Cancel.getMessage()).getItem()))
                .fill(0, 3, false).fill(6, 9, true).setOnClickListener(((player1, field, element, event) -> {
                    if(element == null) return false;
                    if(element) {
                        action.call();
                    }
                    player1.closeInventory();
                    return true;
                })));

        addElement(4, new GUIItem(description));
    }
}
