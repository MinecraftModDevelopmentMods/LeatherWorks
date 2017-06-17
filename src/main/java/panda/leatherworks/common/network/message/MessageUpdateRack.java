package panda.leatherworks.common.network.message;

import io.netty.buffer.ByteBuf;
import panda.leatherworks.common.tileentity.TileEntityItemRack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class MessageUpdateRack implements IMessage {
	public BlockPos pos;
	public ItemStack stack;

	public MessageUpdateRack(BlockPos pos, ItemStack stack) {
		this.pos = pos;
		this.stack = stack;
	}

	public MessageUpdateRack(TileEntityItemRack te) {
		this(te.getPos(), te.inventory.getStackInSlot(0));
	}

	public MessageUpdateRack() {
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
}
