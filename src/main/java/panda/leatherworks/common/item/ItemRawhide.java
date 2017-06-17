package panda.leatherworks.common.item;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemRawhide extends Item {

	public ItemRawhide() {
		this.setHasSubtypes(true);
	}

	@SideOnly(Side.CLIENT)
	public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
		for(int i = 0; i < 6; i++){
			list.add(new ItemStack(itemIn, 1, i));
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		int metadata = stack.getMetadata();
		switch(metadata){
		case 0:
			return super.getUnlocalizedName() + ".cow";
		case 1:
			return super.getUnlocalizedName() + ".pig";
		case 2:
			return super.getUnlocalizedName() + ".horse";
		case 3:
			return super.getUnlocalizedName() + ".wolf";
		case 4:
			return super.getUnlocalizedName() + ".polarbear";
		case 5:
			return super.getUnlocalizedName() + ".mooshroom";
		}
		return super.getUnlocalizedName();
	}

}
