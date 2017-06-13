package panda.leatherworks.common.crafting;

import com.google.common.collect.Lists;

import java.util.List;

import javax.annotation.Nullable;

import panda.leatherworks.common.item.armor.ItemBrokenArmor;
import panda.leatherworks.init.LWItems;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;


//TODO class is messy AF. fix this.
public class RecipeRepairLeatherArmor implements IRecipe
{	
    /**
     * Used to check if a recipe matches current crafting inventory
     */
    public boolean matches(InventoryCrafting inv, World worldIn)
    {
        List<ItemStack> list = Lists.newArrayList();

        for (int i = 0; i < inv.getSizeInventory(); ++i)
        {
            ItemStack itemstack = inv.getStackInSlot(i);
            
            if (itemstack != null)
            {
                list.add(itemstack);

                if (list.size() == 2)
                {
                    ItemStack itemstack1 = list.get(0);

                    	//System.out.println(itemstack1 +":"+itemstack);
                    	if(itemstack1 != null){
                    	Item a = itemstack.getItem();
                    	Item b = itemstack1.getItem();
                    	
                        if (((b instanceof ItemBrokenArmor && a == LWItems.REPAIR_KIT) ||(a instanceof ItemBrokenArmor && b == LWItems.REPAIR_KIT)) && (itemstack1.stackSize == 1 && itemstack.stackSize == 1))
                        {
                        	//System.out.println("recipe matches");
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
        List<ItemStack> list = Lists.newArrayList();

        for (int i = 0; i < inv.getSizeInventory(); ++i)
        {
            ItemStack itemstack = inv.getStackInSlot(i);

            if (itemstack != null)
            {
                list.add(itemstack);

                if (list.size() > 1)
                {
                    ItemStack itemstack1 = list.get(0);

                    if(itemstack1 != null){
                    	Item a = itemstack.getItem();
                    	Item b = itemstack1.getItem();
                    	
                        if (((b instanceof ItemBrokenArmor && a == LWItems.REPAIR_KIT) ||(a instanceof ItemBrokenArmor && b == LWItems.REPAIR_KIT)) && (itemstack1.stackSize == 1 && itemstack.stackSize == 1))
                        {
                        	//System.out.println("recipe matches 2");
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
        	
            ItemStack itemstack2 = list.get(0);
            ItemStack itemstack3 = list.get(1);

            
            
            if (itemstack2.getItem() == itemstack3.getItem()|| ((itemstack3.getItem() instanceof ItemBrokenArmor) && itemstack2.getItem() == LWItems.REPAIR_KIT) || (itemstack2.getItem() instanceof ItemBrokenArmor && itemstack3.getItem() == LWItems.REPAIR_KIT) && itemstack2.stackSize == 1 && itemstack3.stackSize == 1 )
            {
            	
                int toRepair = 20;
                Item repairedItem = null;
               
            	
            	
            	Item a = itemstack3.getItem();
            	Item b = itemstack2.getItem();
            	ItemStack output;
            	if(a instanceof ItemBrokenArmor){
            		
            		repairedItem =determineOutput(a);
            		
            		
            		int i1 = repairedItem.getMaxDamage()-toRepair;

                    if (i1 < 0)
                    {
                        i1 = 0;
                    }

                    output = itemstack3.copy();
                    output.setItem(repairedItem);
                    output.setItemDamage(i1);
                    if(itemstack3.hasTagCompound()){
                    NBTTagList tags = itemstack3.getTagCompound().getTagList("altench", 10);
                    if(!tags.hasNoTags()){
    	            NBTTagList enchantments = itemstack3.getEnchantmentTagList();
    	            int count = tags.tagCount();
    	            for(int j=0;j<count;j++){
    	            	enchantments.appendTag(tags.removeTag(j));
    	                --j;
    	                --count;
    	            }
    	            
    	            output.getTagCompound().removeTag("altench");
                    }}
                    return output;
                }else 
                if( b instanceof ItemBrokenArmor){
                	repairedItem =determineOutput(b);
                	
                	int i1 = repairedItem.getMaxDamage()-toRepair;

                    if (i1 < 0)
                    {
                        i1 = 0;
                    }

                    output = itemstack2.copy();
                    output.setItem(repairedItem);
                    output.setItemDamage(i1);
                    if(itemstack2.hasTagCompound()){
    	            NBTTagList tags = itemstack2.getTagCompound().getTagList("altench", 10);
    	            if(!tags.hasNoTags()){
    	            NBTTagList enchantments = itemstack2.getEnchantmentTagList();
    	            int count = tags.tagCount();
    	            for(int j=0;j<count;j++){
    	            	enchantments.appendTag(tags.removeTag(j));
    	                --j;
    	                --count;
    	            }
    	            
    	            output.getTagCompound().removeTag("altench");
                    }}
                    return output;
                    
                }
            	return null;
                	
            	
                	
            	
            	
            	

                
            }
        }

        return null;
    }

    private Item determineOutput(Item a) {
		if(a== LWItems.BROKEN_LEATHER_HELMET){
			return Items.LEATHER_HELMET;
		}else
		if(a== LWItems.BROKEN_LEATHER_CHESTPLATE){
			return Items.LEATHER_CHESTPLATE;
				
		}else
		if(a== LWItems.BROKEN_LEATHER_LEGGINGS){
			return Items.LEATHER_LEGGINGS;
					
		}else
		if(a== LWItems.BROKEN_LEATHER_BOOTS){
			return Items.LEATHER_BOOTS;
						
		}
		return null;
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