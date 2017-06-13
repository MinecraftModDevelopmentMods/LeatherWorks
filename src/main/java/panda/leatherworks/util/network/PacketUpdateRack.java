package panda.leatherworks.util.network;

import io.netty.buffer.ByteBuf;
import panda.leatherworks.common.tileentity.TileEntityItemRack;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketUpdateRack implements IMessage {
		private BlockPos pos;
		private ItemStack stack;

		
		public PacketUpdateRack(BlockPos pos, ItemStack stack) {
			this.pos = pos;
			this.stack = stack;

		}
		
		public PacketUpdateRack(TileEntityItemRack te) {
			this(te.getPos(), te.inventory.getStackInSlot(0));
		}
		
		public PacketUpdateRack() {
		}
		
		@Override
		public void toBytes(ByteBuf buf) {
			buf.writeLong(pos.toLong());
			
			ByteBufUtils.writeItemStack(buf, stack);

		}
		
		@Override
		public void fromBytes(ByteBuf buf) {
			pos = BlockPos.fromLong(buf.readLong());
			stack = ByteBufUtils.readItemStack(buf);
		}
		
		public static class Handler implements IMessageHandler<PacketUpdateRack, IMessage> {

			@Override
			public IMessage onMessage(PacketUpdateRack message, MessageContext ctx) {
				Minecraft.getMinecraft().addScheduledTask(() -> {
					TileEntityItemRack te = (TileEntityItemRack)Minecraft.getMinecraft().theWorld.getTileEntity(message.pos);
					//if(te != null){
						te.inventory.setStackInSlot(0, message.stack);	
					//}
						
				});
				return null;
			}
		
		}
	}