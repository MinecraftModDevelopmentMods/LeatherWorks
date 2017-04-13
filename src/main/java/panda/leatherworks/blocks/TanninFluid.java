package panda.leatherworks.blocks;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import panda.leatherworks.util.registry.BlockList;
import net.minecraft.block.material.MaterialLiquid;
public class TanninFluid extends Fluid {

	public TanninFluid() {
		super("tannin", new ResourceLocation("leatherworks:blocks/tannin_still"),
				new ResourceLocation("leatherworks:blocks/tannin_flowing"));
		setViscosity(1200);
		setDensity(1200);
		setBlock(BlockList.TANNIN);
		FluidRegistry.addBucketForFluid(this);
	}

}