package de.miinoo.factions.adapter.adapters;

import de.miinoo.factions.adapter.FactionsAdapter;
import de.miinoo.factions.api.ui.gui.anvil.AnvilGUI_v1_16_R1;
import de.miinoo.factions.api.ui.ui.AnvilUI;
import de.miinoo.factions.util.ScoreboardUtil;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

/**
 * @author Miinoo_
 * 12.08.2020
 **/

public class FactionAdapter_v1_16_R1 extends FactionsAdapter {

    @Override
    public AnvilUI createAnvilUI(Player player) {
        return new AnvilGUI_v1_16_R1(player);
    }

    @Override
    public void sendTabListHeaderFooter(Player player, String msg, String msg2) {
        player.setPlayerListHeaderFooter(msg, msg2);
    }

    @Override
    public void sendScoreboard(Player player) {
        ScoreboardUtil.sendScoreboard(player);
    }

    @Override
    public void shootParticle(String particle, Location loc, float xOffset, float yOffset, float zOffset, float speed, int count) {
        if (particle.equals("REDSTONE")) {
            loc.getWorld().spawnParticle(Particle.valueOf("SPELL_WITCH"), loc, count, (double)xOffset, (double)yOffset, (double)zOffset, (double)speed);
        }
        else {
            loc.getWorld().spawnParticle(Particle.valueOf(particle), loc, count, (double)xOffset, (double)yOffset, (double)zOffset, (double)speed);
        }
    }

}
