package panda.leatherworks.common.item;

import java.util.List;

import javax.annotation.Nullable;

import panda.leatherworks.LeatherWorks;
import panda.leatherworks.common.block.BlockBarrel;
import panda.leatherworks.common.GuiHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCauldron;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

//TODO Rework this. It is using a static variable for the inventory. It will not work.
public class ItemPack extends Item {
	
	private static final String TileUtil = null;
	static ItemStack[] Packinventory;

	public ItemPack() {
		this.setMaxStackSize(1);
		setNoRepair();
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return Integer.MAX_VALUE;
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World player, List<String> list, ITooltipFlag advanced)
	{
		list.add(TextFormatting.RED+"WARNING: Packs not working.");
		list.add(TextFormatting.RED+"Your game will crash");
		
	}

	@Override
    public boolean showDurabilityBar(ItemStack stack) {
        if(stack.hasTagCompound()){
        NBTTagCompound nbtTagCompound = stack.getTagCompound();
        //System.out.println(getDurabilityForDisplay(stack));
        if(getDurabilityForDisplay(stack)==0){
        	return false;
        }
          if (nbtTagCompound != null) {
            if (nbtTagCompound.hasKey("Items")) {
            	return true;
            }
          }
        }
        return false;
    }

    @Override
    //Author: Gr8pefish
    public double getDurabilityForDisplay(ItemStack stack) {
            
            int total = 0;
            int full = 0;

            if (stack != null) {
                NBTTagCompound nbtTagCompound = stack.getTagCompound();
                if (nbtTagCompound != null) {
                    if (nbtTagCompound.hasKey("Items")) {
                        NBTTagList tagList = nbtTagCompound.getTagList("Items", Constants.NBT.TAG_COMPOUND);
                        Packinventory = new ItemStack[16];
                        for (int i = 0; i < tagList.tagCount(); i++) {
                            NBTTagCompound stackTag = tagList.getCompoundTagAt(i);
                            int slot = stackTag.getByte("Slot");
                            if (i >= 0 && i <= Packinventory.length)
                            	Packinventory[slot] = new ItemStack(stackTag);
                        }
                        for (ItemStack tempStack : Packinventory) {
                            if (tempStack != null) {
                                full += tempStack.getCount();
                                total += tempStack.getMaxStackSize();
                            } else {
                                total += 64;
                            }
                        }
                    }
                }
            }
            	//System.out.println(full+"/"+total);
            return ((double) full / total);
    }
    
    @Override
	public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
		// We only do this when shift is clicked
		if (player.isSneaking()) {
			
			//return evaluateTileHit(stack, player, world, pos, side) ? EnumActionResult.PASS : EnumActionResult.FAIL;
		}

