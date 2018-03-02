package panda.leatherworks.common.block;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockDummyWoodPillar extends BlockRotatedPillar{

	public BlockDummyWoodPillar() {
		super(Material.WOOD);
		Blocks.FIRE.setFireInfo(this, 5, 5);
    	this.blockHardness = 3;
		this.blockResistance = 2;
		this.setHarvestLevel("axe", 1);
	}
	
	@Override
	public void addInformation(ItemStack stack, @Nullable World player, List<String> list, ITooltipFlag advanced)
	{
		list.add("For Decoration");
	}
}
