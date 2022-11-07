package com.jediexe.pouchviewer;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

import java.io.File;
import java.util.List;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

@Mod(name = Main.NAME, modid = Main.MODID, version = Main.VERSION, acceptedMinecraftVersions = "[1.7.10]")

public class Main{
	
	public static final String NAME = "LOTR Pouch Viewer";
    public static final String MODID = "pouchviewer";
    public static final String VERSION = "1.7";
    
    public static boolean showOwned;
    public static boolean showDyed;
    public static boolean nameFirst;
    public static boolean enableRarity;
    public static boolean addTagItems;
    public static String commonItems;
    public static String uncommonItems;
    public static String rareItems;
    public static String epicItems;
    public static String legendaryItems;
    public static String tagItemsColor;
    public static String slotColor;
    public static String defaultColor;
    public static String commonColor;
    public static String uncommonColor;
    public static String rareColor;
    public static String epicColor;
    public static String legendaryColor;
    
    public void initConfiguration(FMLInitializationEvent event) {
    	Configuration config = new Configuration(new File("config/pouchviewer.cfg"));
    	config.load();
    	showOwned = config.getBoolean("showOwned", "config", false, 
    			"Set to true to keep the 'Belonged to:' text in the tooltip");
    	showDyed = config.getBoolean("showDyed", "config", false, 
    			"Set to true to keep the 'Dyed' text in the tooltip");
    	nameFirst = config.getBoolean("nameFirst", "config", true, 
    			"Set to false to make the quantity (x64) appear after the item name in the tooltip");
    	addTagItems = config.getBoolean("addTagItems", "config", true, 
    			"Set to false to remove the 'Items' line that appears before the listed items in the tooltip");
    	enableRarity = config.getBoolean("enableRarity", "config", true, 
    			"Set to false disable the rarity color system");
    	defaultColor = config.getString("0. defaultColor", "rarity colors", "white", 
    			"The default color of items not listed in 'rarity'. Valid values: dark_red, red, gold, yellow, dark_green, green, aqua, dark_aqua, dark_blue, blue, light_purple, dark_purple, white, gray, dark_gray, black");
    	tagItemsColor = config.getString("0. tagItemsColor", "rarity colors", "blue", 
    			"The default color of the tag 'Items' that appears above listed items. Valid values: dark_red, red, gold, yellow, dark_green, green, aqua, dark_aqua, dark_blue, blue, light_purple, dark_purple, white, gray, dark_gray, black");
    	slotColor = config.getString("0. slotColor", "rarity colors", "dark_aqua", 
    			"The default slot color used when advanced tooltips is enabled. Valid values: dark_red, red, gold, yellow, dark_green, green, aqua, dark_aqua, dark_blue, blue, light_purple, dark_purple, white, gray, dark_gray, black");
    	commonColor = config.getString("1. commonColor", "rarity colors", "gray", 
    			"The default color of common items. Valid values: dark_red, red, gold, yellow, dark_green, green, aqua, dark_aqua, dark_blue, blue, light_purple, dark_purple, white, gray, dark_gray, black");
    	uncommonColor = config.getString("2. uncommonColor", "rarity colors", "green", 
    			"The default color of uncommon items. Valid values: dark_red, red, gold, yellow, dark_green, green, aqua, dark_aqua, dark_blue, blue, light_purple, dark_purple, white, gray, dark_gray, black");
    	rareColor = config.getString("3. rareColor", "rarity colors", "blue", 
    			"The default color of rare items. Valid values: dark_red, red, gold, yellow, dark_green, green, aqua, dark_aqua, dark_blue, blue, light_purple, dark_purple, white, gray, dark_gray, black");
    	epicColor = config.getString("4. epicColor", "rarity colors", "dark_purple", 
    			"The default color of epic items. Valid values: dark_red, red, gold, yellow, dark_green, green, aqua, dark_aqua, dark_blue, blue, light_purple, dark_purple, white, gray, dark_gray, black");
    	legendaryColor = config.getString("5. legendaryColor", "rarity colors", "gold", 
    			"The default color of legendary items. Valid values: dark_red, red, gold, yellow, dark_green, green, aqua, dark_aqua, dark_blue, blue, light_purple, dark_purple, white, gray, dark_gray, black");
    	commonItems = config.getString("1. commonItems", "rarity", "rotten flesh, orc bone, bone, elf bone, dwarf bone, warg bone, troll bone, fur", 
    			"List all item names that will have the common color");
    	uncommonItems = config.getString("2. uncommonItems", "rarity", "silver coin, gunpowder, salt, niter, sulfur, bronze ingot, copper ingot, tin ingot, coal, charcoal", 
    			"List all item names that will have the uncommon color");
    	rareItems = config.getString("3. rareItems", "rarity", "silver coin (10), gold ingot, silver ingot, elven steel ingot, blue dwarven steel ingot, orc steel ingot, reinforced orc steel ingot, dwarven ingot, gilded iron ingot, black uruk steel ingot, galvorn ingot, morgul steel ingot, uruk steel ingot, petty-dwarven steel ingot", 
    			"List all item names that will have the rare color");
    	epicItems = config.getString("4. epicItems", "rarity", "silver coin (100), diamond, opal, amethyst, sapphire, edhelvir, amber, ruby, topaz", 
    			"List all item names that will have the epic color");
    	legendaryItems = config.getString("5. legendaryItems", "rarity", "mithril ingot", 
    			"List all item names that will have the legendary color");
    	config.save();
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event){
    	this.initConfiguration(event);
    	MinecraftForge.EVENT_BUS.register(Pouchviewer.instance);
    }
    
}
