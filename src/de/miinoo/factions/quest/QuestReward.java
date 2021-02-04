package de.miinoo.factions.quest;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class QuestReward implements ConfigurationSerializable {

    private UUID id;

    public QuestReward() {
        this.id = UUID.randomUUID();
    }

    public QuestReward(Map<String, Object> args) {
        this.id = UUID.fromString((String) args.get("id"));
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> result = new HashMap<>();
        result.put("id", id.toString());
        return result;
    }
}
