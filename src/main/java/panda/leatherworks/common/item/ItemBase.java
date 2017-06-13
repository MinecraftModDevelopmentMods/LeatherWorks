package panda.leatherworks.common.item;

import net.minecraft.item.Item;
import panda.leatherworks.LeatherWorks;

/**
 * Using a base class for items limits inheritance of other subclasses of Item.
 * The functionality of this constructor can easily be added to an external method, perhaps applied
 * when items are registered.
 */
@Deprecated
public class ItemBase extends Item {

	public ItemBase(String name) {
		super();
		this.setCreativeTab(LeatherWorks.LeatherTab);
		setRegistryName(name);
	}
}
