package panda.leatherworks.common.item.armor;

import java.util.Random;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class ItemLeatherworksArmor extends ItemArmor implements ISpecialArmor {

	private Item brokenarmor;

	public ItemLeatherworksArmor(ArmorMaterial materialIn,EntityEquipmentSlot equipmentSlotIn, Item brokenArmor) {
		super(materialIn, 0, equipmentSlotIn);
		this.brokenarmor = brokenArmor;
	}

	@Override
	public void damageArmor(EntityLivingBase entity, ItemStack stack,DamageSource source, int damage, int slot) {
		
		if(stack.getItemDamage() >= stack.getMaxDamage()-1){//-1
			if(entity instanceof EntityPlayer){
				
				NBTTagList tags = stack.getEnchantmentTagList();
				int count = tags.tagCount();
				Random rand = entity.world.rand;
				if(tags.hasNoTags()){
					((EntityPlayer)entity).inventory.armorInventory.set(slot, ItemStack.EMPTY);
					ItemStack StackOut = new ItemStack(brokenarmor,1,0);
						if(stack.hasTagCompound()){
							StackOut.setTagCompound(stack.getTagCompound().copy());
						  }
					((EntityPlayer)entity).inventory.armorInventory.set(slot, StackOut);
					return;
				}else{
					for(int i = 0; i < count; i++){
						int num = rand.nextInt(2);
						if(num == 0){
							//IF our level is > 1, reduce it
							int level = tags.getCompoundTagAt(i).getShort("lvl");
							if(level > 1){
								tags.getCompoundTagAt(i).setShort("lvl", (short) (level-1));
							}else{//delete the enchant
								tags.removeTag(i);
								--i;
								--count;
							}
						}
					}

					//remove real list, copy tags to fake NBT so the effects are not applied
					stack.getTagCompound().setTag("altench", new NBTTagList());
					NBTTagList newtags = stack.getTagCompound().getTagList("altench", 10);

					count = tags.tagCount();
					for(int j = 0; j < count; j++){
						newtags.appendTag(tags.removeTag(j));
						--j;
						--count;
					}

					((EntityPlayer)entity).inventory.armorInventory.set(slot, ItemStack.EMPTY);
					ItemStack StackOut = new ItemStack(brokenarmor,1,0);
						StackOut.setTagCompound(stack.getTagCompound().copy());
					((EntityPlayer)entity).inventory.armorInventory.set(slot, StackOut);
				}
			}
		}else{
			stack.damageItem(damage, entity);
		}
	}
	
	@Override
	public ArmorProperties getProperties(EntityLivingBase player,ItemStack armor, DamageSource source, double damage, int slot) {
		return new ArmorProperties(0, this.damageReduceAmount / 25D, Integer.MAX_VALUE);
	}
	
	@Override
	public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
		return this.damageReduceAmount;
	}
	
	@Override
	public void setColor(ItemStack stack, int color)
	{
		NBTTagCompound nbttagcompound = stack.getTagCompound();

		if (nbttagcompound == null)
		{
			nbttagcompound = new NBTTagCompound();
			stack.setTagCompound(nbttagcompound);
		}

		NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");

		if (!nbttagcompound.hasKey("display", 10))
		{
			nbttagcompound.setTag("display", nbttagcompound1);
		}

		nbttagcompound1.setInteger("color", color);
	}
}
