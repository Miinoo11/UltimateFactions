package de.miinoo.factions.adapter;

import de.miinoo.factions.core.ui.ui.AnvilUI;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * @author Miinoo_
 * 12.08.2020
 **/

public abstract class FactionsAdapter {

    public FactionsAdapter(){}

    public abstract AnvilUI createAnvilUI(Player player);

    public abstract void sendTabListHeaderFooter(Player player, String msg, String msg2);

    public abstract void sendScoreboard(Player player);

    public abstract void sendActionBarTitle(Player player, String msg);

    public abstract void shootParticle(final String particle, final Location loc, final float xOffset, final float yOffset, final float zOffset, final float speed, final int count);
}
