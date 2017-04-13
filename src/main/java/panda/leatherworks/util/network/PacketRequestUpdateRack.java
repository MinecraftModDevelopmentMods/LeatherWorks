package panda.leatherworks.util.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import panda.leatherworks.entity.TileEntityDryingRack;
import panda.leatherworks.entity.TileEntityItemRack;

public class PacketRequestUpdateRack implements IMessage {

	private BlockPos pos;
	private int dimension;
	
	public PacketRequestUpdateRack(BlockPos pos, int dimension) {
		this.pos = pos;
		this.dimension = dimension;
	}
	
	public PacketRequestUpdateRack(TileEntityItemRack te) {
		this(te.getPos(), te.getWorld().provider.getDimension());
	}
	
	public PacketRequestUpdateRack() {
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeLong(pos.toLong());
		buf.writeInt(dimension);
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		pos = BlockPos.fromLong(buf.readLong());
		dimension = buf.readInt();
	}
	
	public static class Handler implements IMessageHandler<PacketRequestUpdateRack, PacketUpdateRack> {
	
		@Override
		public PacketUpdateRack onMessage(PacketRequestUpdateRack message, MessageContext ctx) {
			World world = FMLCommonHandler.instance().getMinecraftServerInstance().worldServerForDimension(message.dimension);
			TileEntityItemRack te = (TileEntityItemRack)world.getTileEntity(message.pos);
			if (te != null) {
				return new PacketUpdateRack(te);
			} else {
				return null;
			}
		}
	
	}

}