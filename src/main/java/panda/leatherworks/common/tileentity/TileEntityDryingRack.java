package panda.leatherworks.common.tileentity;

import panda.leatherworks.common.crafting.DryingRecipes;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import panda.leatherworks.common.crafting.IDryingRecipe;

public class TileEntityDryingRack extends TileEntityItemRack implements ITickable {
	private int dryingTicks = 0;

	@Override
	public void update() {
		if (!getWorld().isRemote) {
			ItemStack input = inventory.getStackInSlot(0);
			IDryingRecipe recipe = DryingRecipes.getDryingRecipe(input);
			if (recipe != null) {
				if (++dryingTicks >= recipe.getDurationTicks(input)){
					ItemStack output = recipe.getOutput(input, getWorld().rand);
					this.inventory.setStackInSlot(0, output);
					dryingTicks = 0;
				}
				markDirty();
			} else if (dryingTicks != 0) {
				dryingTicks = 0;
				markDirty();
			}
		}
	} 

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("dryingTicks", dryingTicks);
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		dryingTicks = compound.getInteger("dryingTicks");
		super.readFromNBT(compound);
	}
}
