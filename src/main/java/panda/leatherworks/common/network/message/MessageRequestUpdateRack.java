package panda.leatherworks.common.network.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import panda.leatherworks.common.tileentity.TileEntityItemRack;

public class MessageRequestUpdateRack implements IMessage {
	public BlockPos pos;
	public int dimension;
	
	public MessageRequestUpdateRack(BlockPos pos, int dimension) {
		this.pos = pos;
		this.dimension = dimension;
	}
	
	public MessageRequestUpdateRack(TileEntityItemRack te) {
		this(te.getPos(), te.getWorld().provider.getDimension());
	}
	
	public MessageRequestUpdateRack() {
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
}