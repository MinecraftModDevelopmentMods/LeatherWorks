package panda.leatherworks.blocks;

import java.util.Random;

import panda.leatherworks.LeatherWorks;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockTanninFluid extends BlockFluidClassic {
	public BlockTanninFluid(Fluid fluid) {
		super(fluid,Material.WATER);
		this.quantaPerBlock = 6;
		this.renderLayer = BlockRenderLayer.TRANSLUCENT;
		this.temperature = 380;
		this.setDensity(300);
		this.setCreativeTab(null);
		this.setRegistryName("tannin");
	}
}