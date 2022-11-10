package com.jediexe.pouchviewer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.client.config.IConfigElement;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

@Mod(name = Main.NAME, modid = Main.MODID, version = Main.VERSION, acceptedMinecraftVersions = "[1.7.10]", guiFactory = "com.jediexe.pouchviewer.PouchviewerGuiFactory")

public class Main{
	
	public static final String NAME = "LOTR Pouch Viewer";
    public static final String MODID = "pouchviewer";
    public static final String VERSION = "1.8";
    
    public static Configuration config = new Configuration(new File("config/pouchviewer.cfg"));
    public static List<ConfigCategory> allCategories;
    
    static { 
    	allCategories = new ArrayList<>();
	} 
    
    public static String makeCategory(String name) {
		ConfigCategory category = config.getCategory(name);
		allCategories.add(category);
		return name;
	}
    
    public static String CATEGORY_CONFIG = Main.makeCategory("config");
    public static String CATEGORY_RARITY = Main.makeCategory("rarity");
	public static String CATEGORY_RARITYCOLORS = Main.makeCategory("rarity colors");
	
	Property pshowOwned = config.get(CATEGORY_CONFIG, "showOwned", false, 
			"Set to true to keep the 'Belonged to:' text in the tooltip");
	Property pshowDyed = config.get("config", "showDyed", false, 
			"Set to true to keep the 'Dyed' text in the tooltip");
	Property pnameFirst = config.get(CATEGORY_CONFIG, "nameFirst", true, 
			"Set to false to make the quantity (x64) appear after the item name in the tooltip");
	Property paddTagItems = config.get(CATEGORY_CONFIG, "addTagItems", true, 
			"Set to false to remove the 'Items' line that appears before the listed items in the tooltip");
	Property penableRarity = config.get(CATEGORY_CONFIG, "enableRarity", true, 
			"Set to false disable the rarity color system");
	Property pcommonItems = config.get(CATEGORY_RARITY, "1. commonItems", "rotten flesh, orc bone, bone, elf bone, dwarf bone, warg bone, troll bone, fur", 
			"List all item names that will have the common color");
	Property puncommonItems = config.get(CATEGORY_RARITY, "2. uncommonItems", "silver coin, gunpowder, salt, niter, sulfur, bronze ingot, copper ingot, tin ingot, coal, charcoal", 
			"List all item names that will have the uncommon color");
	Property prareItems = config.get(CATEGORY_RARITY, "3. rareItems", "silver coin (10), gold ingot, silver ingot, elven steel ingot, blue dwarven steel ingot, orc steel ingot, reinforced orc steel ingot, dwarven ingot, gilded iron ingot, black uruk steel ingot, galvorn ingot, morgul steel ingot, uruk steel ingot, petty-dwarven steel ingot", 
			"List all item names that will have the rare color");
	Property pepicItems = config.get(CATEGORY_RARITY, "4. epicItems", "silver coin (100), diamond, opal, amethyst, sapphire, edhelvir, amber, ruby, topaz", 
			"List all item names that will have the epic color");
	Property plegendaryItems = config.get(CATEGORY_RARITY, "5. legendaryItems", "mithril ingot", 
			"List all item names that will have the legendary color");
	Property pdefaultColor = config.get(CATEGORY_RARITYCOLORS, "0. defaultColor", "white", 
			"The default color of items not listed in 'rarity'. Valid values: dark_red, red, gold, yellow, dark_green, green, aqua, dark_aqua, dark_blue, blue, light_purple, dark_purple, white, gray, dark_gray, black");
	Property ptagItemsColor = config.get(CATEGORY_RARITYCOLORS, "0. tagItemsColor", "blue", 
			"The default color of the tag 'Items' that appears above listed items. Valid values: dark_red, red, gold, yellow, dark_green, green, aqua, dark_aqua, dark_blue, blue, light_purple, dark_purple, white, gray, dark_gray, black");
	Property pslotColor = config.get(CATEGORY_RARITYCOLORS, "0. slotColor", "dark_aqua", 
			"The default slot color used when advanced tooltips is enabled. Valid values: dark_red, red, gold, yellow, dark_green, green, aqua, dark_aqua, dark_blue, blue, light_purple, dark_purple, white, gray, dark_gray, black");
	Property pcommonColor = config.get(CATEGORY_RARITYCOLORS, "1. commonColor", "gray", 
			"The default color of common items. Valid values: dark_red, red, gold, yellow, dark_green, green, aqua, dark_aqua, dark_blue, blue, light_purple, dark_purple, white, gray, dark_gray, black");
	Property puncommonColor = config.get(CATEGORY_RARITYCOLORS, "2. uncommonColor", "green", 
			"The default color of uncommon items. Valid values: dark_red, red, gold, yellow, dark_green, green, aqua, dark_aqua, dark_blue, blue, light_purple, dark_purple, white, gray, dark_gray, black");
	Property prareColor = config.get(CATEGORY_RARITYCOLORS, "3. rareColor", "blue", 
			"The default color of rare items. Valid values: dark_red, red, gold, yellow, dark_green, green, aqua, dark_aqua, dark_blue, blue, light_purple, dark_purple, white, gray, dark_gray, black");
	Property pepicColor = config.get(CATEGORY_RARITYCOLORS, "4. epicColor", "dark_purple", 
			"The default color of epic items. Valid values: dark_red, red, gold, yellow, dark_green, green, aqua, dark_aqua, dark_blue, blue, light_purple, dark_purple, white, gray, dark_gray, black");
	Property plegendaryColor = config.get(CATEGORY_RARITYCOLORS, "5. legendaryColor", "gold", 
			"The default color of legendary items. Valid values: dark_red, red, gold, yellow, dark_green, green, aqua, dark_aqua, dark_blue, blue, light_purple, dark_purple, white, gray, dark_gray, black");
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
    
