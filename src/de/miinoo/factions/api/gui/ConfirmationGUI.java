package de.miinoo.factions.api.gui;

/**
 * @author DotClass
 *
 */

import de.miinoo.factions.api.item.Items;
import de.miinoo.factions.api.ui.callback.Callback;
import de.miinoo.factions.api.xutils.XMaterial;
import de.miinoo.factions.api.ui.gui.GUI;
import de.miinoo.factions.api.ui.gui.GUIField;
import de.miinoo.factions.api.ui.gui.GUIItem;
import de.miinoo.factions.configuration.messages.GUITags;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ConfirmationGUI extends GUI {
	
	public ConfirmationGUI(Player player, ItemStack description, Callback action) {
		super(player, "Bestätigen", 9);
		addElement(0,
				new GUIField<Boolean>(9, 1,
						value -> new GUIItem(Items.createItem(value ? XMaterial.GREEN_TERRACOTTA.parseItem() : XMaterial.RED_TERRACOTTA.parseItem())
								.setDisplayName(value ? GUITags.Confirm.getMessage() : GUITags.Cancel.getMessage()).getItem()))
										.fill(0, 3, false).fill(6, 9, true).setOnClickListener((sender, arg1, value, arg3)->{
											if(value) {
												action.call();
											}
											sender.closeInventory();
											return true;
										}));
		addElement(4, new GUIItem(description));
	}

}
