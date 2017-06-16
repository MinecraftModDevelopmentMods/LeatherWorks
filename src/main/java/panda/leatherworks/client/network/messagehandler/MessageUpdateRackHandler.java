package panda.leatherworks.client.network.messagehandler;

import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import panda.leatherworks.common.network.message.MessageUpdateRack;
import panda.leatherworks.common.tileentity.TileEntityItemRack;

@SideOnly(Side.CLIENT)
public class MessageUpdateRackHandler implements IMessageHandler<MessageUpdateRack, IMessage> {
	@Override
	@Nullable
	public IMessage onMessage(MessageUpdateRack message, MessageContext ctx) {
		Minecraft.getMinecraft().addScheduledTask(() -> {
			TileEntityItemRack te = (TileEntityItemRack)Minecraft.getMinecraft().theWorld.getTileEntity(message.pos);
			if(te != null){
				te.inventory.setStackInSlot(0, message.stack);
			}
		});
		return null;
	}
}
