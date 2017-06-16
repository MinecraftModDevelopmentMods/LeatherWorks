package panda.leatherworks.common.item.armor;

import java.util.List;

import panda.leatherworks.LeatherWorks;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBrokenArmor extends ItemArmor{

	public ItemBrokenArmor(EntityEquipmentSlot equipmentSlotIn) {
		super(LeatherWorks.DUMMYLEATHER, 0, equipmentSlotIn);
		this.setCreativeTab(LeatherWorks.LeatherTab);

	}
	@Override
	public boolean isDamageable(){
		return false;
	}
	

	public int getDamageReductionAmount(EntityEquipmentSlot armorType)
    {
        return 0;
    }
	
	@Override
	public boolean hasColor(ItemStack stack)
    {
            NBTTagCompound nbttagcompound = stack.getTagCompound();
            return (nbttagcompound != null && nbttagcompound.hasKey("display", 10))
				&& nbttagcompound.getCompoundTag("display").hasKey("color", 3);
    }

    /**
     * Return the color for the specified armor ItemStack.
     */
	@Override
    public int getColor(ItemStack stack)
    {

            NBTTagCompound nbttagcompound = stack.getTagCompound();

            if (nbttagcompound != null)
            {
                NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");

                if (nbttagcompound1 != null && nbttagcompound1.hasKey("color", 3))
                {
                    return nbttagcompound1.getInteger("color");
                }
            }

            return 10511680;
        
    }

    /**
     * Remove the color from the specified armor ItemStack.
     */
    @Override
    public void removeColor(ItemStack stack)
    {

            NBTTagCompound nbttagcompound = stack.getTagCompound();

            if (nbttagcompound != null)
            {
                NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");

                if (nbttagcompound1.hasKey("color"))
                {
                    nbttagcompound1.removeTag("color");
                }
            }
        
    }

    /**
     * Sets the color of the specified armor ItemStack
     */
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
    
    
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> list, boolean advanced)
    {
 	    if (stack.hasTagCompound())
 	    {
            NBTTagList nbttaglist = stack.getTagCompound().getTagList("altench", 10);

            if (nbttaglist != null)
            {
                for (int j = 0; j < nbttaglist.tagCount(); ++j)
                {
                    int k = nbttaglist.getCompoundTagAt(j).getShort("id");
                    int l = nbttaglist.getCompoundTagAt(j).getShort("lvl");

                    if (Enchantment.getEnchantmentByID(k) != null)
                    {
                        list.add(Enchantment.getEnchantmentByID(k).getTranslatedName(l));
                    }
                }
            }
 	    }
    }



}
