package panda.leatherworks.util.registry;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import panda.leatherworks.LeatherWorks;
import panda.leatherworks.blocks.BlockTileEntity;
import panda.leatherworks.entity.TileEntityItemRack;
import panda.leatherworks.util.TESRRack;

public final class MasterRegistrar {

	public static SoundEvent TOOL_SCRAPE;
	
	public static void register(FMLPreInitializationEvent e, List<?> list) {
		Iterator<?> iterator = list.iterator();
		
		while (iterator.hasNext()) {
			Object k = iterator.next();
			if (k instanceof Block) {
				Block block = (Block) k;
				block.setUnlocalizedName(LeatherWorks.MODID + "." + block.getRegistryName().getResourcePath());
				GameRegistry.register(block);
				if (Item.getItemFromBlock(block) == null)
					GameRegistry.register(new ItemBlock(block), block.getRegistryName());
				if (block instanceof BlockTileEntity) {
					GameRegistry.registerTileEntity(((BlockTileEntity<?>)block).getTileEntityClass(), block.getRegistryName().toString());
				}
			} else if (k instanceof Item) {
				GameRegistry.register((Item) k);
				((Item) k).setUnlocalizedName(LeatherWorks.MODID + "." + ((Item) k).getRegistryName().getResourcePath());
			}

		}
		if (e.getSide() == Side.CLIENT)
			registerModels(list);
	}

	public static void registerModels(List<?> list) {
		Iterator<?> iterator = list.iterator();

		while (iterator.hasNext()) {
			Object k = iterator.next();
			Item item = null;
			if (k instanceof Block) {
				item = Item.getItemFromBlock((Block) k);
			} else if (k instanceof Item) {
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

	public static void callRegistry(FMLPreInitializationEvent e) {
		register(e, BlockList.getList());
		register(e, ItemList.getList());
		TOOL_SCRAPE = registerSound("tool_scrape");
		RecipeRegistry.register();
		
		if (e.getSide() == Side.CLIENT){
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityItemRack.class, new TESRRack());
		}
	}

	@SideOnly(Side.CLIENT)
	public static void handleFluidState(Block block, final String name) {
		ModelLoader.setCustomStateMapper(block, new StateMapperBase() {
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
				return new ModelResourceLocation(LeatherWorks.MODID + ":fluids", name);
			}
		});
	}
	
	private static SoundEvent registerSound(String name)
	{
	ResourceLocation location = new ResourceLocation("leatherworks", name);
	SoundEvent sound = new SoundEvent(location);
	sound.setRegistryName(location);
	ForgeRegistries.SOUND_EVENTS.register(sound);
	return sound;
	}

}
