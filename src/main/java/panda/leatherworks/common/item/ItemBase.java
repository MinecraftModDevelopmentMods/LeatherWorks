package panda.leatherworks.common.item;

import net.minecraft.item.Item;
import panda.leatherworks.LeatherWorks;

public class ItemBase extends Item {

	public ItemBase(String name) {
		super();
		this.setCreativeTab(LeatherWorks.LeatherTab);
		setRegistryName(name);
	}
}
