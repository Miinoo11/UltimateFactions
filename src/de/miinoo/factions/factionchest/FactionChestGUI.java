package de.miinoo.factions.factionchest;

import de.miinoo.factions.core.ui.gui.GUI;
import org.bukkit.entity.Player;

public class FactionChestGUI extends GUI {

    public FactionChestGUI(Player player, FactionChest chest) {
        super(player, chest.getFaction().getName(), 27);

    }
}
