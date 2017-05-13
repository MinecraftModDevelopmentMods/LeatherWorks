package panda.leatherworks.util;

import org.lwjgl.opengl.GL11;

import panda.leatherworks.blocks.BlockRackTest;
import panda.leatherworks.entity.TileEntityItemRack;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.ForgeHooksClient;

public class TESRRack extends TileEntitySpecialRenderer<TileEntityItemRack> {
	@Override
	public void renderTileEntityAt(TileEntityItemRack te, double x, double y, double z, float partialTicks, int destroyStage) {
		ItemStack stack = te.inventory.getStackInSlot(0);
		//System.out.println(stack);
		if (stack != null) {
			GlStateManager.enableRescaleNormal();
			GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1f);
			GlStateManager.enableBlend();
			RenderHelper.enableStandardItemLighting();
			GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
			GlStateManager.pushMatrix();
			GlStateManager.translate(x + 0.5, y + 0.5 , z + 0.5);
			IBlockState blockstate = te.getWorld().getBlockState(te.getPos());
			if(blockstate.getBlock() instanceof BlockRackTest){
				int meta = blockstate.getBlock().getMetaFromState(blockstate);
				//System.out.println(meta);
				switch(meta){
				case 0:
				case 5:
					GlStateManager.translate(-.375, +.25 , 0);
				case 1:
					GlStateManager.rotate(180, 0.0F, 1.0F, 0.0F);
				case 2:
					GlStateManager.rotate(-90, 0.0F, 1.0F, 0.0F);
				case 3:
					GlStateManager.translate(0, 0 , .75);
				case 4:
					GlStateManager.translate(0, -.1875 , -.375);
				
				default:
				}
			}
			IBakedModel model = Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(stack, te.getWorld(), null);
			model = ForgeHooksClient.handleCameraTransforms(model, TransformType.FIXED, false);

			Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
			Minecraft.getMinecraft().getRenderItem().renderItem(stack, model);

			GlStateManager.popMatrix();
			GlStateManager.disableRescaleNormal();
			GlStateManager.disableBlend();
		}
	}
	}
