package panda.leatherworks.common.inventory;

import javax.annotation.Nullable;

import panda.leatherworks.common.item.ItemEnderPack;
import panda.leatherworks.common.item.ItemPack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ContainerPack extends Container {

	private World worldObj;

	public int currentSelection;
	EntityPlayer player;

	private IInventory inventory;

	public ContainerPack(InventoryPlayer playerInventory, World worldIn, BlockPos posIn,ItemStack item) {

		player = playerInventory.player;
		inventory = new InventoryPack(item.getDisplayName(),item,playerInventory.player);
		int i;
		//PACK INVENTORY
		for (i = 0; i < 16; ++i)
		{
			this.addSlotToContainer(new SlotPack(this.inventory, i, 54 + (18 * i/4), 18 + (18*(i%4))));
		}

		// PLAYER INVENTORY 
		for (i = 0; i < 3; ++i)
		{
			for (int j = 0; j < 9; ++j)
			{
				this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 124 + i * 18));
			}
		}

		// PLAYER HOT BAR 
		for (i = 0; i < 9; ++i)
		{
			this.addSlotToContainer(new Slot(playerInventory, i, 8 + i * 18, 182));
		}
	}


	@Nullable
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		ItemStack itemstack = null;
		Slot slot = this.inventorySlots.get(index);

		return itemstack;
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}

	@Override
	public void onContainerClosed(EntityPlayer player) {
		super.onContainerClosed(player);

		if (!player.world.isRemote) //server side
			( (InventoryPack) this.inventory).onGuiSaved(player);
	}

}

class SlotPack extends Slot
{
	public SlotPack(IInventory inv, int index, int xPos, int yPos)
	{
		super(inv, index, xPos, yPos);
	}

	@Override
	public boolean isItemValid(ItemStack itemstack)
	{
		return !(itemstack.getItem() instanceof ItemPack || itemstack.getItem() instanceof ItemEnderPack);
	}
}