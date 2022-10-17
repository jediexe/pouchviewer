package com.jediexe.pouchviewer;

import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = Main.MODID, version = Main.VERSION)

public class Main{
	
    public static final String MODID = "pouchviewer";
    public static final String VERSION = "1.0";
    
    @EventHandler
    public void init(FMLInitializationEvent event){
    	MinecraftForge.EVENT_BUS.register(Draw.instance);
    }
    
}
