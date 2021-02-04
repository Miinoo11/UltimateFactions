package de.miinoo.factions.quest;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;

public class QuestDescription implements ConfigurationSerializable {

    String action;

    public QuestDescription(String action) {
        this.action = action;
    }

    public QuestDescription(Map<String, Object> args) {
        this.action = (String) args.get("action");
    }

    public String getAction() {
        return action;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> result = new HashMap<>();
        result.put("action", action);
        return result;
    }
}