    public static List<IConfigElement> getConfigElements() {
		ArrayList<IConfigElement> list = new ArrayList<>();
		for (ConfigCategory category : allCategories) {
			ConfigElement categoryElement = new ConfigElement(category);
			list.add(categoryElement);
		}
		return list;
	}
    
    public static void load(Configuration config) {
    	//Config options
    	Property pshowOwned = config.get("config", "showOwned", false, 
    			"Set to true to keep the 'Belonged to:' text in the tooltip");
    	showOwned = pshowOwned.getBoolean();
    	Property pshowDyed = config.get("config", "showDyed", false, 
    			"Set to true to keep the 'Dyed' text in the tooltip");
    	showDyed = pshowDyed.getBoolean();
    	Property pnameFirst = config.get("config", "nameFirst", true, 
    			"Set to false to make the quantity (x64) appear after the item name in the tooltip");
    	nameFirst = pnameFirst.getBoolean();
    	Property paddTagItems = config.get("config", "addTagItems", true, 
    			"Set to false to remove the 'Items' line that appears before the listed items in the tooltip");
    	addTagItems = paddTagItems.getBoolean();
    	Property penableRarity = config.get("config", "enableRarity", true, 
    			"Set to false disable the rarity color system");
    	enableRarity = penableRarity.getBoolean();
    	
    	//Rarity options
    	Property pcommonItems = config.get("rarity", "1. commonItems", "rotten flesh, orc bone, bone, elf bone, dwarf bone, warg bone, troll bone, fur", 
    			"List all item names that will have the common color");
    	commonItems = pcommonItems.getString();
    	Property puncommonItems = config.get("rarity", "2. uncommonItems", "silver coin, gunpowder, salt, niter, sulfur, bronze ingot, copper ingot, tin ingot, coal, charcoal", 
    			"List all item names that will have the uncommon color");
    	uncommonItems = puncommonItems.getString();
    	Property prareItems = config.get("rarity", "3. rareItems", "silver coin (10), gold ingot, silver ingot, elven steel ingot, blue dwarven steel ingot, orc steel ingot, reinforced orc steel ingot, dwarven ingot, gilded iron ingot, black uruk steel ingot, galvorn ingot, morgul steel ingot, uruk steel ingot, petty-dwarven steel ingot", 
    			"List all item names that will have the rare color");
    	rareItems = prareItems.getString();
    	Property pepicItems = config.get("rarity", "4. epicItems", "silver coin (100), diamond, opal, amethyst, sapphire, edhelvir, amber, ruby, topaz", 
    			"List all item names that will have the epic color");
    	epicItems = pepicItems.getString();
    	Property plegendaryItems = config.get("rarity", "5. legendaryItems", "mithril ingot", 
    			"List all item names that will have the legendary color");
    	legendaryItems = plegendaryItems.getString();
    	
    	//Rarity color options
    	Property pdefaultColor = config.get("rarity colors", "0. defaultColor", "white", 
    			"The default color of items not listed in 'rarity'. Valid values: dark_red, red, gold, yellow, dark_green, green, aqua, dark_aqua, dark_blue, blue, light_purple, dark_purple, white, gray, dark_gray, black");
    	defaultColor = pdefaultColor.getString();
    	Property ptagItemsColor = config.get("rarity colors", "0. tagItemsColor", "blue", 
    			"The default color of the tag 'Items' that appears above listed items. Valid values: dark_red, red, gold, yellow, dark_green, green, aqua, dark_aqua, dark_blue, blue, light_purple, dark_purple, white, gray, dark_gray, black");
    	tagItemsColor = ptagItemsColor.getString();
    	Property pslotColor = config.get("rarity colors", "0. slotColor", "dark_aqua", 
    			"The default slot color used when advanced tooltips is enabled. Valid values: dark_red, red, gold, yellow, dark_green, green, aqua, dark_aqua, dark_blue, blue, light_purple, dark_purple, white, gray, dark_gray, black");
    	slotColor = pslotColor.getString();
    	Property pcommonColor = config.get("rarity colors", "1. commonColor", "gray", 
    			"The default color of common items. Valid values: dark_red, red, gold, yellow, dark_green, green, aqua, dark_aqua, dark_blue, blue, light_purple, dark_purple, white, gray, dark_gray, black");
    	commonColor = pcommonColor.getString();
    	Property puncommonColor = config.get("rarity colors", "2. uncommonColor", "green", 
    			"The default color of uncommon items. Valid values: dark_red, red, gold, yellow, dark_green, green, aqua, dark_aqua, dark_blue, blue, light_purple, dark_purple, white, gray, dark_gray, black");
    	uncommonColor = puncommonColor.getString();
    	Property prareColor = config.get("rarity colors", "3. rareColor", "blue", 
    			"The default color of rare items. Valid values: dark_red, red, gold, yellow, dark_green, green, aqua, dark_aqua, dark_blue, blue, light_purple, dark_purple, white, gray, dark_gray, black");
    	rareColor = prareColor.getString();
    	Property pepicColor = config.get("rarity colors", "4. epicColor", "dark_purple", 
    			"The default color of epic items. Valid values: dark_red, red, gold, yellow, dark_green, green, aqua, dark_aqua, dark_blue, blue, light_purple, dark_purple, white, gray, dark_gray, black");
    	epicColor = pepicColor.getString();
    	Property plegendaryColor = config.get("rarity colors", "5. legendaryColor", "gold", 
    			"The default color of legendary items. Valid values: dark_red, red, gold, yellow, dark_green, green, aqua, dark_aqua, dark_blue, blue, light_purple, dark_purple, white, gray, dark_gray, black");
    	legendaryColor = plegendaryColor.getString();
    	if (config.hasChanged()) {
    		config.save();
    	}
    }
    
    public static void setupandload(FMLPreInitializationEvent event) {
    	config.load();
    	Main.load(config);
    }
    
    @EventHandler
    public void preinit(FMLPreInitializationEvent event){
    	Main.setupandload(event);
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event){
    	MinecraftForge.EVENT_BUS.register(Pouchviewer.instance);
    	FMLCommonHandler.instance().bus().register(new ConfigChangedHandler());
    }
    
}
