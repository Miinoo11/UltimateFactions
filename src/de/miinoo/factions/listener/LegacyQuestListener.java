package de.miinoo.factions.listener;

import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.adapter.ServerVersion;
import de.miinoo.factions.quest.Quest;
import de.miinoo.factions.quest.QuestTypes;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.EntityTameEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class LegacyQuestListener implements Listener {

    @EventHandler
    public void onKill(EntityDeathEvent event) {
        for(Quest quest : FactionsSystem.getQuests().getQuests()) {
            if(quest.getType().getName().equals(QuestTypes.KILL.getName())) {
                quest.handle(event);
            }
        }
    }

    @EventHandler
    public void onCollect(EntityPickupItemEvent event) {
        for(Quest quest : FactionsSystem.getQuests().getQuests()) {
            if(quest.getType().getName().equals(QuestTypes.COLLECT.getName())) {
                quest.handle(event);
            }
        }
    }

    @EventHandler
    public void onTame(EntityTameEvent event) {
        for(Quest quest : FactionsSystem.getQuests().getQuests()) {
            if(quest.getType().getName().equals(QuestTypes.TAME.getName())) {
                quest.handle(event);
            }
        }
    }

}
