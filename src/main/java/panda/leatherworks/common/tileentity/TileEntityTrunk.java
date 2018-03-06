package panda.leatherworks.common.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import panda.leatherworks.LeatherWorks;
import panda.leatherworks.common.InventoryTrunk;
import panda.leatherworks.common.block.BlockTrunk;

public class TileEntityTrunk extends TileEntity implements IInventory, ITickable{
	
	private static final int NUMSLOTS = 27;
	private NonNullList<ItemStack> itemStacks = NonNullList.<ItemStack>withSize(NUMSLOTS, ItemStack.EMPTY);
	public float lidAngle;
    public float prevLidAngle;
    private int ticksSinceSync;
    public int numPlayersUsing;
    private EnumFacing facing;
    
    public TileEntityTrunk()
	{
		clear();
		this.facing = EnumFacing.NORTH;
	}
    
    public EnumFacing getFacing()
    {
        return this.facing;
    }
    
    @Override
	public int getSizeInventory() {
		return itemStacks.size();
	}

    @Override
	public boolean isEmpty()
    {
    	return itemStacks == NonNullList.<ItemStack>withSize(NUMSLOTS, ItemStack.EMPTY);
    }
    @Override
	public ItemStack getStackInSlot(int slotIndex) {
		return itemStacks.get(slotIndex);
	}
    
    @Override
	public ItemStack decrStackSize(int slotIndex, int count) {
		ItemStack itemStackInSlot = getStackInSlot(slotIndex);
		if (itemStackInSlot.isEmpty()) return ItemStack.EMPTY;

		ItemStack itemStackRemoved;
		if (itemStackInSlot.getCount() <= count) {
			itemStackRemoved = itemStackInSlot;
			setInventorySlotContents(slotIndex, ItemStack.EMPTY);
		} else {
			itemStackRemoved = itemStackInSlot.splitStack(count);
			if (itemStackInSlot.getCount() == 0) { // getStackSize
				setInventorySlotContents(slotIndex, ItemStack.EMPTY);
			}
		}
	  markDirty();
		return itemStackRemoved;
	}
    
    @Override
	public void setInventorySlotContents(int slotIndex, ItemStack itemstack) {
		itemStacks.set(slotIndex, itemstack);
		if (itemstack.isEmpty() && itemstack.getCount() > getInventoryStackLimit()) {
			itemstack.setCount(getInventoryStackLimit());
		}
		markDirty();
	}
    
    @Override
	public int getInventoryStackLimit() {
		return 64;
	}
    
    @Override
	public boolean isItemValidForSlot(int slotIndex, ItemStack itemstack) {
		return true;
	}
    
