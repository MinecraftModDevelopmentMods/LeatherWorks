package panda.leatherworks.client.gui;

import java.awt.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;
import panda.leatherworks.common.InventoryTrunk;
import panda.leatherworks.common.tileentity.TileEntityTrunk;

@SideOnly(Side.CLIENT)
public class GuiTrunk extends GuiContainer {

    protected static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
    		new ResourceLocation("leatherworks:textures/gui/container/trunk_normal.png"),
    		new ResourceLocation("leatherworks:textures/gui/container/trunk_orange.png"),
    		new ResourceLocation("leatherworks:textures/gui/container/trunk_magenta.png"),
    		new ResourceLocation("leatherworks:textures/gui/container/trunk_light_blue.png"),
    		new ResourceLocation("leatherworks:textures/gui/container/trunk_yellow.png"),
    		new ResourceLocation("leatherworks:textures/gui/container/trunk_lime.png"),
    		new ResourceLocation("leatherworks:textures/gui/container/trunk_pink.png"),
    		new ResourceLocation("leatherworks:textures/gui/container/trunk_gray.png"),
    		new ResourceLocation("leatherworks:textures/gui/container/trunk_silver.png"),
    		new ResourceLocation("leatherworks:textures/gui/container/trunk_cyan.png"),
    		new ResourceLocation("leatherworks:textures/gui/container/trunk_purple.png"),
    		new ResourceLocation("leatherworks:textures/gui/container/trunk_blue.png"),
    		new ResourceLocation("leatherworks:textures/gui/container/trunk_normal.png"),
    		new ResourceLocation("leatherworks:textures/gui/container/trunk_green.png"),
    		new ResourceLocation("leatherworks:textures/gui/container/trunk_red.png"),
    		new ResourceLocation("leatherworks:textures/gui/container/trunk_black.png")};
    
	private TileEntityTrunk tileEntity;

	public GuiTrunk(InventoryPlayer invPlayer, TileEntityTrunk tile) {
		super(new InventoryTrunk(invPlayer, tile));
		tileEntity = tile;
		xSize = 176;
		ySize = 188;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int x, int y) {

		Minecraft.getMinecraft().getTextureManager().bindTexture(TEXTURES[tileEntity.getColor().getMetadata()]);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		fontRenderer.drawString(tileEntity.getDisplayName().getUnformattedText(), 8, 6, Color.LIGHT_GRAY.getRGB());
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}
}