package de.miinoo.factions.commands.subcommands;

import de.miinoo.factions.core.command.ArgumentParser;
import de.miinoo.factions.core.command.CommandDescription;
import de.miinoo.factions.core.command.PlayerCommand;
import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.configuration.messages.ErrorMessage;
import de.miinoo.factions.configuration.messages.OtherMessages;
import de.miinoo.factions.configuration.messages.SuccessMessage;
import de.miinoo.factions.model.Faction;
import de.miinoo.factions.model.RankPermission;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

/**
 * @author Mino
 * 12.04.2020
 */
public class HomeCommand extends PlayerCommand {

    public HomeCommand() {
        super("home", new CommandDescription(OtherMessages.Help_HomeCommand.getMessage()), RankPermission.HOME);
    }

    public static HashMap<Player, Integer> delay = new HashMap<>();

    @Override
    public boolean execute(Player player, ArgumentParser args) {
        Faction faction = FactionsSystem.getFactions().getFaction(player);
        if (faction == null) {
            player.sendMessage(ErrorMessage.Player_Not_In_Faction.getMessage());
            return true;
        }

        if(faction.getFactionWarp("home") == null) {
            player.sendMessage(ErrorMessage.Faction_Home_Not_Exists.getMessage());
            return true;
        }

        delay.put(player, FactionsSystem.getFactionLevels().getWarpCooldown(faction.getLevel()));
        player.sendMessage(OtherMessages.Teleport_Start.getMessage().replace("%s", Integer.toString(FactionsSystem.getFactionLevels().getWarpCooldown(faction.getLevel()))));

        new BukkitRunnable() {
            int count = FactionsSystem.getFactionLevels().getWarpCooldown(faction.getLevel());
            @Override
            public void run() {
                if(delay.containsKey(player)) {
                    count--;
                    switch (count) {
                        case 0:
                            player.teleport(faction.getFactionWarp("home").getLocation());
                            player.sendMessage(SuccessMessage.Successfully_Teleported_Home.getMessage());
                            cancel();
                    }
                } else {
                    player.sendMessage(OtherMessages.Teleport_Cancelled.getMessage());
                    cancel();
                }
            }
        }.runTaskTimer(FactionsSystem.getPlugin(), 0, 20);

        return true;
    }
}
