package de.miinoo.factions.quest;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;

public enum QuestType implements ConfigurationSerializable {

    KILL("KILL"),
    COLLECT("COLLECT"),
    PLACE("PLACE"),
    BREAK("BREAK");


    private String name;

    QuestType(String name) {
        this.name = name;
    }

    QuestType(Map<String, Object> args) {
        this.name = (String) args.get("name");
    }

    public String getName() {
        return name;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> result = new HashMap<>();
        result.put("name", name);
        return result;
    }
}
