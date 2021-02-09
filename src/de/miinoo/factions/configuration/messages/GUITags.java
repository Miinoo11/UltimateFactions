package de.miinoo.factions.configuration.messages;

import de.miinoo.factions.FactionsSystem;
import org.bukkit.ChatColor;

public enum GUITags {
    Confirm("&aConfirm"),
    Cancel("&cCancel"),
    Save("&aSave"),
    Next("&eNext"),
    Previous("&ePrevious"),
    Back("&eBack"),
    Enabled("&aenabled"),
    Disabled("&cdisabled"),
    True("&atrue"),
    False("&cfalse"),
    Create_Rank("&aCreate Rank"),
    Set_Icon("&aSet Icon"),
    Set_Name("&aSet Name"),
    Set_Description("&aSet Description"),
    Set_Prefix("&aSet Prefix"),
    Set_Permissions("&aSet Permissions"),
    Rank_Enter_Name("Enter name"),
    Rank_Enter_Prefix("Enter prefix"),
    Remove_Rank("&cRemove Rank"),
    Edit_Players("&eEdit players"),
    Edit_Rank("&eEdit rank"),
    Delete_Rank("&cDelete rank"),
    Edit_Players_Head("&d%player%"),
    Rank_CustomIcon("&eCustom Icon"),
    Rank_NormalIcon("&eNormal Icon"),

    Info_Members("&eFaction members"),
    Info_All("&eInformation"),
    Info_Enemies("&eEnemies"),
    Info_Truces("&eTruces"),
    Info_Allies("&eAllies"),
    Info_Relations("&eRelations"),
    Info_Ranks("&eFaction ranks"),
    Info_All_Name("&eName: &a%name%"),
    Info_All_Level("&eLevel: &a%level%"),
    Info_All_Description("&eDescription: &a%description%"),
    Info_All_Members("&eMembers: &a%count% &8/ &e%max%"),
    Info_All_Power("&ePower: &a%count%"),
    Info_All_Claims("&eClaims: &a%count% / %maxclaims%"),
    Info_Click_Lore("&7&o- Click to open -"),
    Info_Claim_Energy("&eEnergy: &a%count%"),
    Info_WarPieces("&eWarPieces"),
    Info_WarPieces_Lore("&7Click to see warpieces"),

    Edit_Ally_Permission("&eEdit Ally Permission"),
    Unally("&cUnally"),
    Confirm_Description("&eDescription"),
    Disband_Confirm_Lore("&7Click to disband your faction"),
    Leave_Confirm_Lore("&7Click to leave your faction"),
    Delete_Rank_Confirm_Lore("&7Click to delete this rank"),
    Set_Rank_Confirm_Lore("&7Click to set rank &e%rank% &7to player &e%player%"),
    Promote_Leader_Confirm_Lore("&7Click to promote player &e%player% &7to leader"),
    Ally_Request_Lore("&7Click to send faction &e%faction% &7an ally request"),
    Truce_Request_Lore("&7Click to send faction &e%faction% &7an truce request"),
    Neutral_Lore("&7Click to neutral faction &e%faction%"),
    WarPieces_Lore1("&eYour WarPieces: &c%count%"),
    WarPieces_Lore2("&eTheir WarPieces: &c%count%"),

    Warps_Warp("&c%warp%"),
    Warps_Lore("&7Has Password: %value%"),
    Warps_Value_Yes("&aYes"),
    Warps_Value_No("&aNo"),

