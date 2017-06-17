package panda.leatherworks.client;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import panda.leatherworks.LeatherWorks;
import panda.leatherworks.client.network.messagehandler.MessageUpdateRackHandler;
import panda.leatherworks.client.renderer.tileentity.TileEntityItemRackRenderer;
import panda.leatherworks.common.CommonProxy;
import panda.leatherworks.common.item.armor.ItemBrokenArmor;
import panda.leatherworks.common.network.message.MessageUpdateRack;
import panda.leatherworks.common.tileentity.TileEntityItemRack;
import panda.leatherworks.init.LWBlocks;
import panda.leatherworks.init.LWItems;
import panda.leatherworks.util.IMeta;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {
	@Override
	public void registerMessageHandlers(SimpleNetworkWrapper wrapper) {
		super.registerMessageHandlers(wrapper);
		wrapper.registerMessage(new MessageUpdateRackHandler(), MessageUpdateRack.class, 0, Side.CLIENT);
	}

	@Override
	public void registerColorHandlers() {
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(
			(stack, tintIndex) -> tintIndex > 0 ? -1 : ((ItemBrokenArmor) stack.getItem()).getColor(stack),
			LWItems.BROKEN_LEATHER_CHESTPLATE,
			LWItems.BROKEN_LEATHER_HELMET,
			LWItems.BROKEN_LEATHER_LEGGINGS,
			LWItems.BROKEN_LEATHER_BOOTS
		);
	}

	@Override
	public void registerModels() {
		registerBlockModel(LWBlocks.DEBARKED_LOG_OAK, "debarked_log_oak");
		registerBlockModel(LWBlocks.DEBARKED_LOG_ACACIA, "debarked_log_acacia");
		registerBlockModel(LWBlocks.DEBARKED_LOG_BIRCH, "debarked_log_birch");
		registerBlockModel(LWBlocks.DEBARKED_LOG_SPRUCE, "debarked_log_spruce");
		registerBlockModel(LWBlocks.DEBARKED_LOG_DARKOAK, "debarked_log_darkoak");
		registerBlockModel(LWBlocks.DEBARKED_LOG_JUNGLE, "debarked_log_jungle");
		registerBlockModel(LWBlocks.BARREL, "barrel");
		registerBlockModel(LWBlocks.DRYING_RACK, "drying_rack");
		registerBlockModel(LWBlocks.SEALED_BARREL, "sealed_barrel");

		ModelLoader.setCustomStateMapper(LWBlocks.TANNIN, new StateMapperBase() {
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
				return new ModelResourceLocation(LeatherWorks.MODID + ":fluids", "tannin");
			}
		});

		registerModels(LWItems.getList());

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityItemRack.class, new TileEntityItemRackRenderer());
	}

	private static void registerBlockModel(Block block, String name) {
		registerItemModel(Item.getItemFromBlock(block), name);
	}

	private static void registerItemModel(Item item, String name) {
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(new ResourceLocation(LeatherWorks.MODID, name), "inventory"));
	}

	private void registerModels(List<?> list) {
		for (Object k : list) {
			Item item = null;
			if (k instanceof Item) {
				item = (Item) k;
			}
			if (item instanceof IMeta) {
				List<ModelResourceLocation> map = new ArrayList<ModelResourceLocation>();
				for (int i = 0; i <= ((IMeta) item).getMaxMeta(); i++) {
					map = ((IMeta) item).getMetaModelLocations(map);
					ModelLoader.setCustomModelResourceLocation(item, i, map.get(i));
				}
			} else if (item != null) {
				ModelLoader.setCustomModelResourceLocation(item, 0,
					new ModelResourceLocation(item.getRegistryName(), "inventory"));
			}
			if (k instanceof BlockFluidBase) {
				handleFluidState((Block) k, ((Block) k).getRegistryName().getResourcePath());
			}

		}
	}

	private void handleFluidState(Block block, final String name) {
		ModelLoader.setCustomStateMapper(block, new StateMapperBase() {
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
				return new ModelResourceLocation(LeatherWorks.MODID + ":fluids", name);
			}
		});
	}
}
