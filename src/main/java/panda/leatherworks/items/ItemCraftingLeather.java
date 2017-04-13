package panda.leatherworks.items;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import panda.leatherworks.util.registry.IMeta;

public class ItemCraftingLeather extends ItemBase implements IMeta{

	public ItemCraftingLeather() {
		super("craftingleather");
		this.setHasSubtypes(true);

	}

	@Override
	public int getMaxMeta() {
		return 3;
	}

	@Override
	public List<ModelResourceLocation> getMetaModelLocations(List<ModelResourceLocation> map) {
		for(int i = 0; i<getMaxMeta();i++){
			map.add(new ModelResourceLocation(getRegistryName(), "meta="+i));
		}
		return map;
	}
	
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item itemIn, CreativeTabs tab, List list) {
		for(int i = 0; i<getMaxMeta();i++){
			list.add(new ItemStack(itemIn, 1, i));
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		int metadata = stack.getMetadata();
		switch(metadata){
		case 0:
			return super.getUnlocalizedName() + ".scraped";
		case 1:
			return super.getUnlocalizedName() + ".washed";
		case 2:
			return super.getUnlocalizedName() + ".soaked";
		}
		return super.getUnlocalizedName();
	}

}
