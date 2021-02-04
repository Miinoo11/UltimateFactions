package de.miinoo.factions.commands.subcommands;

import de.miinoo.factions.core.command.ArgumentParser;
import de.miinoo.factions.core.command.CommandDescription;
import de.miinoo.factions.core.command.PlayerCommand;
import de.miinoo.factions.Factions;
import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.configuration.messages.ErrorMessage;
import de.miinoo.factions.configuration.messages.OtherMessages;
import de.miinoo.factions.configuration.messages.SuccessMessage;
import de.miinoo.factions.model.Faction;
import de.miinoo.factions.model.RankPermission;
import de.miinoo.factions.model.guis.WarpsGUI;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author Mino
 * 12.05.2020
 */
public class WarpCommand extends PlayerCommand {

    public static Set<UUID> teleportDelay = new HashSet<>();

    public WarpCommand() {
        super("warp", new CommandDescription(OtherMessages.Help_WarpCommand.getMessage(), OtherMessages.Help_WarpCommandSyntax.getMessage()), RankPermission.WARP);
    }

    private Factions factions = FactionsSystem.getFactions();

    @Override
    public boolean execute(Player player, ArgumentParser args) {
        Faction faction = factions.getFaction(player);
        if (faction == null) {
            player.sendMessage(ErrorMessage.Player_Not_In_Faction.getMessage());
            return true;
        }

        if(args.hasExactly(0)) {
            player.sendMessage(ErrorMessage.Warp_Syntax.getMessage());
            return true;
        }

        if(args.get(0).equalsIgnoreCase("list")) {
            new WarpsGUI(player, faction).open();
            return true;
        }

        if (faction.getFactionWarp(args.get(0)) == null) {
            player.sendMessage(ErrorMessage.Warp_Not_Found.getMessage());
            return true;
        }

        if (args.hasExactly(1)) {
            if (faction.getFactionWarp(args.get(0)).hasPassword()) {
                player.sendMessage(ErrorMessage.Faction_Warp_Error.getMessage());
                return true;
            }
            teleportPlayer(player, faction, args.get(0));
            return true;
        } else if (args.hasExactly(2)) {
            String password = args.get(1);
            if (faction.getFactionWarp(args.get(0)).hasPassword() && !(faction.getFactionWarp(args.get(0)).getPassword().equals(password))) {
                player.sendMessage(ErrorMessage.Faction_Warp_Wrong_Password.getMessage());
                return true;
            }
            teleportPlayer(player, faction, args.get(0));
            return true;
        } else {
            player.sendMessage(ErrorMessage.Warp_Syntax.getMessage());
            return true;
        }
    }

    private void teleportPlayer(Player player, Faction faction, String name) {
        teleportDelay.add(player.getUniqueId());
        player.sendMessage(OtherMessages.Teleport_Start.getMessage().replace("%s", Integer.toString(FactionsSystem.getFactionLevels().getWarpCooldown(faction.getLevel()))));

        new BukkitRunnable() {
            int i = FactionsSystem.getFactionLevels().getWarpCooldown(faction.getLevel());

            @Override
            public void run() {
                if (teleportDelay.contains(player.getUniqueId())) {
                    i--;
                    if (i == 0) {
                        player.teleport(faction.getFactionWarp(name).getLocation());
                        player.sendMessage(SuccessMessage.Successfully_Teleported_Warp.getMessage().replace("%warp%", name));
                        cancel();
                    }
                } else {
                    player.sendMessage(OtherMessages.Teleport_Cancelled.getMessage());
                    cancel();
                }
            }
        }.runTaskTimer(FactionsSystem.getPlugin(), 0, 20);
    }

}
