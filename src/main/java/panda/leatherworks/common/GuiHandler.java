package panda.leatherworks.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import panda.leatherworks.client.gui.GuiEnderPack;
import panda.leatherworks.client.gui.GuiPack;
import panda.leatherworks.common.inventory.ContainerPack;

public class GuiHandler implements IGuiHandler {
	public static final int Pack_GUI = 0;
	public static final int Ender_Pack_GUI = 1;

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

		if (ID == Pack_GUI) {

			return new ContainerPack(player.inventory, world, new BlockPos(x,y,z),player.getHeldItem(EnumHand.MAIN_HAND));

		}
		
		if (ID == Ender_Pack_GUI) {

		return new ContainerChest(player.inventory,player.getInventoryEnderChest(),player);
		
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == Pack_GUI) {

			return new GuiPack(player.inventory, world, player.getHeldItem(EnumHand.MAIN_HAND));
		}
		if (ID == Ender_Pack_GUI) {

			return new GuiEnderPack(player.inventory,player.getInventoryEnderChest() );
		}

		return null;
	}
}
