package panda.leatherworks.util;

import net.minecraft.item.ItemStack;

public interface IInvSlot {

	boolean canPutStackInSlot(ItemStack stack);

	boolean canTakeStackFromSlot(ItemStack stack);

	ItemStack decreaseStackInSlot();

	/**
	 * It is not legal to edit the stack returned from this function.
	 */
	ItemStack getStackInSlot();

//    void setStackInSlot(ItemStack stack);

	int getIndex();

}