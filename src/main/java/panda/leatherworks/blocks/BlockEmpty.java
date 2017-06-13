package panda.leatherworks.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import panda.leatherworks.LeatherWorks;

public class BlockEmpty extends BlockRotatedPillar{

	public BlockEmpty() {
		super(Material.WOOD);
		Blocks.FIRE.setFireInfo(this, 5, 5);
    	this.blockHardness = 3;
		this.blockResistance = 2;
		this.setHarvestLevel("axe", 1);
		this.setCreativeTab(LeatherWorks.LeatherTab);
		this.setRegistryName("sealed_barrel");
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
	{
		list.add("For Decoration");
		
	}

}
