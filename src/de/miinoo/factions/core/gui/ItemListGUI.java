package de.miinoo.factions.core.gui;

/**
 * @author DotClass
 *
 */

import de.miinoo.factions.core.ui.gui.GUIItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;

public class ItemListGUI extends ListGUI<ItemStack> {

	public ItemListGUI(Player player, String title, Collection<ItemStack> items) {
		super(player, title, items, item -> new GUIItem(item), null);
	}

}
