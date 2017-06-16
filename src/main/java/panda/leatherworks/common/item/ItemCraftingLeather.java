package panda.leatherworks.common.item;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import panda.leatherworks.util.IMeta;

public class ItemCraftingLeather extends ItemBase implements IMeta{

	public ItemCraftingLeather() {
		super("craftingleather");
		this.setHasSubtypes(true);

	}

	@Override
	public int getMaxMeta() {
		return 3;
	}

	@Override
	public List<ModelResourceLocation> getMetaModelLocations(List<ModelResourceLocation> map) {
		for(int i = 0; i<getMaxMeta();i++){
			map.add(new ModelResourceLocation(getRegistryName(), "meta="+i));
		}
		return map;
	}
	
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item itemIn, CreativeTabs tab, List list) {
		for(int i = 0; i<getMaxMeta();i++){
			list.add(new ItemStack(itemIn, 1, i));
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		int metadata = stack.getMetadata();
		switch(metadata){
		case 0:
			return super.getUnlocalizedName() + ".scraped";
		case 1:
			return super.getUnlocalizedName() + ".washed";
		case 2:
			return super.getUnlocalizedName() + ".soaked";
		}
		return super.getUnlocalizedName();
	}
	
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand)
    {
		if(itemStackIn.getItemDamage() == 0){
            RayTraceResult raytraceresult = this.rayTrace(worldIn, playerIn, true);

            if (raytraceresult == null)
            {
                return new ActionResult<>(EnumActionResult.PASS, itemStackIn);
            }
            else
            {
                if (raytraceresult.typeOfHit == RayTraceResult.Type.BLOCK)
                {
                    BlockPos blockpos = raytraceresult.getBlockPos();

                    if (!worldIn.isBlockModifiable(playerIn, blockpos) || !playerIn.canPlayerEdit(blockpos.offset(raytraceresult.sideHit), raytraceresult.sideHit, itemStackIn))
                    {
                        return new ActionResult<>(EnumActionResult.PASS, itemStackIn);
                    }

                    if (worldIn.getBlockState(blockpos).getMaterial() == Material.WATER)
                    {
                        worldIn.playSound(playerIn, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.NEUTRAL, 1.0F, 1.0F);
                        //worldIn.spawnParticle(particleType, ignoreRange, xCoord, yCoord, zCoord, xSpeed, ySpeed, zSpeed, parameters);
                       //TODO particles
                        itemStackIn.setItemDamage(1);
                        return new ActionResult<>(EnumActionResult.SUCCESS, itemStackIn);
                    }
                }

                return new ActionResult<>(EnumActionResult.PASS, itemStackIn);
            }
		}
		return new ActionResult<>(EnumActionResult.PASS, itemStackIn);
    }

}
