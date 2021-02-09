package de.miinoo.factions.quest;

import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class QuestType implements ConfigurationSerializable {

    private UUID id;
    private String name;
    private Material icon;

    public QuestType(QuestTypes type) {
        this.id = UUID.randomUUID();
        this.name = type.getName();
        this.icon = type.getIcon();
    }

    public QuestType(Map<String, Object> args) {
        this.id = UUID.fromString((String) args.get("id"));
        this.name = (String) args.get("name");
        this.icon = Material.valueOf((String) args.get("icon"));
    }

    public String getName() {
        return name;
    }

    public Material getIcon() {
        return icon;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> result = new HashMap<>();
        result.put("id", id.toString());
        result.put("name", name);
        result.put("icon", icon.toString());
        return result;
    }
}
