package panda.leatherworks.init;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import panda.leatherworks.LeatherWorks;
import panda.leatherworks.common.block.BlockTileEntity;
import panda.leatherworks.common.tileentity.TileEntityItemRack;
import panda.leatherworks.client.renderer.tileentity.TileEntityItemRackRenderer;
import panda.leatherworks.util.IMeta;

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
	}


	public static void callRegistry(FMLPreInitializationEvent e) {
		register(e, LWBlocks.getList());
		register(e, LWItems.getList());
		TOOL_SCRAPE = registerSound("tool_scrape");
		LWRecipes.register();
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
