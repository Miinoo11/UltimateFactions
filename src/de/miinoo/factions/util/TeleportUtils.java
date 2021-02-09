package de.miinoo.factions.util;


import de.miinoo.factions.hooks.xseries.XMaterial;
import org.bukkit.block.*;
import org.bukkit.*;

import java.util.*;
import java.util.Comparator;

/**
 * @author Miinoo_
 * 23.08.2020
 **/

public class TeleportUtils {

    private static final Set<Material> UNSAFE_MATERIALS;
    public static final int RADIUS = 3;
    public static final Vector3D[] VOLUME;

    public static boolean isBlockAboveAir(final World world, final int x, final int y, final int z) {
        return y > world.getMaxHeight() || !world.getBlockAt(x, y - 1, z).getType().isSolid();
    }

    public static boolean isBlockUnsafe(final World world, final int x, final int y, final int z) {
        final Block block = world.getBlockAt(x, y, z);
        final Block below = world.getBlockAt(x, y - 1, z);
        final Block above = world.getBlockAt(x, y + 1, z);
        return TeleportUtils.UNSAFE_MATERIALS.contains(below.getType()) || block.getType().isSolid() || above.getType().isSolid() || isBlockAboveAir(world, x, y, z);
    }

    public static Location safeizeLocation(final Location location) {
        final World world = location.getWorld();
        int x = location.getBlockX();
        int y = (int) location.getY();
        int z = location.getBlockZ();
        final int origX = x;
        final int origY = y;
        final int origZ = z;
        location.setY(location.getWorld().getHighestBlockYAt(location));
        while (isBlockAboveAir(world, x, y, z)) {
            if (--y < 0) {
                y = origY;
                break;
            }
        }
        if (isBlockUnsafe(world, x, y, z)) {
            x = ((Math.round(location.getX()) == origX) ? (x - 1) : (x + 1));
            z = ((Math.round(location.getZ()) == origZ) ? (z - 1) : (z + 1));
        }
        for (int i = 0; isBlockUnsafe(world, x, y, z); x = origX + TeleportUtils.VOLUME[i].x, y = origY + TeleportUtils.VOLUME[i].y, z = origZ + TeleportUtils.VOLUME[i].z) {
            if (++i >= TeleportUtils.VOLUME.length) {
                x = origX;
                y = origY + 3;
                z = origZ;
                break;
            }
        }
        while (isBlockUnsafe(world, x, y, z)) {
            if (++y >= world.getMaxHeight()) {
                ++x;
                break;
            }
        }
        while (isBlockUnsafe(world, x, y, z)) {
            if (--y <= 1) {
                ++x;
                y = world.getHighestBlockYAt(x, z);
                if (x - 48 > location.getBlockX()) {
                    return null;
                }
                continue;
            }
        }
        return new Location(world, x + 0.5, (double) y, z + 0.5, location.getYaw(), location.getPitch());
    }

    static {
        (UNSAFE_MATERIALS = new HashSet<Material>()).add(XMaterial.LAVA.parseMaterial());
        TeleportUtils.UNSAFE_MATERIALS.add(XMaterial.FIRE.parseMaterial());
        TeleportUtils.UNSAFE_MATERIALS.add(XMaterial.WATER.parseMaterial());
        final List<Vector3D> pos = new ArrayList<Vector3D>();
        for (int x = -3; x <= 3; ++x) {
            for (int y = -3; y <= 3; ++y) {
                for (int z = -3; z <= 3; ++z) {
                    pos.add(new Vector3D(x, y, z));
                }
            }
        }
        Collections.sort(pos, new Comparator<Vector3D>() {
            @Override
            public int compare(final Vector3D a, final Vector3D b) {
                return a.x * a.x + a.y * a.y + a.z * a.z - (b.x * b.x + b.y * b.y + b.z * b.z);
            }
        });
        VOLUME = pos.toArray(new Vector3D[0]);
    }

    public static class Vector3D {
        public int x;
        public int y;
        public int z;

        public Vector3D(final int x, final int y, final int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }
}
