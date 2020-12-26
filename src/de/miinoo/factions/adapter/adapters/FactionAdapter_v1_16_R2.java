package de.miinoo.factions.adapter.adapters;

import de.miinoo.factions.adapter.FactionsAdapter;
import de.miinoo.factions.api.ui.gui.anvil.AnvilGUI_v1_16_R2;
import de.miinoo.factions.api.ui.ui.AnvilUI;
import de.miinoo.factions.util.ScoreboardUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class FactionAdapter_v1_16_R2 extends FactionsAdapter {


    @java.lang.Override
    public AnvilUI createAnvilUI(Player player) {
        return new AnvilGUI_v1_16_R2(player);
    }

    @java.lang.Override
    public void sendTabListHeaderFooter(Player player, String msg, String msg2) {
        player.setPlayerListHeaderFooter(msg, msg2);
    }

    @java.lang.Override
    public void sendScoreboard(Player player) {
        ScoreboardUtil.sendScoreboard(player);
    }

    @java.lang.Override
    public void shootParticle(String particle, Location loc, float xOffset, float yOffset, float zOffset, float speed, int count) {

    }
}
