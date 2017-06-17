package panda.leatherworks.common.inventory;

import javax.annotation.Nullable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

public class InventoryPack extends InventoryBasic{

	 private ItemStack backpackStack;
	 private EntityPlayer player;
	 private ItemStack[] inventoryContents;
	
	public InventoryPack(String title, ItemStack stack, EntityPlayer player) {
		super(title,false, 16);
		this.player = player;
		this.backpackStack = stack;
		
		this.inventoryContents = new ItemStack[16];
		if (!player.worldObj.isRemote) { //server side only
			if (stack.hasTagCompound()) {
				readFromNBT(stack.getTagCompound());
			}
		}
	}
	
	public void onGuiSaved(EntityPlayer entityPlayer){
        if (backpackStack != null){
        	//System.out.println("saved");
            save();
        }
    }
	
	 @Override
	    public ItemStack getStackInSlot(int slotIndex) {
	        return slotIndex >= this.getSizeInventory() ? null : this.inventoryContents[slotIndex];
	    }

	    @Override
	    public ItemStack decrStackSize(int slotIndex, int amount) {
	        if (inventoryContents[slotIndex] != null) {
	            if (inventoryContents[slotIndex].stackSize <= amount) {
	                ItemStack itemstack = inventoryContents[slotIndex];
	                inventoryContents[slotIndex] = null;
	                return itemstack;
	            }
	            ItemStack itemstack1 = inventoryContents[slotIndex].splitStack(amount);
	            if (inventoryContents[slotIndex].stackSize == 0) {
	            	inventoryContents[slotIndex] = null;
	            }
	            return itemstack1;
	        }
	        else {
	            return null;
	        }
	    }

	    @Override
	    public void setInventorySlotContents(int slotIndex, @Nullable ItemStack itemStack) {
	    	inventoryContents[slotIndex] = itemStack;
	        if (itemStack != null && itemStack.stackSize > getInventoryStackLimit()) {
	            itemStack.stackSize = getInventoryStackLimit();
	        }
	    }
	    
	    @Override
	    public ItemStack removeStackFromSlot(int index) {
	        ItemStack stack = null;
	        if (inventoryContents[index] != null){
	            stack = inventoryContents[index];
	            inventoryContents[index] = null;
	        }
	        return stack;
	    }
	    
	    @Override
	    public void clear() {
	        for (int i = 0; i < inventoryContents.length; i++){
	        	inventoryContents[i] = null;
	        }
	    }
	
	
	
	public void save(){
		if (!player.worldObj.isRemote) {
			NBTTagCompound nbtTagCompound = backpackStack.getTagCompound();

			if (nbtTagCompound == null) {
				nbtTagCompound = new NBTTagCompound();
			}

			writeToNBT(nbtTagCompound);
			backpackStack.setTagCompound(nbtTagCompound);
		}
    }

    /**
     * Writes the data of the backpack to NBT form.
     * @param nbtTagCompound - the tag compound
     */
    public void writeToNBT(NBTTagCompound nbtTagCompound){
		// Write the ItemStacks in the inventory to NBT
		NBTTagList tagList = new NBTTagList();
		for (int i = 0; i < inventoryContents.length; i++) {
			if (inventoryContents[i] != null) {
				NBTTagCompound tagCompound = new NBTTagCompound();
				tagCompound.setByte("Slot", (byte) i);
				inventoryContents[i].writeToNBT(tagCompound);
				tagList.appendTag(tagCompound);
			}
		}
		nbtTagCompound.setTag("Items", tagList);
    }

    /**
     * Loads in the data stored in the NBT of this stack and puts the items in their respective slots.
     * @param nbtTagCompound - the tag compound
     */
    //ToDo: Really need to remove this nonsense in next refactor
    public void readFromNBT(NBTTagCompound nbtTagCompound){
		//load in items
		if (nbtTagCompound.hasKey("Items")) {
			NBTTagList tagList = nbtTagCompound.getTagList("Items", Constants.NBT.TAG_COMPOUND);
			this.inventoryContents = new ItemStack[this.getSizeInventory()];

			for (int i = 0; i < tagList.tagCount(); i++) {
				NBTTagCompound stackTag = tagList.getCompoundTagAt(i);
				int j = stackTag.getByte("Slot");
				if (i >= 0 && i <= inventoryContents.length) { //ToDo: Remove 2nd equals (so just less than) as per a 1.7.10 PR; test it
					this.inventoryContents[j] = ItemStack.loadItemStackFromNBT(stackTag);
				}
			}
		}
    }
	
	
	
	
	

}
