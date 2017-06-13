package panda.leatherworks.util.registry;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraftforge.fluids.Fluid;
import panda.leatherworks.common.block.BlockBarrel;
import panda.leatherworks.common.block.BlockDebarkedLog;
import panda.leatherworks.common.block.BlockEmpty;
import panda.leatherworks.common.block.BlockRackTest;
import panda.leatherworks.common.block.BlockTanninFluid;
import panda.leatherworks.common.block.TanninFluid;

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
