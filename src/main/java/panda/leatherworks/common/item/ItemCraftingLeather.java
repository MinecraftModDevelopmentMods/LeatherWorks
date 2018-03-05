package panda.leatherworks.common.item;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import panda.leatherworks.init.LWItems;


public class ItemCraftingLeather extends Item {

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	{
		ItemStack itemStackIn = playerIn.getHeldItem(handIn);
		if(!itemStackIn.isEmpty() && itemStackIn.getItem() == LWItems.LEATHER_SCRAPED){
			RayTraceResult raytraceresult = this.rayTrace(worldIn, playerIn, true);

			if (raytraceresult != null && raytraceresult.typeOfHit == RayTraceResult.Type.BLOCK){

				BlockPos blockpos = raytraceresult.getBlockPos();

				if (!worldIn.isBlockModifiable(playerIn, blockpos) || !playerIn.canPlayerEdit(blockpos.offset(raytraceresult.sideHit), raytraceresult.sideHit, itemStackIn))
				{
					return new ActionResult<>(EnumActionResult.PASS, itemStackIn);
				}

				if (worldIn.getBlockState(blockpos).getMaterial() == Material.WATER)
				{
					worldIn.playSound(playerIn, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_BOBBER_SPLASH, SoundCategory.NEUTRAL, 1.0F, (float) (1.0F + (worldIn.rand.nextGaussian()) * 0.4F));
					BlockPos clickPos = raytraceresult.getBlockPos().offset(raytraceresult.sideHit);
					//doWaterSplashEffect(worldIn,clickPos);
					ItemStack itemStackout = new ItemStack(LWItems.LEATHER_WASHED,itemStackIn.getCount(),itemStackIn.getMetadata(),itemStackIn.getTagCompound());
					return new ActionResult<>(EnumActionResult.SUCCESS, itemStackout);
				}
			}
		}
		return new ActionResult<>(EnumActionResult.PASS, itemStackIn);
	}

}
