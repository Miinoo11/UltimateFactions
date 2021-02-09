package de.miinoo.factions.quest.rewards;

import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.quest.QuestReward;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class MoneyReward extends QuestReward implements ConfigurationSerializable {

    private double amount;

    public MoneyReward(String name, String rewardText, double amount) {
        super(name, rewardText);
        this.amount = amount;
    }

    public MoneyReward(Map<String, Object> args) {
        super(args);
    }


    public double getAmount() {
        return amount;
    }

    @Override
    public void give(Player player) {
        FactionsSystem.getEconomy().depositPlayer(player, amount);
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> result = new HashMap<>();
        result.put("amount", amount);
        result.putAll(super.serialize());
        return result;
    }
}
