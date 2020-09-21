package de.miinoo.factions.commands.subcommands;

import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.api.command.ArgumentParser;
import de.miinoo.factions.api.command.CommandDescription;
import de.miinoo.factions.api.command.PlayerCommand;
import de.miinoo.factions.configuration.messages.ErrorMessage;
import de.miinoo.factions.configuration.messages.OtherMessages;
import de.miinoo.factions.configuration.messages.SuccessMessage;
import de.miinoo.factions.randomteleport.TeleportHandler;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

/**
 * @author Miinoo_
 * 23.08.2020
 **/

public class WildCommand extends PlayerCommand {

    public static HashMap<Player, Integer> teleportDelay = new HashMap<>();

    public WildCommand() {
        super("wild", new CommandDescription("Teleports you to a random location"));
    }

    @Override
    public boolean execute(Player player, ArgumentParser args) {
        if(!FactionsSystem.getSettings().wildIsEnabled()) {
            player.sendMessage(ErrorMessage.Wild_Teleport_Error.getMessage());
            return true;
        }

        if(FactionsSystem.getEconomy().getBalance(player) - FactionsSystem.getSettings().wildCosts() < 0) {
            player.sendMessage(ErrorMessage.Not_Enough_Money.getMessage());
            return true;
        }

        for(String name : FactionsSystem.getSettings().wildDisabledWorlds()) {
            if(player.getWorld().getName().equalsIgnoreCase(name)) {
                player.sendMessage(ErrorMessage.Wild_Teleport_World_Error.getMessage());
                return true;
            }
        }

        teleportDelay.put(player, FactionsSystem.getSettings().wildDelay());
        if(FactionsSystem.getSettings().wildCosts() > 0) {
            player.sendMessage(OtherMessages.Teleport_Pay.getMessage().replace("%costs%", Double.toString(FactionsSystem.getSettings().wildCosts())));
        }
        player.sendMessage(OtherMessages.Teleport_Start.getMessage().replace("%s", Integer.toString(FactionsSystem.getSettings().wildDelay())));

        new BukkitRunnable() {
            int count = FactionsSystem.getSettings().wildDelay();
            @Override
            public void run() {
                if(teleportDelay.containsKey(player)) {
                    count--;
                    switch (count) {
                        case 0:
                            teleportPlayer(player);
                            player.sendMessage(SuccessMessage.Successfully_Teleported_Wild.getMessage());
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

    public void teleportPlayer(final Player player) {
        if((FactionsSystem.getEconomy().getBalance(player) - FactionsSystem.getSettings().wildCosts()) < 0) {
            player.sendMessage(ErrorMessage.Not_Enough_Money.getMessage());
            return;
        }
        final int maxX = FactionsSystem.getSettings().wildMaxX();
        final int maxZ = FactionsSystem.getSettings().wildMaxZ();
        final TeleportHandler teleportHandler = new TeleportHandler(FactionsSystem.getPlugin(), player, player.getWorld(), maxX, maxZ);
        teleportHandler.teleport();
    }

}
