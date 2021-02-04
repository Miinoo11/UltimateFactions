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
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

/**
 * @author Mino
 * 04.05.2020
 */
public class SiegeCMD extends PlayerCommand {

    public SiegeCMD() {
        super("siege", new CommandDescription(OtherMessages.Help_SiegeCommand.getMessage(), OtherMessages.Help_SiegeCommandSyntax.getMessage()), RankPermission.SIEGE);
    }

    private Factions factions = FactionsSystem.getFactions();

    public static HashMap<Faction, Faction> siege = new HashMap<>();

    @Override
    public boolean execute(Player player, ArgumentParser args) {
        Faction faction = factions.getFaction(player);
        if (faction == null) {
            player.sendMessage(ErrorMessage.Player_Not_In_Faction.getMessage());
            return true;
        }

        if (!args.hasExactly(1)) {
            player.sendMessage(ErrorMessage.Siege_Syntax.getMessage());
            return true;
        }

        if (siege.containsKey(faction)) {
            player.sendMessage(ErrorMessage.Already_Siege_Faction.getMessage());
            return true;
        }

        Faction target = factions.getFaction(args.get(0));
        if (target == null) {
            player.sendMessage(ErrorMessage.Faction_Not_Found.getMessage());
            return true;
        }

        if (faction.getWarPieces(target) == null) {
            player.sendMessage(ErrorMessage.WarPiece_Error.getMessage());
            return true;
        }

        if (!(faction.getWarPieces(target).getAmount() >= FactionsSystem.getSettings().getSiegePieceNeeded())) {
            player.sendMessage(ErrorMessage.Siege_Error.getMessage().replace("%needed%", "" + FactionsSystem.getSettings().getSiegePieceNeeded()));
            return true;
        }

        siege.put(faction, target);
        player.sendMessage(SuccessMessage.Successfully_Started_Siege.getMessage().replace("%time%", "" + FactionsSystem.getSettings().getSiegeCount()));


        for(UUID uuid : target.getPlayers()) {
            Player all = Bukkit.getPlayer(uuid);
            all.sendMessage(OtherMessages.Siege_Alert.getMessage().replace("%faction%", faction.getName()));
        }

        faction.setWarPieces(target, 0);

        new BukkitRunnable() {
            int count = (FactionsSystem.getSettings().getSiegeCount() * 60);

            @Override
            public void run() {
                count--;
                if (count == 0) {
                    siege.remove(faction);
                    for(UUID uuid : faction.getPlayers()) {
                        Player all = Bukkit.getPlayer(uuid);
                        if(all != null && all.isOnline()) {
                            all.sendMessage(OtherMessages.Siege_Ended.getMessage());
                        }
                    }
                    for(UUID uuid : target.getPlayers()) {
                        Player all = Bukkit.getPlayer(uuid);
                        if(all != null && all.isOnline()) {
                            all.sendMessage(OtherMessages.Siege_Ended.getMessage());
                        }
                    }
                    cancel();
                }
            }
        }.runTaskTimer(FactionsSystem.getPlugin(), 0, 20);

        return true;
    }
}
