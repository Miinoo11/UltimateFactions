package de.miinoo.factions.util;

import com.google.common.util.concurrent.AtomicDouble;
import de.miinoo.factions.Factions;
import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.events.TopFactionUpdateEvent;
import de.miinoo.factions.hooks.xseries.XMaterial;
import de.miinoo.factions.configuration.configurations.BankConfiguration;
import de.miinoo.factions.configuration.messages.GUITags;
import de.miinoo.factions.model.Faction;
import de.miinoo.factions.model.FactionChunk;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
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

    public static void calculate() {
        topFactions.clear();
        runAsync(() -> {

            new BukkitRunnable() {
                @Override
                public void run() {
                    for (Faction fac : factions.getFactions()) {
                        fac.getPlacedBlocks().clear();
                        double value = fac.getBank();
                        for (FactionChunk chunk : fac.getClaimed()) {
                            Chunk bukkitChunk = chunk.getBukkitChunk();
                            for (int y = 1; y < 256; ++y) {
                                for (int x = 0; x < 16; ++x) {
                                    for (int z = 0; z < 16; ++z) {
                                        Block block = bukkitChunk.getBlock(x, y, z);
                                        Material material = block.getType();
                                        if (materials.containsKey(material)) {
                                            value += materials.get(material);
                                            if (fac.getPlacedBlocks().get(material) != null) {
                                                fac.getPlacedBlocks().replace(material, fac.getPlacedBlocks().get(material) + 1);
                                            } else {
                                                fac.getPlacedBlocks().put(material, 1);
                                            }
                                        }
                                        if (block.getState() instanceof CreatureSpawner) {
                                            final CreatureSpawner spawner = (CreatureSpawner) block.getState();
                                            if (spawners.containsKey(spawner.getCreatureTypeName().toUpperCase() + "_SPAWNER")) {
                                                value += spawners.get(spawner.getCreatureTypeName().toUpperCase() + "_SPAWNER");
                                                if (fac.getPlacedBlocks().get(XMaterial.SPAWNER.parseMaterial()) != null) {
                                                    fac.getPlacedBlocks().replace(XMaterial.SPAWNER.parseMaterial(), fac.getPlacedBlocks().get(XMaterial.SPAWNER.parseMaterial()) + 1);
                                                } else {
                                                    fac.getPlacedBlocks().put(XMaterial.SPAWNER.parseMaterial(), 1);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        items.put(fac, fac.getPlacedBlocks());
                        topFactions.put(fac, value);
                    }
                }
            }.runTask(FactionsSystem.getPlugin());
        });
        Bukkit.getPluginManager().callEvent(new TopFactionUpdateEvent(getTopFactions()));
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