    Admin_Factions("&eFactions"),
    Admin_Leader_Lore("&7Leader: &3%leader%"),
    Admin_Get_Permissions("&eToggle Leader permissions"),
    Admin_Get_Permissions_Lore("&7You receive all permissions in this faction"),
    Admin_Warps("&eFaction Warps"),
    Admin_Warps_Warp("&c%warp%"),
    Admin_Warps_Warp_Lore("&ePassword: &7%password%"),
    Admin_Warps_Warp_Lore_No_Password("&7No password set!"),
    Admin_Warps_Lore("&7Manage all faction warps of this faction"),
    Admin_Warps_Warp_Teleport("&eTeleport"),
    Admin_Warps_Warp_Teleport_Lore("&eClick to teleport"),
    Admin_Warps_Warp_Info("&eInfo"),
    Admin_Warps_Warp_Info_Lore("&ePassword: &7%password%"),
    Admin_Warps_Warp_Delete("&eDelete"),
    Admin_Warps_Warp_Delete_Lore("&eClick to delete this warp"),
    Admin_Disband_Faction("&eDisband Faction"),
    Admin_Disband_Faction_Lore("&7You Disband Faction"),
    Admin_Faction_Change_Name("&eChange Name"),
    Admin_Faction_Change_Desc("&eChange Description"),
    Toggle_Advanced_Permissions("&eToggle advanced permissions"),
    Admin_Get_Advanced_Permissions_Lore("&8- &7Teleport ByPass"),
    Admin_Bypass_All("&bToggle Bypass All"),
    Admin_Bypass_All_Lore("&7Bypass all Factions"),
    Admin_TownHall("&eTownhall options"),
    Admin_TownHall_Lore("&7- Click to open -"),
    Admin_TownHall_Remove("&eRemove Townhall"),
    Admin_TownHall_Remove_Lore("&7Click to remove"),
    Admin_TownHall_Get_Egg("&eGet Townhall egg"),
    Admin_TownHall_Get_Egg_Lore1("&cWarning! &7To avoid Errors, please make sure"),
    Admin_TownHall_Get_Egg_Lore2("&7the townhall don't exist when you place this egg!"),

    Bank_Deposit("&eDeposit"),
    Bank_Withdraw("&eWithdraw"),
    Bank_Info("&eInfo"),
    Bank_Info_Lore("&a&lCurrent Balance: &7$%amount%"),
    Bank_Item_Info_Lore("&a&lAmount: &7%amount%"),
    Bank_Item_Info_Lore2("&a&lTotal sum: &7$%amount%"),
    Bank_Deposit_1("&b&lDeposit 1: &a+ $%price% &7&o(Left-Click)"),
    Bank_Deposit_32("&b&lDeposit 32: &a+ $%price% &7&o(Shift-Left-Click)"),
    Bank_Deposit_64("&b&lDeposit 64: &a+ $%price% &7&o(Right-Click)"),
    Bank_Deposit_All("&b&lDeposit All: &a+ $%price% &7&o(Shift-Right-Click)"),
    Bank_Withdraw_1("&b&lWithdraw 1: &7&o(Left-Click)"),
    Bank_Withdraw_32("&b&lWithdraw 32: &7&o(Shift-Left-Click)"),
    Bank_Withdraw_64("&b&lWithdraw 64: &7&o(Right-Click)"),

    Townhall_Title("&8Townhall"),
    Townhall_Lore("&eSets the villager to ur current location"),
    Townhall_Energy("&eEnergy"),
    Townhall_Energy_Lore("&7%count%"),
    Townhall_Buy_Energy("&eBuy 24h of energy"),
    Townhall_Buy_Energy_Lore("&7Costs: &a$%costs%"),
    Townhall_Upgrade("&eUpgrade"),
    Townhall_Upgrade_Lore("&7Upgrade your Townhall"),
    Townhall_Bank("&eBank"),
    Townhall_Bank_Lore2("&7Current value: &a$%count%"),
    Townhall_Move("&eMove your townhall"),
    Townhall_Move_Lore("&7Can be moved in: &e%time%"),

    TopFactions_Faction("&6%faction%"),
    TopFactions_Faction_Lore("&7%description%"),
    TopFactions_Faction_Lore1(" "),
    TopFactions_Faction_Lore2("&8&m     [&r&6Blocks&8]&m     "),
    TopFactions_Faction_Lore3("&7Bank amount: &e$%amount%"),
    TopFactions_Faction_Lore4("&7Total: &e$%value%"),
    TopFactions_PlacedBlock("&e%block% &8: &c%amount%"),
    TopFactions_PlacedBlock_Empty("&cN/A"),

    Regions_Lore("&8» &7Left-Click to set &cpos1"),
    Regions_Lore1("&8» &7Right-Click to set &cpos2"),
    Regions_Delete_Description("&7Click to delete region &e%name%"),

    Upgrade_Info_Lore("&8» &ecurrent level: &6%level%"),
    Upgrade_Info_Lore1("&8» &anext level: &2%level%"),
    Upgrade_Info_Lore2("&8» &c$%cost%"),
    Upgrade_Title("&aUpgrade"),
    Upgrade_Lore("&7&o- Click to upgrade -"),
    Upgrade_Maxed("&cMaxed!"),
    Upgrade_Upgrades("&bSee all upgrades!"),