		return super.onItemUseFirst(player, world, pos, side, hitX, hitY, hitZ, hand);
	}
    
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand handIn) {
    	ItemStack itemStack = player.getHeldItem(handIn);
    	if(itemStack.getItem() != this){
    		return new ActionResult(EnumActionResult.FAIL, itemStack);
    	}
    	if (world.isRemote){ 
             if (!player.isSneaking()){
            	 player.playSound(SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 1F, 1F);
            	 
                 return new ActionResult(EnumActionResult.SUCCESS, itemStack);
             }else{
            	 //Code to add to chest
             }
         }else{
        	 Block hit = world.getBlockState(player.rayTrace(3,1f).getBlockPos()).getBlock();
        	 if(!(hit instanceof BlockCauldron || hit instanceof BlockBarrel) &&!player.isSneaking() ){
        		 player.openGui(LeatherWorks.INSTANCE, GuiHandler.PACK_GUI, world, (int) player.posX, (int) player.posY, (int) player.posZ);
        	 }
        	 
         }
    	 return new ActionResult(EnumActionResult.FAIL, itemStack);
    }
   
    
    private boolean evaluateTileHit(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side) {

		// Shift right-clicking on an inventory tile will attempt to transfer
		// items contained in the backpack
    	TileEntity targeted = world.getTileEntity(pos);
    	IItemHandler inventory = getInventoryFromTile(targeted, null);
		// Process only inventories
		if (inventory != null) {
			
			// Must have inventory slots
			if (inventory.getSlots() <= 0) {
				return true;
			}

			if (!world.isRemote && inventory != null) {
				// Create our own backpack inventory
				
				Packinventory = readFromNBT(Packinventory,world,stack);
				if (Packinventory.length > 0) {
					System.out.println("doot");
					transferToChest(Packinventory, inventory, world, stack);
				} else {
					
					receiveFromChest(Packinventory, inventory);
					
				}
			}

			return true;
		}

		return false;
	}
    
    @Nullable
	public static IItemHandler getInventoryFromTile(@Nullable TileEntity tile, @Nullable EnumFacing side) {
		if (tile == null) {
			return null;
		}

		if (tile.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side)) {
			return tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side);
		}

		if (tile instanceof ISidedInventory) {
			return new SidedInvWrapper((ISidedInventory) tile, side);
		}

		if (tile instanceof IInventory) {
			return new InvWrapper((IInventory) tile);
		}

		return null;
	}
    
    public void writeToNBT(ItemStack[] backpackInventory,World world,ItemStack stack){
        if (!world.isRemote) { //server side only
        	if(stack.hasTagCompound()){
        		NBTTagCompound nbtTagCompound = stack.getTagCompound();

            // Write the ItemStacks in the inventory to NBT
            NBTTagList tagList = new NBTTagList();
            for (int i = 0; i < backpackInventory.length; i++) {
                if (backpackInventory[i] != null) {
                    NBTTagCompound tagCompound = new NBTTagCompound();
                    tagCompound.setByte("Slot", (byte) i);
                    backpackInventory[i].writeToNBT(tagCompound);
                    tagList.appendTag(tagCompound);
                }
            }
            nbtTagCompound.setTag("Items", tagList);
        }}
    }
    
    
    
    private void transferToChest(ItemStack[] backpackInventory, IItemHandler target,World world,ItemStack stack) {
    	System.out.println(backpackInventory);
    	for(int i =0;i<target.getSlots();i++){
			for(int j =0;i<backpackInventory.length;j++){
				
				if(backpackInventory[j] != null){
					ItemStack remainder = target.insertItem(i, backpackInventory[j], true);
					if(backpackInventory[j] != null){
						backpackInventory[j] = remainder;
					}
				}
				
			}
		}
		writeToNBT(backpackInventory,world,stack);
    	
	}

	private void receiveFromChest(ItemStack[] backpackInventory, IItemHandler target) {
		//ItemHandlerInventoryManipulator manipulator = new ItemHandlerInventoryManipulator();
		//manipulator.transferStacks(target,backpackInventory);
	}
	
	public ItemStack[] readFromNBT(ItemStack[] backpackInventory,World world,ItemStack stack){
        if (!world.isRemote) { //server side only
            if (stack != null) {
            	NBTTagCompound nbtTagCompound = stack.getTagCompound();

                if (nbtTagCompound != null) {

                    //load in items
                    if (nbtTagCompound.hasKey("Items")) {
                        NBTTagList tagList = nbtTagCompound.getTagList("Items", Constants.NBT.TAG_COMPOUND);
                        backpackInventory = new ItemStack[16];

                        for (int i = 0; i < tagList.tagCount(); i++) {
                            NBTTagCompound stackTag = tagList.getCompoundTagAt(i);
                            int j = stackTag.getByte("Slot");
                            if (i >= 0 && i <= backpackInventory.length) { //ToDo: Remove 2nd equals (so just less than) as per a 1.7.10 PR; test it
                                backpackInventory[j] = new ItemStack(stackTag);
                            }
                        }
                        return backpackInventory;
                    }
                }
            }
        }
		return new ItemStack[0];
    }
    
}
