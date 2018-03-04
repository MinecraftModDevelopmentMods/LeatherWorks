package panda.leatherworks.client.renderer.tileentity;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import panda.leatherworks.common.block.BlockDryingRack;
import panda.leatherworks.common.tileentity.TileEntityItemRack;
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

@SideOnly(Side.CLIENT)
public class TileEntityItemRackRenderer extends TileEntitySpecialRenderer<TileEntityItemRack> {
	@Override
	public void render(TileEntityItemRack te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		ItemStack stack = te.inventory.getStackInSlot(0);
		if (!stack.isEmpty()) {
			GlStateManager.enableRescaleNormal();
			GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1f);
			GlStateManager.enableBlend();
			RenderHelper.enableStandardItemLighting();
			GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
			GlStateManager.pushMatrix();
			GlStateManager.translate(x + 0.5, y + 0.5 , z + 0.5);
			IBlockState blockstate = te.getWorld().getBlockState(te.getPos());
			if(blockstate.getBlock() instanceof BlockDryingRack){
				int meta = blockstate.getBlock().getMetaFromState(blockstate);
				float oy = 0.15F;
				float oz =  0.375F;
				switch(meta){
				case 1:
					GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
					GlStateManager.translate(0.0, -oy , oz);
					break;
				case 2:
					GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
					GlStateManager.translate(0.0, -oy , oz);
					break;
				case 3:
					GlStateManager.translate(0.0, -oy , oz);	
					break;
				case 4:
					GlStateManager.translate(0.0, -oy , -oz);	
					break;
				default:
					GlStateManager.translate(0.0, -oy , oz);	
					break;
				}
			}
			IBakedModel model = Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(stack, te.getWorld(), null);
			model = ForgeHooksClient.handleCameraTransforms(model, TransformType.FIXED, false);

			Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
			GlStateManager.scale(0.9F, 0.9F, 0.9F);
			Minecraft.getMinecraft().getRenderItem().renderItem(stack, model);

			GlStateManager.popMatrix();
			GlStateManager.disableRescaleNormal();
			GlStateManager.disableBlend();
		}
	}
	}
