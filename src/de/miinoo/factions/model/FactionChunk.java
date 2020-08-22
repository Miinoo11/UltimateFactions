package de.miinoo.factions.model;

import org.bukkit.*;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Mino
 * 10.04.2020
 */
public class FactionChunk implements ConfigurationSerializable {

    protected World world;
    protected int x, z;

    public FactionChunk(World world, int x, int z) {
        this.world = world;
        this.x = x;
        this.z = z;
    }

    public FactionChunk(Map<String, Object> args) {
        world = Bukkit.getWorld(UUID.fromString((String) args.get("world")));
        x = ((Number) args.get("x")).intValue();
        z = ((Number) args.get("z")).intValue();
    }

    public FactionChunk(Location location) {
        this.world = location.getWorld();
        this.x = location.getChunk().getX();
        this.z = location.getChunk().getZ();
    }

    public World getWorld() {
        return world;
    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }

    public Chunk getBukkitChunk() {
        return world.getChunkAt(x, z);
    }

    public FactionChunk getRelative(final int x, final int z) {
        return new FactionChunk(this.world, this.x + x, this.z + z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FactionChunk chunk = (FactionChunk) o;
        return x == chunk.x &&
                z == chunk.z &&
                Objects.equals(world, chunk.world);
    }

    @Override
    public int hashCode() {
        return Objects.hash(world, x, z);
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("world", world.getUID().toString());
        result.put("x", x);
        result.put("z", z);
        return result;
    }

    public boolean isOutsideBorder() {
        final WorldBorder border = this.getWorld().getWorldBorder();
        final int chunkX = (this.x << 4) + 8;
        final int chunkZ = (this.z << 4) + 8;
        final double size = border.getSize() / 2.0;
        final Location center = border.getCenter();
        final double x = chunkX - center.getX();
        final double z = chunkZ - center.getZ();
        return x > size || -x > size || z > size || -z > size;
    }
}
