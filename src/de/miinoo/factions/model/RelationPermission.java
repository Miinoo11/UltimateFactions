package de.miinoo.factions.model;

import de.miinoo.factions.api.xutils.XMaterial;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Mino
 * 06.04.2020
 */
public enum RelationPermission{

    BUILD("Build", XMaterial.GRASS_BLOCK.parseMaterial()),
    BREAK("Break", XMaterial.DIAMOND_PICKAXE.parseMaterial()),
    OPEN_DOOR("Open Door", XMaterial.OAK_DOOR.parseMaterial()),
    OPEN_CHEST("Open Chest", XMaterial.CHEST.parseMaterial()),
    USE_BARREL("Use Barrel", XMaterial.BARREL.parseMaterial()),
    OPEN_TRAPPED_CHEST("Open Trapped Chest", XMaterial.TRAPPED_CHEST.parseMaterial()),
    OPEN_ENDER_CHEST("Open Ender Chest", XMaterial.ENDER_CHEST.parseMaterial()),
    OPEN_SHULKER("Open Shulker", XMaterial.GRAY_SHULKER_BOX.parseMaterial()),
    USE_BUTTONS("Use Buttons", XMaterial.OAK_BUTTON.parseMaterial()),
    USE_LEVER("Use Levers", XMaterial.LEVER.parseMaterial()),
    USE_PRESSURE_PLATE("Use Pressureplate", XMaterial.HEAVY_WEIGHTED_PRESSURE_PLATE.parseMaterial()),
    USE_TRAPDOOR("Use Trapdoors", XMaterial.DARK_OAK_TRAPDOOR.parseMaterial()),
    USE_REPEATER("Use Repeater", XMaterial.REPEATER.parseMaterial()),
    USE_COMPARATOR("Use Comparator", XMaterial.COMPARATOR.parseMaterial()),
    USE_DISPENSER("Use Dispenser", XMaterial.DISPENSER.parseMaterial()),
    USE_DROPPER("Use Dropper", XMaterial.DROPPER.parseMaterial()),
    USE_HOPPER("Use Hopper", XMaterial.HOPPER.parseMaterial()),
    USE_FURNACE("Use Furnace", XMaterial.FURNACE.parseMaterial()),
    USE_FENCE_GATE("Use Fence Gate", XMaterial.BIRCH_FENCE_GATE.parseMaterial()),
    USE_DAYLIGHT_SENSOR("Use Daylight Sensor", XMaterial.DAYLIGHT_DETECTOR.parseMaterial()),
    FILL_BUCKET("Fill bucket", XMaterial.WATER_BUCKET.parseMaterial()),
    EMPTY_BUCKET("Empty bucket", XMaterial.BUCKET.parseMaterial()),
    USE_ANVIL("Use Anvil", XMaterial.ANVIL.parseMaterial());

    public static RelationPermission forName(String name) {
        for(RelationPermission permission : values()){
            if(permission.name.equals(name)) {
                return permission;
            }
        }
        return null;
    }

    public static Collection<RelationPermissionValue> getValues() {
        Collection<RelationPermissionValue> values = new ArrayList<>();
        for(RelationPermission permission : values()) {
            values.add(new RelationPermissionValue(permission));
        }
        return values;
    }

    public static Collection<RelationPermissionValue> getValues(Collection<RelationPermission> permissions) {
        Collection<RelationPermissionValue> values = new ArrayList<>();
        for(RelationPermission permission : values()) {
            RelationPermissionValue value = new RelationPermissionValue(permission);
            if(permissions.contains(permission)) {
                value.setValue(true);
            }
            values.add(value);
        }
        return values;
    }

    private String name;
    private Material material;

    RelationPermission(String name, Material material) {
        this.name = name;
        this.material = material;
    }

    public String getName() {
        return name;
    }

    public Material getMaterial() {
        return material;
    }
}
