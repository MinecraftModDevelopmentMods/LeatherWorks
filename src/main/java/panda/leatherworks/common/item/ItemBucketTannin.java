package panda.leatherworks.common.item;

import javax.annotation.Nullable;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import panda.leatherworks.init.LWBlocks;

public class ItemBucketTannin extends ItemBucket {

	public ItemBucketTannin() {
		super(LWBlocks.TANNIN);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn){

		RayTraceResult raytraceresult = this.rayTrace(worldIn, playerIn, false);
		ItemStack itemStackIn = playerIn.getHeldItem(handIn);
		if (raytraceresult == null) {
			return new ActionResult<>(EnumActionResult.PASS, itemStackIn);
		} else if (raytraceresult.typeOfHit != RayTraceResult.Type.BLOCK) {
			return new ActionResult<>(EnumActionResult.PASS, itemStackIn);
		} else {
			BlockPos blockpos = raytraceresult.getBlockPos();

			if (!worldIn.isBlockModifiable(playerIn, blockpos)) {
				return new ActionResult<>(EnumActionResult.FAIL, itemStackIn);
			}

			else {
				boolean flag1 = worldIn.getBlockState(blockpos).getBlock().isReplaceable(worldIn, blockpos);
				BlockPos blockpos1 = flag1 && raytraceresult.sideHit == EnumFacing.UP ? blockpos
						: blockpos.offset(raytraceresult.sideHit);

				if (!playerIn.canPlayerEdit(blockpos1, raytraceresult.sideHit, itemStackIn)) {
					return new ActionResult<>(EnumActionResult.FAIL, itemStackIn);
				} else if (this.tryPlaceContainedLiquid(playerIn, worldIn, blockpos1)) {
					playerIn.addStat(StatList.getObjectUseStats(this));
					return !playerIn.capabilities.isCreativeMode
							? new ActionResult<>(EnumActionResult.SUCCESS, new ItemStack(Items.BUCKET))
							: new ActionResult<>(EnumActionResult.SUCCESS, itemStackIn);
				} else {
					return new ActionResult<>(EnumActionResult.FAIL, itemStackIn);
				}
			}
		}
	}

	@Override
	public boolean tryPlaceContainedLiquid(@Nullable EntityPlayer worldIn, World pos, BlockPos posIn) {

		IBlockState iblockstate = pos.getBlockState(posIn);
		Material material = iblockstate.getMaterial();
		boolean flag = !material.isSolid();
		boolean flag1 = iblockstate.getBlock().isReplaceable(pos, posIn);

		if (!pos.isAirBlock(posIn) && !flag && !flag1) {
			return false;
		} else {

			if (!pos.isRemote && (flag || flag1) && !material.isLiquid()) {
				pos.destroyBlock(posIn, true);
			}

			pos.playSound(worldIn, posIn, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
			pos.setBlockState(posIn, LWBlocks.TANNIN.getDefaultState(), 11);

			return true;
		}

	}

}
