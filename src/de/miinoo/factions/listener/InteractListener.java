package de.miinoo.factions.listener;

import de.miinoo.factions.Factions;
import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.adapter.ServerVersion;
import de.miinoo.factions.api.xutils.XMaterial;
import de.miinoo.factions.commands.subcommands.SiegeCMD;
import de.miinoo.factions.configuration.configurations.SettingsConfiguration;
import de.miinoo.factions.configuration.messages.ErrorMessage;
import de.miinoo.factions.model.*;
import org.bukkit.Chunk;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.UUID;

/**
 * @author Mino
 * 23.04.2020
 */
public class InteractListener implements Listener {

    private Factions factions = FactionsSystem.getFactions();

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();

        SettingsConfiguration settings = FactionsSystem.getSettings();
        if (block == null) {
            return;
        }
        Chunk chunk = block.getChunk();
        Faction faction = factions.getFaction(new FactionChunk(block.getLocation()));
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (factions.isClaimedChunk(chunk)) {

                if (ServerVersion.isLegacy()) {
                    if (block.getType().equals(XMaterial.STONE_BUTTON.parseMaterial()) || block.getType().equals(XMaterial.OAK_BUTTON.parseMaterial())) {
                        if (!FactionsSystem.getSettings().canUseButton()) {
                            check(faction, player, RelationPermission.USE_BUTTONS, RankPermission.USE_BUTTONS, ErrorMessage.Interact_Button_Error.getMessage(), event);
                        }
                    } else if (block.getType().equals(XMaterial.LEVER.parseMaterial())) {
                        if (!settings.canUseLever()) {
                            check(faction, player, RelationPermission.USE_LEVER, RankPermission.USE_LEVER, ErrorMessage.Interact_Lever_Error.getMessage(), event);
                        }
                    } else if (block.getType().equals(XMaterial.IRON_DOOR.parseMaterial()) || block.getType().equals(XMaterial.OAK_DOOR.parseMaterial())
                    || block.getType().equals(XMaterial.ACACIA_DOOR.parseMaterial()) || block.getType().equals(XMaterial.SPRUCE_DOOR.parseMaterial()) ||
                    block.getType().equals(XMaterial.BIRCH_DOOR.parseMaterial()) || block.getType().equals(XMaterial.JUNGLE_DOOR.parseMaterial()) ||
                    block.getType().equals(XMaterial.DARK_OAK_DOOR.parseMaterial())) {
                        if (!settings.canUseDoor()) {
                            check(faction, player, RelationPermission.OPEN_DOOR, RankPermission.OPEN_DOOR, ErrorMessage.Interact_Door_Error.getMessage(), event);
                        }
                    } else if (block.getType().equals(XMaterial.OAK_TRAPDOOR.parseMaterial()) || block.getType().equals(XMaterial.IRON_TRAPDOOR.parseMaterial())) {
                        if (!settings.canUseTrapDoor()) {
                            check(faction, player, RelationPermission.USE_TRAPDOOR, RankPermission.USE_TRAPDOOR, ErrorMessage.Interact_Trap_Door_Error.getMessage(), event);
                        }
                    } else if (block.getType().equals(XMaterial.CHEST.parseMaterial())) {
                        if (!settings.canUseChest()) {
                            check(faction, player, RelationPermission.OPEN_CHEST, RankPermission.OPEN_CHEST, ErrorMessage.Interact_Chest_Error.getMessage(), event);
                        }
                    } else if (block.getType().equals(XMaterial.TRAPPED_CHEST.parseMaterial())) {
                        if (!settings.canUseTrappedChest()) {
                            check(faction, player, RelationPermission.OPEN_TRAPPED_CHEST, RankPermission.OPEN_TRAPPED_CHEST, ErrorMessage.Interact_Trapped_Chest_Error.getMessage(), event);
                        }
                    } else if (block.getType().equals(XMaterial.ENDER_CHEST.parseMaterial())) {
                        if (!settings.canUseEnderChest()) {
                            check(faction, player, RelationPermission.OPEN_ENDER_CHEST, RankPermission.OPEN_ENDER_CHEST, ErrorMessage.Interact_Ender_Chest_Error.getMessage(), event);
                        }
                    } else if (block.getType().equals(XMaterial.ANVIL.parseMaterial()) || block.getType().equals(XMaterial.CHIPPED_ANVIL) || block.getType().equals(XMaterial.DAMAGED_ANVIL.parseMaterial())) {
                        if (!settings.canUseAnvil()) {
                            check(faction, player, RelationPermission.USE_ANVIL, RankPermission.USE_ANVIL, ErrorMessage.Interact_Anvil_Error.getMessage(), event);
                        }
                    } else if (block.getType().equals(XMaterial.HOPPER.parseMaterial())) {
                        if (!settings.canUseHopper()) {
                            check(faction, player, RelationPermission.USE_HOPPER, RankPermission.USE_HOPPER, ErrorMessage.Interact_Hopper_Error.getMessage(), event);
                        }
                    } else if (block.getType().equals(XMaterial.REPEATER.parseMaterial())) {
                        if (!settings.canUseRepeater()) {
                            check(faction, player, RelationPermission.USE_REPEATER, RankPermission.USE_REPEATER, ErrorMessage.Interact_Repeater_Error.getMessage(), event);
                        }
                    } else if (block.getType().equals(XMaterial.COMPARATOR.parseMaterial())) {
                        if (!settings.canUseComparator()) {
                            check(faction, player, RelationPermission.USE_COMPARATOR, RankPermission.USE_COMPARATOR, ErrorMessage.Interact_Comparator_Error.getMessage(), event);
                        }
                    } else if (block.getType().equals(XMaterial.FURNACE.parseMaterial())) {
                        if (!settings.canUseOven()) {
                            check(faction, player, RelationPermission.USE_FURNACE, RankPermission.USE_FURNACE, ErrorMessage.Interact_Furnace_Error.getMessage(), event);
                        }
                    } else if (block.getType().equals(XMaterial.DROPPER.parseMaterial())) {
                        if (!settings.canUseDropper()) {
                            check(faction, player, RelationPermission.USE_DROPPER, RankPermission.USE_DROPPER, ErrorMessage.Interact_Dropper_Error.getMessage(), event);
                        }
                    } else if (block.getType().equals(XMaterial.OAK_FENCE_GATE.parseMaterial()) || block.getType().equals(XMaterial.SPRUCE_FENCE_GATE.parseMaterial())
                           || block.getType().equals(XMaterial.BIRCH_FENCE_GATE.parseMaterial()) || block.getType().equals(XMaterial.JUNGLE_FENCE_GATE.parseMaterial())
                    || block.getType().equals(XMaterial.DARK_OAK_FENCE_GATE.parseMaterial()) || block.getType().equals(XMaterial.ACACIA_FENCE_GATE.parseMaterial())) {
                        if (!settings.canUseFenceGate()) {
                            check(faction, player, RelationPermission.USE_FENCE_GATE, RankPermission.USE_FENCE_GATE, ErrorMessage.Interact_Gate_Error.getMessage(), event);
                        }
                    }
                } else {

                    if (block.getType().getKey().getKey().contains("button")) {
                        if (!FactionsSystem.getSettings().canUseButton()) {
                            check(faction, player, RelationPermission.USE_BUTTONS, RankPermission.USE_BUTTONS, ErrorMessage.Interact_Button_Error.getMessage(), event);
                        }
                    } else if (block.getType().getKey().getKey().contains("lever")) {
                        if (!settings.canUseLever()) {
                            check(faction, player, RelationPermission.USE_LEVER, RankPermission.USE_LEVER, ErrorMessage.Interact_Lever_Error.getMessage(), event);
                        }
                    } else if (block.getType().getKey().getKey().contains("door") && !block.getType().getKey().getKey().contains("trap_door")) {
                        if (!settings.canUseDoor()) {
                            check(faction, player, RelationPermission.OPEN_DOOR, RankPermission.OPEN_DOOR, ErrorMessage.Interact_Door_Error.getMessage(), event);
                        }
                    } else if (block.getType().getKey().getKey().contains("trap_door")) {
                        if (!settings.canUseTrapDoor()) {
                            check(faction, player, RelationPermission.USE_TRAPDOOR, RankPermission.USE_TRAPDOOR, ErrorMessage.Interact_Trap_Door_Error.getMessage(), event);
                        }
                    } else if (block.getType().getKey().getKey().contains("shulker") && !block.getType().getKey().getKey().contains("shulker_shell")) {
                        if (!settings.canUseShulker()) {
                            check(faction, player, RelationPermission.OPEN_SHULKER, RankPermission.OPEN_CHEST, ErrorMessage.Interact_Shulker_Error.getMessage(), event);
                        }
                    } else if (block.getType().getKey().getKey().contains("chest") && !block.getType().getKey().getKey().contains("trapped_chest")
                            && !block.getType().getKey().getKey().contains("ender_chest")) {
                        if (!settings.canUseChest()) {
                            check(faction, player, RelationPermission.OPEN_CHEST, RankPermission.OPEN_CHEST, ErrorMessage.Interact_Chest_Error.getMessage(), event);
                        }
                    } else if (block.getType().getKey().getKey().contains("trapped_chest")) {
                        if (!settings.canUseTrappedChest()) {
                            check(faction, player, RelationPermission.OPEN_TRAPPED_CHEST, RankPermission.OPEN_TRAPPED_CHEST, ErrorMessage.Interact_Trapped_Chest_Error.getMessage(), event);
                        }
                    } else if (block.getType().getKey().getKey().contains("ender_chest")) {
                        if (!settings.canUseEnderChest()) {
                            check(faction, player, RelationPermission.OPEN_ENDER_CHEST, RankPermission.OPEN_ENDER_CHEST, ErrorMessage.Interact_Ender_Chest_Error.getMessage(), event);
                        }
                    } else if (block.getType().getKey().getKey().contains("anvil")) {
                        if (!settings.canUseAnvil()) {
                            check(faction, player, RelationPermission.USE_ANVIL, RankPermission.USE_ANVIL, ErrorMessage.Interact_Anvil_Error.getMessage(), event);
                        }
                    } else if (block.getType().getKey().getKey().contains("hopper")) {
                        if (!settings.canUseHopper()) {
                            check(faction, player, RelationPermission.USE_HOPPER, RankPermission.USE_HOPPER, ErrorMessage.Interact_Hopper_Error.getMessage(), event);
                        }
                    } else if (block.getType().getKey().getKey().contains("repeater")) {
                        if (!settings.canUseRepeater()) {
                            check(faction, player, RelationPermission.USE_REPEATER, RankPermission.USE_REPEATER, ErrorMessage.Interact_Repeater_Error.getMessage(), event);
                        }
                    } else if (block.getType().getKey().getKey().contains("comparator")) {
                        if (!settings.canUseComparator()) {
                            check(faction, player, RelationPermission.USE_COMPARATOR, RankPermission.USE_COMPARATOR, ErrorMessage.Interact_Comparator_Error.getMessage(), event);
                        }
                    } else if (block.getType().getKey().getKey().contains("dispenser")) {
                        if (!settings.canUseDispenser()) {
                            check(faction, player, RelationPermission.USE_DISPENSER, RankPermission.USE_DISPENSER, ErrorMessage.Interact_Dispenser_Error.getMessage(), event);
                        }
                    } else if (block.getType().getKey().getKey().contains("furnace")) {
                        if (!settings.canUseOven()) {
                            check(faction, player, RelationPermission.USE_FURNACE, RankPermission.USE_FURNACE, ErrorMessage.Interact_Furnace_Error.getMessage(), event);
                        }
                    } else if (block.getType().getKey().getKey().contains("dropper")) {
                        if (!settings.canUseDropper()) {
                            check(faction, player, RelationPermission.USE_DROPPER, RankPermission.USE_DROPPER, ErrorMessage.Interact_Dropper_Error.getMessage(), event);
                        }
                    } else if (block.getType().getKey().getKey().contains("fence_gate")) {
                        if (!settings.canUseFenceGate()) {
                            check(faction, player, RelationPermission.USE_FENCE_GATE, RankPermission.USE_FENCE_GATE, ErrorMessage.Interact_Gate_Error.getMessage(), event);
                        }
                    } else if (block.getType().getKey().getKey().contains("barrel")) {
                        if (!settings.canUseBarrel()) {
                            check(faction, player, RelationPermission.USE_BARREL, RankPermission.OPEN_CHEST, ErrorMessage.Interact_Barrel_Error.getMessage(), event);
                        }
                    }
                }
            }
        }
    }

    private void check(Faction faction, Player player, RelationPermission relationPermission, RankPermission rankPermission, String message, PlayerInteractEvent event) {
        Faction siege = factions.getFaction(player);
        if (SiegeCMD.siege.containsKey(siege)) {
            event.setCancelled(false);
            return;
        }

        if (faction.getPlayers().contains(player.getUniqueId())) {
            if (faction.hasPermission(player.getUniqueId(), rankPermission)) {
                event.setCancelled(false);
            } else {
                event.setCancelled(true);
                player.sendMessage(message);
            }
        } else {
            if ((faction.getPower() > 0)) {
                for (UUID uuid : faction.getAlliesRelation()) {
                    Relation relation = faction.getRelation(uuid);
                    Faction ally = factions.getFaction(uuid);
                    if (ally.getPlayers().contains(player.getUniqueId())) {
                        if (!faction.relationIsPermitted(relation, relationPermission)) {
                            event.setCancelled(true);
                            player.sendMessage(message);
                        } else {
                            event.setCancelled(false);
                            return;
                        }
                    } else {
                        event.setCancelled(true);
                        player.sendMessage(message);
                    }
                }
                if (faction.getAlliesRelation().isEmpty()) {
                    event.setCancelled(true);
                    player.sendMessage(message);
                }
            } else {
                event.setCancelled(false);
            }
        }
    }

}
