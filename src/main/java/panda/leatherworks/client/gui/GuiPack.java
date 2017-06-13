package panda.leatherworks.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import panda.leatherworks.common.inventory.ContainerPack;

@SideOnly(Side.CLIENT)
public class GuiPack extends GuiContainer {
	private static ResourceLocation PACK_GUI_TEXTURES = new ResourceLocation("leatherworks:textures/gui/container/pack.png");


	private ItemStack item;

	public GuiPack(InventoryPlayer playerInv, World worldIn, ItemStack Packitem) {
		this(playerInv, worldIn, BlockPos.ORIGIN, Packitem);
		
	}

	@Override
	public void initGui() {
		super.initGui();
	}

	public GuiPack(InventoryPlayer playerInv, World worldIn, BlockPos blockPosition, ItemStack Packitem) {
		super(new ContainerPack(playerInv, worldIn, blockPosition,Packitem));
		this.xSize = 176;
		this.ySize = 206;
		this.guiLeft = Math.abs((this.width - this.xSize) / 2);
		this.guiTop = Math.abs((this.height - this.ySize) / 2);
		this.item = Packitem;
		setGuiTexture(this.item);
	}

	private void setGuiTexture(ItemStack item) {
		PACK_GUI_TEXTURES = new ResourceLocation("leatherworks:textures/gui/container/pack_"+item.getItemDamage()+".png");
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
	}

	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		this.fontRendererObj.drawString(this.item.getDisplayName(), 50, 6, 12963509);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 94, 4210752);
	}

	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(PACK_GUI_TEXTURES);
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	}
	
}