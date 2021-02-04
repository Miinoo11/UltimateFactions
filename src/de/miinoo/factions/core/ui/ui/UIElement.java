package de.miinoo.factions.core.ui.ui;

/**
 * @author DotClass
 *
 */

import de.miinoo.factions.core.ui.Dimension;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public interface UIElement {

	Dimension getSize();
	
	boolean onClick(Player player, int x, int y, InventoryClickEvent event);
	
	default void onClose(Player player, ItemStack[][] items) {
	}
	
	default void call(Player player, ItemStack[][] items){
	}
	
	UIItem[][] getItems();
	
	boolean isEnabled(Player player);

	interface OnClickListener {

		boolean onClick(Player player, InventoryClickEvent event);

	}
	
}