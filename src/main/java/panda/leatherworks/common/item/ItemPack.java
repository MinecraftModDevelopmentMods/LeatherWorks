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

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
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

        	 Block hit = world.getBlockState(player.rayTrace(3,1f).getBlockPos()).getBlock();
        	 if(!(hit instanceof BlockCauldron || hit instanceof BlockBarrel) &&!player.isSneaking() ){
        		 player.openGui(LeatherWorks.INSTANCE, GuiHandler.PACK_GUI, world, 0,0,0);
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
    	}
    	return ((double) full / total);
    }
}
