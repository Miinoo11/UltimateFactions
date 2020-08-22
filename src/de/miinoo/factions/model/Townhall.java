package de.miinoo.factions.model;

import de.miinoo.factions.FactionsSystem;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Mino
 * 09.05.2020
 */
public class Townhall implements ConfigurationSerializable {

    private UUID entityUUID;
    private double health;
    private Location location;
    private BukkitTask moveTask;
    private boolean canMoved = false;
    private int moveTime;

    public Townhall(UUID entityUUID, double health, Location location) {
        this.entityUUID = entityUUID;
        this.health = health;
        this.location = location;

        startMoveTask();
    }

    public Townhall(Map<String, Object> args) {
        entityUUID = UUID.fromString((String) args.get("entityUUID"));
        health = (double) args.get("health");
        location = (Location) args.get("location");
    }

    public Townhall(){};

    public UUID getEntityUUID() {
        return entityUUID;
    }

    public void setEntityUUID(UUID entityUUID) {
        this.entityUUID = entityUUID;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public void removeHealth(double health) {
        this.health -= health;
    }

    public void addHealth(double health) {
        this.health += health;
    }

    public BukkitTask getMoveTask() {
        return moveTask;
    }

    public boolean canMoved() {
        return canMoved;
    }

    public int getMoveTime() {
        return moveTime;
    }

    public void startMoveTask() {
        stopMoveTask();
        moveTask = new BukkitRunnable() {
            int i = ((FactionsSystem.getSettings().getMoveCooldown() *60) * 60);
            @Override
            public void run() {
                i--;
                if(i == 0) {
                    canMoved = true;
                    cancel();
                }
                moveTime = i;
            }
        }.runTaskTimer(FactionsSystem.getPlugin(), 20, 20);
    }

    public void stopMoveTask() {
        if(moveTask != null && !Bukkit.getScheduler().isCurrentlyRunning(moveTask.getTaskId())) {
            moveTask.cancel();
        }
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> results = new HashMap<>();
        results.put("entityUUID", entityUUID.toString());
        results.put("health", health);
        results.put("location", location);
        return results;
    }
}
