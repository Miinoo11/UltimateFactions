package de.miinoo.factions.adapter.adapters;

import de.miinoo.factions.adapter.FactionsAdapter;
import de.miinoo.factions.api.ui.gui.anvil.AnvilGUI_v1_9_R1;
import de.miinoo.factions.api.ui.ui.AnvilUI;
import de.miinoo.factions.util.ScoreboardUtil;
import net.minecraft.server.v1_9_R1.IChatBaseComponent;
import net.minecraft.server.v1_9_R1.PacketPlayOutPlayerListHeaderFooter;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

/**
 * @author Miinoo_
 * 24.08.2020
 **/

public class FactionAdapter_v1_9_R1 extends FactionsAdapter {

    @Override
    public AnvilUI createAnvilUI(Player player) {
        return new AnvilGUI_v1_9_R1(player);
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

}
