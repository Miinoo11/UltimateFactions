package de.miinoo.factions.api.ui.gui.anvil;

/**
 * @author Miinoo_
 * 12.08.2020
 **/

import net.minecraft.server.v1_14_R1.*;
import org.bukkit.craftbukkit.v1_14_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AnvilContainer1_14_4_R1 extends ContainerAnvil {

    public AnvilContainer1_14_4_R1(Player player, int containerId, String guiTitle) {
        super(containerId, ((CraftPlayer) player).getHandle().inventory,
                ContainerAccess.at(((CraftWorld) player.getWorld()).getHandle(), new BlockPosition(0, 0, 0)));
        this.checkReachable = false;
        setTitle(new ChatMessage(guiTitle));
    }

    @Override
    public void e() {
        super.e();
        try {
            Method method = ContainerProperty.class.getMethod("a", Integer.class);
            method.invoke(levelCost, 0);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public int getContainerId() {
        return windowId;
    }

}