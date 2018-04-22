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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import panda.leatherworks.LeatherWorks;
import panda.leatherworks.common.InventoryTrunk;

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
    
    @Override
    public void update()
    {
    	
        if (this.world != null && !this.world.isRemote  && this.numPlayersUsing != 0 && (this.ticksSinceSync + this.pos.getX() + this.pos.getY() + this.pos.getZ()) % 200 == 0)

        {
            this.numPlayersUsing = 0;

            float f = 5.0F;

            for (EntityPlayer player : this.world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(this.pos.getX() - f, this.pos.getY() - f, this.pos.getZ() - f, this.pos.getX() + 1 + f, this.pos.getY() + 1 + f, this.pos.getZ() + 1 + f)))
            {
                if (player.openContainer instanceof InventoryTrunk)
                {
                    ++this.numPlayersUsing;
                }
            }
        }

        if (this.world != null && !this.world.isRemote && this.ticksSinceSync < 0)
        {
            this.world.addBlockEvent(this.pos,  this.getBlockType(), 3, ((this.numPlayersUsing << 3) & 0xF8) | (this.facing.getHorizontalIndex() & 0x7));
        }


        this.ticksSinceSync++;

        this.prevLidAngle = this.lidAngle;

        float angle = 0.1F;

        if (this.numPlayersUsing > 0 && this.lidAngle == 0.0F)
        {
            double x = this.pos.getX() + 0.5D;
            double y = this.pos.getY() + 0.5D;
            double z = this.pos.getZ() + 0.5D;

            this.world.playSound(null, x, y, z, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, SoundCategory.BLOCKS, 6F, this.world.rand.nextFloat() * 0.1F + 0.6F);
        }

        if (this.numPlayersUsing == 0 && this.lidAngle > 0.0F || this.numPlayersUsing > 0 && this.lidAngle < 1.0F)
        {
            float currentAngle = this.lidAngle;

            if (this.numPlayersUsing > 0)
            {
                this.lidAngle += angle;
            }
            else
            {
                this.lidAngle -= angle;
            }

            if (this.lidAngle > 1.0F)
            {
                this.lidAngle = 1.0F;
            }

            float maxAngle = 0.5F;

            if (this.lidAngle < maxAngle && currentAngle >= maxAngle)
            {
                double x = this.pos.getX() + 0.5D;
                double y = this.pos.getY() + 0.5D;
                double z = this.pos.getZ() + 0.5D;

                this.world.playSound(null, x, y, z, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, SoundCategory.BLOCKS, 6F, this.world.rand.nextFloat() * 0.1F + 0.6F);
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
		LeatherWorks.logger.info("Saving: " + (byte) this.facing.getHorizontalIndex());
		parentNBTTagCompound.setByte("facing", (byte) this.facing.getHorizontalIndex());
		
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
		
		this.facing = EnumFacing.getHorizontal(parentNBTTagCompound.getByte("facing"));
		LeatherWorks.logger.info("Loading: " + this.facing);
	}
	
	@Override
    public boolean receiveClientEvent(int id, int type)
    {
        if (id == 1)
        {
            this.numPlayersUsing = type;
            
        }
        else if (id == 2)
        {
            this.facing = EnumFacing.getHorizontal(type);
        }
        else if (id == 3)
        {
            this.facing = EnumFacing.getHorizontal(type & 0x7);
            this.numPlayersUsing = (type & 0xF8) >> 3;
        }
        return true;

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
		if (!player.isSpectator())
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

        compound.setByte("facing", (byte) this.facing.getHorizontalIndex());

        return new SPacketUpdateTileEntity(this.pos, 0, compound);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
    {
        if (pkt.getTileEntityType() == 0)
        {
            NBTTagCompound compound = pkt.getNbtCompound();

            this.facing = EnumFacing.getHorizontal(compound.getByte("facing"));
        }
    }

}
