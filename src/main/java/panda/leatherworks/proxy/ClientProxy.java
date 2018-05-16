package panda.leatherworks.proxy;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import panda.leatherworks.ConfigLeatherWorks;
import panda.leatherworks.LeatherWorks;
import panda.leatherworks.client.network.messagehandler.MessageUpdateRackHandler;
import panda.leatherworks.client.renderer.tileentity.TileEntityDryingRackRenderer;
import panda.leatherworks.client.renderer.tileentity.TileEntityTrunkRenderer;
import panda.leatherworks.common.item.armor.ItemBrokenArmor;
import panda.leatherworks.common.network.MessageUpdateRack;
import panda.leatherworks.common.tileentity.TileEntityDryingRack;
import panda.leatherworks.common.tileentity.TileEntityTrunk;
import panda.leatherworks.init.LWBlocks;
import panda.leatherworks.init.LWItems;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {
	@Override
	public void registerMessageHandlers(SimpleNetworkWrapper wrapper) {
		super.registerMessageHandlers(wrapper);
		wrapper.registerMessage(new MessageUpdateRackHandler(), MessageUpdateRack.class, 0, Side.CLIENT);
	}

	@Override
	public void registerColorHandlers() {
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(
			(stack, tintIndex) -> tintIndex > 0 ? 0xFFFFFF : ((ItemBrokenArmor) stack.getItem()).getColor(stack),
			LWItems.BROKEN_LEATHER_CHESTPLATE,
			LWItems.BROKEN_LEATHER_HELMET,
			LWItems.BROKEN_LEATHER_LEGGINGS,
			LWItems.BROKEN_LEATHER_BOOTS
		);
	}

	@SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
		registerItemModel(LWItems.RAWHIDE_COW);
		registerItemModel(LWItems.RAWHIDE_HORSE);
		registerItemModel(LWItems.RAWHIDE_PIG);
		registerItemModel(LWItems.RAWHIDE_MOOSHROOM);
		registerItemModel(LWItems.RAWHIDE_WOLF);
		registerItemModel(LWItems.RAWHIDE_POLARBEAR);
		registerItemModel(LWItems.RAWHIDE_LLAMA);
		registerItemModel(LWItems.RAWHIDE_DONKEY);
		registerItemModel(LWItems.RAWHIDE_MULE);
		registerItemModel(LWItems.RAWHIDE_DEER);
		registerItemModel(LWItems.RAWHIDE_BOAR);
		
		if(!ConfigLeatherWorks.disableAllPacks){
			registerItemModel(LWItems.PACK_BROWN);
			if(!ConfigLeatherWorks.disableColoredPacks){
				registerItemModel(LWItems.PACK_RED);
				registerItemModel(LWItems.PACK_BLACK);
				registerItemModel(LWItems.PACK_BLUE); 
				registerItemModel(LWItems.PACK_CYAN); 
				registerItemModel(LWItems.PACK_GRAY); 
				registerItemModel(LWItems.PACK_GREEN);
				registerItemModel(LWItems.PACK_LIGHT_BLUE);
				registerItemModel(LWItems.PACK_LIME); 
				registerItemModel(LWItems.PACK_MAGENTA);
				registerItemModel(LWItems.PACK_ORANGE);
				registerItemModel(LWItems.PACK_PINK); 
				registerItemModel(LWItems.PACK_PURPLE);
				registerItemModel(LWItems.PACK_YELLOW);
				registerItemModel(LWItems.PACK_SILVER);
			}
			if(!ConfigLeatherWorks.disableEnderPack){
				registerItemModel(LWItems.ENDER_PACK);
			}
		}
		registerItemModel(LWItems.BARK_OAK);
		registerItemModel(LWItems.BARK_ACACIA);
		registerItemModel(LWItems.BARK_BIRCH);
		registerItemModel(LWItems.BARK_DARKOAK);
		registerItemModel(LWItems.BARK_JUNGLE);
		registerItemModel(LWItems.BARK_SPRUCE);
		
		registerItemModel(LWItems.TANNIN_BOTTLE);
		registerItemModel(LWItems.TANNIN_BALL);
		registerItemModel(LWItems.REPAIR_KIT);
		registerItemModel(LWItems.TANNIN_BUCKET);
		registerItemModel(LWItems.LEATHER_SOAKED);
		registerItemModel(LWItems.LEATHER_SCRAPED);
		registerItemModel(LWItems.LEATHER_WASHED);
		
		registerItemModel(LWItems.LEATHER_STRIP);
		registerItemModel(LWItems.LEATHER_SHEET);
		registerItemModel(LWItems.BROKEN_LEATHER_HELMET);
		registerItemModel(LWItems.BROKEN_LEATHER_CHESTPLATE);
		registerItemModel(LWItems.BROKEN_LEATHER_LEGGINGS);
		registerItemModel(LWItems.BROKEN_LEATHER_BOOTS);
		
		registerBlockModel(LWBlocks.DEBARKED_LOG_OAK);
		registerBlockModel(LWBlocks.DEBARKED_LOG_ACACIA);
		registerBlockModel(LWBlocks.DEBARKED_LOG_BIRCH);
		registerBlockModel(LWBlocks.DEBARKED_LOG_SPRUCE);
		registerBlockModel(LWBlocks.DEBARKED_LOG_DARKOAK);
		registerBlockModel(LWBlocks.DEBARKED_LOG_JUNGLE);
		registerBlockModel(LWBlocks.BARK_OAK);
		registerBlockModel(LWBlocks.BARK_BIRCH);
		registerBlockModel(LWBlocks.BARK_JUNGLE);
		registerBlockModel(LWBlocks.BARK_SPRUCE);
		registerBlockModel(LWBlocks.BARK_ACACIA);
		registerBlockModel(LWBlocks.BARK_DARKOAK);
		registerBlockModel(LWBlocks.DRYING_RACK_OAK);
		registerBlockModel(LWBlocks.DRYING_RACK_ACACIA);
		registerBlockModel(LWBlocks.DRYING_RACK_BIRCH);
		registerBlockModel(LWBlocks.DRYING_RACK_SPRUCE);
		registerBlockModel(LWBlocks.DRYING_RACK_DARKOAK);
		registerBlockModel(LWBlocks.DRYING_RACK_JUNGLE);
		registerBlockModel(LWBlocks.BARREL_OAK);
		registerBlockModel(LWBlocks.SEALED_BARREL_OAK);
		registerBlockModel(LWBlocks.BARREL_SPRUCE);
		registerBlockModel(LWBlocks.SEALED_BARREL_SPRUCE);
		registerBlockModel(LWBlocks.BARREL_BIRCH);
		registerBlockModel(LWBlocks.SEALED_BARREL_BIRCH);
		registerBlockModel(LWBlocks.BARREL_JUNGLE);
		registerBlockModel(LWBlocks.SEALED_BARREL_JUNGLE);
		registerBlockModel(LWBlocks.BARREL_ACACIA);
		registerBlockModel(LWBlocks.SEALED_BARREL_ACACIA);
		registerBlockModel(LWBlocks.BARREL_DARKOAK);
		registerBlockModel(LWBlocks.SEALED_BARREL_DARKOAK);
		if(!ConfigLeatherWorks.disableTrunk){
		registerBlockModel(LWBlocks.LEATHER_TRUNK);
		if(!ConfigLeatherWorks.disableColoredTrunks){
			
		}
		}
		
		ModelLoader.setCustomStateMapper(LWBlocks.TANNIN, new StateMapperBase() {
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
				return new ModelResourceLocation(LeatherWorks.MODID + ":fluids", "tannin");
			}
		});

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDryingRack.class, new TileEntityDryingRackRenderer());
		if(!ConfigLeatherWorks.disableTrunk){
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTrunk.class, new TileEntityTrunkRenderer());
		}
	}

	private static void registerBlockModel(Block block) {
		registerItemModel(Item.getItemFromBlock(block));
	}

	private static void registerItemModel(Item item) {
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
}
