package panda.leatherworks.common.network.messagehandler;

import javax.annotation.Nullable;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * This dummy handler will do nothing.
 *
 * Forge doesn't let us register messages without a handler. This is unfortunate because it makes
 * registering messages that should never be handled on a physical side inconvenient.
 * Use this message handler when registering client handled messages on a dedicated server.
 */
public class DummyMessageHandler implements IMessageHandler<IMessage, IMessage> {
	@Override
	@Nullable
	public IMessage onMessage(IMessage message, MessageContext ctx) {
		return null;
	}
}
