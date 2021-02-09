package de.miinoo.factions.quest.types;

import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.configuration.messages.SuccessMessage;
import de.miinoo.factions.events.FactionCompletedQuestEvent;
import de.miinoo.factions.model.Faction;
import de.miinoo.factions.quest.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.Map;
import java.util.UUID;

public class KillQuest extends Quest implements ConfigurationSerializable {

    private QuestAction action;

    //public KillQuest(String name, String description, Material material, int amount, EntityType toKill, QuestReward reward) {
    //    super(name, description, material, new QuestAction(amount, toKill), new QuestType(QuestTypes.KILL), reward);
    //    this.toKill = toKill;
    //    this.amount = amount;
    //}
    public KillQuest(String name, String description, Material material, QuestAction action, QuestReward reward) {
        super(name, description, material, action, new QuestType(QuestTypes.KILL), reward);
        this.action = action;
    }

    public KillQuest(Map<String, Object> args) {
        super(args);
        this.action = (QuestAction) args.get("action");
    }

    @Override
    public void handle(Event mainEvent) {
        EntityDeathEvent event = (EntityDeathEvent) mainEvent;
        Player player = event.getEntity().getKiller();
        Faction faction = null;
        if(player != null) {
            faction = FactionsSystem.getFactions().getFaction(player);
        }
        if(faction == null || !this.hasAccepted(faction)) {
            return;
        }
        if (event.getEntity().getType().equals(EntityType.valueOf((String) action.getQuestObject()))) {
             addQuestProcess(faction, 1);
             if(getProcess(faction) == action.getAmount()) {
                 for(UUID uuid : faction.getPlayers()) {
                     Player online = Bukkit.getPlayer(uuid);
                     if(online != null && online.isOnline()) {
                         online.sendMessage(SuccessMessage.Successfully_Completed_Quest.getMessage().replace("%quest%", getName()));
                         online.playSound(online.getLocation(), FactionsSystem.getSounds().getQuestCompleteSound(), 1, 1);
                     }
                 }
                 addToCompleted(faction);
                 Bukkit.getPluginManager().callEvent(new FactionCompletedQuestEvent(faction, this));
             }
        }
    }


    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> result = super.serialize();
        result.put("action", action);
        return result;
    }
}
