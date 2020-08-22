package de.miinoo.factions.util;

import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.model.Faction;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

/**
 * @author Mino
 * 12.05.2020
 */
public class BossUtil {

    public static Map<UUID, BossBar> bossMap = new HashMap<>();

    public static BossBar createBossBar(Plugin plugin, Faction faction, LivingEntity entity, String title, BarColor barColor, BarStyle barStyle, BarFlag... flags) {
        BossBar bossBar = plugin.getServer().createBossBar(title, barColor, barStyle, flags);
        new BukkitRunnable() {
            @Override
            public void run() {
                if(entity == null || faction.getTownHall() == null) {
                    cancel();
                    return;
                }
                if(!entity.isDead() || !(faction.getTownHall().getHealth() <= 0)) {
                    bossBar.setProgress(faction.getTownHall().getHealth() / FactionsSystem.getSettings().getDefaultHealth());
                } else {
                    List<Player> players = bossBar.getPlayers();
                    for(Player player : players) {
                        bossBar.removePlayer(player);
                    }
                    bossBar.setVisible(false);
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 0, 1);
        return bossBar;
    }

    public static BossBar getBossBar(Player player) {
        if(player == null) return null;
        return bossMap.get(player.getUniqueId());
    }

    public static void addBar(Player player, BossBar bossBar) {
        bossMap.putIfAbsent(player.getUniqueId(), bossBar);
        bossBar.addPlayer(player);
        bossBar.setVisible(true);
    }

    public static void removeBar( Player player) {
        BossBar bar = getBossBar(player);
        bossMap.remove(player.getUniqueId());
        if (bar != null) { bar.setVisible(false); }
    }

    public boolean hasBar(Player player) {
        return bossMap.containsKey(player.getUniqueId());
    }

}
