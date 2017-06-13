package panda.leatherworks.common.network.messagehandler;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class DummyMessageHandler implements IMessageHandler<IMessage, IMessage> {
	@Override
	public IMessage onMessage(IMessage message, MessageContext ctx) {
		return null;
	}
}
