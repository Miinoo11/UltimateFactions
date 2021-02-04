package de.miinoo.factions.adapter.adapters;

import de.miinoo.factions.adapter.FactionsAdapter;
import de.miinoo.factions.core.ui.gui.anvil.AnvilGUI_v1_16_R2;
import de.miinoo.factions.core.ui.ui.AnvilUI;
import de.miinoo.factions.util.ScoreboardUtil;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;
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

    @Override
    public void sendActionBarTitle(Player player, String msg) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new ComponentBuilder(msg).create());
    }

    @java.lang.Override
    public void shootParticle(String particle, Location loc, float xOffset, float yOffset, float zOffset, float speed, int count) {

    }
}
