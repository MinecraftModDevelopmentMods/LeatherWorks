package panda.leatherworks.common.item;

import panda.leatherworks.LeatherWorks;
import panda.leatherworks.common.block.BlockBarrel;
import panda.leatherworks.init.LWBlocks;
import panda.leatherworks.init.LWItems;
import panda.leatherworks.common.GuiHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCauldron;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

//cache the fullness of the pack
public class ItemPack extends Item {

	public ItemPack() {
		this.setMaxStackSize(1);
		setNoRepair();
	}
	
	public static ItemStackHandler getHandlerForContainer(ItemStack stack) {
		if (stack.isEmpty() || !(stack.getItem() instanceof ItemPack)) return null;
		ItemStackHandler handler = new ItemStackHandler(16);
		if (stack.hasTagCompound()){
			handler.deserializeNBT(stack.getTagCompound().getCompoundTag("inv"));
		}
		return handler;
	}
    
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand handIn) {
    	ItemStack itemStack = player.getHeldItem(handIn);
//&& !world.isRemote
        	 if(!player.isSneaking() ){
        		 player.openGui(LeatherWorks.instance, GuiHandler.PACK_GUI, world, 0,0,0);
        		 return new ActionResult(EnumActionResult.PASS, itemStack);
        	 }
    	 return new ActionResult(EnumActionResult.FAIL, itemStack);
    }
    
    @Override
    public boolean showDurabilityBar(ItemStack stack) {
    	if(stack.hasTagCompound()){
    		NBTTagCompound nbtTagCompound = stack.getTagCompound();
    		if (nbtTagCompound.hasKey("inv")) {
    			return true;
    		}
    	}
    	return false;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
    	int total = 0;
    	int full = 0;
    	ItemStackHandler handler = getHandlerForContainer(stack);
    	if(handler != null){
    		for(int i = 0; i < handler.getSlots(); i++){
    			ItemStack tempStack = handler.getStackInSlot(i);
    			if (!tempStack.isEmpty()) {
    				full += tempStack.getCount();
    				total += tempStack.getMaxStackSize();
    			} else {
    				total += 64;
    			}
    		}
    		return ((double) full / total);
    	}else{
    		return 0;
    	}
    		
    	
    }

	public static ItemStack getColoredItemStack(EnumDyeColor colorIn) {
		 return new ItemStack(getPackByColor(colorIn));
	}
	
    public static Item getPackByColor(EnumDyeColor colorIn)
    {
        switch (colorIn)
        {
            case ORANGE:
                return LWItems.PACK_ORANGE;
            case MAGENTA:
                return LWItems.PACK_MAGENTA;
            case LIGHT_BLUE:
                return LWItems.PACK_LIGHT_BLUE;
            case YELLOW:
                return LWItems.PACK_YELLOW;
            case LIME:
                return LWItems.PACK_LIME;
            case PINK:
                return LWItems.PACK_PINK;
            case GRAY:
                return LWItems.PACK_GRAY;
            case SILVER:
                return LWItems.PACK_SILVER;
            case CYAN:
                return LWItems.PACK_CYAN;
            case BLUE:
                return LWItems.PACK_BLUE;
            case BROWN:
            default:
                return LWItems.PACK_BROWN;
            case GREEN:
                return LWItems.PACK_GREEN;
            case RED:
                return LWItems.PACK_RED;
            case BLACK:
                return LWItems.PACK_BLACK;
            case PURPLE:
                return LWItems.PACK_PURPLE;
        }
    }
}
