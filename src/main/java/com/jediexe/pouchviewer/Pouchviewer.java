package com.jediexe.pouchviewer;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import lotr.common.item.LOTRItemPouch;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

public class Pouchviewer {
	
	public static Pouchviewer instance = new Pouchviewer();
	public static RenderItem renderItem = new RenderItem();
	static int count;
	static int usedslots;
	static ItemStack pouchitem = null;
	static LOTRItemPouch pouch = null;
	static List itemslist;
	static List<String> tooltiplist;
	static NBTTagList itemlist;
	GuiContainer Agui = null;
	static ResourceLocation pouchtexture = new ResourceLocation("pouchviewer:pouch.png");

	@SubscribeEvent
    public void onTooltipGen(ItemTooltipEvent event) {
		if (!Main.showOwned && event.toolTip.toString().contains("Belonged to:")) {
			int size = event.toolTip.size();
			int z = size-1;
			while (event.toolTip.toString().contains("Belonged to: ")) {
				event.toolTip.remove(z);
				z-=1;
			}
			event.toolTip.remove(event.toolTip.size()-1);
		}
		if (!Main.showDyed && event.toolTip.contains("Dyed")) event.toolTip.remove("Dyed");
		pouchitem=null;
		if(event.itemStack.hasTagCompound() && event.itemStack.getTagCompound().hasKey("LOTRPouchData")){
			NBTTagCompound nbt = event.itemStack.getTagCompound().getCompoundTag("LOTRPouchData");
			NBTTagList items = nbt.getTagList("Items", 10);
			if(items.tagCount()>0 && event.toolTip!=null){
				ArrayList<NBTTagCompound> list = new ArrayList<NBTTagCompound>();
				itemlist = items;
				count = LOTRItemPouch.getCapacity(event.itemStack);
				int empty = 0;
				for (int j=0; j<count; j++) list.add(j, itemlist.getCompoundTagAt(j));
				for (int y=0; y<count; y++) {
					if (!list.get(y).toString().contains("Slot:"+y+"b")) {
						list.add(y, null);
						empty+=1;
					}
				}
				itemslist = list;
				usedslots = count-empty;
				pouch = (LOTRItemPouch)event.itemStack.getItem();
				pouchitem = event.itemStack;
				int size = event.toolTip.size();
				int z = size-1;
				while (event.toolTip.size()>1){
					event.toolTip.remove(z);
					z-=1;
				}
				event.toolTip.remove(event.toolTip.size()-1);
				event.toolTip.add(" ");
				event.toolTip.add("                                        ");
			}
		}
	}
	
	@SubscribeEvent
	public void checkContainer(final GuiScreenEvent.DrawScreenEvent.Post event) {
		if (event.gui instanceof GuiContainer) Agui = (GuiContainer)event.gui;
		else Agui = null;
		if (Agui!=null) {
			Slot slot = null;
			for (int i = 0; i < Agui.inventorySlots.inventorySlots.size(); i++) {
				slot = Agui.inventorySlots.getSlot(i);
				if (slot!=null) {
					if (slot.getStack()!=null) {
						if (slot.getStack().getItem() instanceof LOTRItemPouch && Minecraft.getMinecraft().thePlayer.inventory.getItemStack()==null && slot.getStack() == pouchitem && pouchitem!=null) {
							if (slot.getStack()==pouchitem) {
								draw();
								pouchitem=null;
							}
						}
					}
				}
			}
		}
	}
	
