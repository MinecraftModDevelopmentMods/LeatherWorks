package panda.leatherworks.init;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.IForgeRegistry;
import panda.leatherworks.LeatherWorks;

@EventBusSubscriber
public final class LWSoundEvents {

	public static final SoundEvent TOOL_SCRAPE = simply("tool_scrape");

	private static SoundEvent simply(String name) {
		ResourceLocation resourceLocation = new ResourceLocation(LeatherWorks.MODID, name);
		 return new SoundEvent(resourceLocation).setRegistryName(resourceLocation);
	}

	@SubscribeEvent
	public static void register(RegistryEvent.Register<SoundEvent> event) {
		IForgeRegistry<SoundEvent> registry = event.getRegistry();

		registry.register(TOOL_SCRAPE);
	}
}
