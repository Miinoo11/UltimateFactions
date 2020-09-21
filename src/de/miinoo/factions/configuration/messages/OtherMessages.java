package de.miinoo.factions.configuration.messages;

import de.miinoo.factions.Factions;
import de.miinoo.factions.FactionsSystem;
import org.bukkit.ChatColor;

public enum OtherMessages {
    Default_ChatPrefix("&7[&b%faction%&7] &e%player% &8: &f"),
    Player_Get_Invite("&aYou have been invited to join faction &e%faction%&a. Do /f join &e%faction% &ato enter the faction!"),
    Player_Joined_Faction("&aPlayer &e%player% &ajoined your faction!"),
    Player_Faction_Disbanded("&cYour faction was disbanded, you are now not longer in a faction!"),
    Player_Faction_Left("&e%player% &cleft the faction!"),
    Player_Get_Kicked("&cYou get kicked out of faction &e%faction%&c!"),
    Player_Received_Rank("&aYou received rank &e%rank%&a!"),
    Player_Get_Promoted("&bYou get promoted as leader!"),
    Command_CoolDown("&cPlease wait &e%ss &cbefore using this command again!"),
    Entering_Territory("&9Entering territory of &b%faction%"),
    Entering_Wilderness("&2Entering &awilderness"),
    Entered_ChatMode("&aSwitched to &9%chat% &achat"),
    Public_Chat("&7[&b%faction%&7] &e%player%&8 : &f"),
    Public_Chat_No_Faction("&7[] &e%player%&8 : &f"),
    Faction_Chat("&7[%rank%&7] &e%player%&8 : &f"),
    Ally_Chat("&7[&9Ally&7] [&b%faction%&7] [%rank%&7] &e%player%&8 : &f"),
    Truce_Chat("&7[&9Truce&7] [&b%faction%&7] [%rank%&7] &e%player%&8 : &f"),
    Map_Header("&9&m                     &r&9[ %faction% (%direction%)&9]&m                     "),
    Ally_Request_Get("&aFaction &e%faction% &awants an alliance. Type /f ally &e%faction% &ato accept their request!"),
    Truce_Request_Get("&aFaction &e%faction% &awants an truce. Type /f truce &e%faction% &ato accept their request!"),
    Teleport_Start("&aYou'll get teleported in %s seconds..."),
    Teleport_Pay("&aYou've paid $%costs% to teleport"),
    Teleport_Cancelled("&cTeleport cancelled, you moved!"),
    AutoClaim_Enabled("&aAutoclaim enabled"),
    AutoClaim_Disabled("&aAutoclaim disabled"),
    Faction_Unallied("&cYour faction was unallied by &e%faction%&c!"),
    Faction_Untruced("&cYour faction was untruced by &e%faction%&c!"),
    Faction_Unenemied("&cYour faction was unenemied by &e%faction%&c!"),
    Siege_Ended("&cThe Siege is over!"),
    Siege_Alert("&cYour faction gets sieged by %faction%! Hurry up!"),
    Energy_Is_Getting_Low("&cThe energy of your faction is getting low!"),
    Energy_Limit_Reached("&cThe energy of your faction hit 0! All your chunks are unclaimed"),
    TownHall_DisplayName("&bTownhall: &e%faction%"),

    Enabled_Fly("&aEnabled &7fly mode!"),
    Disabled_Fly("&cDisabled &7fly mode!"),
    Fly_Left_Chunk("&cYou left your faction territory while having fly enabled! It will be disabled in 3 seconds!"),

    Enabled_Fill("&aEnabled &7fill mode! Right-Click a storage container to fill it with tnt out of your inventory!"),
    Disabled_Fill("&cDisabled &7fill mode!"),

    Regions_Header("&8&m     [&r&cRegions&8]&m     "),

    Scoreboard_Header("&b&lScoreboard"),
    Scoreboard_Line0("&f"),
    Scoreboard_Line1("&l&f&m    &r&e&lOnline&l&f&m    "),
    Scoreboard_Line2("&7%online%"),
    Scoreboard_Line3("&e"),
    Scoreboard_Line4("&l&f&m    &r&e&lFaction&l&f&m    "),
    Scoreboard_Line5("&7%faction%"),
    Scoreboard_Line6("&6"),
    Scoreboard_Line7("&l&f&m    &r&e&lPower&l&f&m    "),
    Scoreboard_Line8("&7%power%"),
    ;

    private String message;
    OtherMessages(String msg){
        this.message = msg;
    }

    public String getDefaultMessage(){return this.message;}
    public String getMessage(){return ChatColor.translateAlternateColorCodes('&', FactionsSystem.getMessages().getMessage("Other."+this.name()));}
}
