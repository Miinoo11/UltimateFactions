package de.miinoo.factions.configuration.messages;

import de.miinoo.factions.FactionsSystem;
import org.bukkit.ChatColor;

public enum SuccessMessage {
    Successfully_Created("&aSuccessfully created faction &e%faction%&a!"),
    Successfully_Set_Home("&aSuccessfully set home for faction &e%faction%&a!"),
    Successfully_Left("&aSuccessfully left faction &e%faction%&a!"),
    Successfully_Disbanded("&aSuccessfully disbanded faction &e%faction%&a!"),
    Successfully_Invited("&aSuccessfully invited &e%player%&a!"),
    Successfully_Joined("&aSuccessfully joined &e%faction%&a!"),
    Successfully_Reloaded("&aSuccessfully reloaded!"),
    Successfully_Created_Rank("&aSuccessfully created rank!"),
    Successfully_Deleted_Rank("&aSuccessfully deleted rank &e%rank%!"),
    Successfully_Edited_Rank("&aSuccessfully edited rank!"),
    Successfully_Added_Warp("&aSuccessfully added Warp &e%warp%&a!"),
    Successfully_Deleted_Warp("&aSuccessfully deleted Warp &e%warp%&a!"),
    Successfully_Kicked("&aSuccessfully kicked &e%player%&a!"),
    Successfully_Set_Rank("&aSuccessfully added &e%player%&a to rank &e%rank%!"),
    Successfully_Warped("&aYou teleported to warp &e%warp%!"),
    Successfully_Teleported_Home("&aYou've got teleported to your faction home!"),
    Successfully_Teleported_Warp("&aYou've got teleported to warp &e%warp%&a!"),
    Successfully_Teleported_Wild("&aYou've got teleported to a wild spot!"),
    Successfully_Claimed("&aSuccessfully claimed this chunk for &e$%money%!"),
    Chunk_AutoClaimed("&2AutoClaim &8| &aClaimed for &e$%money%!"),
    Successfully_UnClaimed("&aSuccessfully unclaimed this chunk!"),
    Successfully_UnClaimedAll("&aSuccessfully unclaimed all chunks!"),
    Successfully_Added_Enemy("&aSuccessfully added enemy &e%enemy% &ato your list!"),
    Successfully_Promoted("&aSuccessfully promoted &e%player% &aas leader!"),
    Successfully_Set_Name("&aSuccessfully renamed faction to &e%faction%&a!"),
    Successfully_Set_Description("&aSuccessfully set description of faction &e%faction%&a to &e%description%&a!"),
    Successfully_Ally_Sent("&aSuccessfully sent an request to faction &e%faction%&a"),
    Successfully_Ally_Added("&aYou are now allied with faction &e%faction%&a!"),
    Successfully_Truce_Sent("&aSuccessfully sent an request to faction &e%faction%&a"),
    Successfully_Truced_Added("&aYou are now truced with faction &e%faction%&a!"),
    Successfully_Unallied("&aSuccessfully unallied faction &e%faction%&a!"),
    Successfully_Untruced("&aSuccessfully untruced faction &e%faction%&a!"),
    Successfully_Unenemied("&aSuccessfully unenemied faction &e%faction%&a!"),
    Successfully_Edited_Ally("&aSuccessfully edited ally!"),
    Successfully_Entered_AdminMode("&aSuccessfully entered adminmode!"),
    Successfully_Left_AdminMode("&aSuccessfully left adminmode!"),
    Successfully_Entered_ByPassAll("&aSuccessfully entered bypass all mode!"),
    Successfully_Left_ByPassAll("&aSuccessfully left bypass all mode!"),
    Successfully_Started_Siege("&aSuccessfully started a siege! Hurry up, you have only &e%time% &aminutes!"),
    Successfully_Received_Advanced_Permissions("&aSuccessfully received advanced permissions!"),
    Successfully_Removed_Advanced_Permissions("&aSuccessfully &cremoved &aadvanced permissions!"),
    Successfully_Set_TownHall("&aSuccessfully set townhall!"),
    Successfully_Egged_TownHall("&aSuccessfully egged townhall!"),
    Successfully_Upgraded_TownHall("&aSuccessfully upgraded townhall to level &e%level%&a for &e%money%!"),
    Successfully_Removed_TownHall("&aSuccessfully removed townhall!"),
    Successfully_Deposit("&aSuccessfully deposit &7%amount% &e%item%(s)!"),
    Successfully_Withdraw("&aSuccessfully withdrawn &7%amount% &e%item%(s)!"),
    Successfully_Deposit_Money("&aSuccessfully deposit &e%amount%$"),
    Successfully_Bought_Energy("&aSuccessfully bought 24h of energy!"),
    Successfully_Updated_Top("&aSuccessfully updated top factions!"),

    Successfully_Received_RegionWand("&aSuccessfully received wand!"),
    Successfully_Set_Pos1("&aSuccessfully set position 1 &8(&c%coords%&8)!"),
    Successfully_Set_Pos2("&aSuccessfully set position 2 &8(&c%coords%&8)!"),
    Successfully_Created_Region("&aSuccessfully created region &e%name%&a!"),
    Successfully_Deleted_Region("&aSuccessfully deleted region &e%name%&a!"),

    Successfully_Upgraded_Faction("&aSuccessfully upgraded faction to level: &e%level% &7(&c$%cost%&7)!"),

    Successfully_Added_ShopCategory("&aSuccessfully added a ShopCategory!"),
    Successfully_Edited_ShopCategory("&aSuccessfully edited a ShopCategory!"),
    Successfully_Bought_ShopItem("&aSuccessfully bought %amount%x %item%!"),
    Successfully_Sold_ShopItem("&aSuccessfully sold %amount% x %item%!"),
    Successfully_Added_ShopItem("&aSuccessfully added a Shop Item!"),
    Successfully_Removed_ShopItem("&aSuccessfully removed a ShopItem!"),
    Successfully_Removed_ShopCategory("&aSuccessfully removed a ShopCategory!"),
    ;

    private String message;
    SuccessMessage(String msg){
        this.message = msg;
    }

    public String getDefaultMessage(){return this.message;}
    public String getMessage(){return ChatColor.translateAlternateColorCodes('&', FactionsSystem.getMessages().getMessage("Success_Messages."+this.name()));}
}