    Upgrades_Fly("&eFaction fly"),
    Upgrades_Fill("&eFaction Fill"),
    Upgrades_MemberCount("&eMember Count &7(max: &a%value%&7)"),
    Upgrades_ClaimCount("&eClaim Count &7(max: &a%value%&7)"),
    Upgrades_TeleportDelay("&eTeleport delay &7(&a%value%&7s)"),
    Upgrades_MobDropMultiplayer("&eMobDrop Multiplier &7(&a%value%&7)"),
    Upgrades_PotionEffects("&ePotion Effects &7(&a%value%&7)"),
    Upgrades_PotionEffects_Next_Lore("&eNext effects:"),
    Upgrades_PotionEffects_Lore_Format("&7- &7%effect%"),
    Upgrades_GrowSpeed("&eGrow-Speed Multiplier &7(&a%value%&7)"),
    Upgrades_Soon("&cComming soon"),
    Upgrades_WarpCount("&eWarp Count &7(max: &a%value%&7)"),
    Upgrades_Lore_Unlocked("&8» &aunlocked"),
    Upgrades_Lore_Locked("&8» &clocked"),
    Upgrades_Lore_Next_Value("&8» &6next&7: &e%value%"),

    Shop_MainGUI("&8UltimateFactions - Shop"),
    Shop_AddCategoryGUI("&8Add Category"),
    Shop_AddCategory("&eAdd Category"),
    Shop_RemoveCategory("&eRemove Category"),
    Shop_EditCategory("&eEdit Category"),
    Shop_AddItem("&8Add Item"),
    Shop_RemoveItem("&eRemove Item"),
    Shop_AddCategory_Name("&eName"),
    Shop_AddCategory_Description("&eDescription"),
    Shop_AddCategory_Icon("&eIcon"),
    Shop_AddCategory_Enter_Name("Enter Name"),
    Shop_AddCategory_Enter_Description("Enter Description"),
    Shop_ChooseIcon("&eChoose Icon"),
    Shop_ChooseIcon_Lore("&7- Click on the Item in your inventory -"),

    Shop_EditCategory_Name("%name%"),
    Shop_EditCategory_Icon("&eIcon"),
    Shop_EditCategory_Description("%description%"),
    Shop_EditCategory_Add_Item("&eAdd Shop Item"),
    Shop_EditCategory_Remove_Item("&eRemove Shop Item"),

    Shop_AddItem_Name("&eName"),
    Shop_AddItem_Description("&eDescription"),
    Shop_AddItem_Price("&ePrice"),
    Shop_AddItem_Sell_Price("&eSell Price"),
    Shop_AddItem_Enter_Name("Enter Name"),
    Shop_AddItem_Enter_Description("Enter Description"),
    Shop_AddItem_Enter_Price("Enter Price"),
    Shop_AddItem_Enter_Sell_Price("Enter Sell Price"),
    Shop_AddItem_Item("&eItem"),
    Shop_AddItem_canSell("&eCan sell"),

    Shop_Item_Price_Lore("&7Buy: &a$%price% &8| &7&oLeftClick"),
    Shop_Item_Sell_Price_Lore("&7Sell: &a$%price% &8| &7&oRightClick"),

    Regions_Title("Regions"),
    Regions_Region("&3%name%"),
    Regions_Info_Title("Region: %region%"),
    Regions_Info_Edit("&eEdit region"),
    Regions_Info_Delete("&eDelete region"),
    Regions_Edit_Change_Name("&eChange name"),
    Regions_Edit_Flags("&eFlags"),
    Regions_Edit_Flags_Lore_Format("&7- &a%flag%"),

    Regions_Flags_Title("Flags"),
    Region_Flags_DisablePVP("&eDisable PvP"),
    Region_Flags_DisablePlace("&eDisable Place"),
    Region_Flags_DisableBreak("&eDisable Break"),

    Invites_Title("Invites"),
    Invites_Faction("&e%faction%"),
    Invites_Faction_Description_Lore("&7Click to accept or deny the invitation"),

