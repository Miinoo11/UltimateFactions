package de.miinoo.factions.quest.rewardtypes;

import de.miinoo.factions.quest.QuestReward;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;

public class MoneyReward extends QuestReward implements ConfigurationSerializable {

    private int amount;

    public MoneyReward(int amount) {
        this.amount = amount;
    }

    public MoneyReward(Map<String, Object> args) {
        this.amount = ((Number) args.get("amount")).intValue();
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> result = new HashMap<>();
        result.put("amount", amount);
        return result;
    }
}
