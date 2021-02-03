package de.miinoo.factions.model;

import de.miinoo.factions.adapter.ServerVersion;
import de.miinoo.factions.api.xutils.XMaterial;
import de.miinoo.factions.configuration.messages.GUITags;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Mino
 * 06.04.2020
 */
public enum RankPermission {

    CHAT(GUITags.Permission_Chat.getMessage(), XMaterial.PAPER.parseMaterial()),
    BUILD(GUITags.Permission_Build.getMessage(), XMaterial.GRASS_BLOCK.parseMaterial()),
    BREAK(GUITags.Permission_Break.getMessage(), XMaterial.DIAMOND_PICKAXE.parseMaterial()),
    OPEN_DOOR(GUITags.Permission_Open_Door.getMessage(), XMaterial.IRON_DOOR.parseMaterial()),
    OPEN_CHEST(GUITags.Permission_Open_Chest.getMessage(), XMaterial.CHEST.parseMaterial()),
    OPEN_TRAPPED_CHEST(GUITags.Permission_Open_Trapped_Chest.getMessage(), XMaterial.TRAPPED_CHEST.parseMaterial()),
    OPEN_ENDER_CHEST(GUITags.Permission_Open_Ender_Chest.getMessage(), XMaterial.ENDER_CHEST.parseMaterial()),
    HOME(GUITags.Permission_Home.getMessage(), XMaterial.RED_BED.parseMaterial()),
    SET_HOME(GUITags.Permission_Set_Home.getMessage(), XMaterial.MAGMA_CREAM.parseMaterial()),
    WARP(GUITags.Permission_Warp.getMessage(), XMaterial.ENDER_PEARL.parseMaterial()),
    MANAGE_WARPS(GUITags.Permission_Manage_Warps.getMessage(), XMaterial.ENDER_EYE.parseMaterial()),
    PLACE_TNT(GUITags.Permission_Place_TNT.getMessage(), XMaterial.TNT.parseMaterial()),
    KICK(GUITags.Permission_Kick.getMessage(), XMaterial.DIAMOND_BOOTS.parseMaterial()),
    INVITE(GUITags.Permission_Invite.getMessage(), XMaterial.GOLDEN_APPLE.parseMaterial()),
    CLAIM(GUITags.Permission_Claim.getMessage(), XMaterial.GOLDEN_SHOVEL.parseMaterial()),
    UNCLAIM(GUITags.Permission_UnClaim.getMessage(), XMaterial.IRON_SHOVEL.parseMaterial()),
    ALLY(GUITags.Permission_Ally.getMessage(), XMaterial.POPPY.parseMaterial()),
    TRUCE(GUITags.Permission_Truce.getMessage(), XMaterial.RED_TULIP.parseMaterial()),
    ENEMY(GUITags.Permission_Enemy.getMessage(), XMaterial.DIAMOND_SWORD.parseMaterial()),
    NEUTRAL(GUITags.Permission_Neutral.getMessage(), XMaterial.NETHER_STAR.parseMaterial()),
    WITHDRAW(GUITags.Permission_Withdraw.getMessage(), XMaterial.GOLD_NUGGET.parseMaterial()),
    MANAGE_TOWNHALL(GUITags.Permission_Manage_Townhall.getMessage(), XMaterial.VILLAGER_SPAWN_EGG.parseMaterial()),
    MANAGE_ROLES(GUITags.Permission_Manage_Roles.getMessage(), XMaterial.DIAMOND_CHESTPLATE.parseMaterial()),
    ASSIGN_ROLES(GUITags.Permission_Assign_Roles.getMessage(), XMaterial.DIAMOND.parseMaterial()),
    FILL_BUCKET(GUITags.Permission_Fill_Bucket.getMessage(), XMaterial.WATER_BUCKET.parseMaterial()),
    EMPTY_BUCKET(GUITags.Permission_Empty_Bucket.getMessage(), XMaterial.BUCKET.parseMaterial()),
    USE_BUTTONS(GUITags.Permission_Use_Button.getMessage(), XMaterial.OAK_BUTTON.parseMaterial()),
    USE_LEVER(GUITags.Permission_Use_Lever.getMessage(), XMaterial.LEVER.parseMaterial()),
    USE_TRAPDOOR(GUITags.Permission_Use_Trapdoor.getMessage(), XMaterial.DARK_OAK_TRAPDOOR.parseMaterial()),
    USE_REPEATER(GUITags.Permission_Use_Repeater.getMessage(), XMaterial.REPEATER.parseMaterial()),
    USE_COMPARATOR(GUITags.Permission_Use_Comparator.getMessage(), XMaterial.COMPARATOR.parseMaterial()),
    USE_DISPENSER(GUITags.Permission_Use_Dispenser.getMessage(), XMaterial.DISPENSER.parseMaterial()),
    USE_DROPPER(GUITags.Permission_Use_Dropper.getMessage(), XMaterial.DROPPER.parseMaterial()),
    USE_HOPPER(GUITags.Permission_Use_Hopper.getMessage(), XMaterial.HOPPER.parseMaterial()),
    USE_FURNACE(GUITags.Permission_Use_Furnace.getMessage(), XMaterial.FURNACE.parseMaterial()),
    USE_FENCE_GATE(GUITags.Permission_Use_Fence_Gate.getMessage(), XMaterial.BIRCH_FENCE_GATE.parseMaterial()),
    USE_ANVIL(GUITags.Permission_Use_Anvil.getMessage(), XMaterial.ANVIL.parseMaterial()),
    DISBAND(GUITags.Permission_Disband.getMessage(), XMaterial.BARRIER.parseMaterial()),
    HIT_ALLIES(GUITags.Permission_Hit_Allies.getMessage(), XMaterial.IRON_SWORD.parseMaterial()),
    HIT_TRUCE(GUITags.Permission_Hit_Truces.getMessage(), XMaterial.GOLDEN_SWORD.parseMaterial()),
    SIEGE(GUITags.Permission_Siege.getMessage(), XMaterial.FIRE_CHARGE.parseMaterial()),
    FLY(GUITags.Permission_Fly.getMessage(), XMaterial.FEATHER.parseMaterial()),
    FILL(GUITags.Permission_Fill.getMessage(), XMaterial.TNT_MINECART.parseMaterial()),
    CHANGE_INFO(GUITags.Permission_Change_Info.getMessage(), XMaterial.OAK_SIGN.parseMaterial()),

    ;
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
