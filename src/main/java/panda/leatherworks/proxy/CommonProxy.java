package panda.leatherworks.proxy;

import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public class CommonProxy {
	public void registerMessageHandlers(SimpleNetworkWrapper wrapper) {}

	public void registerColorHandlers() {
	}

    public static void registerModels(ModelRegistryEvent event) {}
}
