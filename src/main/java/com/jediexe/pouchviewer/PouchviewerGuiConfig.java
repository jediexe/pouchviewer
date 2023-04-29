package com.jediexe.pouchviewer;

import cpw.mods.fml.client.config.GuiConfig;
import net.minecraft.client.gui.GuiScreen;

public class PouchviewerGuiConfig extends GuiConfig {
	
	public PouchviewerGuiConfig(GuiScreen parentScreen) {
		super(parentScreen, 
				Main.getConfigElements(), Main.MODID, 
				false, false, GuiConfig.getAbridgedConfigPath(Main.config.toString()));
	}
	
}