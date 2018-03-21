package panda.leatherworks.common.crafting;

import javax.annotation.Nonnull;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry.Impl;
import panda.leatherworks.LeatherWorks;

public class RepairKitRecipe extends Impl<IRecipe> implements IRecipe {

private Item brokenArmor;
private int repairValue;
private Item repairedArmor;
private Item repairKit;

public RepairKitRecipe(Item itemIn,Item itemOut,Item repairItem, int value) {
	this.brokenArmor = itemIn;
	this.repairedArmor = itemOut;
	this.repairValue = value;
	this.repairKit = repairItem;
    this.setRegistryName(new ResourceLocation(LeatherWorks.MODID,"repairkit_"+itemIn.getRegistryName().getResourcePath()));
  }
  
  @Override
  public boolean matches(@Nonnull InventoryCrafting inv, @Nonnull World worldIn) {
	  int numPacks = 0;
	  int c = inv.getSizeInventory();
	  int count = 0;
	  for(int i = 0; i < c; i++){
		  if(inv.getStackInSlot(i).getItem() == brokenArmor){
			  count++;
		  }
		  if(inv.getStackInSlot(i).getItem() == repairKit){
			  numPacks++;
		  }
	  }
	return  count == 1 && numPacks >= 1;
  }

  @Nonnull
  @Override
  public ItemStack getCraftingResult(@Nonnull InventoryCrafting inv) {
	  int numPacks = 0;
	  int armorcount = 0;
	  ItemStack oldArmor = ItemStack.EMPTY;
	for(int i = 0; i < inv.getSizeInventory(); i++){
		  ItemStack stack = inv.getStackInSlot(i);
		  if(stack.getItem() == repairKit){
			  numPacks++;
		  }
		  if(stack.getItem() == brokenArmor){
			  oldArmor = stack;
			  armorcount++;
		  }
	  }
	if(armorcount!=1){
		return ItemStack.EMPTY;
	}
	int newdamage = 0;
	if(this.repairedArmor == brokenArmor){
		newdamage = brokenArmor.getDamage(oldArmor)-this.repairValue*numPacks;
	}else{
		newdamage = this.repairedArmor.getMaxDamage()-this.repairValue*numPacks;
	}
	  ItemStack stackReturn = new ItemStack(this.repairedArmor,1,newdamage);
	  if(oldArmor.hasTagCompound()){
		  NBTTagList dummytags = oldArmor.getTagCompound().getTagList("altench", 10);
		    
		  NBTTagList enchantTag = oldArmor.getTagCompound().getTagList("ench", 10);
		  
		  for(int i = 0; i < dummytags.tagCount(); i++){
			  enchantTag.appendTag(dummytags.get(i));
		  }
		  oldArmor.getTagCompound().removeTag("altench");
		  stackReturn.setTagCompound(oldArmor.getTagCompound().copy());
	  }
	  return stackReturn;
  }
  
  @Nonnull
  @Override
  public ItemStack getRecipeOutput() {
    return new ItemStack(repairedArmor);
  }

  @Override
  public NonNullList<ItemStack> getRemainingItems(@Nonnull InventoryCrafting inv) {
    return NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);
  }

  @Override
  public boolean canFit(int width, int height) {
    return width * height >= 2;
  }

}