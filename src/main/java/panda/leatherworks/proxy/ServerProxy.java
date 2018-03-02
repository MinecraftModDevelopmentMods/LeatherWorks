package panda.leatherworks.proxy;

import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import panda.leatherworks.common.network.MessageUpdateRack;
import panda.leatherworks.common.network.DummyMessageHandler;

@SideOnly(Side.SERVER)
public abstract class ServerProxy extends CommonProxy {
	@Override
	public void registerMessageHandlers(SimpleNetworkWrapper wrapper) {
		super.registerMessageHandlers(wrapper);
		wrapper.registerMessage(new DummyMessageHandler(), MessageUpdateRack.class, 0, Side.CLIENT);
	}
}
