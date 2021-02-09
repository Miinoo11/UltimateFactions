package de.miinoo.factions.quest;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;

public class QuestAction implements ConfigurationSerializable {

    private int amount;
    private Object questObject;

    public QuestAction(int amount, Object questObject) {
        this.amount = amount;
        this.questObject = questObject;
    }

    public QuestAction(Map<String, Object> args) {
        this.amount = ((Number) args.get("amount")).intValue();
        this.questObject = args.get("questObject");
    }

    public int getAmount() {
        return amount;
    }

    public Object getQuestObject() {
        return questObject;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> result = new HashMap<>();
        result.put("amount", amount);
        result.put("questObject", questObject.toString());
        return result;
    }
}
