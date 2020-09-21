package de.miinoo.factions.commands.subcommands;

import de.miinoo.factions.adapter.ServerVersion;
import de.miinoo.factions.api.command.ArgumentParser;
import de.miinoo.factions.api.command.CommandDescription;
import de.miinoo.factions.api.command.PlayerCommand;
import de.miinoo.factions.api.item.Items;
import de.miinoo.factions.Factions;
import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.api.xutils.XMaterial;
import de.miinoo.factions.configuration.messages.ErrorMessage;
import de.miinoo.factions.configuration.messages.GUITags;
import de.miinoo.factions.configuration.messages.OtherMessages;
import de.miinoo.factions.configuration.messages.SuccessMessage;
import de.miinoo.factions.model.*;
import de.miinoo.factions.model.guis.ConfirmationGUI;
import net.minecraft.server.v1_10_R1.EntityInsentient;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

/**
 * @author Mino
 * 08.05.2020
 */
public class TownhallCommand extends PlayerCommand {

    public TownhallCommand() {
        super("townhall", new CommandDescription("Sets the faction townhall"), RankPermission.MANAGE_TOWNHALL);
    }

    private Factions factions = FactionsSystem.getFactions();

    @Override
    public boolean execute(Player player, ArgumentParser args) {

        if (!FactionsSystem.getSettings().townhallIsEnabled()) {
            player.sendMessage(ErrorMessage.TownHall_Enabled_Error.getMessage());
            return true;
        }

        Faction faction = factions.getFaction(player);
        if (faction == null) {
            player.sendMessage(ErrorMessage.Player_Not_In_Faction.getMessage());
            return true;
        }

        if (SiegeCMD.siege.containsValue(faction)) {
            player.sendMessage(ErrorMessage.TownHall_Create_Error.getMessage());
            return true;
        }

        if (faction.getClaimed().size() <= 0) {
            player.sendMessage(ErrorMessage.TownHall_Location_Error.getMessage());
            return true;
        }

        if (faction.townHallExists()) {
            player.sendMessage(ErrorMessage.TownHall_Already_Exists.getMessage());
            return true;
        }

        for (FactionChunk chunk : faction.getClaimed()) {
            if (factions.isInChunk(player.getLocation(), chunk.getBukkitChunk())) {
                new ConfirmationGUI(player, Items.createItem(XMaterial.PAPER.parseMaterial()).setDisplayName(GUITags.Confirm_Description.getMessage()).setLore(GUITags.Townhall_Lore.getMessage()).getItem(),
                        () -> {
                            Entity entity = player.getWorld().spawnEntity(player.getLocation(), EntityType.VILLAGER);
                            entity.setCustomNameVisible(true);
                            entity.setCustomName(OtherMessages.TownHall_DisplayName.getMessage().replace("%faction%", faction.getName()));
                            if (ServerVersion.getServerVersion().equals(ServerVersion.VERSION_1_8_R3)) {
                                net.minecraft.server.v1_8_R3.Entity nmsEntity = ((org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity) entity).getHandle();
                                NBTTagCompound tag = nmsEntity.getNBTTag();
                                if (tag == null) {
                                    tag = new NBTTagCompound();
                                }
                                nmsEntity.c(tag);
                                tag.setInt("NoAI", 1);
                                nmsEntity.b(true);
                                nmsEntity.f(tag);
                            } else if (ServerVersion.getServerVersion().equals(ServerVersion.VERSION_1_8_R1)) {
                                net.minecraft.server.v1_8_R1.Entity nmsEntity = ((CraftEntity) entity).getHandle();
                                net.minecraft.server.v1_8_R1.NBTTagCompound tag = nmsEntity.getNBTTag();
                                if (tag == null) {
                                    tag = new net.minecraft.server.v1_8_R1.NBTTagCompound();
                                }
                                nmsEntity.c(tag);
                                tag.setInt("NoAI", 1);
                                nmsEntity.b(true);
                                nmsEntity.f(tag);
                            } else if (ServerVersion.getServerVersion().equals(ServerVersion.VERSION_1_9_R1)) {
                                net.minecraft.server.v1_9_R1.Entity nmsEntity = ((org.bukkit.craftbukkit.v1_9_R1.entity.CraftEntity) entity).getHandle();
                                net.minecraft.server.v1_9_R1.NBTTagCompound tag = new net.minecraft.server.v1_9_R1.NBTTagCompound();
                                nmsEntity.c(tag);
                                tag.setInt("NoAI", 1);
                                nmsEntity.b(true);
                                nmsEntity.f(tag);
                            } else if (ServerVersion.getServerVersion().equals(ServerVersion.VERSION_1_10_R1)) {
                                if (entity instanceof LivingEntity) {
                                    try {
                                        Object nmsEntity = entity.getClass().getMethod("getHandle").invoke(entity);
                                        nmsEntity.getClass().getMethod("setAI", boolean.class).invoke(nmsEntity, true);
                                    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                                        e.printStackTrace();
                                    }
                                }
                                //net.minecraft.server.v1_10_R1.Entity nmsEntity = ((org.bukkit.craftbukkit.v1_10_R1.entity.CraftEntity) entity).getHandle();
                                //net.minecraft.server.v1_10_R1.NBTTagCompound tag = new net.minecraft.server.v1_10_R1.NBTTagCompound();
                                //nmsEntity.c(tag);
                                //tag.setInt("NoAI", 1);
                                //nmsEntity.b(true);
                                //nmsEntity.f(tag);
                                //} else if(ServerVersion.getServerVersion().equals(ServerVersion.VERSION_1_10_R2)) {
                                //    net.minecraft.server.v1_10_R2.Entity nmsEntity = ((org.bukkit.craftbukkit.v1_10_R2.entity.CraftEntity) entity).getHandle();
                                //    net.minecraft.server.v1_10_R2.NBTTagCompound tag = new net.minecraft.server.v1_10_R2.NBTTagCompound();
                                //    nmsEntity.c(tag);
                                //    tag.setInt("NoAI", 1);
                                //    nmsEntity.f(tag);
                                //} else if(ServerVersion.getServerVersion().equals(ServerVersion.VERSION_1_11_R1)) {
                                //    net.minecraft.server.v1_11_R1.Entity nmsEntity = ((org.bukkit.craftbukkit.v1_11_R1.entity.CraftEntity) entity).getHandle();
                                //    net.minecraft.server.v1_11_R1.NBTTagCompound tag = new net.minecraft.server.v1_11_R1.NBTTagCompound();
                                //    nmsEntity.c(tag);
                                //    tag.setInt("NoAI", 1);
                                //    nmsEntity.f(tag);
                            } else {
                                entity.setSilent(true);
                                if (entity instanceof LivingEntity) {
                                    ((LivingEntity) entity).setAI(false);
                                }
                            }

                            faction.setTownHall(new Townhall(entity.getUniqueId(), FactionsSystem.getSettings().getDefaultHealth(), player.getLocation()));
                            player.sendMessage(SuccessMessage.Successfully_Set_TownHall.getMessage());
                            factions.saveFaction(faction);
                        }).open();
                return true;
            }
        }

        player.sendMessage(ErrorMessage.TownHall_Location_Error.getMessage());
        return true;
    }

    void noAI(Entity bukkitEntity) {

    }
}
