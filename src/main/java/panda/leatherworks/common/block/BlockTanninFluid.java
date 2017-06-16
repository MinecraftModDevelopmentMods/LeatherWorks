package panda.leatherworks.common.block;

import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class BlockTanninFluid extends BlockFluidClassic {
	public BlockTanninFluid(Fluid fluid) {
		super(fluid,Material.WATER);
		this.quantaPerBlock = 6;
		this.renderLayer = BlockRenderLayer.TRANSLUCENT;
		this.temperature = 380;
		this.setDensity(300);
		this.setRegistryName("tannin");
	}
}