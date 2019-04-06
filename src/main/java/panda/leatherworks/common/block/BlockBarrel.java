package panda.leatherworks.common.block;

import java.util.List;
import java.util.Random;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import panda.leatherworks.ConfigLeatherWorks;
import panda.leatherworks.common.item.ItemCraftingLeather;
import panda.leatherworks.common.item.ItemPack;
import panda.leatherworks.init.LWBlocks;
import panda.leatherworks.init.LWItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBanner;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockBarrel extends Block
{
    public static final PropertyInteger LEVEL = PropertyInteger.create("level", 0, 3);
    public static final PropertyInteger FLUID = PropertyInteger.create("fluid", 0, 1);
    private Block decorationBlock;
    protected static final AxisAlignedBB AABB_LEGS = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.3125D, 1.0D);
    protected static final AxisAlignedBB AABB_WALL_NORTH = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.125D);
    protected static final AxisAlignedBB AABB_WALL_SOUTH = new AxisAlignedBB(0.0D, 0.0D, 0.875D, 1.0D, 1.0D, 1.0D);
    protected static final AxisAlignedBB AABB_WALL_EAST = new AxisAlignedBB(0.875D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
    protected static final AxisAlignedBB AABB_WALL_WEST = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.125D, 1.0D, 1.0D);

    public BlockBarrel(Block decoration)
    {
        super(Material.WOOD, MapColor.WOOD);
        this.setSoundType(SoundType.WOOD);
        decorationBlock = decoration;
        this.setDefaultState(this.blockState.getBaseState().withProperty(LEVEL, 0).withProperty(FLUID, 0));
        this.setTickRandomly(true);
        this.setHardness(2.0F);
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean unknown)
    {
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_LEGS);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_WEST);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_NORTH);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_EAST);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_SOUTH);
    }
    
    @Override	
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return FULL_BLOCK_AABB;
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
    public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
    {
        int i = state.getValue(LEVEL);
        float f = (float)pos.getY() + (6.0F + (float)(3 * i)) / 16.0F;

        if (!worldIn.isRemote && entityIn.isBurning() && i > 0 && entityIn.getEntityBoundingBox().minY <= (double)f)
        {
            entityIn.extinguish();
            this.setFluidLevel(worldIn, pos, state, i - 1);
        }
    }
    
    private boolean drainBarrel(EntityPlayer playerIn, ItemStack heldItem, EnumHand hand, World worldIn, BlockPos pos, IBlockState state)
    {
        
        // Get the fluid handler item which is needed to modify fluids on the itemstack
        IFluidHandlerItem fluidHandlerItem = FluidUtil.getFluidHandler(heldItem);
        
    	if (fluidHandlerItem.getTankProperties()[0].getCapacity() >= Fluid.BUCKET_VOLUME)
    	{
    	    
    		// Fill if not creative
            if (!playerIn.capabilities.isCreativeMode)
            {
            	
                // Which fluid is this
            	int fluidInt = state.getValue(FLUID);
            	Fluid fluid = FluidRegistry.WATER;
            	
            	if (fluidInt == 1)
            	{
            		fluid = LWBlocks.TANNIN_FLUID;
            	}
                
	        	// Add the fluid to the stack
	        	fluidHandlerItem.fill(new FluidStack(fluid, Fluid.BUCKET_VOLUME), true);
	        	
	        	// Get the stack based on the handler and actually set the players hand to it
	        	playerIn.setHeldItem(hand, fluidHandlerItem.getContainer());
            }
    	}
    	
        worldIn.setBlockState(pos, this.getDefaultState().withProperty(FLUID, 0) , 2);
        this.setFluidLevel(worldIn, pos, state, 0);
        worldIn.updateComparatorOutputLevel(pos, this);
        
        return true;
    }
    
    private boolean fillBarrel(EntityPlayer playerIn, ItemStack heldItem, EnumHand hand, Fluid fluid, World worldIn, BlockPos pos, IBlockState state)
    {
    	
    	// Get the fluid handler item which is needed to modify fluids on the itemstack
    	IFluidHandlerItem fluidHandlerItem = FluidUtil.getFluidHandler(heldItem);
    	if (fluidHandlerItem.getTankProperties()[0].getCapacity() >= Fluid.BUCKET_VOLUME)
    	{
    		
    		// Drain if not creative
            if (!playerIn.capabilities.isCreativeMode)
            {
	        	// Remove the fluid from the stack
	        	fluidHandlerItem.drain(Fluid.BUCKET_VOLUME, true);
	        	
	        	// Get the stack based on the handler and actually set the players hand to it
	        	playerIn.setHeldItem(hand, fluidHandlerItem.getContainer());
            }
        	
            // Which fluid is this
            int fluidInt = 0;
            
            if (fluid == LWBlocks.TANNIN_FLUID)
            {
            	fluidInt = 1;
            }
            
            worldIn.setBlockState(pos, state.withProperty(LEVEL, MathHelper.clamp(3, 0, 3)).withProperty(FLUID, fluidInt) , 2);
            worldIn.updateComparatorOutputLevel(pos, this);
    	}
    	
    	return true;
    }
    
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
    	ItemStack heldItem = playerIn.getHeldItem(hand);
    	
        if (heldItem.isEmpty())
        {
            return true;
        }
        else
        {
            int level = state.getValue(LEVEL);
            int f = state.getValue(FLUID);
            FluidStack fluidStack = FluidUtil.getFluidContained(heldItem);
            Item item = heldItem.getItem();
            boolean hasFluid = heldItem.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
            
            if (fluidStack != null)
            {
            	
            	// Fluid Give
                Fluid fluid = fluidStack.getFluid();
                if (level == 0 && !worldIn.isRemote )
			    {
                    // If this is a stack with water or tannin fluid in it
					if (fluid == FluidRegistry.WATER || fluid == LWBlocks.TANNIN_FLUID)
				    {
					    return fillBarrel(playerIn, heldItem, hand, fluid, worldIn, pos, state);
				    }
				}
                
                return true;
			}
            else if (hasFluid)
            {
            	
            	// Fluid Take
                if (level == 3 && !worldIn.isRemote )
                {
                	return drainBarrel(playerIn, heldItem, hand, worldIn, pos, state);
                }
                
                return true;
			}
            else if (item == LWItems.TANNIN_BALL)
            {
            	
            	// Use Tannin Ball
                if (level >0 && !worldIn.isRemote && f == 0)
                {
                    if (!playerIn.capabilities.isCreativeMode)
                    {
                    	heldItem.shrink(1);
                    }
                    worldIn.setBlockState(pos, state.withProperty(FLUID, 1) , 2);
                    worldIn.updateComparatorOutputLevel(pos, this);
                }
                
                return true;
            }
            else if (level > 0 && item instanceof ItemBanner && f == 0)
            {
            	
            	// Use Banner
                if (TileEntityBanner.getPatterns(heldItem) > 0 && !worldIn.isRemote)
                {
                	useBanner(playerIn, heldItem, worldIn, pos, state, hand, level);
                }
                
                return true;
            }
            else if (item instanceof ItemCraftingLeather)
            {
            	
            	// Use Leather
            	if (level > 0 && !worldIn.isRemote)
            	{
            		useLeather(playerIn, heldItem, worldIn, pos, state, hand, level, f);
            	}
            	
            	return true;
            }
            else if (item instanceof ItemArmor)
            {
            	
            	// Use Armor
            	if (level > 0 && !worldIn.isRemote && f == 0)
            	{
            		useArmor(playerIn, heldItem, worldIn, pos, state, hand, level, f);
            	}
            	
            	return true;
            }
            else if (item == Items.GLASS_BOTTLE)
            {
            	
            	// Use Glass Bottle
                if (level > 0 && !worldIn.isRemote)
                {
                	useEmptyBottle(playerIn, heldItem, worldIn, pos, state, hand, level, f);
                }
                
                return true;
            }
            else if (item == LWItems.TANNIN_BOTTLE)
            {
            	
            	// Use Tannin Bottle
                if (level < 3 && !worldIn.isRemote && (f == 1 || level == 0))
                {
                	useTanninBottle(playerIn, heldItem, worldIn, pos, state, hand, level, f);
                }
                
                return true;
            }
            else if (item == Items.POTIONITEM)
            {
            	
            	// Use Water Bottle
                if (level < 3 && !worldIn.isRemote && (f == 0 || level == 0))
                {
                	useWaterBottle(playerIn, heldItem, worldIn, pos, state, hand, level, f);
                }
                
                return true;
            }
            else if (item == Item.getItemFromBlock(Blocks.WOODEN_PRESSURE_PLATE))
            {
            	
            	// Use Pressure Plate
                if (!worldIn.isRemote )
                {
                    if (!playerIn.capabilities.isCreativeMode)
                    {
                      	heldItem.shrink(1);
                    }
                    
                    worldIn.setBlockState(pos, decorationBlock.getDefaultState().withProperty(BlockRotatedPillar.AXIS, EnumFacing.Axis.Y), 2);
                }
                worldIn.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_WOOD_PLACE, SoundCategory.BLOCKS, 1, 1, true);
                
                return true;
            }
            else if (level > 0 && (item instanceof ItemPack ) && f == 0 && item != LWItems.PACK_BROWN && !worldIn.isRemote)
            {
            	
            	// Use Pack
                ItemStack stackout = new ItemStack(LWItems.PACK_BROWN);
                if(heldItem.hasTagCompound())
                {
                	stackout.setTagCompound(heldItem.getTagCompound().copy());
                }
                
                playerIn.setHeldItem(hand, stackout);
                this.setFluidLevel(worldIn, pos, state, level - 1);
                playerIn.addStat(StatList.ARMOR_CLEANED);
                worldIn.playSound(null, pos, SoundEvents.ENTITY_GENERIC_SPLASH, SoundCategory.BLOCKS, 1f, 1f);
                
                return true;
            }
            else
            {
            	return false;
            }
		}
    }
    
    private void useWaterBottle(EntityPlayer playerIn, ItemStack heldItem, World worldIn, BlockPos pos, IBlockState state, EnumHand hand, int level, int fluid)
    {
    	if(PotionUtils.getPotionFromItem(heldItem) == PotionTypes.WATER)
    	{
    		if (!playerIn.capabilities.isCreativeMode)
    		{
            	ItemStack itemstack1 = new ItemStack(Items.GLASS_BOTTLE);
            	if (heldItem.getCount() == 1)
            	{
            		playerIn.setHeldItem(hand, itemstack1);
            	}
            	else if (!playerIn.inventory.addItemStackToInventory(itemstack1))
            	{
                	playerIn.dropItem(itemstack1, false);
            	}
            	else if (playerIn instanceof EntityPlayerMP)
            	{
            		((EntityPlayerMP)playerIn).sendContainerToPlayer(playerIn.inventoryContainer);
            	}
        	}
        	this.setFluidLevel(worldIn, pos, state.withProperty(FLUID, 0), level + 1); 
    	}
    }
    
    private void useTanninBottle(EntityPlayer playerIn, ItemStack heldItem, World worldIn, BlockPos pos, IBlockState state, EnumHand hand, int level, int fluid)
    {
        if (heldItem.getItem() == LWItems.TANNIN_BOTTLE && level < 3 && !worldIn.isRemote && (fluid == 1 || level == 0))
        {
            if (!playerIn.capabilities.isCreativeMode)
            {
                ItemStack itemstack1 = new ItemStack(Items.GLASS_BOTTLE);

                if (heldItem.getCount() == 1)
                {
                    playerIn.setHeldItem(hand, itemstack1);
                }
                else
                {
                	heldItem.shrink(1);
                	if (!playerIn.inventory.addItemStackToInventory(itemstack1))
                    {
                        playerIn.dropItem(itemstack1, false);
                    }
                    else if (playerIn instanceof EntityPlayerMP)
                    {
                        ((EntityPlayerMP)playerIn).sendContainerToPlayer(playerIn.inventoryContainer);
                    }
                }
                
            }
            this.setFluidLevel(worldIn, pos, state.withProperty(FLUID, 1), level + 1);
        }
    }
    
	private void useEmptyBottle(EntityPlayer playerIn, ItemStack heldItem, World worldIn, BlockPos pos, IBlockState state, EnumHand hand, int level, int fluid) {
		
		if (!playerIn.capabilities.isCreativeMode)
		{
			ItemStack itemstack1 = PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.WATER);
			ItemStack itemstack2 = new ItemStack(LWItems.TANNIN_BOTTLE);
			
			if (heldItem.getCount() == 1)
			{
				playerIn.setHeldItem(hand, (fluid == 0 ? itemstack1 : itemstack2));
			}
			else
			{
				heldItem.shrink(1);
				if (!playerIn.inventory.addItemStackToInventory((fluid == 0 ? itemstack1 : itemstack2)))
				{
					playerIn.dropItem((fluid == 0 ? itemstack1 : itemstack2), false);
				}
				else if (playerIn instanceof EntityPlayerMP)
				{
					((EntityPlayerMP) playerIn).sendContainerToPlayer(playerIn.inventoryContainer);
				}
			}

		}

		this.setFluidLevel(worldIn, pos, state, level - 1);
	}
    
    private void useBanner(EntityPlayer playerIn, ItemStack heldItem, World worldIn, BlockPos pos, IBlockState state, EnumHand hand, int level) {
    	ItemStack itemstack = heldItem.copy();
        itemstack.setCount(1);
        TileEntityBanner.removeBannerData(itemstack);
        playerIn.addStat(StatList.BANNER_CLEANED);
        
        if (!playerIn.capabilities.isCreativeMode)
        {
            heldItem.shrink(1);
        }
        
        if (heldItem.isEmpty())
        {
            playerIn.setHeldItem(hand, itemstack);
        }
        else if (!playerIn.inventory.addItemStackToInventory(itemstack))
        {
            playerIn.dropItem(itemstack, false);
        }
        else if (playerIn instanceof EntityPlayerMP)
        {
            ((EntityPlayerMP)playerIn).sendContainerToPlayer(playerIn.inventoryContainer);
        }
        
        if (!playerIn.capabilities.isCreativeMode)
        {
            this.setFluidLevel(worldIn, pos, state, level - 1);
        }
    }
    
    private void useLeather(EntityPlayer playerIn, ItemStack heldItem, World worldIn, BlockPos pos, IBlockState state, EnumHand hand, int level, int fluid)
    {
        //TODO
        if (heldItem.getItem() == LWItems.LEATHER_SCRAPED && fluid == 0)
        {
        	this.setFluidLevel(worldIn, pos, state, level - 1);
        	
        	if(ConfigLeatherWorks.allowBatchProcessing)
        	{
        		playerIn.setHeldItem(hand, new ItemStack(LWItems.LEATHER_WASHED,heldItem.getCount()));
        	}
        	else
        	{
        		heldItem.shrink(1);
        		playerIn.addItemStackToInventory(new ItemStack(LWItems.LEATHER_WASHED));
        	}
        }
        else if (heldItem.getItem() == LWItems.LEATHER_WASHED && fluid == 1)
        {
            this.setFluidLevel(worldIn, pos, state, level - 1);
            if (ConfigLeatherWorks.allowBatchProcessing)
            {
            	playerIn.setHeldItem(hand, new ItemStack(LWItems.LEATHER_SOAKED,heldItem.getCount()));
            }
            else
            {
            	heldItem.shrink(1);
            	if (!playerIn.addItemStackToInventory(new ItemStack(LWItems.LEATHER_SOAKED)))
            	{
            		EntityItem entityitem = new EntityItem(worldIn, pos.getX(), pos.getY()+0.5f, pos.getZ(), new ItemStack(LWItems.LEATHER_SOAKED));
            		worldIn.spawnEntity(entityitem);
            	}
            }
        }
    }
    
    private void useArmor(EntityPlayer playerIn, ItemStack heldItem, World worldIn, BlockPos pos, IBlockState state, EnumHand hand, int level, int fluid)
    {
    	Item item = heldItem.getItem();
    	
        if (level > 0 && item instanceof ItemArmor && fluid == 0)
        {
            ItemArmor itemarmor = (ItemArmor)item;
            
            if ((itemarmor.getArmorMaterial() == ItemArmor.ArmorMaterial.LEATHER || itemarmor.getArmorMaterial() == LWItems.DUMMYLEATHER) && itemarmor.hasColor(heldItem) && !worldIn.isRemote)
            {
                itemarmor.removeColor(heldItem);
                this.setFluidLevel(worldIn, pos, state, level - 1);
                playerIn.addStat(StatList.ARMOR_CLEANED);
            }
        }
        
        if (level > 0 && (item instanceof ItemPack ) && fluid == 0 && item != LWItems.PACK_BROWN && !worldIn.isRemote)
        {
        	ItemStack stackout = new ItemStack(LWItems.PACK_BROWN);
        	if(heldItem.hasTagCompound())
        	{
        		stackout.setTagCompound(heldItem.getTagCompound().copy());
			}
        	
        	playerIn.setHeldItem(hand, stackout);
            this.setFluidLevel(worldIn, pos, state, level - 1);
            playerIn.addStat(StatList.ARMOR_CLEANED);
            worldIn.playSound(null, pos, SoundEvents.ENTITY_GENERIC_SPLASH, SoundCategory.BLOCKS, 1f, 1f);
        }
    }
    
	@Override
	public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random)
    {
        this.updateTick(worldIn, pos, state, random);
    }
	
	public void setFluidLevel(World worldIn, BlockPos pos, IBlockState state, int level)
    {
        worldIn.setBlockState(pos, state.withProperty(LEVEL, MathHelper.clamp(level, 0, 3)), 2);
        worldIn.updateComparatorOutputLevel(pos, this);
    }

	@Override
    public void fillWithRain(World worldIn, BlockPos pos)
    {
            float f = worldIn.getBiome(pos).getTemperature(pos);

            if (worldIn.getBiomeProvider().getTemperatureAtHeight(f, pos.getY()) >= 0.15F)
            {
                IBlockState iblockstate = worldIn.getBlockState(pos);

                if (iblockstate.getValue(LEVEL) < 3 && iblockstate.getValue(FLUID) == 0)
                {
                    worldIn.setBlockState(pos, iblockstate.cycleProperty(LEVEL), 2);
                }
            }
    }

    @Override
	public void onPlayerDestroy(World worldIn, BlockPos pos, IBlockState state) {
		if(state.getValue(LEVEL) == 3){
			IBlockState newstate = state.getValue(FLUID) == 1? LWBlocks.TANNIN.getDefaultState():Blocks.WATER.getDefaultState();
			worldIn.setBlockState(pos,newstate,1);
		}
		super.onPlayerDestroy(worldIn, pos, state);
	}

	@Nullable
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(this);
    }
    
    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return new ItemStack(this);
    }
    
    @Override
    public boolean hasComparatorInputOverride(IBlockState state)
    {
        return true;
    }
    
    @Override
    public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos)
    {
        return blockState.getValue(LEVEL);
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        switch(meta){
        case 0:
        	return this.getDefaultState().withProperty(LEVEL, 0).withProperty(FLUID, 0);
        case 1:
        	return this.getDefaultState().withProperty(LEVEL, 1).withProperty(FLUID, 0);
        case 2:
        	return this.getDefaultState().withProperty(LEVEL, 2).withProperty(FLUID, 0);
        case 3:
        	return this.getDefaultState().withProperty(LEVEL, 3).withProperty(FLUID, 0);
        case 4:
        	return this.getDefaultState().withProperty(LEVEL, 0).withProperty(FLUID, 1);
        case 5:
        	return this.getDefaultState().withProperty(LEVEL, 1).withProperty(FLUID, 1);
        case 6:
        	return this.getDefaultState().withProperty(LEVEL, 2).withProperty(FLUID, 1);
        case 7:
        	return this.getDefaultState().withProperty(LEVEL, 3).withProperty(FLUID, 1);
        default:
        	return this.getDefaultState();
        }
    	
    	
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    @Nonnull
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }
    

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(LEVEL) + 4 * state.getValue(FLUID);
    }
    
    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, LEVEL,FLUID);
    }
    
    @Override
    public boolean isPassable(IBlockAccess worldIn, BlockPos pos)
    {
        return true;
    }
}