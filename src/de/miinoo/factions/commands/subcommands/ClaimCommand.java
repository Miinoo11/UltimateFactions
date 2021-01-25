package de.miinoo.factions.commands.subcommands;

import de.miinoo.factions.api.command.ArgumentParser;
import de.miinoo.factions.api.command.CommandDescription;
import de.miinoo.factions.api.command.PlayerCommand;
import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.configuration.messages.ErrorMessage;
import de.miinoo.factions.configuration.messages.OtherMessages;
import de.miinoo.factions.configuration.messages.SuccessMessage;
import de.miinoo.factions.events.FactionClaimChunkEvent;
import de.miinoo.factions.model.Faction;
import de.miinoo.factions.model.FactionChunk;
import de.miinoo.factions.model.RankPermission;
import de.miinoo.factions.util.RegionUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * @author Mino
 * 10.04.2020
 */
public class ClaimCommand extends PlayerCommand {

    public ClaimCommand() {
        super("claim", new CommandDescription(OtherMessages.Help_ClaimCommand.getMessage()), RankPermission.CLAIM);
    }

    private final NumberFormat formatter = new DecimalFormat("#,###.00");

    @Override
    public boolean execute(Player player, ArgumentParser args) {

        Faction faction = FactionsSystem.getFactions().getFaction(player);
        if (faction == null) {
            player.sendMessage(ErrorMessage.Player_Not_In_Faction.getMessage());
            return true;
        }

        FactionChunk chunk = new FactionChunk(player.getWorld(), player.getLocation().getBlockX() >> 4, player.getLocation().getBlockZ() >> 4);
        if (FactionsSystem.getFactions().getFaction(chunk) != null) {
            player.sendMessage(ErrorMessage.Chunk_Already_Claimed.getMessage());
            return true;
        }

        if (FactionsSystem.getRegionUtil().isInRegion(player)) {
            player.sendMessage(ErrorMessage.Claim_Error_Region.getMessage());
            return true;
        }

        if (faction.getClaimed().size() == FactionsSystem.getFactionLevels().getMaxClaims(faction.getLevel())) {
            player.sendMessage(ErrorMessage.Max_Claim_Error.getMessage());
            return true;
        }

        /*
            claim 1 = 100;
            claim 2 = 100;
            claim 3 = 100;
            claim 4 = 100;
            claim 5 = 100;
            claim 6 = 150;
            claim 7 = 150;
         */
        double multiplier = FactionsSystem.getSettings().getClaimPriceMultiplier();
        double price = FactionsSystem.getSettings().getClaimDefaultPrice();

        // 100; 100 <= 5; i++
        // 100 = 100 * 1.5
        for (int i = 1; i <= faction.getClaimed().size() / FactionsSystem.getSettings().getClaimPriceStreak(); i++) {
            price *= multiplier;
        }

        if (FactionsSystem.getSettings().connectedClaims()) {
            if (faction.getClaimed().size() > 0) {
                boolean connected = false;
                for (FactionChunk factionChunk : faction.getClaimed()) {
                    if ((chunk.getX() == factionChunk.getX() && Math.abs(chunk.getZ() - factionChunk.getZ()) == 1) ||
                            (chunk.getZ() == factionChunk.getZ() && Math.abs(chunk.getX() - factionChunk.getX()) == 1)) {
                        connected = true;
                        break;
                    }
                }
                if (!connected) {
                    player.sendMessage(ErrorMessage.Chunk_Not_Connected.getMessage());
                    return true;
                }
            }
        }
        if (!((FactionsSystem.getEconomy().getBalance(player) - price) >= 0)) {
            player.sendMessage(ErrorMessage.Not_Enough_Money.getMessage());
            return true;
        }

        FactionsSystem.getEconomy().withdrawPlayer(player, price);
        FactionsSystem.getFactions().claimChunk(faction, chunk);

        Bukkit.getPluginManager().callEvent(new FactionClaimChunkEvent(player, faction));
        player.sendMessage(SuccessMessage.Successfully_Claimed.getMessage().replace("%money%", formatter.format(price)));

        return true;
    }
}