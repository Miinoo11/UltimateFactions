package de.miinoo.factions.api.ui.manager;

/**
 * @author DotClass
 *
 */

import de.miinoo.factions.api.ui.LockManager;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class EmptyLockManager implements LockManager {

	@Override
	public ItemStack lock(ItemStack item, Inventory inventory) {
		return item;
	}

	@Override
	public ItemStack unlock(ItemStack item) {
		return item;
	}

	@Override
	public boolean verify(ItemStack item, Inventory inventory) {
		return true;
	}

}