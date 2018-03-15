package panda.leatherworks.common.eventhandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import panda.leatherworks.ConfigLeatherWorks;
import panda.leatherworks.LeatherWorks;
import panda.leatherworks.client.gui.GuiPack;
import panda.leatherworks.common.item.armor.ItemBrokenArmor;

public class TooltipEventHandler {
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void tooltip(ItemTooltipEvent event) {
		if (Minecraft.getMinecraft().currentScreen instanceof GuiPack &&
				ConfigLeatherWorks.isBlacklistedFromBag(event.getItemStack())) {
			event.getToolTip().add(TextFormatting.RED + I18n.format("leatherworks.item_not_allowed.tooltip"));
		}else
		if(event.getItemStack().getItem() instanceof ItemBrokenArmor){
			//event.getToolTip().remove(1); //iffy?
			
			String key = I18n.format("item.modifiers.feet");
			
			if(event.getToolTip().contains(key)){
				event.getToolTip().remove(key);
			}
			key = I18n.format("item.modifiers.legs");
			if(event.getToolTip().contains(key)){
				event.getToolTip().remove(key);
			}
			key = I18n.format("item.modifiers.chest");
			if(event.getToolTip().contains(key)){
				event.getToolTip().remove(key);
			}
			key = I18n.format("item.modifiers.head");
			if(event.getToolTip().contains(key)){
				event.getToolTip().remove(key);
			}
		}
	}
}
