package panda.leatherworks.init;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public final class MasterRegistrar {

	public static SoundEvent TOOL_SCRAPE;

	public static void callRegistry(FMLPreInitializationEvent e) {
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
