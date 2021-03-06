package de.miinoo.factions.commands;

import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.adapter.ServerVersion;
import de.miinoo.factions.commands.subcommands.*;
import de.miinoo.factions.core.command.ArgumentParser;
import de.miinoo.factions.core.command.Command;
import de.miinoo.factions.core.command.HelpCommand;
import de.miinoo.factions.core.command.PlayerCommand;
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
        //addCommand(new TopCommand());
        addCommand(new ChatCommand());
        addCommand(new KickCommand());
        if(FactionsSystem.getSettings().enableHome()) {
            addCommand(new HomeCommand());
            addCommand(new SetHomeCommand());
        }
        addCommand(new RanksCommand());
        addCommand(new SetRankCommand());
        addCommand(new DisbandCommand());
        addCommand(new ClaimCommand());
        addCommand(new AutoClaimCommand());
        if(!ServerVersion.is1_8_X()) {
            addCommand(new ChunkSeeCommand());
        }

        addCommand(new UnClaimCommand());
        if(FactionsSystem.getSettings().enableWarps()) {
            addCommand(new WarpCommand());
            addCommand(new SetWarpCommand());
            addCommand(new DelWarpCommand());
        }
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
        addCommand(new RegionsCommand());
        addCommand(new WildCommand());
        addCommand(new UpgradeCommand());
        addCommand(new FlyCommand());
        addCommand(new FillCommand());
        addCommand(new EnergyCommand());

        addCommand(new ShopCommand());

        addCommand(new InvitesCommand());

        addCommand(new QuestsCommand());

        addCommand(new PlayerFactionCommand());

        //addCommand(new TestCommand());

    }

    @Override
    public boolean execute(Player player, ArgumentParser args) {
        helpCommand.execute(player, args);
        return true;
    }
}
