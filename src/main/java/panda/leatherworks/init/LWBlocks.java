package panda.leatherworks.init;

import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.IForgeRegistry;
import panda.leatherworks.LeatherWorks;
import panda.leatherworks.common.block.BlockBarrel;
import panda.leatherworks.common.block.BlockDebarkedLog;
import panda.leatherworks.common.block.BlockEmpty;
import panda.leatherworks.common.block.BlockDryingRack;
import panda.leatherworks.common.block.BlockTanninFluid;

@EventBusSubscriber
public final class LWBlocks {

	public static final Block TANNIN = simply(new BlockTanninFluid(LWFluids.TANNIN), "tannin");
	public static final Block DEBARKED_LOG_OAK = simply(new BlockDebarkedLog("oak"), "debarked_log_oak").setCreativeTab(LeatherWorks.LeatherTab);
	public static final Block DEBARKED_LOG_ACACIA = simply(new BlockDebarkedLog("acacia"), "debarked_log_acacia").setCreativeTab(LeatherWorks.LeatherTab);
	public static final Block DEBARKED_LOG_BIRCH = simply(new BlockDebarkedLog("birch"), "debarked_log_birch").setCreativeTab(LeatherWorks.LeatherTab);
	public static final Block DEBARKED_LOG_SPRUCE = simply(new BlockDebarkedLog("spruce"), "debarked_log_spruce").setCreativeTab(LeatherWorks.LeatherTab);
	public static final Block DEBARKED_LOG_DARKOAK = simply(new BlockDebarkedLog("darkoak"), "debarked_log_darkoak").setCreativeTab(LeatherWorks.LeatherTab);
	public static final Block DEBARKED_LOG_JUNGLE = simply(new BlockDebarkedLog("jungle"), "debarked_log_jungle").setCreativeTab(LeatherWorks.LeatherTab);
	public static final Block BARREL = simply(new BlockBarrel(), "barrel").setCreativeTab(LeatherWorks.LeatherTab);
	public static final Block DRYING_RACK = simply(new BlockDryingRack(),"drying_rack").setCreativeTab(LeatherWorks.LeatherTab);
	public static final Block SEALED_BARREL = simply(new BlockEmpty(), "sealed_barrel").setCreativeTab(LeatherWorks.LeatherTab);

	private static Block simply(Block block, String name) {
		return block.setRegistryName(LeatherWorks.MODID, name).setUnlocalizedName(LeatherWorks.MODID + "." + name);
	}

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
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
