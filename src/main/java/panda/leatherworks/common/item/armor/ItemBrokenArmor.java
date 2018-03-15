package panda.leatherworks.common.item.armor;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import panda.leatherworks.LeatherWorks;
import panda.leatherworks.init.LWItems;

public class ItemBrokenArmor extends ItemArmor {

	private static final String DSP = "display";
	private static final String CLR = "color";

	public ItemBrokenArmor(EntityEquipmentSlot equipmentSlotIn) {
		super(LWItems.DUMMYLEATHER, 0, equipmentSlotIn);
		this.setMaxDamage(0);
	}

	@Override
	public boolean isDamageable(){
		return false;
	}
	
	@Override
	public boolean showDurabilityBar(ItemStack stack)
    {
        return false;
    }

	@Override
	public boolean hasColor(ItemStack stack)
	{
		NBTTagCompound nbttagcompound = stack.getTagCompound();
        return nbttagcompound != null && nbttagcompound.hasKey(DSP, 10) ? nbttagcompound.getCompoundTag(DSP).hasKey(CLR, 3) : false;
	}

	@Override
	public int getColor(ItemStack stack)
	{
		NBTTagCompound nbttagcompound = stack.getTagCompound();

        if (nbttagcompound != null)
        {
            NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag(DSP);

            if (nbttagcompound1 != null && nbttagcompound1.hasKey(CLR, 3))
            {
                return nbttagcompound1.getInteger(CLR);
            }
        }
        return 10511680;
	}

	@Override
	public void removeColor(ItemStack stack)
	{
		NBTTagCompound nbttagcompound = stack.getTagCompound();

        if (nbttagcompound != null)
        {
            NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag(DSP);

            if (nbttagcompound1.hasKey(CLR))
            {
                nbttagcompound1.removeTag(CLR);
            }
        }
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

		NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag(DSP);

		if (!nbttagcompound.hasKey(DSP, 10))
		{
			nbttagcompound.setTag(DSP, nbttagcompound1);
		}

		nbttagcompound1.setInteger(CLR, color);
	}
	
	@Override
	public boolean hasOverlay(ItemStack stack)
    {
        return true;
    }

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World player, List<String> list, ITooltipFlag advanced)
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
