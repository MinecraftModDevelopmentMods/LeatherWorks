package panda.leatherworks.common.crafting;

import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.oredict.DyeUtils;
import net.minecraftforge.registries.IForgeRegistryEntry.Impl;
import panda.leatherworks.LeatherWorks;
import panda.leatherworks.common.block.BlockTrunk;

public class LeatherTrunkColoring extends Impl<IRecipe> implements IRecipe{
	
	public LeatherTrunkColoring() {
	    this.setRegistryName(new ResourceLocation(LeatherWorks.MODID,"leathertrunkcoloring"));
	  }
	/**
	 * Used to check if a recipe matches current crafting inventory
	 */
	public boolean matches(InventoryCrafting inv, World worldIn)
	{
		int i = 0;
		int j = 0;

		for (int k = 0; k < inv.getSizeInventory(); ++k)
		{
			ItemStack itemstack = inv.getStackInSlot(k);

			if (!itemstack.isEmpty())
			{
				if (Block.getBlockFromItem(itemstack.getItem()) instanceof BlockTrunk)

				{
					++i;
				}
				else
				{
					if (!DyeUtils.isDye(itemstack))
					{
						return false;
					}

					++j;
				}

				if (j > 1 || i > 1)
				{
					return false;
				}
			}
		}

		return i == 1 && j == 1;
	}

	/**
	 * Returns an Item that is the result of this recipe
	 */
	public ItemStack getCraftingResult(InventoryCrafting inv)
	{
		ItemStack itemstack = ItemStack.EMPTY;
		ItemStack itemstack1 = ItemStack.EMPTY;

		for (int i = 0; i < inv.getSizeInventory(); ++i)
		{
			ItemStack itemstack2 = inv.getStackInSlot(i);

			if (!itemstack2.isEmpty())
			{
				if (Block.getBlockFromItem(itemstack2.getItem()) instanceof BlockTrunk)
				{
					itemstack = itemstack2;
				}
				else if (DyeUtils.isDye(itemstack2))
				{
					itemstack1 = itemstack2;
				}
			}
		}

		ItemStack itemstack3 = BlockTrunk.getColoredItemStack(DyeUtils.colorFromStack(itemstack1).get());

		if (itemstack.hasTagCompound())
		{
			itemstack3.setTagCompound(itemstack.getTagCompound().copy());
		}

		return itemstack3;
	}

	/**
	 * Get the result of this recipe, usually for display purposes (e.g. recipe book). If your recipe has more
	 * than one possible result (e.g. it's dynamic and depends on its inputs), then return an empty stack.
	 */
	public ItemStack getRecipeOutput()
	{
		return ItemStack.EMPTY;
	}

	@Override
	public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv)
	{
		NonNullList<ItemStack> nonnulllist = NonNullList.<ItemStack>withSize(inv.getSizeInventory(), ItemStack.EMPTY);

		for (int i = 0; i < nonnulllist.size(); ++i)
		{
			ItemStack itemstack = inv.getStackInSlot(i);

			if (itemstack.getItem().hasContainerItem())
			{
				nonnulllist.set(i, new ItemStack(itemstack.getItem().getContainerItem()));
			}
		}

		return nonnulllist;
	}

	/**
	 * If true, this recipe does not appear in the recipe book and does not respect recipe unlocking (and the
	 * doLimitedCrafting gamerule)
	 */
	@Override
	public boolean isDynamic()
	{
		return true;
	}

	/**
	 * Used to determine if this recipe can fit in a grid of the given width/height
	 */
	public boolean canFit(int width, int height)
	{
		return width * height >= 2;
	}
}
