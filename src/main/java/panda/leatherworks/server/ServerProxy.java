package panda.leatherworks.server;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import panda.leatherworks.common.CommonProxy;

@SideOnly(Side.SERVER)
public class ServerProxy extends CommonProxy {
	@Override
	public void registerColorHandlers() {}
}
