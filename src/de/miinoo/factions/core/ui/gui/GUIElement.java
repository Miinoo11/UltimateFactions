package de.miinoo.factions.core.ui.gui;

/**
 * @author DotClass
 *
 */

import de.miinoo.factions.core.ui.Permissions;
import de.miinoo.factions.core.ui.ui.UIElement;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class GUIElement implements UIElement {

	public boolean enabled = true;
	protected String permission;
	
	@Override
	public void onClose(Player player, ItemStack[][] items) {
	}
	
	public void setPermission(String permission) {
		this.permission = permission;
	}
	
	@Override
	public boolean isEnabled(Player player) {
		return enabled && Permissions.isPermitted(player, permission);
	}
	
}