package de.miinoo.factions.model;

import de.miinoo.factions.adapter.ServerVersion;
import de.miinoo.factions.api.xutils.XMaterial;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Mino
 * 06.04.2020
 */
public enum RankPermission {

    CHAT("Chat", XMaterial.PAPER.parseMaterial()),
    BUILD("Build", XMaterial.GRASS_BLOCK.parseMaterial()),
    BREAK("Break", XMaterial.DIAMOND_PICKAXE.parseMaterial()),
    OPEN_DOOR("Open Door", XMaterial.OAK_DOOR.parseMaterial()),
    OPEN_CHEST("Open Chest", XMaterial.CHEST.parseMaterial()),
    OPEN_TRAPPED_CHEST("Open Trapped Chest", XMaterial.TRAPPED_CHEST.parseMaterial()),
    OPEN_ENDER_CHEST("Open Ender Chest", XMaterial.ENDER_CHEST.parseMaterial()),
    HOME("Home", XMaterial.RED_BED.parseMaterial()),
    SET_HOME("Set Home", XMaterial.MAGMA_CREAM.parseMaterial()),
    WARP("Warp", XMaterial.ENDER_PEARL.parseMaterial()),
    MANAGE_WARPS("Manage Warps", XMaterial.ENDER_EYE.parseMaterial()),
    PLACE_TNT("Place TNT", XMaterial.TNT.parseMaterial()),
    KICK("Kick", XMaterial.DIAMOND_BOOTS.parseMaterial()),
    INVITE("Invite", XMaterial.GOLDEN_APPLE.parseMaterial()),
    CLAIM("Claim", XMaterial.GOLDEN_SHOVEL.parseMaterial()),
    UNCLAIM("Unclaim", XMaterial.IRON_SHOVEL.parseMaterial()),
    ALLY("Add Ally", XMaterial.POPPY.parseMaterial()),
    TRUCE("Add Truce", XMaterial.RED_TULIP.parseMaterial()),
    ENEMY("Add Enemy", XMaterial.DIAMOND_SWORD.parseMaterial()),
    NEUTRAL("Neutral a Faction", XMaterial.NETHER_STAR.parseMaterial()),
    WITHDRAW("Withdraw", XMaterial.GOLD_NUGGET.parseMaterial()),
    MANAGE_TOWNHALL("Manage Townhall", XMaterial.VILLAGER_SPAWN_EGG.parseMaterial()),
    MANAGE_ROLES("Manage Roles", XMaterial.DIAMOND_CHESTPLATE.parseMaterial()),
    ASSIGN_ROLES("Assign Roles", XMaterial.DIAMOND.parseMaterial()),
    FILL_BUCKET("Fill bucket", XMaterial.WATER_BUCKET.parseMaterial()),
    EMPTY_BUCKET("Empty bucket", XMaterial.BUCKET.parseMaterial()),
    OPEN_SHULKER("Open Shulker", XMaterial.GRAY_SHULKER_BOX.parseMaterial()),
    USE_BUTTONS("Use Buttons", XMaterial.OAK_BUTTON.parseMaterial()),
    USE_LEVER("Use Levers", XMaterial.LEVER.parseMaterial()),
    USE_TRAPDOOR("Use Trapdoors", XMaterial.DARK_OAK_TRAPDOOR.parseMaterial()),
    USE_REPEATER("Use Repeater", XMaterial.REPEATER.parseMaterial()),
    USE_COMPARATOR("Use Comparator", XMaterial.COMPARATOR.parseMaterial()),
    USE_DISPENSER("Use Dispenser", XMaterial.DISPENSER.parseMaterial()),
    USE_DROPPER("Use Dropper", XMaterial.DROPPER.parseMaterial()),
    USE_BARREL("Use Barrel", XMaterial.BARREL.parseMaterial()),
    USE_HOPPER("Use Hopper", XMaterial.HOPPER.parseMaterial()),
    USE_FURNACE("Use Furnace", XMaterial.FURNACE.parseMaterial()),
    USE_FENCE_GATE("Use Fence Gate", XMaterial.BIRCH_FENCE_GATE.parseMaterial()),
    USE_ANVIL("Use Anvil", XMaterial.ANVIL.parseMaterial()),
    DISBAND("Disband", XMaterial.BARRIER.parseMaterial()),
    HIT_ALLIES("Hit Allies", XMaterial.IRON_SWORD.parseMaterial()),
    HIT_TRUCE("Hit Truces", XMaterial.GOLDEN_SWORD.parseMaterial()),
    SIEGE("Siege", XMaterial.FIRE_CHARGE.parseMaterial()),
    FLY("Fly", XMaterial.FEATHER.parseMaterial()),
    FILL("Fill", XMaterial.TNT_MINECART.parseMaterial()),
    CHANGE_INFO("Change Info", XMaterial.OAK_SIGN.parseMaterial());

    public static RankPermission forName(String name) {
        for (RankPermission permission : values()) {
            if (permission.name.equals(name)) {
                return permission;
            }
        }
        return null;
    }

    // BARREL ADDED IN 1.14
    // SHULKER ADDED IN 1.11

    public static Collection<RankPermissionValue> getValues() {
        Collection<RankPermissionValue> values = new ArrayList<>();
        if (ServerVersion.isLegacy()) {
            if (ServerVersion.hasShulker()) {
                for (RankPermission permission : values()) {
                    if (!permission.name.equals("Use Barrel")) {
                        values.add(new RankPermissionValue(permission));
                    }
                }
            }
            if (ServerVersion.hasBarrel()) {
                for (RankPermission permission : values()) {
                    values.add(new RankPermissionValue(permission));
                }
            }
            for (RankPermission permission : values()) {
                if (!permission.name.equals("Use Barrel") && !permission.name.equals("Open Shulker")) {
                    values.add(new RankPermissionValue(permission));
                }
            }
        } else {
            for (RankPermission permission : values()) {
                values.add(new RankPermissionValue(permission));
            }
        }
        return values;
    }

    public static Collection<RankPermissionValue> getValues(Collection<RankPermission> permissions) {
        Collection<RankPermissionValue> values = new ArrayList<>();
        for (RankPermission permission : values()) {
            RankPermissionValue value = new RankPermissionValue(permission);
            if (permissions.contains(permission)) {
                value.setValue(true);
            }
            values.add(value);
        }
        return values;
    }

    private String name;
    private Material material;

    RankPermission(String name, Material material) {
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
