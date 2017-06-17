package panda.leatherworks.client;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
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
		registerItemModelMetaVariants(LWItems.RAWHIDE, "rawhide", 6);
		registerItemModelMetaVariants(LWItems.BARK, "bark", 6);
		registerItemModel(LWItems.TANNIN_BOTTLE, "tannin_bottle");
		registerItemModel(LWItems.TANNIN_BALL, "tannin_ball");
		registerItemModel(LWItems.REPAIR_KIT, "repair_kit");
		registerItemModel(LWItems.TANNIN_BUCKET, "tannin_bucket");
		registerItemModelMetaVariants(LWItems.CRAFTING_LEATHER, "crafting_leather", 3);
		registerItemModelMetaVariants(LWItems.PACK, "pack", 15);
		registerItemModel(LWItems.ENDER_PACK, "ender_pack");
		registerItemModel(LWItems.LEATHER_STRIP, "leather_strip");
		registerItemModel(LWItems.LEATHER_SHEET, "leather_sheet");
		registerItemModel(LWItems.BROKEN_LEATHER_BOOTS, "broken_leather_boots");
		registerItemModel(LWItems.BROKEN_LEATHER_CHESTPLATE, "broken_leather_chestplate");
		registerItemModel(LWItems.BROKEN_LEATHER_LEGGINGS, "broken_leather_leggings");
		registerItemModel(LWItems.BROKEN_LEATHER_BOOTS, "broken_leather_boots");

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

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityItemRack.class, new TileEntityItemRackRenderer());
	}

	private static void registerBlockModel(Block block, String name) {
		registerItemModel(Item.getItemFromBlock(block), name);
	}

	private static void registerItemModel(Item item, String name) {
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(new ResourceLocation(LeatherWorks.MODID, name), "inventory"));
	}

	private static void registerItemModelMetaVariants(Item item, String name, int metas) {
		for (int meta = 0; meta < metas; meta++) {
			ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(new ResourceLocation(LeatherWorks.MODID, name), "meta=" + meta));
		}
	}
}
