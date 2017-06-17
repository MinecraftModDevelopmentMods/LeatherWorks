package panda.leatherworks.common.item;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBark extends Item {

	public ItemBark() {
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
			return super.getUnlocalizedName() + ".oak";
		case 1:
			return super.getUnlocalizedName() + ".spruce";
		case 2:
			return super.getUnlocalizedName() + ".birch";
		case 3:
			return super.getUnlocalizedName() + ".jungle";
		case 4:
			return super.getUnlocalizedName() + ".acacia";
		case 5:
			return super.getUnlocalizedName() + ".darkoak";
		}
		return super.getUnlocalizedName();
	}

}
