package panda.leatherworks.server;

import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import panda.leatherworks.common.CommonProxy;
import panda.leatherworks.common.network.message.MessageUpdateRack;
import panda.leatherworks.common.network.messagehandler.DummyMessageHandler;

@SideOnly(Side.SERVER)
public class ServerProxy extends CommonProxy {
	@Override
	public void registerMessageHandlers(SimpleNetworkWrapper wrapper) {
		super.registerMessageHandlers(wrapper);
		wrapper.registerMessage(new DummyMessageHandler(), MessageUpdateRack.class, 0, Side.CLIENT);
	}

	@Override
	public void registerColorHandlers() {}

	@Override
	public void registerModels() {}
}
