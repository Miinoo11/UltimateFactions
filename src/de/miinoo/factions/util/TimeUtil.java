package de.miinoo.factions.util;


/**
 * @author Mino
 * 08.05.2020
 */
public class TimeUtil {

    public static String convertSeconds(long seconds) {
        if (seconds >= 86400)
            return String.format("%02dd:%02dh:%02dm:%02ds", seconds / 86400, (seconds % 86400) / 3600, (seconds % 3600) / 60, seconds % 60);
        else if (seconds >= 3600)
            return String.format("%02dh:%02dm:%02ds", seconds / 3600, (seconds % 3600) / 60, seconds % 60);
        else if (seconds >= 60)
            return String.format("%02dm:%02ds", (seconds % 3600) / 60, seconds % 60);
        else
            return String.format("%2ds", seconds % 60);
    }

}