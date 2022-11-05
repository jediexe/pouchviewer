package com.jediexe.pouchviewer;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = Main.MODID, version = Main.VERSION)

public class Main{
	
    public static final String MODID = "pouchviewer";
    public static final String VERSION = "1.5";
    
    public static boolean showOwned;
    public static boolean showDyed;
    
    public void initConfiguration(FMLInitializationEvent event) {
    	Configuration config = new Configuration(new File("config/pouchviewer.cfg"));
    	config.load();
    	showOwned = config.getBoolean("showOwned", "config", false, "Set to true to keep the 'Belonged to:' text in the tooltip");
    	showDyed = config.getBoolean("showDyed", "config", false, "Set to true to keep the 'Dyed' text in the tooltip");
    	config.save();
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event){
    	this.initConfiguration(event);
    	MinecraftForge.EVENT_BUS.register(Pouchviewer.instance);
    }
    
}
