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
		registerHide(LWItems.RAWHIDE_DEER);
		registerHide(LWItems.RAWHIDE_BOAR);
		
		OreDictionary.registerOre("leatherSheet", LWItems.LEATHER_SHEET);
		OreDictionary.registerOre("leatherStrip", LWItems.LEATHER_STRIP);
		OreDictionary.registerOre("barkWood", LWItems.LEATHER_SOAKED);
		OreDictionary.registerOre("barkAcacia", LWItems.BARK_ACACIA);
		OreDictionary.registerOre("barkBirch", LWItems.BARK_BIRCH);
		OreDictionary.registerOre("barkDarkoak", LWItems.BARK_DARKOAK);
		OreDictionary.registerOre("barkJungle", LWItems.BARK_JUNGLE);
		OreDictionary.registerOre("barkOak", LWItems.BARK_OAK);
		OreDictionary.registerOre("barkSpruce", LWItems.BARK_SPRUCE);
		registerBark(LWItems.BARK_ACACIA);
		registerBark(LWItems.BARK_BIRCH);
		registerBark(LWItems.BARK_DARKOAK);
		registerBark(LWItems.BARK_JUNGLE);
		registerBark(LWItems.BARK_OAK);
		registerBark(LWItems.BARK_SPRUCE);

	}
	    
	private void registerHide(Item item){
	   OreDictionary.registerOre("resourceHide", item);
	   OreDictionary.registerOre("rawhide", item);
	}
	private void registerBark(Item item){
	OreDictionary.registerOre("barkWood", item);
	}
	
}
