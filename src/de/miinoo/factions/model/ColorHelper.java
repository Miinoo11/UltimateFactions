package de.miinoo.factions.model;

import org.bukkit.ChatColor;

/**
 * @author Mino
 * 14.04.2020
 */
public class ColorHelper {

    public static String colorize(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

}
