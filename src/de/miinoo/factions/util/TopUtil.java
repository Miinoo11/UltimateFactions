package de.miinoo.factions.util;

import de.miinoo.factions.Factions;
import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.configuration.configurations.BankConfiguration;
import de.miinoo.factions.configuration.messages.GUITags;
import de.miinoo.factions.hooks.xseries.XMaterial;
import de.miinoo.factions.model.Faction;
import de.miinoo.factions.model.FactionChunk;
import org.bukkit.Bukkit;
import org.bukkit.ChunkSnapshot;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

/**
 * @author Mino
 * 17.05.2020
 */
public class TopUtil {

    private static Factions factions = FactionsSystem.getFactions();
    private static BankConfiguration bank = FactionsSystem.getBank();
    private static Map<Material, Integer> materials = bank.getMaterials();
    private static Map<String, Integer> spawners = bank.getSpawners();
    private static BukkitTask updateTask;
    // Faction, Wert der Faction
    // Wert = platzierte Bank Items + in der Bank die Items
    private static Map<Faction, Double> topFactions = new HashMap<>();

    // Anzahl der Items (für Lores)
    private static Map<Faction, Map<Material, Integer>> items = new HashMap<>();

   // private static final Map<Faction, Location> map = new HashMap<>();

    public static void calculate() {
        topFactions.clear();
        List<Location> spawnerLocations = new ArrayList<>();
        for (Faction fac : factions.getFactions()) {
            //List<UUID> onlinePlayers = new ArrayList<>();
            //for (UUID uuid : fac.getPlayers()) {
            //    if (Bukkit.getPlayer(uuid) != null && Bukkit.getPlayer(uuid).isOnline()) {
            //        onlinePlayers.add(uuid);
            //    }
            //}
            //if (onlinePlayers.size() == 0) {
            //    continue;
            //}
            fac.getPlacedBlocks().clear();
            for (FactionChunk chunk : fac.getClaimed()) {
                ChunkSnapshot chunkSnapshot = chunk.getBukkitChunk().getChunkSnapshot();
                // Chunk bukkitChunk = chunk.getBukkitChunk();
                final double[] value = {fac.getBank()};
                runAsync(() -> {
                    for (int x = 0; x < 16; ++x) {
                        for (int y = 1; y < 256; ++y) {
                            for (int z = 0; z < 16; ++z) {
                                //Block block = chunk.getBukkitChunk().getBlock(x, y, z);
                                //Material material = block.getType();
                                Material material = chunkSnapshot.getBlockType(x, y, z);

                                //if (material == XMaterial.SPAWNER.parseMaterial()) {
                                //    //spawnerLocations.add(new Location(Bukkit.getWorld(chunkSnapshot.getWorldName()),  (chunk.getX() * 16) + x , y,  (chunk.getZ() * 16) + z ));
                                //    //map.put(fac, new Location(Bukkit.getWorld(chunkSnapshot.getWorldName()),  (chunk.getX() * 16) + x , y,  (chunk.getZ() * 16) + z ));
                                //}

                                if (materials.containsKey(material)) {
                                    value[0] += materials.get(material);
                                    if (fac.getPlacedBlocks().get(material) != null) {
                                        fac.getPlacedBlocks().replace(material, fac.getPlacedBlocks().get(material) + 1);
                                    } else {
                                        fac.getPlacedBlocks().put(material, 1);
                                    }
                                }
                            }
                        }
                    }
                    //System.out.println(spawnerLocations);
                });

                //for(Map.Entry<Faction, Location> entry : map.entrySet()) {
                //    Location loc = entry.getValue();
                //    Block block = Objects.requireNonNull(loc.getWorld()).getBlockAt(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
                //    System.out.println("D1: " + loc.getChunk().getX() * 16 + " " + loc.getBlockY() + " " + loc.getChunk().getZ() * 16);
                //    System.out.println("D2: " + loc.getBlockX() + " " + loc.getBlockY() + " " + loc.getBlockZ());
                //    if (block.getState() instanceof CreatureSpawner) {
                //        System.out.println("D2");
                //        final CreatureSpawner spawner = (CreatureSpawner) block.getState();
                //        if (spawners.containsKey(spawner.getCreatureTypeName().toUpperCase() + "_SPAWNER")) {
                //            value[0] += spawners.get(spawner.getCreatureTypeName().toUpperCase() + "_SPAWNER");
                //            if (fac.getPlacedBlocks().get(XMaterial.SPAWNER.parseMaterial()) != null) {
                //                fac.getPlacedBlocks().replace(XMaterial.SPAWNER.parseMaterial(), fac.getPlacedBlocks().get(XMaterial.SPAWNER.parseMaterial()) + 1);
                //            } else {
                //                fac.getPlacedBlocks().put(XMaterial.SPAWNER.parseMaterial(), 1);
                //            }
                //        }
                //    }
//
                //}

                items.put(fac, fac.getPlacedBlocks());
                topFactions.put(fac, value[0]);
            }
        }

    }

    private static void runAsync(final Runnable runnable) {
        BukkitRunnable r = new BukkitRunnable() {
            public void run() {
                runnable.run();
            }
        };
        r.runTaskAsynchronously(FactionsSystem.getPlugin());
    }

    public static Collection<Faction> getTopFactions() {
        List<Faction> list = new ArrayList<>();
        topFactions.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).limit(10)
                .forEach(factionDoubleEntry -> list.add(factionDoubleEntry.getKey()));
        return list;
    }

    public static List<String> itemLores(Faction faction) {
        List<String> lores = new ArrayList<>();
        Map<Material, Integer> materialCount = items.get(faction);
        for (Material material : materials.keySet()) {
            if (materialCount != null && materialCount.isEmpty()) {
                lores.add(GUITags.TopFactions_PlacedBlock_Empty.getMessage());
                break;
            }
            if (materialCount.get(material) != null && materialCount.get(material) > 0) {
                String mat = material.name();
                lores.add(GUITags.TopFactions_PlacedBlock.getMessage()
                        .replace("%block%", mat.substring(0, 1).toUpperCase() + mat.substring(1).toLowerCase())
                        .replace("%amount%", Integer.toString(materialCount.get(material))));
            }
        }
        if (materialCount.get(XMaterial.SPAWNER.parseMaterial()) != null && materialCount.get(XMaterial.SPAWNER.parseMaterial()) > 0) {
            String mat = "Spawner";
            lores.add(GUITags.TopFactions_PlacedBlock.getMessage()
                    .replace("%block%", mat.substring(0, 1).toUpperCase() + mat.substring(1).toLowerCase())
                    .replace("%amount%", Integer.toString(materialCount.get(XMaterial.SPAWNER.parseMaterial()))));
        }
        return lores;
    }

    public static void startUpdateTask() {
        updateTask = new BukkitRunnable() {
            int i = (FactionsSystem.getSettings().getTopUpdater() * 60);

            @Override
            public void run() {
                if (i == 0) {
                    calculate();
                    i = (FactionsSystem.getSettings().getTopUpdater() * 60);
                }
                i--;
            }
        }.runTaskTimer(FactionsSystem.getPlugin(), 20, 20);
    }

    public static double getFactionValue(Faction faction) {
        if (topFactions.containsKey(faction)) {
            return topFactions.get(faction);
        }
        return 0.0D;
    }

}