	public static void draw() {
		ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
		int sw = sr.getScaledWidth();
	    int sh = sr.getScaledHeight();
	    int mx = Mouse.getX() * sw / Minecraft.getMinecraft().displayWidth;
	    int my = sh - Mouse.getY() * sh / Minecraft.getMinecraft().displayHeight;
	    if (mx+172>sw) mx=mx-188;
	    if (Main.showEmptySlots) {
			if (count==9) drawBackground(mx+11,my, 7, 97, 162, 18);
			if (count==18) drawBackground(mx+11,my, 7, 97, 162, 36);
			if (count==27) drawBackground(mx+11,my, 7, 97, 162, 54);
			if (Main.showOwned && pouchitem.getTagCompound().hasKey("LOTRPrevOwnerList")) {
				if (pouchitem.getTagCompound().getTag("LOTRPrevOwnerList")!=null) {
					String[] owner = pouchitem.getTagCompound().getTag("LOTRPrevOwnerList").toString().substring(4, pouchitem.getTagCompound().getTag("LOTRPrevOwnerList").toString().length()-2).split(", the ");
					if (count==9) {
						drawBackground(mx+11,my+18, 7, 5, 176, 9);
						drawBackground(mx+4,my-2, 0, 97, 7, 32);
						drawBackground(mx+173,my-2, 169, 97, 7, 32);
						drawBackground(mx+4,my-16, 0, 0, 176, 16);
						drawBackground(mx+4,my+27, 0, 173, 176, 7);
						if (owner!=null) {
							if (Main.complicatedOwner) drawText(owner[0] + " " + owner[1], mx+11, my+20);
							else if (owner.length==2) drawText(owner[1], mx+11, my+20);
						}
					}
					if (count==18) {
						drawBackground(mx+11,my+36, 7, 5, 176, 9);
						drawBackground(mx+4,my-2, 0, 97, 7, 50);
						drawBackground(mx+173,my-2, 169, 97, 7, 50);
						drawBackground(mx+4,my-16, 0, 0, 176, 16);
						drawBackground(mx+4,my+45, 0, 173, 176, 7);
						if (owner!=null) {
							if (Main.complicatedOwner) drawText(owner[0] + " " + owner[1], mx+11, my+38);
							else if (owner.length==2) drawText(owner[1], mx+11, my+38);
						}
					}
					if (count==27) {
						drawBackground(mx+11,my+54, 7, 5, 176, 9);
						drawBackground(mx+4,my-2, 0, 97, 7, 68);
						drawBackground(mx+173,my-2, 169, 97, 7, 68);
						drawBackground(mx+4,my-16, 0, 0, 176, 16);
						drawBackground(mx+4,my+63, 0, 173, 176, 7);
						if (owner!=null) {
							if (Main.complicatedOwner) drawText(owner[0] + " " + owner[1], mx+11, my+56);
							else if (owner.length==2) drawText(owner[1], mx+11, my+56);
						}
					}
				}
			}
			else {
				if (count==9) {
					drawBackground(mx+11,my+18, 7, 5, 176, 4);
					drawBackground(mx+4,my-2, 0, 97, 7, 27);
					drawBackground(mx+173,my-2, 169, 97, 7, 27);
					drawBackground(mx+4,my-16, 0, 0, 176, 16);
					drawBackground(mx+4,my+22, 0, 173, 176, 7);
				}
				if (count==18) {
					drawBackground(mx+11,my+36, 7, 5, 176, 4);
					drawBackground(mx+4,my-2, 0, 97, 7, 45);
					drawBackground(mx+173,my-2, 169, 97, 7, 45);
					drawBackground(mx+4,my-16, 0, 0, 176, 16);
					drawBackground(mx+4,my+40, 0, 173, 176, 7);
				}
				if (count==27) {
					drawBackground(mx+11,my+54, 7, 5, 176, 9);
					drawBackground(mx+4,my-2, 0, 97, 7, 63);
					drawBackground(mx+173,my-2, 169, 97, 7, 63);
					drawBackground(mx+4,my-16, 0, 0, 176, 16);
					drawBackground(mx+4,my+58, 0, 173, 176, 7);
				}
			}
			for (int i = 0; i < count; ++i) {
				ItemStack item = null;
				if (itemslist.get(i)==null) continue;
				else {
					NBTTagCompound itemData = (NBTTagCompound) itemslist.get(i);
					item = ItemStack.loadItemStackFromNBT(itemData);
				}
				if (((i)/9)==0) renderItem(renderItem, Minecraft.getMinecraft().fontRenderer, Minecraft.getMinecraft().getTextureManager(), item, mx+12+(i*18), my);
				if ((i)/9==1) renderItem(renderItem, Minecraft.getMinecraft().fontRenderer, Minecraft.getMinecraft().getTextureManager(), item, mx+12+((i-9)*18), my+18);
				if ((i)/9==2) renderItem(renderItem, Minecraft.getMinecraft().fontRenderer, Minecraft.getMinecraft().getTextureManager(), item, mx+12+((i-18)*18), my+36);
			}
	    }
	    else {
			if (usedslots<=9) drawBackground(mx+11,my, 7, 97, 162, 18);
			if (usedslots>9) drawBackground(mx+11,my, 7, 97, 162, 36);
			if (usedslots>18) drawBackground(mx+11,my, 7, 97, 162, 54);
			if (Main.showOwned && pouchitem.getTagCompound().hasKey("LOTRPrevOwnerList")) {
				if (pouchitem.getTagCompound().getTag("LOTRPrevOwnerList")!=null) {
					String[] owner = pouchitem.getTagCompound().getTag("LOTRPrevOwnerList").toString().substring(4, pouchitem.getTagCompound().getTag("LOTRPrevOwnerList").toString().length()-2).split(", the ");
					if (usedslots>18) {
						drawBackground(mx+4,my-2, 0, 97, 7, 63);
						drawBackground(mx+173,my-2, 169, 97, 7, 63);
						drawBackground(mx+4,my-16, 0, 0, 176, 16);
						drawBackground(mx+4,my+58, 0, 173, 176, 7);
						drawBackground(mx+11,my+54, 7, 5, 176, 4);
						if (owner!=null) {
							if (Main.complicatedOwner) drawText(owner[0] + " " + owner[1], mx+11, my+56);
							else if (owner.length==2) drawText(owner[1], mx+11, my+56);
						}
					}
					else {
						if (usedslots>9) {
							drawBackground(mx+4,my-2, 0, 97, 7, 45);
							drawBackground(mx+173,my-2, 169, 97, 7, 45);
							drawBackground(mx+4,my-16, 0, 0, 176, 16);
							drawBackground(mx+4,my+40, 0, 173, 176, 7);
							drawBackground(mx+11,my+36, 7, 5, 176, 4);
							if (owner!=null) {
								if (Main.complicatedOwner) drawText(owner[0] + " " + owner[1], mx+11, my+38);
								else if (owner.length==2) drawText(owner[1], mx+11, my+38);
							}
						}
						else {
							if (usedslots<=9) {
								drawBackground(mx+4,my-2, 0, 97, 7, 27);
								drawBackground(mx+173,my-2, 169, 97, 7, 27);
								drawBackground(mx+4,my-16, 0, 0, 176, 16);
								drawBackground(mx+4,my+22, 0, 173, 176, 7);
								drawBackground(mx+11,my+18, 7, 5, 176, 4);
								if (owner!=null) {
									if (Main.complicatedOwner) drawText(owner[0] + " " + owner[1], mx+11, my+20);
									else if (owner.length==2) drawText(owner[1], mx+11, my+20);
								}
							}
						}
					}
				}
			}
			if (!Main.showOwned) {
				if (usedslots>18) {
					drawBackground(mx+11,my+54, 7, 5, 176, 9);
					drawBackground(mx+4,my-2, 0, 97, 7, 63);
					drawBackground(mx+173,my-2, 169, 97, 7, 63);
					drawBackground(mx+4,my-16, 0, 0, 176, 16);
					drawBackground(mx+4,my+58, 0, 173, 176, 7);
				}
				else {
					if (usedslots>9) {
						drawBackground(mx+11,my+36, 7, 5, 176, 4);
						drawBackground(mx+4,my-2, 0, 97, 7, 45);
						drawBackground(mx+173,my-2, 169, 97, 7, 45);
						drawBackground(mx+4,my-16, 0, 0, 176, 16);
						drawBackground(mx+4,my+40, 0, 173, 176, 7);
					}
					else {
						if (usedslots<=9) {
							drawBackground(mx+11,my+18, 7, 5, 176, 4);
							drawBackground(mx+4,my-2, 0, 97, 7, 27);
							drawBackground(mx+173,my-2, 169, 97, 7, 27);
							drawBackground(mx+4,my-16, 0, 0, 176, 16);
							drawBackground(mx+4,my+22, 0, 173, 176, 7);
						}
					}
				}
			}
			for (int i = 0; i < usedslots; ++i) {
				ItemStack item = null;
				NBTTagCompound itemData = (NBTTagCompound) itemlist.getCompoundTagAt(i);
				item = ItemStack.loadItemStackFromNBT(itemData);
				renderItem(renderItem, Minecraft.getMinecraft().fontRenderer, Minecraft.getMinecraft().getTextureManager(), item, mx+12+(i*18), my);
				if (usedslots>i && i==8) {
					my+=18;
					mx-=162;
				}
				if (usedslots>i && i==17) {
					my+=18;
					mx-=162;
				}
			}
	    }
	    if (!Main.showDyed) drawText(pouchitem.getDisplayName() + " (" + usedslots + "/" + LOTRItemPouch.getCapacity(pouchitem) + ")", mx+11, my-11);
		else {
			if (LOTRItemPouch.isPouchDyed(pouchitem)) drawText(pouchitem.getDisplayName() + " - " + I18n.format("item.lotr.pouch.dyed") + " (" + usedslots + "/" + LOTRItemPouch.getCapacity(pouchitem) + ")", mx+11, my-11);
			else drawText(pouchitem.getDisplayName() + " (" + usedslots + "/" + LOTRItemPouch.getCapacity(pouchitem) + ")", mx+11, my-11);
		}
	}
	
