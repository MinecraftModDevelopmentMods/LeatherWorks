package panda.leatherworks.common.block;


import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * Using a base class for blocks limits inheritance of other subclasses of Block.
 * The functionality of this class can easily be added to an external method, perhaps applied
 * when blocks are registered.
 */
@Deprecated
public abstract class BlockTileEntity<TE extends TileEntity> extends Block {

	public BlockTileEntity(Material material, String name) {
		super(material);
		this.setRegistryName(name);
	}
	
	public abstract Class<TE> getTileEntityClass();
	
	public TE getTileEntity(IBlockAccess world, BlockPos pos) {
		return (TE)world.getTileEntity(pos);
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	@Nullable
	@Override
	public abstract TE createTileEntity(World world, IBlockState state);

}