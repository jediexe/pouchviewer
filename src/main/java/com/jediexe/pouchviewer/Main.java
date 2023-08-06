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
    public static final String VERSION = "2.4.2";
    
    public static Configuration config = new Configuration(new File("config/lotrpouchviewer.cfg"));
    public static List<ConfigCategory> Categories;
        
    static { 
    	Categories = new ArrayList<>();
	} 
    
    public static String makeCategory(String name) {
		ConfigCategory c = config.getCategory(name);
		c.setLanguageKey(Main.MODID + ".config." + name);
		Categories.add(c);
		return name;
	}
	
	public static boolean showEmptySlots;
    public static boolean showOwned;
    public static boolean complicatedOwner;
    public static boolean showDyed;
    public static boolean usePouchColor;
    public static String CATEGORY_VISUAL = Main.makeCategory("visual");
    public static List<IConfigElement> getConfigElements() {
		ArrayList<IConfigElement> list = new ArrayList<>();
		for (ConfigCategory c : Categories) {
			ConfigElement ce = new ConfigElement(c);
			list.add(ce);
		}
		return list;
	}
    
    public static void load(Configuration config) {
    	Property pshowEmptySlots = config.get(CATEGORY_VISUAL, "showEmptySlots", true, "Set to true use add gaps between items where an empty slot is in the pouch");
    	showEmptySlots = pshowEmptySlots.getBoolean();
    	Property pshowOwned = config.get(CATEGORY_VISUAL, "showOwned", false, "Set to true to keep the 'Belonged to:' text in the tooltip");
    	showOwned = pshowOwned.getBoolean();
    	Property pcomplicatedOwner = config.get(CATEGORY_VISUAL, "complicatedOwner", false, "Set to true to use the actual name of the previous owner in the visual tooltip (risk of text overflow)");
    	complicatedOwner = pcomplicatedOwner.getBoolean();
    	Property pshowDyed = config.get(CATEGORY_VISUAL, "showDyed", false, "Set to true to keep the 'Dyed' text in the tooltip");
    	showDyed = pshowDyed.getBoolean();
    	Property pusePouchColor = config.get(CATEGORY_VISUAL, "usePouchColor", true, "Set to true to use the pouch color as the tooltip background color");
    	usePouchColor = pusePouchColor.getBoolean();
    	if (config.hasChanged()) config.save();
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
    	FMLCommonHandler.instance().bus().register(new ConfigChangedHandler());
    	MinecraftForge.EVENT_BUS.register(Pouchviewer.instance);
    }
    
}