	public static void renderItem(final RenderItem ri, final FontRenderer fr, final TextureManager tm, final ItemStack item, final int x, final int y) {
		RenderHelper.enableGUIStandardItemLighting();
    	GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
		Block block = null;
        if (item.getItem() instanceof ItemBlock) block = Block.getBlockFromItem(item.getItem());
        if (block!=null && !block.isOpaqueCube() && block.hasTileEntity()) {
        	ri.renderItemAndEffectIntoGUI(fr, tm, item, x, y);
        	ri.renderItemOverlayIntoGUI(fr, tm, item, x, y);
    	}
        else {
    		GL11.glDisable(2929);
        	ri.renderItemAndEffectIntoGUI(fr, tm, item, x, y);
            ri.renderItemOverlayIntoGUI(fr, tm, item, x, y);
            GL11.glEnable(2929);
        }
        
        RenderHelper.disableStandardItemLighting();
    }
	
	public static void drawBackground(int x, int y, int a, int b, int aa, int bb) {
		if (Main.usePouchColor) {
			try {
				int color = LOTRItemPouch.getPouchColor(pouchitem);
				String hex = ("#" + Integer.toHexString(color));
				if (hex.length()!=6 && hex.startsWith("#ff")) hex = hex.replaceFirst("#ff", "#");
				float red = (float)Color.decode(hex).getRed();
			    float green = (float)Color.decode(hex).getGreen();
			    float blue = (float)Color.decode(hex).getBlue();
				GL11.glColor4f(red/255.0f, green/255.0f, blue/255.0f, 1.0f);
			}
			catch (Exception e){
				GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
				System.err.println(e);
			}
		}
		else GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        Minecraft.getMinecraft().renderEngine.bindTexture(pouchtexture);
        Gui gui = new Gui();
        GL11.glDisable(2929);
        gui.drawTexturedModalRect(x, y-1, a, b, aa, bb);
        GL11.glEnable(2929);
    }
	
	public static void drawText(String text, int x, int y) {
        GL11.glDisable(2929);
        Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(text, x, y, 0xffffff);
        GL11.glEnable(2929);
	}
}