package panda.leatherworks.common.network.messagehandler;

import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import panda.leatherworks.common.network.message.MessageRequestUpdateRack;
import panda.leatherworks.common.network.message.MessageUpdateRack;
import panda.leatherworks.common.tileentity.TileEntityItemRack;

public class MessageRequestUpdateRackHandler implements IMessageHandler<MessageRequestUpdateRack, MessageUpdateRack> {
	@Override
	public MessageUpdateRack onMessage(MessageRequestUpdateRack message, MessageContext ctx) {
		World world = FMLCommonHandler.instance().getMinecraftServerInstance().worldServerForDimension(message.dimension);
		TileEntityItemRack te = (TileEntityItemRack)world.getTileEntity(message.pos);
		if (te != null) {
			return new MessageUpdateRack(te);
		} else {
			return null;
		}
	}
}
