package panda.leatherworks.common;

import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public abstract class CommonProxy {
	public void registerMessageHandlers(SimpleNetworkWrapper wrapper) {}

	public abstract void registerColorHandlers();

	public abstract void registerModels();
}
