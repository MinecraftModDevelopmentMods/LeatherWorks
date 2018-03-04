package panda.leatherworks.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import panda.leatherworks.common.ContainerPack;
//This code modified from
//https://github.com/Shadows-of-Fire/EnderBags/blob/master/src/main/java/gigabit101/EnderBags/gui/GuiEnderBag.java
@SideOnly(Side.CLIENT)
public class GuiPack extends GuiContainer {
	
	public EntityPlayer player;
	private ItemStack item;

	public GuiPack(EntityPlayer player,ItemStack packstack) {
		super(new ContainerPack(player));
		this.guiLeft = Math.abs((this.width - this.xSize) / 2);
		this.guiTop = Math.abs((this.height - this.ySize) / 2);
		this.player = player;
		this.item = packstack;
		this.xSize = 176;
		this.ySize = 206;
	}

	@Override
	public void updateScreen() {
		if(!inventorySlots.canInteractWith(player)) player.closeScreen();
		super.updateScreen();
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		this.fontRenderer.drawString(this.item.getDisplayName(), 50, 6, 12963509);
		this.fontRenderer.drawString(I18n.format("container.inventory"), 8, this.ySize - 94, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		
		this.mc.getTextureManager().bindTexture(new ResourceLocation("leatherworks:textures/gui/container/pack_"+getNumFromPack(item)+".png"));
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}

	private int getNumFromPack(ItemStack stack) {

		return 0;
	}
	
}