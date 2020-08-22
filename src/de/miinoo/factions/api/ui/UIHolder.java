package de.miinoo.factions.api.ui;

import de.miinoo.factions.api.ui.ui.UI;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.UUID;

public class UIHolder implements InventoryHolder {

	private Player player;
	private UI ui;
	private String id;

	public UIHolder(Player player, UI ui) {
		this.player = player;
		this.ui = ui;
		id = UUID.randomUUID().toString();
	}

	@Override
	public Inventory getInventory() {
		return player.getInventory();
	}
	
	public UI getUI() {
		return ui;
	}
	
	public String getId() {
		return id;
	}

}
