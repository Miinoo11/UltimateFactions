package de.miinoo.factions.adapter;

import de.miinoo.factions.Factions;
import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.adapter.adapters.*;
import org.bukkit.Bukkit;

import java.lang.reflect.InvocationTargetException;
import java.util.function.Supplier;

/**
 * @author Miinoo_
 * 12.08.2020
 **/

public enum ServerVersion {

    VERSION_1_8_R1(FactionAdapter_v1_8_R1.class),
    VERSION_1_8_R3(FactionAdapter_v1_8_R3.class),
    VERSION_1_9_R1(FactionAdapter_v1_9_R1.class),
    VERSION_1_10_R1(FactionAdapter_v1_10_R1.class),
    VERSION_1_11_R1(FactionAdapter_v1_11_R1.class),
    VERSION_1_12_R1(FactionAdapter_v1_12_R1.class),
    VERSION_1_13_R1(FactionAdapter_v1_13_R1.class),
    VERSION_1_13_R2(FactionAdapter_v1_13_R2.class),
    VERSION_1_14(FactionAdapter_v1_14_R1.class),
    VERSION_1_15_R1(FactionAdapter_v1_15_R1.class),
    VERSION_1_16(FactionAdapter_v1_16_R1.class);

    public static boolean is1_8_X() {
        String version = getServerVersion().toString();
        return version.equals(VERSION_1_8_R1.toString()) || version.equals(VERSION_1_8_R3.toString());
    }

    public static boolean is1_9_X() {
        String version = getServerVersion().toString();
        return version.equals(VERSION_1_9_R1.toString());
    }

    public static boolean hasShulker() {
        String version = getServerVersion().toString();
        return !version.equals(VERSION_1_8_R1.toString())
                && !version.equals(VERSION_1_8_R3.toString())
                && !version.equals(VERSION_1_9_R1.toString())
                && !version.equals(VERSION_1_10_R1.toString())
                && !version.equals(VERSION_1_11_R1.toString())
                && !version.equals(VERSION_1_12_R1.toString());
    }

    public static boolean hasBarrel() {
        String version = getServerVersion().toString();
        return !isLegacy();
    }

    public static boolean isLegacy() {
        String version = getServerVersion().toString();
        return version.equals(VERSION_1_8_R1.toString()) || version.equals(VERSION_1_8_R3.toString()) || version.equals(VERSION_1_9_R1.toString())
                || version.equals(VERSION_1_10_R1.toString()) || version.equals(VERSION_1_11_R1.toString()) || version.equals(VERSION_1_12_R1.toString())
                || version.equals(VERSION_1_13_R1.toString()) || version.equals(VERSION_1_13_R2.toString());
    }

    public static ServerVersion getServerVersion() {
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];

        for (ServerVersion serverVersion : values()) {
            if (version.contains(serverVersion.name().replace("VERSION_", ""))) {
                return serverVersion;
            }
        }
        throw new RuntimeException("Unknown server version!" + version);
    }

    //private final Supplier<FactionsAdapter> adapter;
//
    //ServerVersion(Supplier<FactionsAdapter> adapter) {
    //    this.adapter = adapter;
    //}
//
    //public FactionsAdapter getAdapter() {
    // return adapter.get();
    // }
    private final Class<? extends FactionsAdapter> adapter;

    ServerVersion(Class<? extends FactionsAdapter> adapter) {
        this.adapter = adapter;
    }

    public FactionsAdapter getAdapter() {
        try {
            return adapter.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

}
