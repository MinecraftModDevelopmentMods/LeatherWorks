package panda.leatherworks.common;

import panda.leatherworks.ConfigLeatherWorks;
import panda.leatherworks.common.item.ItemPack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
//This code modified from
//https://github.com/Shadows-of-Fire/EnderBags/blob/master/src/main/java/gigabit101/EnderBags/container/ContainerEnderBag.java
public class ContainerPack extends Container {

	private ItemStackHandler inv;
	private EnumHand bagHand;
	private ItemStack bag;


	public ContainerPack(EntityPlayer player) {

		bag = player.getHeldItemMainhand();
		bagHand = EnumHand.MAIN_HAND;
		if (bag.isEmpty() || !(bag.getItem()  instanceof ItemPack)) {
			bag = player.getHeldItemOffhand();
			bagHand = EnumHand.OFF_HAND;
		}

		inv = ItemPack.getHandlerForContainer(bag);

		int j;
		int k;

		for (j = 0; j < 4; ++j) {
			for (k = 0; k < 4; ++k) {
				this.addSlotToContainer(new SlotItemHandler(inv, k + j * 4, 54 + k * 18, 18 + j * 18) {
					@Override
					public boolean isItemValid(ItemStack stack) {
						return !ConfigLeatherWorks.isBlacklistedFromBag(stack);
					}
				});
			}
		}

		for (j = 0; j < 3; ++j) {
			for (k = 0; k < 9; ++k) {
				this.addSlotToContainer(new Slot(player.inventory, k + j * 9 + 9, 8 + k * 18, 124 + j * 18));
			}
		}

		for (j = 0; j < 9; ++j) {
			if (player.inventory.currentItem == j) {
				this.addSlotToContainer(new SlotLocked(player.inventory, j, 8 + j * 18, 182));
			} else {
				addSlotToContainer(new Slot(player.inventory, j, 8 + j * 18, 182));
			}
		}
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
		ItemStack originalStack = ItemStack.EMPTY;
		Slot slot = inventorySlots.get(slotIndex);
		int numSlots = inventorySlots.size();
		if (slot != null && slot.getHasStack()) {
			ItemStack stackInSlot = slot.getStack();
			originalStack = stackInSlot.copy();
			if (slotIndex >= numSlots - 9 * 4 && tryShiftItem(stackInSlot, numSlots)) {
				// NOOP
			} else if (slotIndex >= numSlots - 9 * 4 && slotIndex < numSlots - 9) {
				if (!shiftItemStack(stackInSlot, numSlots - 9, numSlots)) { return ItemStack.EMPTY; }
			} else if (slotIndex >= numSlots - 9 && slotIndex < numSlots) {
				if (!shiftItemStack(stackInSlot, numSlots - 9 * 4, numSlots - 9)) { return ItemStack.EMPTY; }
			} else if (!shiftItemStack(stackInSlot, numSlots - 9 * 4, numSlots)) { return ItemStack.EMPTY; }
			slot.onSlotChange(stackInSlot, originalStack);
			if (stackInSlot.getCount() <= 0) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}
			if (stackInSlot.getCount() == originalStack.getCount()) { return ItemStack.EMPTY; }
			slot.onTake(player, stackInSlot);
		}
		return originalStack;
	}

	protected boolean shiftItemStack(ItemStack stackToShift, int start, int end) {
		boolean changed = false;
		if (stackToShift.isStackable()) {
			for (int slotIndex = start; stackToShift.getCount() > 0 && slotIndex < end; slotIndex++) {
				Slot slot = inventorySlots.get(slotIndex);
				ItemStack stackInSlot = slot.getStack();
				if (!stackInSlot.isEmpty() && canStacksMerge(stackInSlot, stackToShift)) {
					int resultingStackSize = stackInSlot.getCount() + stackToShift.getCount();
					int max = Math.min(stackToShift.getMaxStackSize(), slot.getSlotStackLimit());
					if (resultingStackSize <= max) {
						stackToShift.setCount(0);
						stackInSlot.setCount(resultingStackSize);
						slot.onSlotChanged();
						changed = true;
					} else if (stackInSlot.getCount() < max) {
						stackToShift.shrink(max - stackInSlot.getCount());
						stackInSlot.setCount(max);
						slot.onSlotChanged();
						changed = true;
					}
				}
			}
		}
		if (stackToShift.getCount() > 0) {
			for (int slotIndex = start; stackToShift.getCount() > 0 && slotIndex < end; slotIndex++) {
				Slot slot = inventorySlots.get(slotIndex);
				ItemStack stackInSlot = slot.getStack();
				if (stackInSlot.isEmpty()) {
					int max = Math.min(stackToShift.getMaxStackSize(), slot.getSlotStackLimit());
					stackInSlot = stackToShift.copy();
					stackInSlot.setCount(Math.min(stackToShift.getCount(), max));
					stackToShift.shrink(stackInSlot.getCount());
					slot.putStack(stackInSlot);
					slot.onSlotChanged();
					changed = true;
				}
			}
		}
		return changed;
	}

	private boolean tryShiftItem(ItemStack stackToShift, int numSlots) {
		for (int machineIndex = 0; machineIndex < numSlots - 9 * 4; machineIndex++) {
			Slot slot = inventorySlots.get(machineIndex);
			if (!slot.isItemValid(stackToShift)) continue;
			if (shiftItemStack(stackToShift, machineIndex, machineIndex + 1)) return true;
		}
		return false;
	}

	public static boolean canStacksMerge(ItemStack stack1, ItemStack stack2) {
		return !(stack1.isEmpty() || stack2.isEmpty() 
			|| !stack1.isItemEqual(stack2) || !ItemStack.areItemStackTagsEqual(stack1, stack2));
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return !bag.isEmpty() && player.getHeldItem(bagHand).getItem() instanceof ItemPack;
	}

	@Override
	public void onContainerClosed(EntityPlayer player) {
		if (!bag.hasTagCompound()) bag.setTagCompound(new NBTTagCompound());
		bag.getTagCompound().setTag("inv", inv.serializeNBT());
		super.onContainerClosed(player);
	}


}

class SlotLocked extends Slot {
	public SlotLocked(IInventory inv, int index, int x, int y) {
		super(inv, index, x, y);
	}

	@Override
	public boolean canTakeStack(EntityPlayer player) {
		return false;
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return false;
	}
}