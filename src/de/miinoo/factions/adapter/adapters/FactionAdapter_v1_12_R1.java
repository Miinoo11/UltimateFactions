package de.miinoo.factions.adapter.adapters;

import de.miinoo.factions.adapter.FactionsAdapter;
import de.miinoo.factions.api.ui.gui.anvil.AnvilGUI_v1_12_R1;
import de.miinoo.factions.api.ui.ui.AnvilUI;
import de.miinoo.factions.util.ScoreboardUtil;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.PacketPlayOutPlayerListHeaderFooter;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

/**
 * @author Miinoo_
 * 09.09.2020
 **/

public class FactionAdapter_v1_12_R1 extends FactionsAdapter {

    @Override
    public AnvilUI createAnvilUI(Player player) {
        return new AnvilGUI_v1_12_R1(player);
    }

    @Override
    public void sendTabListHeaderFooter(Player player, String msg, String msg2) {
        IChatBaseComponent tabheader = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + msg + "\"}");
        IChatBaseComponent tabfooter = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + msg2 + "\"}");
        PacketPlayOutPlayerListHeaderFooter tablist = new PacketPlayOutPlayerListHeaderFooter();

        try {
            Field headerField = tablist.getClass().getDeclaredField("a");
            headerField.setAccessible(true);
            headerField.set(tablist, tabheader);
            headerField.setAccessible(!headerField.isAccessible());
            Field footerField = tablist.getClass().getDeclaredField("b");
            footerField.setAccessible(true);
            footerField.set(tablist, tabfooter);
            footerField.setAccessible(!footerField.isAccessible());
        } catch (Exception var11) {
            var11.printStackTrace();
        } finally {
            ((CraftPlayer)player).getHandle().playerConnection.sendPacket(tablist);
        }

    }

    @Override
    public void sendScoreboard(Player player) {
        ScoreboardUtil.sendLegacyScoreboard(player);
    }
}
