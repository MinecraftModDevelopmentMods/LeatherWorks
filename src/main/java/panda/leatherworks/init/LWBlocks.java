package panda.leatherworks.init;

import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import panda.leatherworks.LeatherWorks;
import panda.leatherworks.common.block.BlockBarrel;
import panda.leatherworks.common.block.BlockDebarkedLog;
import panda.leatherworks.common.block.BlockDryingRack;
import panda.leatherworks.common.block.BlockDummyWoodPillar;
import panda.leatherworks.common.block.BlockTanninFluid;
import panda.leatherworks.common.block.TanninFluid;

@EventBusSubscriber
public final class LWBlocks {
	
	private LWBlocks(){LeatherWorks.logger.info("Registering Blocks");}
	
	public static final Fluid TANNIN_FLUID = new TanninFluid();
	public static final Block TANNIN = simply(new BlockTanninFluid(TANNIN_FLUID), "tannin");
	public static final Block DEBARKED_LOG_OAK = simply(new BlockDebarkedLog(), "debarked_log_oak");
	public static final Block DEBARKED_LOG_ACACIA = simply(new BlockDebarkedLog(), "debarked_log_acacia");
	public static final Block DEBARKED_LOG_BIRCH = simply(new BlockDebarkedLog(), "debarked_log_birch");
	public static final Block DEBARKED_LOG_SPRUCE = simply(new BlockDebarkedLog(), "debarked_log_spruce");
	public static final Block DEBARKED_LOG_DARKOAK = simply(new BlockDebarkedLog(), "debarked_log_darkoak");
	public static final Block DEBARKED_LOG_JUNGLE = simply(new BlockDebarkedLog(), "debarked_log_jungle");
	public static final Block BARREL = simply(new BlockBarrel(), "barrel");
	public static final Block DRYING_RACK = simply(new BlockDryingRack(),"drying_rack");
	public static final Block SEALED_BARREL = simply(new BlockDummyWoodPillar(), "sealed_barrel");

	private static Block simply(Block block, String name) {
		return block.setRegistryName(LeatherWorks.MODID, name).setUnlocalizedName(LeatherWorks.MODID + "." + name).setCreativeTab(LeatherWorks.LeatherTab);
	}

	@SubscribeEvent
	public static void register(RegistryEvent.Register<Block> event) {
		IForgeRegistry<Block> registry = event.getRegistry();

		registry.register(TANNIN);
		registry.register(DEBARKED_LOG_OAK);
		registry.register(DEBARKED_LOG_ACACIA);
		registry.register(DEBARKED_LOG_BIRCH);
		registry.register(DEBARKED_LOG_SPRUCE);
		registry.register(DEBARKED_LOG_DARKOAK);
		registry.register(DEBARKED_LOG_JUNGLE);
		registry.register(BARREL);
		registry.register(DRYING_RACK);
		registry.register(SEALED_BARREL);
	}
}
