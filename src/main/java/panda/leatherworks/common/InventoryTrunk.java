package panda.leatherworks.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import panda.leatherworks.LeatherWorks;
import panda.leatherworks.common.tileentity.TileEntityTrunk;

public class InventoryTrunk extends Container{


    private final TileEntityTrunk trunkentity;
    private static final int NUMSLOTS = 27;

    public InventoryTrunk(InventoryPlayer invPlayer, TileEntityTrunk tileentitytrunk)
    {
    	trunkentity = tileentitytrunk;
    	final int HOTBAR_XPOS = 8;
		final int HOTBAR_YPOS = 164;
    	for (int x = 0; x < 9; x++) {
			int slotNumber = x;
			addSlotToContainer(new Slot(invPlayer, slotNumber, HOTBAR_XPOS + 18* x, HOTBAR_YPOS));
		}

		final int PLAYER_INVENTORY_XPOS = 8;
		final int PLAYER_INVENTORY_YPOS = 106;
		// Add the rest of the players inventory to the gui
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 9; x++) {
				int slotNumber = 9 + y * 9+ x;
				int xpos = PLAYER_INVENTORY_XPOS + x * 18;
				int ypos = PLAYER_INVENTORY_YPOS + y * 18;
				addSlotToContainer(new Slot(invPlayer, slotNumber,  xpos, ypos));
			}
		}

		if (NUMSLOTS != trunkentity.getSizeInventory()) {
			LeatherWorks.logger.error("Mismatched slot count in Container(" + NUMSLOTS+ ") and TileInventory (" + trunkentity.getSizeInventory()+")");
		}
		final int TILE_INVENTORY_XPOS = 8;
		final int TILE_INVENTORY_YPOS = 18;
		// Add the tile inventory container to the gui
		for (int x = 0; x < NUMSLOTS; x++) {
			int slotNumber = x;
			addSlotToContainer(new Slot(trunkentity, slotNumber, TILE_INVENTORY_XPOS + 18*(x%9), TILE_INVENTORY_YPOS + 18*(x/9)));
		}
    }

	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return trunkentity.isUsableByPlayer(player);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int sourceSlotIndex)
	{
		Slot sourceSlot = inventorySlots.get(sourceSlotIndex);
		if (sourceSlot == null || !sourceSlot.getHasStack()) return ItemStack.EMPTY;  //EMPTY_ITEM
		ItemStack sourceStack = sourceSlot.getStack();
		ItemStack copyOfSourceStack = sourceStack.copy();

		if (sourceSlotIndex >= 0 && sourceSlotIndex < 0 + 27) {

			if (!mergeItemStack(sourceStack, 27, 27+ NUMSLOTS, false)){
				return ItemStack.EMPTY;
			}
		} else if (sourceSlotIndex >= 27 && sourceSlotIndex < 27 + NUMSLOTS) {
			if (!mergeItemStack(sourceStack, 0, 27, false)) {
				return ItemStack.EMPTY;
			}
		} else {
			LeatherWorks.logger.error("Invalid slotIndex:" + sourceSlotIndex);
			return ItemStack.EMPTY;
		}

		if (sourceStack.getCount() == 0) {
			sourceSlot.putStack(ItemStack.EMPTY);
		} else {
			sourceSlot.onSlotChanged();
		}

		sourceSlot.onTake(player, sourceStack);
		return copyOfSourceStack;
	}
	
    @Override
	public void onContainerClosed(EntityPlayer playerIn)
	{
		super.onContainerClosed(playerIn);
		this.trunkentity.closeInventory(playerIn);
	}

}
