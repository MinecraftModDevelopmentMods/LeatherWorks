package panda.leatherworks.common.block;

import net.minecraft.block.BlockLog;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockDebarkedLog extends BlockLog
{

    public BlockDebarkedLog()
    {
    	this.setDefaultState(this.blockState.getBaseState().withProperty(LOG_AXIS, BlockLog.EnumAxis.Y));
    	Blocks.FIRE.setFireInfo(this, 5, 5);
    	this.blockHardness = 2;
		this.blockResistance = 2;
		this.setHarvestLevel("axe", 0);
    }
    
    @Override
    public boolean isWood(IBlockAccess world, BlockPos pos)
    {
        return false;
    }
    
    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, LOG_AXIS);
    }
	
	@Override
	@Deprecated
    public IBlockState getStateFromMeta(int meta)
    {
        IBlockState iblockstate = this.getDefaultState();

        switch (meta)
        {
            case 0:
                iblockstate = iblockstate.withProperty(LOG_AXIS, BlockLog.EnumAxis.Y);
                break;
            case 1:
                iblockstate = iblockstate.withProperty(LOG_AXIS, BlockLog.EnumAxis.X);
                break;
            case 2:
                iblockstate = iblockstate.withProperty(LOG_AXIS, BlockLog.EnumAxis.Z);
                break;
            default:
                iblockstate = iblockstate.withProperty(LOG_AXIS, BlockLog.EnumAxis.NONE);
        }

        return iblockstate;
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        switch (state.getValue(LOG_AXIS))
        {
            case Y:
                return 0;
            case X:
                return 1;
            case Z:
                return 2;
            case NONE:
                return 0;
        }
        return 0;
    }
   
}

