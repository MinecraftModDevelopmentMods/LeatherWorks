package panda.leatherworks.common.item;

import java.util.List;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import panda.leatherworks.util.IMeta;

public class ItemRawhide extends ItemBase implements IMeta{

	public ItemRawhide() {
		super("rawhide");
		this.setHasSubtypes(true);

	}

	@Override
	public int getMaxMeta() {
		return 6;
	}

	@Override
	public List<ModelResourceLocation> getMetaModelLocations(List<ModelResourceLocation> map) {
		for(int i = 0; i<getMaxMeta();i++){
			map.add(new ModelResourceLocation(getRegistryName(), "meta="+i));
		}
		return map;
	}
	
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
		for(int i = 0; i<getMaxMeta();i++){
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
