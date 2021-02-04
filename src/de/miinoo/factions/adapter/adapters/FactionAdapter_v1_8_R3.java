package de.miinoo.factions.adapter.adapters;

import de.miinoo.factions.adapter.FactionsAdapter;
import de.miinoo.factions.core.ui.gui.anvil.AnvilGUI_v1_8_R3;
import de.miinoo.factions.core.ui.ui.AnvilUI;
import de.miinoo.factions.util.ScoreboardUtil;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

/**
 * @author Miinoo_
 * 13.08.2020
 **/

public class FactionAdapter_v1_8_R3 extends FactionsAdapter {

    @Override
    public AnvilUI createAnvilUI(Player player) {
        return new AnvilGUI_v1_8_R3(player);
    }

    @Override
    public void sendTabListHeaderFooter(Player player, String msg, String msg2) {
        IChatBaseComponent tabTitle = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + msg+ "\"}");
        IChatBaseComponent tabSubTitle = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + msg2 + "\"}");

        PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter(tabTitle);

        try {
            Field field = packet.getClass().getDeclaredField("b");
            field.setAccessible(true);
            field.set(packet, tabSubTitle);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
        }
    }

    @Override
    public void sendScoreboard(Player player) {
        ScoreboardUtil.sendLegacyScoreboard(player);
    }

    @Override
    public void sendActionBarTitle(Player player, String msg) {
        net.minecraft.server.v1_8_R3.PacketPlayOutChat packet = new net.minecraft.server.v1_8_R3.PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + msg + "\"}"), (byte) 2);
        ((org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
    }

    @Override
    public void shootParticle(String particle, Location loc, float xOffset, float yOffset, float zOffset, float speed, int count) {
        final EnumParticle enumParticle = EnumParticle.valueOf(particle);
        final float x = (float)loc.getX();
        final float y = (float)loc.getY();
        final float z = (float)loc.getZ();
        final PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(enumParticle, false, x, y, z, xOffset, yOffset, zOffset, speed, count, (int[])null);
        for (final Player p : Bukkit.getOnlinePlayers()) {
            ((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
        }
    }


}
