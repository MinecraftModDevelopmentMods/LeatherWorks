package panda.leatherworks.common.item;

import java.util.List;

import javax.annotation.Nullable;

import panda.leatherworks.LeatherWorks;
import panda.leatherworks.common.block.BlockBarrel;
import panda.leatherworks.common.GuiHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCauldron;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.EnumDyeColor;
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
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

//TODO Rework this. It is using a static variable for the inventory. It will not work.
public class ItemPack extends Item {
	
	private static final String TileUtil = null;
	static ItemStack[] Packinventory;

	public ItemPack() {
		this.setHasSubtypes(true);
		this.setMaxStackSize(1);
		setNoRepair();
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return Integer.MAX_VALUE;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
	{
		list.add("WARNING: Filling more than one pack in an inventory is not always item safe.");
		list.add("Do so at your own risk");
		
	}

	@SideOnly(Side.CLIENT)
	public void getSubItems(Item itemIn, CreativeTabs tab, List list) {
		for(int i = 0; i < 15; i++){
			list.add(new ItemStack(itemIn, 1, i));
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		int metadata = stack.getMetadata();
		EnumDyeColor color = null;
		switch(metadata){
		case 0:
			return super.getUnlocalizedName() + ".undyed";
		case 1:
			return super.getUnlocalizedName() + "."+EnumDyeColor.RED.getName().toLowerCase();
		case 2:
			return super.getUnlocalizedName() + "."+EnumDyeColor.ORANGE.getName().toLowerCase();
		case 3:
			return super.getUnlocalizedName() + "."+EnumDyeColor.YELLOW.getName().toLowerCase();
		case 4:
			return super.getUnlocalizedName() + "."+EnumDyeColor.LIME.getName().toLowerCase();
		case 5:
			return super.getUnlocalizedName() + "."+EnumDyeColor.GREEN.getName().toLowerCase();
		case 6:
			return super.getUnlocalizedName() + "."+EnumDyeColor.CYAN.getName().toLowerCase();
		case 7:
			return super.getUnlocalizedName() + "."+EnumDyeColor.LIGHT_BLUE.getName().toLowerCase();
		case 8:
			return super.getUnlocalizedName() + "."+EnumDyeColor.BLUE.getName().toLowerCase();
		case 9:
			return super.getUnlocalizedName() + "."+EnumDyeColor.MAGENTA.getName().toLowerCase();
		case 10:	
			return super.getUnlocalizedName() + "."+EnumDyeColor.PURPLE.getName().toLowerCase();
		case 11:
			return super.getUnlocalizedName() + "."+EnumDyeColor.PINK.getName().toLowerCase();
		case 12:
			return super.getUnlocalizedName() + "."+EnumDyeColor.SILVER.getName().toLowerCase();
		case 13:
			return super.getUnlocalizedName() + "."+EnumDyeColor.GRAY.getName().toLowerCase();
		case 14:
			return super.getUnlocalizedName() + "."+EnumDyeColor.BLACK.getName().toLowerCase();
		}
		return null;
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
                            	Packinventory[slot] = ItemStack.loadItemStackFromNBT(stackTag);
                        }
                        for (ItemStack tempStack : Packinventory) {
                            if (tempStack != null) {
                                full += tempStack.stackSize;
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
	public EnumActionResult onItemUseFirst(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
		// We only do this when shift is clicked
		if (player.isSneaking()) {
			
			//return evaluateTileHit(stack, player, world, pos, side) ? EnumActionResult.PASS : EnumActionResult.FAIL;
		}

		return super.onItemUseFirst(stack, player, world, pos, side, hitX, hitY, hitZ, hand);
	}
    
    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStack, World world, EntityPlayer player, EnumHand hand) {
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
        		 player.openGui(LeatherWorks.INSTANCE, GuiHandler.Pack_GUI, world, (int) player.posX, (int) player.posY, (int) player.posZ);
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
                                backpackInventory[j] = ItemStack.loadItemStackFromNBT(stackTag);
                            }
                        }
                        return backpackInventory;
                    }
                }
            }
        }
		return null;
    }
    
}
