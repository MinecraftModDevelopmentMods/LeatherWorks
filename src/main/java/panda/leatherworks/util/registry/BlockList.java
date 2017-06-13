package panda.leatherworks.util.registry;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPane;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraftforge.fluids.Fluid;
import panda.leatherworks.blocks.BlockBarrel;
import panda.leatherworks.blocks.BlockDebarkedLog;
import panda.leatherworks.blocks.BlockEmpty;
import panda.leatherworks.blocks.BlockRackTest;
import panda.leatherworks.blocks.BlockTanninFluid;
import panda.leatherworks.blocks.TanninFluid;

public final class BlockList {

	/*
	 * Declare blocks here and then subsequently add them to getList(); They
	 * will be automatically moved to registration and model loading.
	 */

	public static final Fluid TANNIN_FLUID = new TanninFluid();
	public static final Block TANNIN = new BlockTanninFluid(TANNIN_FLUID);
	
	public static final Block DEBARKED_LOG_OAK = new BlockDebarkedLog("oak");
	public static final Block DEBARKED_LOG_ACACIA = new BlockDebarkedLog("acacia");
	public static final Block DEBARKED_LOG_BIRCH = new BlockDebarkedLog("birch");
	public static final Block DEBARKED_LOG_SPRUCE = new BlockDebarkedLog("spruce");
	public static final Block DEBARKED_LOG_DARKOAK = new BlockDebarkedLog("darkoak");
	public static final Block DEBARKED_LOG_JUNGLE = new BlockDebarkedLog("jungle");
	public static final Block BARREL = new BlockBarrel();
	public static final Block RACK = new BlockRackTest();
	public static final Block BARREL_SEALED = new BlockEmpty();
	
	public static List<Block> getList() {
		List<Block> list = new ArrayList<Block>();
		list.add(DEBARKED_LOG_OAK);
		list.add(DEBARKED_LOG_SPRUCE);
		list.add(DEBARKED_LOG_BIRCH);
		list.add(DEBARKED_LOG_JUNGLE);
		list.add(DEBARKED_LOG_ACACIA);
		list.add(DEBARKED_LOG_DARKOAK);
		list.add(TANNIN);
		list.add(BARREL);
		list.add(BARREL_SEALED);
		list.add(RACK);
		
		return list;
	}

}
