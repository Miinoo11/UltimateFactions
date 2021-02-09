package de.miinoo.factions.quest;

import de.miinoo.factions.configuration.messages.GUITags;
import de.miinoo.factions.hooks.xseries.XMaterial;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

public enum QuestTypes {

    KILL(GUITags.Quest_Type_Kill.getMessage(), XMaterial.ZOMBIE_HEAD.parseMaterial()),
    COLLECT(GUITags.Quest_Type_Collect.getMessage(), XMaterial.CHEST.parseMaterial()),
    TAME(GUITags.Quest_Type_Tame.getMessage(), XMaterial.BONE.parseMaterial())
    ;

    private UUID id;
    private String name;
    private Material icon;

    QuestTypes(String name, Material icon) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public UUID getId() {
        return id;
    }

    public Material getIcon() {
        return icon;
    }

    public static QuestTypes getType(Material material) {
        return Arrays.stream(values()).filter(types -> types.getIcon().equals(material)).findAny().orElseGet(null);
    }

    public static Collection<QuestTypes> getTypes() {
        return Arrays.asList(values());
    }
}
