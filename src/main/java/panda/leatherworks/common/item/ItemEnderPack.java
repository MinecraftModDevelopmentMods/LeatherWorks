package panda.leatherworks.common.item;

import net.minecraft.item.Item;
import panda.leatherworks.LeatherWorks;
import panda.leatherworks.common.GuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.InventoryEnderChest;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemEnderPack extends Item {

	public ItemEnderPack() {
		this.setMaxStackSize(1);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
		InventoryEnderChest inventoryenderchest = playerIn.getInventoryEnderChest();
		if (worldIn.isRemote) { //client side
			playerIn.playSound(SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 1F, 1F);
		} else {
			 playerIn.openGui(LeatherWorks.INSTANCE, GuiHandler.Ender_Pack_GUI, worldIn, (int) playerIn.posX, (int) playerIn.posY, (int) playerIn.posZ);
		}
		playerIn.addStat(StatList.ENDERCHEST_OPENED);
		return new ActionResult<>(EnumActionResult.PASS, itemStackIn);
	}
}
