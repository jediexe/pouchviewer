package com.jediexe.pouchviewer;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ConfigChangedHandler {
	
	@SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
    	if (Main.MODID.equals(event.modID)){
			Main.load(Main.config);
    	}
	}

}