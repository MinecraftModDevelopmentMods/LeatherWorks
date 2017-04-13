package panda.leatherworks.util.recipe;


import com.google.common.collect.Lists;

import java.util.List;

import javax.annotation.Nullable;

import panda.leatherworks.items.armor.ItemBrokenArmor;
import panda.leatherworks.items.armor.ItemLeatherworksArmor;
import panda.leatherworks.util.registry.ItemList;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

public class RecipeRepair implements IRecipe
{
    /**
     * Used to check if a recipe matches current crafting inventory
     */
    public boolean matches(InventoryCrafting inv, World worldIn)
    {
    	List<ItemStack> list = Lists.<ItemStack>newArrayList();

        for (int i = 0; i < inv.getSizeInventory(); ++i)
        {
            ItemStack itemstack = inv.getStackInSlot(i);
            
            if (itemstack != null)
            {
                list.add(itemstack);

                if (list.size() == 2)
                {
                    ItemStack itemstack1 = (ItemStack)list.get(0);

                    	//System.out.println(itemstack1 +":"+itemstack);
                    	if(itemstack1 != null){
                    	Item a = itemstack.getItem();
                    	Item b = itemstack1.getItem();
                    	
                        if (((b instanceof ItemLeatherworksArmor && a == ItemList.REPAIR_KIT) ||(a instanceof ItemLeatherworksArmor && b == ItemList.REPAIR_KIT)) && (itemstack1.stackSize == 1 && itemstack.stackSize == 1))
                        {
                        	
                        	return true;
                        }
                    	}
                    
                }
            }
        }
        //System.out.println("recipe doesnt match");
        return false;
    }

    /**
     * Returns an Item that is the result of this recipe
     */
    @Nullable
    public ItemStack getCraftingResult(InventoryCrafting inv)
    {
    	List<ItemStack> list = Lists.<ItemStack>newArrayList();

        for (int i = 0; i < inv.getSizeInventory(); ++i)
        {
            ItemStack itemstack = inv.getStackInSlot(i);

            if (itemstack != null)
            {
                list.add(itemstack);

                if (list.size() > 1)
                {
                    ItemStack itemstack1 = (ItemStack)list.get(0);

                    if(itemstack1 != null){
                    	Item a = itemstack.getItem();
                    	Item b = itemstack1.getItem();
                    	
                        if (((b instanceof ItemLeatherworksArmor && a == ItemList.REPAIR_KIT) ||(a instanceof ItemLeatherworksArmor && b == ItemList.REPAIR_KIT)) && (itemstack1.stackSize == 1 && itemstack.stackSize == 1))
                        {
                        	
                        	continue;
                        }else{
                        	return null;
                        }
                    	}
                }
            }
        }

        if (list.size() == 2)
        {
        	
            ItemStack itemstack2 = (ItemStack)list.get(0);
            ItemStack itemstack3 = (ItemStack)list.get(1);

            if (itemstack2.getItem() == itemstack3.getItem()|| ((itemstack3.getItem() instanceof ItemLeatherworksArmor) && itemstack2.getItem() == ItemList.REPAIR_KIT) || (itemstack2.getItem() instanceof ItemLeatherworksArmor && itemstack3.getItem() == ItemList.REPAIR_KIT) && itemstack2.stackSize == 1 && itemstack3.stackSize == 1 )
            {
            	//System.out.println("recipe matches 2");
                int toRepair = 20;
                Item repairedItem = null;
               
            	
            	
            	Item a = itemstack3.getItem();
            	Item b = itemstack2.getItem();
            	ItemStack output;
            	if(a instanceof ItemLeatherworksArmor){
            		
            		int i1 = a.getDamage(itemstack3)-toRepair;

                    if (i1 < 0)
                    {
                        i1 = 0;
                    }

                    output = itemstack3.copy();
                    output.setItemDamage(i1);

                    return output;
                }else 
                if( b instanceof ItemLeatherworksArmor){
                	
                	int i1 = b.getDamage(itemstack2)-toRepair;

                    if (i1 < 0)
                    {
                        i1 = 0;
                    }

                    output = itemstack2.copy();
                    output.setItemDamage(i1);
                    
                    return output;
                    
                }
            	return null;}}return null;
    }

    /**
     * Returns the size of the recipe area
     */
    public int getRecipeSize()
    {
        return 4;
    }

    @Nullable
    public ItemStack getRecipeOutput()
    {
        return null;
    }

    public ItemStack[] getRemainingItems(InventoryCrafting inv)
    {
        ItemStack[] aitemstack = new ItemStack[inv.getSizeInventory()];

        for (int i = 0; i < aitemstack.length; ++i)
        {
            ItemStack itemstack = inv.getStackInSlot(i);
            aitemstack[i] = net.minecraftforge.common.ForgeHooks.getContainerItem(itemstack);
        }

        return aitemstack;
    }
}