package de.miinoo.factions.commands.subcommands;

import de.miinoo.factions.core.command.ArgumentParser;
import de.miinoo.factions.core.command.CommandDescription;
import de.miinoo.factions.core.command.PlayerCommand;
import de.miinoo.factions.core.item.Items;
import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.Regions;
import de.miinoo.factions.hooks.xseries.XMaterial;
import de.miinoo.factions.configuration.messages.ErrorMessage;
import de.miinoo.factions.configuration.messages.GUITags;
import de.miinoo.factions.configuration.messages.OtherMessages;
import de.miinoo.factions.configuration.messages.SuccessMessage;
import de.miinoo.factions.region.Region;
import de.miinoo.factions.model.guis.ConfirmationGUI;
import de.miinoo.factions.util.RegionUtil;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RegionCommand extends PlayerCommand {

    public RegionCommand() {
        super("region", new CommandDescription(OtherMessages.Help_RegionCommand.getMessage(), OtherMessages.Help_RegionCommandSyntax.getMessage()));
    }

    private Regions regions = FactionsSystem.getRegions();
    private List<String> regionFormat = new ArrayList<>();

    @Override
    public boolean execute(Player player, ArgumentParser args) {
        if (!player.hasPermission("ultimatefactions.admin")) {
            player.sendMessage(ErrorMessage.Player_Not_Permitted.getMessage());
            return true;
        }

        if(args.length() == 0) {
            player.sendMessage(ErrorMessage.Region_Syntax.getMessage());
            return true;
        }

        if (!args.get(0).equalsIgnoreCase("create") && !args.get(0).equalsIgnoreCase("delete")
                && !args.get(0).equalsIgnoreCase("list")) {
            player.sendMessage(ErrorMessage.Region_Syntax.getMessage());
            return true;
        }

        if(args.length() == 2) {
            if (args.get(0).equalsIgnoreCase("create")) {
                if (regions.exists(args.get(1))) {
                    player.sendMessage(ErrorMessage.Region_Already_Exist.getMessage());
                    return true;
                }

                if (!RegionUtil.pos1.containsKey(player) || !RegionUtil.pos2.containsKey(player)) {
                    player.sendMessage(ErrorMessage.Region_Locations_Error.getMessage());
                    return true;
                }

                Region region = new Region(UUID.randomUUID(), args.get(1), RegionUtil.pos1.get(player), RegionUtil.pos2.get(player));
                regions.saveRegion(region);
                player.sendMessage(SuccessMessage.Successfully_Created_Region.getMessage().replace("%name%", args.get(1)));
            } else if (args.get(0).equalsIgnoreCase("delete")) {
                if (!regions.exists(args.get(1))) {
                    player.sendMessage(ErrorMessage.Region_Does_Not_Exist.getMessage());
                    return true;
                }

                Region region = regions.getRegion(args.get(1));

                new ConfirmationGUI(player, Items.createItem(XMaterial.PAPER.parseMaterial())
                        .setDisplayName(GUITags.Confirm_Description.getMessage())
                        .setLore(GUITags.Regions_Delete_Description.getMessage()
                                .replace("%name%", regions.getRegion(args.get(1)).getName())).getItem(), () -> {
                    player.sendMessage(SuccessMessage.Successfully_Deleted_Region.getMessage().replace("%name%", region.getName()));
                    regions.removeRegion(region);
                }).open();

            }
        } else if(args.length() == 1) {
            if (args.get(0).equalsIgnoreCase("list")) {
                for (Region region : regions.getRegions()) {
                    regionFormat.add("§7» §c" + region.getName());
                }
                player.sendMessage(OtherMessages.Regions_Header.getMessage());
                player.sendMessage(" ");
                if (regionFormat.isEmpty()) {
                    player.sendMessage(ErrorMessage.Regions_Not_Found.getMessage());
                    return true;
                }
                for (String entry : regionFormat) {
                    player.sendMessage(entry);
                }
            } else {
                player.sendMessage(ErrorMessage.Region_Syntax.getMessage());
                return true;
            }
        } else {
            player.sendMessage(ErrorMessage.Region_Syntax.getMessage());
            return true;
        }

        return true;
    }

}
