package panda.leatherworks.blocks;

import net.minecraft.block.BlockLog;
import net.minecraft.block.SoundType;
import net.minecraft.block.BlockLever.EnumOrientation;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.ImmutableMap;

import panda.leatherworks.LeatherWorks;
import panda.leatherworks.entity.TileEntityDryingRack;
import panda.leatherworks.util.recipe.DryingRecipe;
import panda.leatherworks.util.recipe.DryingRecipes;

public class BlockRackTest extends BlockTileEntity<TileEntityDryingRack> {

	public static final PropertyEnum<EnumFacing> FACING = PropertyEnum.create("facing", EnumFacing.class);
	
	public BlockRackTest() {
		super(Material.WOOD, "drying_rack");
		this.setSoundType(SoundType.WOOD);
		this.setCreativeTab(LeatherWorks.LeatherTab);
		this.setDefaultState(getBlockState().getBaseState().withProperty(FACING, EnumFacing.NORTH));
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			TileEntityDryingRack tile = getTileEntity(world, pos);
			IItemHandler itemHandler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side);
			if (!player.isSneaking()) {
				if (heldItem == null ) {
					player.setHeldItem(hand, itemHandler.extractItem(0, 1, false));
				} else if(!(heldItem.getItem() instanceof ItemBlock) ){
					player.setHeldItem(hand, itemHandler.insertItem(0, heldItem, false));
					
					if(DryingRecipes.containsRecipe(heldItem.getItem())){
						DryingRecipe recipe = DryingRecipes.getDryingResults(heldItem);
						if(recipe != null){
							tile.setEndTime(world.getTotalWorldTime()+ recipe.getTicks());
							System.out.println("found recipe!");
						}
						
					}
					
				}
				tile.markDirty();
			} else {
				ItemStack stack = itemHandler.getStackInSlot(0);
				if (stack != null) {
					//String localized = TutorialMod.proxy.localize(stack.getUnlocalizedName() + ".name");
					player.addChatMessage(new TextComponentString(stack.stackSize + "x " + stack.getDisplayName()));
				} else {
					player.addChatMessage(new TextComponentString("Empty"));
				}
			}
		}
		return true;
	}
	
	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getStateFromMeta(meta).withProperty(FACING, facing.getOpposite());
    }

	
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		TileEntityDryingRack tile = getTileEntity(world, pos);
		IItemHandler itemHandler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH);
		ItemStack stack = itemHandler.getStackInSlot(0);
		if (stack != null) {
			EntityItem item = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), stack);
			world.spawnEntityInWorld(item);
		}
		super.breakBlock(world, pos, state);
	}
	
	@Override
	  public boolean isSideSolid(@Nonnull IBlockState base_state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nonnull EnumFacing side) {
	    return side == EnumFacing.UP;
	  }
	
	@Override
	public Class<TileEntityDryingRack> getTileEntityClass() {
		return TileEntityDryingRack.class;
	}
	
	@Nullable
	@Override
	public TileEntityDryingRack createTileEntity(World world, IBlockState state) {
		return new TileEntityDryingRack();
	}
	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot)
    {
        return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
    }

    /**
     * Returns the blockstate with the given mirror of the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     */
	@Override
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
    {
        return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
    }
	
	
	@Nonnull
	  @Override
	  public IBlockState getStateFromMeta(int meta) {
	    return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta));
	  }

	  /**
	   * Convert the BlockState into the correct metadata value
	   */
	  @Override
	  public int getMetaFromState(IBlockState state) {

	    return state.getValue(FACING).getHorizontalIndex();

	  }
	  
	  @Nonnull
	  @Override
	  protected BlockStateContainer createBlockState() {
	    // FACING is used to orientate the item, while ORIENTATION is the location of the rack. This mainly affects centered racks
	    return new BlockStateContainer(this, new IProperty[]{FACING});
	  }
	  
	  @Override
	  public boolean isOpaqueCube(IBlockState state) {
	    return false;
	  }

	  @Override
	  public boolean isFullCube(IBlockState state) {
	    return false;
	  }
	  
	  private static final ImmutableMap<EnumFacing, AxisAlignedBB> BOUNDS;

	  static {
	    ImmutableMap.Builder<EnumFacing, AxisAlignedBB> builder = ImmutableMap.builder();
	    builder.put(EnumFacing.NORTH, new AxisAlignedBB(0, 0.75, 0, 1, 1, 0.25));
	    builder.put(EnumFacing.SOUTH, new AxisAlignedBB(0, 0.75, 0.75, 1, 1, 1));
	    builder.put(EnumFacing.EAST, new AxisAlignedBB(0.75, 0.75, 0, 1, 1, 1));
	    builder.put(EnumFacing.WEST, new AxisAlignedBB(0, 0.75, 0, 0.25, 1, 1));
	    BOUNDS = builder.build();
	  }

	  @Override
	  public AxisAlignedBB getCollisionBoundingBox(IBlockState state, @Nonnull World worldIn, @Nonnull BlockPos pos) {
	    return BOUNDS.get(state.getValue(FACING));
	  }

	  @Nonnull
	  @Override
	  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
	    return BOUNDS.get(state.getValue(FACING));
	  }

}
