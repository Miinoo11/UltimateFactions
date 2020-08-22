package de.miinoo.factions.adapter;

import org.bukkit.Bukkit;

/**
 * @author Miinoo_
 * 12.08.2020
 **/

public class AdapterUtil {

    public static Class<?> getNmsClass(String nmsClassName)
            throws ClassNotFoundException {
        return Class.forName("net.minecraft.server." + Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + "." + nmsClassName);
    }
}
