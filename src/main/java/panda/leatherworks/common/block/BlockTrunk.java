package panda.leatherworks.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import panda.leatherworks.LeatherWorks;
import panda.leatherworks.common.GuiHandler;
import panda.leatherworks.common.tileentity.TileEntityTrunk;
import panda.leatherworks.init.LWBlocks;

public class BlockTrunk  extends BlockContainer{
    
	private final EnumDyeColor color;
	
    public BlockTrunk(EnumDyeColor colorIn)
    {
        super(Material.WOOD);
        this.color = colorIn;
        this.setDefaultState(this.blockState.getBaseState());
        this.setHardness(5);
        this.setSoundType(SoundType.WOOD);
    }
    
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityTrunk(this.color);
    }

    public static Block getBlockByColor(EnumDyeColor colorIn)
    {
        switch (colorIn)
        {
            case WHITE:
                return Blocks.AIR;
            case ORANGE:
                return LWBlocks.LEATHER_TRUNK_ORANGE;
            case MAGENTA:
                return LWBlocks.LEATHER_TRUNK_MAGENTA;
            case LIGHT_BLUE:
                return LWBlocks.LEATHER_TRUNK_LIGHT_BLUE;
            case YELLOW:
                return LWBlocks.LEATHER_TRUNK_YELLOW;
            case LIME:
                return LWBlocks.LEATHER_TRUNK_LIME;
            case PINK:
                return LWBlocks.LEATHER_TRUNK_PINK;
            case GRAY:
                return LWBlocks.LEATHER_TRUNK_GRAY;
            case SILVER:
                return LWBlocks.LEATHER_TRUNK_SILVER;
            case CYAN:
                return LWBlocks.LEATHER_TRUNK_CYAN;
            case BLUE:
                return LWBlocks.LEATHER_TRUNK_BLUE;
            case BROWN:
                return LWBlocks.LEATHER_TRUNK;
            case GREEN:
                return LWBlocks.LEATHER_TRUNK_GREEN;
            case RED:
                return LWBlocks.LEATHER_TRUNK_RED;
            case BLACK:
                return LWBlocks.LEATHER_TRUNK_BLACK;
            case PURPLE:
            default:
                return LWBlocks.LEATHER_TRUNK_PURPLE;
        }
    }
    
    @SideOnly(Side.CLIENT)
    public static EnumDyeColor getColorFromItem(Item itemIn)
    {
        return getColorFromBlock(Block.getBlockFromItem(itemIn));
    }

    public static ItemStack getColoredItemStack(EnumDyeColor colorIn)
    {
        return new ItemStack(getBlockByColor(colorIn));
    }

    @SideOnly(Side.CLIENT)
    public static EnumDyeColor getColorFromBlock(Block blockIn)
    {
        return blockIn instanceof BlockTrunk ? ((BlockTrunk)blockIn).getColor() : EnumDyeColor.BROWN;
    }
    
    @SideOnly(Side.CLIENT)
    public EnumDyeColor getColor()
    {
        return this.color;
    }
    
    @Override
	public boolean hasTileEntity(IBlockState state)
	{
		return true;
	}
    
    @Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (!worldIn.isRemote && !isBlocked(worldIn, pos))
        {
        	playerIn.openGui(LeatherWorks.instance, GuiHandler.TRUNK_GUI, worldIn, pos.getX(), pos.getY(), pos.getZ());
        	((IInventory) worldIn.getTileEntity(pos)).openInventory(playerIn);
        	playerIn.addStat(StatList.CHEST_OPENED);
        }
        return true;
    }
    
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        TileEntity tileentity = worldIn.getTileEntity(pos);

        if (tileentity instanceof IInventory)
        {
            InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory)tileentity);
            worldIn.updateComparatorOutputLevel(pos, this);
        }

        super.breakBlock(worldIn, pos, state);
    }
    
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
		return new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 0.875D, 0.9375D);
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public boolean hasCustomBreakingProgress(IBlockState state)
    {
        return true;
    }
    
	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}
	
	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}
    
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
    }
	
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.SOLID;
	}

	@Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        TileEntity te = worldIn.getTileEntity(pos);

        if (te != null && te instanceof TileEntityTrunk)
        {
        	TileEntityTrunk tile = (TileEntityTrunk) te;

        	tile.setFacing(placer.getHorizontalFacing().getOpposite());

            worldIn.notifyBlockUpdate(pos, state, state, 3);
        }
    }

    private boolean isBlocked(World worldIn, BlockPos pos)
    {
        return this.isBelowSolidBlock(worldIn, pos) || this.isOcelotSittingOnChest(worldIn, pos);
    }

    private boolean isBelowSolidBlock(World worldIn, BlockPos pos)
    {
        return worldIn.getBlockState(pos.up()).isSideSolid(worldIn, pos.up(), EnumFacing.DOWN);
    }

    private boolean isOcelotSittingOnChest(World worldIn, BlockPos pos)
    {
        for (Entity entity : worldIn.getEntitiesWithinAABB(EntityOcelot.class, new AxisAlignedBB((double)pos.getX(), (double)(pos.getY() + 1), (double)pos.getZ(), (double)(pos.getX() + 1), (double)(pos.getY() + 2), (double)(pos.getZ() + 1))))
        {
            EntityOcelot entityocelot = (EntityOcelot)entity;

            if (entityocelot.isSitting())
            {
                return true;
            }
        }

        return false;
    }
	
	@Override
	public boolean hasComparatorInputOverride(IBlockState state)
    {
        return true;
    }
	
	@Override
    public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos)
    {
        return Container.calcRedstoneFromInventory((IInventory) this);
    }
	
	@Override
    @Deprecated
    public boolean eventReceived(IBlockState state, World worldIn, BlockPos pos, int id, int param)
    {
        super.eventReceived(state, worldIn, pos, id, param);

        TileEntity tileentity = worldIn.getTileEntity(pos);

        return tileentity != null && tileentity.receiveClientEvent(id, param);
    }

}
