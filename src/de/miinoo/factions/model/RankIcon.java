package de.miinoo.factions.model;

import de.miinoo.factions.hooks.xseries.XMaterial;
import org.bukkit.Material;

/**
 * @author Mino
 * 09.04.2020
 */
public enum RankIcon {

    DIAMOND_CHESTPLATE(XMaterial.DIAMOND_CHESTPLATE.parseMaterial()),
    GOLD_CHESTPLATE(XMaterial.GOLDEN_CHESTPLATE.parseMaterial()),
    IRON_CHESTPLATE(XMaterial.IRON_CHESTPLATE.parseMaterial()),
    CHAINMAIL_CHESTPLATE(XMaterial.CHAINMAIL_CHESTPLATE.parseMaterial()),
    LEATHER_CHESTPLATE(XMaterial.LEATHER_CHESTPLATE.parseMaterial()),
    NETHER_STAR(XMaterial.NETHER_STAR.parseMaterial()),
    CAKE(XMaterial.CAKE.parseMaterial()),
    ;
    private Material material;

    RankIcon(Material material) {
        this.material = material;
    }

    public Material getMaterial() {
        return material;
    }
}
