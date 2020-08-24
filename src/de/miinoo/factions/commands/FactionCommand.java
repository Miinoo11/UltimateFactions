package de.miinoo.factions.commands;

import de.miinoo.factions.api.command.ArgumentParser;
import de.miinoo.factions.api.command.Command;
import de.miinoo.factions.api.command.HelpCommand;
import de.miinoo.factions.api.command.PlayerCommand;
import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.commands.subcommands.*;
import org.bukkit.entity.Player;

/**
 * @author Mino
 * 10.04.2020
 */
public class FactionCommand extends PlayerCommand {

    private final Command helpCommand;

    public FactionCommand() {
        super("faction");

        addCommand(helpCommand = new HelpCommand(FactionsSystem.getPlugin().getDescription().getName(), 7));
        addCommand(new ListCommand());
        addCommand(new CreateCommand());
        addCommand(new InviteCommand());
        addCommand(new JoinCommand());
        addCommand(new LeaveCommand());
        addCommand(new TopCommand());
        addCommand(new ChatCommand());
        addCommand(new KickCommand());
        addCommand(new HomeCommand());
        addCommand(new SetHomeCommand());
        addCommand(new RanksCommand());
        addCommand(new SetRankCommand());
        addCommand(new DisbandCommand());
        addCommand(new ClaimCommand());
        addCommand(new AutoClaimCommand());
        addCommand(new ChunkSeeCommand());
        addCommand(new UnClaimCommand());
        addCommand(new WarpCommand());
        addCommand(new SetWarpCommand());
        addCommand(new DelWarpCommand());
        addCommand(new EnemyCommand());
        addCommand(new SetLeaderCommand());
        addCommand(new MapCommand());
        addCommand(new SetNameCommand());
        addCommand(new SetDescriptionCommand());
        addCommand(new InfoCommand());
        addCommand(new AllyCommand());
        addCommand(new AlliesCommand());
        addCommand(new TruceCommand());
        addCommand(new NeutralCommand());
        addCommand(new AdminCommand());
        addCommand(new SiegeCMD());
        addCommand(new TownhallCommand());
        addCommand(new BankCommand());
        addCommand(new ReloadCommand());
        addCommand(new WandCommand());
        addCommand(new RegionCommand());
        addCommand(new WildCommand());

    }

    @Override
    public boolean execute(Player player, ArgumentParser args) {
        helpCommand.execute(player, args);
        return true;
    }
}