    Permission_Chat("Chat"),
    Permission_Build("Build"),
    Permission_Break("Break"),
    Permission_Open_Door("Open Door"),
    Permission_Open_Chest("Open Chest"),
    Permission_Open_Trapped_Chest("Open Trapped Chest"),
    Permission_Open_Ender_Chest("Open Ender Chest"),
    Permission_Home("Home"),
    Permission_Set_Home("Set Home"),
    Permission_Warp("Warp"),
    Permission_Manage_Warps("Manage Warps"),
    Permission_Place_TNT("Place TNT"),
    Permission_Kick("Kick"),
    Permission_Invite("Invite"),
    Permission_Claim("Claim"),
    Permission_UnClaim("UnClaim"),
    Permission_Ally("Add Ally"),
    Permission_Truce("Add Truce"),
    Permission_Enemy("Add Enemy"),
    Permission_Neutral("Neutral a Faction"),
    Permission_Withdraw("Withdraw"),
    Permission_Manage_Townhall("Manage Townhall"),
    Permission_Manage_Roles("Manage Roles"),
    Permission_Assign_Roles("Assing Roles"),
    Permission_Fill_Bucket("Fill Bucket"),
    Permission_Empty_Bucket("Empty Bucket"),
    Permission_Use_Button("Use Button"),
    Permission_Use_Lever("Use Lever"),
    Permission_Use_Trapdoor("Use Trapdoor"),
    Permission_Use_Repeater("Use Repeater"),
    Permission_Use_Comparator("Use Comparator"),
    Permission_Use_Dispenser("Use Dispenser"),
    Permission_Use_Dropper("Use Dropper"),
    Permission_Use_Hopper("Use Hopper"),
    Permission_Use_Furnace("Use Furnace"),
    Permission_Use_Fence_Gate("Use Fence Gate"),
    Permission_Use_Anvil("Use Anvil"),
    Permission_Disband("Disband"),
    Permission_Hit_Allies("Hit Allies"),
    Permission_Hit_Truces("Hit Truces"),
    Permission_Siege("Siege"),
    Permission_Fly("Fly"),
    Permission_Fill("Fill"),
    Permission_Change_Info("Change Info"),
    Permission_Manage_Quests("Manage Quests"),
    Permission_Claim_Quests("Claim Quest Reward"),

    Quests_Title("Quests"),
    Quests_Completed_Title("Completed Quests"),
    Quests_Completed_Quests("&aCompleted Quests"),
    Quests_Quest("&e%quest%"),
    Quests_Quest_Completed("&e%quest% &8(&aCompleted&8)"),
    Quests_Quest_Lore("&7accepted: %value%"),
    Quests_Quest_Completed_Lore("&7completed: &atrue"),
    Quests_Quest_Lore1("&7type: &b%type%"),
    Quests_Quest_Lore_Reward("&7reward: &e%reward%"),
    Quests_Quest_Completed_Claim_Lore("&7&o- click to claim &ereward &7&o-"),
    Quests_Quest_Completed_Claim_Lore2("&c&lalready claimed"),
    Quests_Quest_Lore2("&8&m               "),
    Quests_Quest_Description("&7%description%"),
    Quests_Quest_Process("&7process: &b%value%&7 / &3%max%"),

    Quests_Create_Quest_Title("Create Quest"),
    Quests_Create_Quest("&eCreate Quest"),

    Quest_Type_Kill("Kill"),
    Quest_Type_Collect("Collect"),
    Quest_Type_Tame("Tame"),

    Quest_Choose_Icon("Choose Icon"),
    Quest_Choose_Type("Choose Type"),
    Quest_Choose_Reward("Choose Choose Reward"),
    Quest_Enter_Description("Enter description"),
    Quest_Enter_Name("Enter name"),
    Quest_Enter_Reward("Enter reward"),
    Quest_Set_Type("&eSet Type"),
    Quest_Set_Reward("&eSet reward"),
    Quest_Set_Action("&eSet Action"),
    Quest_Enter_Action("<amount>;<type>"),
    Quest_Reward_Text("&e$%reward%")
    ;

    private String message;
    GUITags(String msg){
        this.message = msg;
    }

    public String getDefaultMessage(){return this.message;}
    public String getMessage(){return ChatColor.translateAlternateColorCodes('&', FactionsSystem.getMessages().getMessage("GUI_Tags."+this.name()));}
}
