package com.jediexe.pouchviewer;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.IConfigElement;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;

public class PouchviewerGuiConfig extends GuiConfig {
	
	public PouchviewerGuiConfig(GuiScreen parentScreen) {
		super(parentScreen, 
				Main.getConfigElements(), Main.MODID, 
				false, false, GuiConfig.getAbridgedConfigPath(Main.config.toString()));
	}
	
}