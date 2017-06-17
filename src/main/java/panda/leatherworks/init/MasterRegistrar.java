package panda.leatherworks.init;

import java.util.Iterator;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import panda.leatherworks.LeatherWorks;

public final class MasterRegistrar {

	public static SoundEvent TOOL_SCRAPE;
	
	public static void register(FMLPreInitializationEvent e, List<?> list) {
		Iterator<?> iterator = list.iterator();
		
		while (iterator.hasNext()) {
			Object k = iterator.next();
			if (k instanceof Item) {
				GameRegistry.register((Item) k);
				((Item) k).setUnlocalizedName(LeatherWorks.MODID + "." + ((Item) k).getRegistryName().getResourcePath());
			}

		}
	}


	public static void callRegistry(FMLPreInitializationEvent e) {
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
