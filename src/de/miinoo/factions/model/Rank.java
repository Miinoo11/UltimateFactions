package de.miinoo.factions.model;

import de.miinoo.factions.configuration.messages.GUITags;
import de.miinoo.factions.core.item.Items;
import de.miinoo.factions.hooks.xseries.XMaterial;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Mino
 * 09.04.2020
 */
public class Rank implements Comparable<Rank>, ConfigurationSerializable {

    public static final Rank LEADER = new Rank(GUITags.Rank_Leader.getMessage(), "§c" + GUITags.Rank_Leader.getMessage(), XMaterial.DIAMOND_CHESTPLATE.parseMaterial(), Arrays.asList(RankPermission.values()));

    public static final Rank GENERAL = new Rank(GUITags.Rank_General.getMessage(), "§d" + GUITags.Rank_General.getMessage(), XMaterial.GOLDEN_CHESTPLATE.parseMaterial(), Arrays.asList(RankPermission.CHAT,
            RankPermission.BUILD, RankPermission.BREAK, RankPermission.OPEN_DOOR, RankPermission.OPEN_CHEST,
            RankPermission.HOME, RankPermission.SET_HOME, RankPermission.WARP, RankPermission.MANAGE_WARPS, RankPermission.KICK,
            RankPermission.INVITE, RankPermission.CLAIM, RankPermission.UNCLAIM, RankPermission.ALLY,
            RankPermission.ASSIGN_ROLES, RankPermission.CHANGE_INFO, RankPermission.MANAGE_QUESTS, RankPermission.CLAIM_QUESTS));

    public static final Rank LIEUTENANT = new Rank(GUITags.Rank_Lieutenant.getMessage(), "§e" + GUITags.Rank_Lieutenant.getMessage(), XMaterial.IRON_CHESTPLATE.parseMaterial(), Arrays.asList(RankPermission.CHAT,
            RankPermission.BUILD, RankPermission.BREAK, RankPermission.OPEN_DOOR, RankPermission.OPEN_CHEST,
            RankPermission.HOME, RankPermission.SET_HOME, RankPermission.WARP, RankPermission.KICK,
            RankPermission.INVITE, RankPermission.CLAIM, RankPermission.UNCLAIM, RankPermission.ALLY, RankPermission.CHANGE_INFO, RankPermission.CLAIM_QUESTS));

    public static final Rank MEMBER = new Rank(GUITags.Rank_Member.getMessage(), "§9" + GUITags.Rank_Member.getMessage(), XMaterial.CHAINMAIL_CHESTPLATE.parseMaterial(), Arrays.asList(RankPermission.CHAT,
            RankPermission.BUILD, RankPermission.BREAK, RankPermission.OPEN_DOOR, RankPermission.OPEN_CHEST,
            RankPermission.HOME, RankPermission.WARP, RankPermission.INVITE, RankPermission.CLAIM_QUESTS));

    public static final Rank RECRUIT = new Rank(GUITags.Rank_Recruit.getMessage(), "§f" + GUITags.Rank_Recruit.getMessage(), XMaterial.LEATHER_CHESTPLATE.parseMaterial(), Arrays.asList(RankPermission.CHAT,
            RankPermission.BUILD, RankPermission.OPEN_DOOR, RankPermission.WARP));

    private final UUID id;
    private String name;
    private String prefix;
    private Material material;
    private List<RankPermission> permissions;

    public Rank(String name, String prefix, Material material, List<RankPermission> permissions) {
        id = UUID.randomUUID();
        this.name = name;
        this.prefix = prefix;
        this.material = material;
        this.permissions = permissions;
    }

    public Rank(Map<String, Object> args) {
        id = UUID.fromString((String) args.get("id"));
        name = (String) args.get("name");
        prefix = (String) args.get("prefix");
        material = Material.valueOf((String) args.get("material"));
        permissions = ((List<String>) args.get("permissions")).stream().map(permission -> RankPermission.valueOf(permission)).collect(Collectors.toList());
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPrefix() {
        return prefix;
    }

    public Material getMaterial() {
        return material;
    }

    public List<RankPermission> getPermissions() {
        return permissions;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public void setPermissions(List<RankPermission> permissions) {
        this.permissions = permissions;
    }

    public ItemStack createItem() {
        return Items.createItem(material).setDisplayName(prefix).setLore(permissions.stream().map(s -> "§8- §7" + s.getName()).collect(Collectors.toList())).getItem();
    }

    public boolean hasPermission(RankPermission permission) {
        return permissions.contains(permission);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rank rank = (Rank) o;
        return Objects.equals(name, rank.name) &&
                Objects.equals(prefix, rank.prefix) &&
                material == rank.material &&
                Objects.equals(permissions, rank.permissions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, prefix, material, permissions);
    }

    @Override
    public int compareTo(Rank o) {
        if(o.permissions.size() == permissions.size()) {
            return o.name.compareTo(name);
        }
        return Integer.compare(o.permissions.size(), permissions.size());
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("id", id.toString());
        result.put("name", name);
        result.put("prefix", prefix);
        result.put("material", material.name());
        result.put("permissions", permissions.stream().map(permission -> permission.name()).collect(Collectors.toList()));
        return result;
    }
}
