package panda.leatherworks.init;

import net.minecraft.block.Block;
import net.minecraft.item.EnumDyeColor;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import panda.leatherworks.ConfigLeatherWorks;
import panda.leatherworks.LeatherWorks;
import panda.leatherworks.common.block.BlockBarrel;
import panda.leatherworks.common.block.BlockDebarkedLog;
import panda.leatherworks.common.block.BlockDryingRack;
import panda.leatherworks.common.block.BlockDummyWood;
import panda.leatherworks.common.block.BlockDummyWoodPillar;
import panda.leatherworks.common.block.BlockTanninFluid;
import panda.leatherworks.common.block.BlockTrunk;
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
	
	public static final Block SEALED_BARREL_OAK = simply(new BlockDummyWoodPillar(), "sealed_barrel_oak");
	public static final Block BARREL_OAK = simply(new BlockBarrel(LWBlocks.SEALED_BARREL_OAK), "barrel_oak");
	
	public static final Block SEALED_BARREL_SPRUCE = simply(new BlockDummyWoodPillar(), "sealed_barrel_spruce");
	public static final Block BARREL_SPRUCE = simply(new BlockBarrel(LWBlocks.SEALED_BARREL_SPRUCE), "barrel_spruce");
	
	public static final Block SEALED_BARREL_BIRCH = simply(new BlockDummyWoodPillar(), "sealed_barrel_birch");
	public static final Block BARREL_BIRCH = simply(new BlockBarrel(LWBlocks.SEALED_BARREL_BIRCH), "barrel_birch");
	
	public static final Block SEALED_BARREL_JUNGLE = simply(new BlockDummyWoodPillar(), "sealed_barrel_jungle");
	public static final Block BARREL_JUNGLE = simply(new BlockBarrel(LWBlocks.SEALED_BARREL_JUNGLE), "barrel_jungle");
	
	public static final Block SEALED_BARREL_ACACIA = simply(new BlockDummyWoodPillar(), "sealed_barrel_acacia");
	public static final Block BARREL_ACACIA = simply(new BlockBarrel(LWBlocks.SEALED_BARREL_ACACIA), "barrel_acacia");
	
	public static final Block SEALED_BARREL_DARKOAK = simply(new BlockDummyWoodPillar(), "sealed_barrel_darkoak");
	public static final Block BARREL_DARKOAK = simply(new BlockBarrel(LWBlocks.SEALED_BARREL_DARKOAK), "barrel_darkoak");
	
	public static final Block DRYING_RACK_OAK = simply(new BlockDryingRack(),"drying_rack_oak");
	public static final Block DRYING_RACK_SPRUCE = simply(new BlockDryingRack(),"drying_rack_spruce");
	public static final Block DRYING_RACK_BIRCH = simply(new BlockDryingRack(),"drying_rack_birch");
	public static final Block DRYING_RACK_JUNGLE = simply(new BlockDryingRack(),"drying_rack_jungle");
	public static final Block DRYING_RACK_ACACIA = simply(new BlockDryingRack(),"drying_rack_acacia");
	public static final Block DRYING_RACK_DARKOAK = simply(new BlockDryingRack(),"drying_rack_darkoak");
	public static final Block LEATHER_TRUNK = simply(new BlockTrunk(EnumDyeColor.BROWN),"leather_trunk");
	public static final Block LEATHER_TRUNK_RED = simply(new BlockTrunk(EnumDyeColor.RED),"leather_trunk_red");
	public static final Block LEATHER_TRUNK_BLACK = simply(new BlockTrunk(EnumDyeColor.BLACK),"leather_trunk_black");
	public static final Block LEATHER_TRUNK_BLUE = simply(new BlockTrunk(EnumDyeColor.BLUE),"leather_trunk_blue");
	public static final Block LEATHER_TRUNK_CYAN = simply(new BlockTrunk(EnumDyeColor.CYAN),"leather_trunk_cyan");
	public static final Block LEATHER_TRUNK_GRAY = simply(new BlockTrunk(EnumDyeColor.GRAY),"leather_trunk_gray");
	public static final Block LEATHER_TRUNK_GREEN = simply(new BlockTrunk(EnumDyeColor.GREEN),"leather_trunk_green");
	public static final Block LEATHER_TRUNK_LIGHT_BLUE = simply(new BlockTrunk(EnumDyeColor.LIGHT_BLUE),"leather_trunk_light_blue");
	public static final Block LEATHER_TRUNK_LIME = simply(new BlockTrunk(EnumDyeColor.LIME),"leather_trunk_lime");
	public static final Block LEATHER_TRUNK_MAGENTA = simply(new BlockTrunk(EnumDyeColor.MAGENTA),"leather_trunk_magenta");
	public static final Block LEATHER_TRUNK_ORANGE = simply(new BlockTrunk(EnumDyeColor.ORANGE),"leather_trunk_orange");
	public static final Block LEATHER_TRUNK_PINK = simply(new BlockTrunk(EnumDyeColor.PINK),"leather_trunk_pink");
	public static final Block LEATHER_TRUNK_PURPLE = simply(new BlockTrunk(EnumDyeColor.PURPLE),"leather_trunk_purple");
	public static final Block LEATHER_TRUNK_YELLOW = simply(new BlockTrunk(EnumDyeColor.YELLOW),"leather_trunk_yellow");
	public static final Block LEATHER_TRUNK_SILVER = simply(new BlockTrunk(EnumDyeColor.SILVER),"leather_trunk_silver");
	
	//I know they are currently in the game as meta blocks but this way I can have proper lang files.
	public static final Block BARK_OAK = simply(new BlockDummyWood(),"oak_bark");
	public static final Block BARK_SPRUCE = simply(new BlockDummyWood(),"spruce_bark");
	public static final Block BARK_BIRCH = simply(new BlockDummyWood(),"birch_bark");
	public static final Block BARK_JUNGLE = simply(new BlockDummyWood(),"jungle_bark");
	public static final Block BARK_ACACIA = simply(new BlockDummyWood(),"acacia_bark");
	public static final Block BARK_DARKOAK = simply(new BlockDummyWood(),"darkoak_bark");
	

	private static Block simply(Block block, String name) {
		return block.setRegistryName(LeatherWorks.MODID, name).setTranslationKey(LeatherWorks.MODID + "." + name).setCreativeTab(LeatherWorks.LeatherTab);
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
		registry.register(BARK_OAK);
		registry.register(BARK_SPRUCE);
		registry.register(BARK_BIRCH);
		registry.register(BARK_JUNGLE);
		registry.register(BARK_ACACIA);
		registry.register(BARK_DARKOAK);
		
		registry.register(SEALED_BARREL_OAK);
		registry.register(BARREL_OAK);
		registry.register(SEALED_BARREL_SPRUCE);
		registry.register(BARREL_SPRUCE);
		registry.register(SEALED_BARREL_BIRCH);
		registry.register(BARREL_BIRCH);
		registry.register(SEALED_BARREL_JUNGLE);
		registry.register(BARREL_JUNGLE);
		registry.register(SEALED_BARREL_ACACIA);
		registry.register(BARREL_ACACIA);
		registry.register(SEALED_BARREL_DARKOAK);
		registry.register(BARREL_DARKOAK);
		
		registry.register(DRYING_RACK_OAK);
		registry.register(DRYING_RACK_SPRUCE);
		registry.register(DRYING_RACK_BIRCH);
		registry.register(DRYING_RACK_JUNGLE);
		registry.register(DRYING_RACK_ACACIA);
		registry.register(DRYING_RACK_DARKOAK);
		if(!ConfigLeatherWorks.disableTrunk){
		registry.register(LEATHER_TRUNK);
			if(!ConfigLeatherWorks.disableColoredTrunks){
				registry.register(LEATHER_TRUNK_RED);
				registry.register(LEATHER_TRUNK_ORANGE);
				registry.register(LEATHER_TRUNK_YELLOW);
				registry.register(LEATHER_TRUNK_LIME);
				registry.register(LEATHER_TRUNK_GREEN);
				registry.register(LEATHER_TRUNK_CYAN);
				registry.register(LEATHER_TRUNK_LIGHT_BLUE);
				registry.register(LEATHER_TRUNK_BLUE);
				registry.register(LEATHER_TRUNK_PURPLE);
				registry.register(LEATHER_TRUNK_MAGENTA);
				registry.register(LEATHER_TRUNK_PINK);
				registry.register(LEATHER_TRUNK_GRAY);
				registry.register(LEATHER_TRUNK_SILVER);
				registry.register(LEATHER_TRUNK_BLACK);
			}
		}
		
	}
}
