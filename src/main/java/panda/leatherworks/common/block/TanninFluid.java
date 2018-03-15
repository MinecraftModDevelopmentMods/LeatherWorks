package panda.leatherworks.common.block;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import panda.leatherworks.init.LWBlocks;

public class TanninFluid extends Fluid {

	public TanninFluid() {
		super("leatherworks:tannin", new ResourceLocation("leatherworks:blocks/tannin_still"),
				new ResourceLocation("leatherworks:blocks/tannin_flowing"));
		setViscosity(1200);
		setDensity(1200);
		setBlock(LWBlocks.TANNIN);
		FluidRegistry.addBucketForFluid(this);
	}

}
