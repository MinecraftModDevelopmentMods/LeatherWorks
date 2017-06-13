package panda.leatherworks.client;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import panda.leatherworks.client.network.messagehandler.MessageUpdateRackHandler;
import panda.leatherworks.common.CommonProxy;
import panda.leatherworks.common.item.armor.ItemBrokenArmor;
import panda.leatherworks.common.network.message.MessageUpdateRack;
import panda.leatherworks.util.registry.ItemList;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {
	@Override
	public void registerMessageHandlers(SimpleNetworkWrapper wrapper) {
		super.registerMessageHandlers(wrapper);
		wrapper.registerMessage(new MessageUpdateRackHandler(), MessageUpdateRack.class, 0, Side.CLIENT);
	}

	@Override
	public void registerColorHandlers() {
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(
			(stack, tintIndex) -> tintIndex > 0 ? -1 : ((ItemBrokenArmor) stack.getItem()).getColor(stack),
			ItemList.BROKEN_LEATHER_CHESTPLATE,
			ItemList.BROKEN_LEATHER_HELMET,
			ItemList.BROKEN_LEATHER_LEGGINGS,
			ItemList.BROKEN_LEATHER_BOOTS
		);
	}
}
