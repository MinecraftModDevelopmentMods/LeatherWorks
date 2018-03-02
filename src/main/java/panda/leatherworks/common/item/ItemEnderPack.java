package panda.leatherworks.common.item;

import net.minecraft.item.Item;
import panda.leatherworks.LeatherWorks;
import panda.leatherworks.common.GuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
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
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand handIn){
		ItemStack itemStackIn = player.getHeldItem(handIn);
		if (world.isRemote) { //client side
			player.playSound(SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 1F, 1F);
		} else {
			player.openGui(LeatherWorks.INSTANCE, GuiHandler.ENDER_PACK_GUI, world, (int) player.posX, (int) player.posY, (int) player.posZ);
		}
		player.addStat(StatList.ENDERCHEST_OPENED);
		return new ActionResult<>(EnumActionResult.PASS, itemStackIn);
	}
}
