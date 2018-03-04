package panda.leatherworks.common.eventhandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import panda.leatherworks.ConfigLeatherWorks;
import panda.leatherworks.client.gui.GuiPack;

public class TooltipEventHandler {
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void tooltip(ItemTooltipEvent event) {
		if (Minecraft.getMinecraft().currentScreen instanceof GuiPack &&
				ConfigLeatherWorks.isBlacklistedFromBag(event.getItemStack())) {
			event.getToolTip().add(TextFormatting.RED + I18n.format("leatherworks.item_not_allowed.tooltip"));
		}
	}
}
