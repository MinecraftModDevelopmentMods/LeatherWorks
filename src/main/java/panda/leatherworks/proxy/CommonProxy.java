package panda.leatherworks.proxy;

import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.oredict.OreDictionary;
import panda.leatherworks.init.LWItems;

public class CommonProxy {
	public void registerMessageHandlers(SimpleNetworkWrapper wrapper) {}

	public void registerColorHandlers() {
	}

    public static void registerModels(ModelRegistryEvent event) {}

	public void registerOreDicts() {
		registerHide(LWItems.RAWHIDE_COW);
		registerHide(LWItems.RAWHIDE_DONKEY);
		registerHide(LWItems.RAWHIDE_HORSE);
		registerHide(LWItems.RAWHIDE_LLAMA);
		registerHide(LWItems.RAWHIDE_MOOSHROOM);
		registerHide(LWItems.RAWHIDE_MULE);
		registerHide(LWItems.RAWHIDE_PIG);
		registerHide(LWItems.RAWHIDE_POLARBEAR);
		registerHide(LWItems.RAWHIDE_WOLF);
	}
	    
	private void registerHide(Item item){
	   OreDictionary.registerOre("resourceHide", item);
	   OreDictionary.registerOre("rawhide", item);
	}
}