    @Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		if (this.world.getTileEntity(this.pos) != this) return false;
		return player.getDistanceSq(pos.add(0.5, 0.5, 0.5)) < 64;
	}
    
    public void update()
    {
    	
    	//LeatherWorks.logger.info(numPlayersUsing);
        int i = this.pos.getX();
        int j = this.pos.getY();
        int k = this.pos.getZ();
        ++this.ticksSinceSync;
        
        if (this.numPlayersUsing < 0)
        {
            this.numPlayersUsing = 0;
        }
        

        if (!this.world.isRemote && this.numPlayersUsing != 0 && (this.ticksSinceSync + i + j + k) % 200 == 0)
        {
            this.numPlayersUsing = 0;

            for (EntityPlayer entityplayer : this.world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB((double)((float)i - 5.0F), (double)((float)j - 5.0F), (double)((float)k - 5.0F), (double)((float)(i + 1) + 5.0F), (double)((float)(j + 1) + 5.0F), (double)((float)(k + 1) + 5.0F))))
            {
                if (entityplayer.openContainer instanceof InventoryTrunk)
                {
                	++this.numPlayersUsing;
                }
            }
        }

        this.prevLidAngle = this.lidAngle;

        if (this.numPlayersUsing > 0 && this.lidAngle == 0.0F)
        {
            double d1 = (double)i + 0.5D;
            double d2 = (double)k + 0.5D;

            this.world.playSound(null, d1, (double)j + 0.5D, d2, SoundEvents.BLOCK_CHEST_OPEN, SoundCategory.BLOCKS, 1F, this.world.rand.nextFloat() * 0.1F + 0.9F);
        }

        if (this.numPlayersUsing == 0 && this.lidAngle > 0.0F || this.numPlayersUsing > 0 && this.lidAngle < 1.0F)
        {
            float f2 = this.lidAngle;

            if (this.numPlayersUsing > 0)
            {
                this.lidAngle += 0.1F;
            }
            else
            {
                this.lidAngle -= 0.1F;
            }

            if (this.lidAngle > 1.0F)
            {
                this.lidAngle = 1.0F;
            }


            if (this.lidAngle < 0.5F && f2 >= 0.5F)
            {
                double d3 = (double)i + 0.5D;
                double d0 = (double)k + 0.5D;


                this.world.playSound(null, d3, (double)j + 0.5D, d0, SoundEvents.BLOCK_CHEST_CLOSE, SoundCategory.BLOCKS, 1F, this.world.rand.nextFloat() * 0.1F + 0.9F);
            }

            if (this.lidAngle < 0.0F)
            {
                this.lidAngle = 0.0F;
            }
        }
    }
    
    @Override
	public NBTTagCompound writeToNBT(NBTTagCompound parentNBTTagCompound)
	{
		super.writeToNBT(parentNBTTagCompound);
		NBTTagList dataForAllSlots = new NBTTagList();
		for (int i = 0; i < this.itemStacks.size(); ++i) {
			if (!this.itemStacks.get(i).isEmpty())	{
				NBTTagCompound dataForThisSlot = new NBTTagCompound();
				dataForThisSlot.setByte("Slot", (byte) i);
				this.itemStacks.get(i).writeToNBT(dataForThisSlot);
				dataForAllSlots.appendTag(dataForThisSlot);
			}
		}
		parentNBTTagCompound.setTag("Items", dataForAllSlots);
		parentNBTTagCompound.setByte("facing", (byte) this.facing.ordinal());
		
		return parentNBTTagCompound;
	}

	@Override
	public void readFromNBT(NBTTagCompound parentNBTTagCompound)
	{
		super.readFromNBT(parentNBTTagCompound);
		NBTTagList dataForAllSlots = parentNBTTagCompound.getTagList("Items", 10);

		itemStacks = NonNullList.<ItemStack>withSize(NUMSLOTS, ItemStack.EMPTY);
		for (int i = 0; i < dataForAllSlots.tagCount(); ++i) {
			NBTTagCompound dataForOneSlot = dataForAllSlots.getCompoundTagAt(i);
			int slotIndex = dataForOneSlot.getByte("Slot") & 255;

			if (slotIndex >= 0 && slotIndex < this.itemStacks.size()) {
				this.itemStacks.set(slotIndex, new ItemStack(dataForOneSlot));
			}
		}
		this.facing = EnumFacing.VALUES[parentNBTTagCompound.getByte("facing")];
	}
	
	@Override
    public boolean receiveClientEvent(int id, int type)
    {
        if (id == 1)
        {
            this.numPlayersUsing = type;
            return true;
        }
        else if (id == 2)
        {
            this.facing = EnumFacing.VALUES[type];
        }
        else if (id == 3)
        {
            this.facing = EnumFacing.VALUES[type & 0x7];
            this.numPlayersUsing = (type & 0xF8) >> 3;
        }
           return super.receiveClientEvent(id, type);

    }
	
	@Override
	public void clear() {
		itemStacks = NonNullList.<ItemStack>withSize(NUMSLOTS, ItemStack.EMPTY);
	}
	
	@Override
	public String getName() {
		return "container.trunk.name";
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}
	
	@Override
	public ITextComponent getDisplayName() {
		return this.hasCustomName() ? new TextComponentString(this.getName()) : new TextComponentTranslation(this.getName());
	}
	
	@Override
	public ItemStack removeStackFromSlot(int slotIndex) {
		ItemStack itemStack = getStackInSlot(slotIndex);
		if (!itemStack.isEmpty()) setInventorySlotContents(slotIndex, ItemStack.EMPTY);
		return itemStack;
	}

	@Override
	public void openInventory(EntityPlayer player) {
		LeatherWorks.logger.info(numPlayersUsing);
		if (!player.isSpectator())
        {
            if (this.numPlayersUsing < 0)
            {
                this.numPlayersUsing = 0;
            }

            ++this.numPlayersUsing;
            this.world.addBlockEvent(this.pos, this.getBlockType(), 1, this.numPlayersUsing);
            this.world.notifyNeighborsOfStateChange(this.pos, this.getBlockType(), false);
        }
	}
	

	@Override
	public void closeInventory(EntityPlayer player) {
		if (!player.isSpectator() && this.getBlockType() instanceof BlockTrunk)
        {
            --this.numPlayersUsing;
            this.world.addBlockEvent(this.pos, this.getBlockType(), 1, this.numPlayersUsing);
            this.world.notifyNeighborsOfStateChange(this.pos, this.getBlockType(), false);
        }
	}
	

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {//have to have this
	}
	

	@Override
	public int getFieldCount() {
		return 0;
	}
	
	public void setFacing(EnumFacing facing)
    {
        this.facing = facing;
    }
	
	@Override
    public SPacketUpdateTileEntity getUpdatePacket()
    {
        NBTTagCompound compound = new NBTTagCompound();

        compound.setByte("facing", (byte) this.facing.ordinal());

        return new SPacketUpdateTileEntity(this.pos, 0, compound);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
    {
        if (pkt.getTileEntityType() == 0)
        {
            NBTTagCompound compound = pkt.getNbtCompound();

            this.facing = EnumFacing.VALUES[compound.getByte("facing")];
        }
    }

}
