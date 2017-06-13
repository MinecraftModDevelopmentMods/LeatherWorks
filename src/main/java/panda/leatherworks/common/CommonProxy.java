package panda.leatherworks.common;

import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import panda.leatherworks.common.network.messagehandler.MessageRequestUpdateRackHandler;
import panda.leatherworks.common.network.message.MessageRequestUpdateRack;

public abstract class CommonProxy {
	public void registerMessageHandlers(SimpleNetworkWrapper wrapper) {
		wrapper.registerMessage(new MessageRequestUpdateRackHandler(), MessageRequestUpdateRack.class, 1, Side.SERVER);
	}

	public abstract void registerColorHandlers();
}
