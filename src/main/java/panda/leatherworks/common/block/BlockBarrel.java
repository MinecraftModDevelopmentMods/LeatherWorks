package panda.leatherworks.common.block;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import panda.leatherworks.LeatherWorks;
import panda.leatherworks.common.item.ItemCraftingLeather;
import panda.leatherworks.common.item.ItemPack;
import panda.leatherworks.util.registry.BlockList;
import panda.leatherworks.util.registry.ItemList;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBanner;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockBarrel extends Block
{
    public static final PropertyInteger LEVEL = PropertyInteger.create("level", 0, 3);
    public static final PropertyInteger FLUID = PropertyInteger.create("fluid", 0, 1);

    protected static final AxisAlignedBB AABB_LEGS = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.3125D, 1.0D);
    protected static final AxisAlignedBB AABB_WALL_NORTH = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.125D);
    protected static final AxisAlignedBB AABB_WALL_SOUTH = new AxisAlignedBB(0.0D, 0.0D, 0.875D, 1.0D, 1.0D, 1.0D);
    protected static final AxisAlignedBB AABB_WALL_EAST = new AxisAlignedBB(0.875D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
    protected static final AxisAlignedBB AABB_WALL_WEST = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.125D, 1.0D, 1.0D);

    public BlockBarrel()
    {
        super(Material.WOOD, MapColor.WOOD);
        this.setDefaultState(this.blockState.getBaseState().withProperty(LEVEL, Integer.valueOf(0)).withProperty(FLUID, Integer.valueOf(0)));
        this.setCreativeTab(LeatherWorks.LeatherTab);
        this.setRegistryName("barrel");
        this.setTickRandomly(true);
    }

    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn)
    {
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_LEGS);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_WEST);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_NORTH);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_EAST);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_SOUTH);
    }

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return FULL_BLOCK_AABB;
    }

    /**
     * Used to determine ambient occlusion and culling when rebuilding chunks for render
     */
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    /**
     * Called When an Entity Collided with the Block
     */
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
    {
        int i = ((Integer)state.getValue(LEVEL)).intValue();
        float f = (float)pos.getY() + (6.0F + (float)(3 * i)) / 16.0F;

        if (!worldIn.isRemote && entityIn.isBurning() && i > 0 && entityIn.getEntityBoundingBox().minY <= (double)f)
        {
            entityIn.extinguish();
            this.setFluidLevel(worldIn, pos, state, i - 1);
        }
    }

    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if (heldItem == null)
        {
            return true;
        }
        else
        {
        	

        	
            int i = ((Integer)state.getValue(LEVEL)).intValue();
            int f = ((Integer)state.getValue(FLUID)).intValue();
            Item item = heldItem.getItem();
        	if (item == Item.getItemFromBlock(Blocks.WOODEN_PRESSURE_PLATE))
            {
                if (!worldIn.isRemote )
                {
                    if (!playerIn.capabilities.isCreativeMode)
                    {
                    	--heldItem.stackSize;

                    }

                    worldIn.setBlockState(pos,BlockList.BARREL_SEALED.getDefaultState(), 2);
                }

                return true;
            }
            if (item == Items.WATER_BUCKET)
            {
                if (i == 0 && !worldIn.isRemote )
                {
                    if (!playerIn.capabilities.isCreativeMode)
                    {
                        playerIn.setHeldItem(hand, new ItemStack(Items.BUCKET));
                    }

                    worldIn.setBlockState(pos, state.withProperty(LEVEL, Integer.valueOf(MathHelper.clamp_int(3, 0, 3))).withProperty(FLUID, Integer.valueOf(0)) , 2);
                    worldIn.updateComparatorOutputLevel(pos, this);
                }

                return true;
            }else
            	if (item == ItemList.TANNIN_BUCKET)
                {
                    if (i == 0 && !worldIn.isRemote )
                    {
                        if (!playerIn.capabilities.isCreativeMode)
                        {
                            playerIn.setHeldItem(hand, new ItemStack(Items.BUCKET));
                        }
                        worldIn.setBlockState(pos, state.withProperty(LEVEL, Integer.valueOf(MathHelper.clamp_int(3, 0, 3))).withProperty(FLUID, Integer.valueOf(1)) , 2);
                        worldIn.updateComparatorOutputLevel(pos, this);
                    }

                    return true;
                }
            
            if (item == ItemList.TANNIN_BOTTLE && i < 3 && !worldIn.isRemote && (f == 1 || i==0 ))
            {
                if (!playerIn.capabilities.isCreativeMode)
                {
                    ItemStack itemstack1 = new ItemStack(Items.GLASS_BOTTLE);

                    if (--heldItem.stackSize == 0)
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
                if(f==0){
                	state.cycleProperty(FLUID);//TODO DOESNT CHANGE PROPERTY?
                }
                this.setFluidLevel(worldIn, pos, state, i + 1);
                
            }
            
            if (item == Items.POTIONITEM && i < 3 && !worldIn.isRemote && (f == 0 || i==0 ))
            {
            	if(PotionUtils.getPotionFromItem(new ItemStack(item)) == PotionTypes.WATER ){
                if (!playerIn.capabilities.isCreativeMode)
                {
                    ItemStack itemstack1 = new ItemStack(Items.GLASS_BOTTLE);

                    if (--heldItem.stackSize == 0)
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
                if(f==1){
                	worldIn.setBlockState(pos,state.withProperty(FLUID, 0),3);
                }
                this.setFluidLevel(worldIn, pos, state, i + 1);
                
            	}
            }
            if (item == Items.WATER_BUCKET)
            {
                if (i <3 && !worldIn.isRemote && f ==0)
                {
                    if (!playerIn.capabilities.isCreativeMode)
                    {
                        playerIn.setHeldItem(hand, new ItemStack(Items.BUCKET));
                    }

                    this.setFluidLevel(worldIn, pos, state, 3);
                }

                return true;
            }else
            	if (item == ItemList.TANNIN_BUCKET)
                {
                    if (i <3 && !worldIn.isRemote && f ==1)
                    {
                        if (!playerIn.capabilities.isCreativeMode)
                        {
                            playerIn.setHeldItem(hand, new ItemStack(Items.BUCKET));
                        }
                        this.setFluidLevel(worldIn, pos, state, 3);
                    }

                    return true;
                }else
            if (item == ItemList.TANNIN_BALL)
            {
                if (i >0 && !worldIn.isRemote && f ==0)
                {
                    if (!playerIn.capabilities.isCreativeMode)
                    {
                    	--heldItem.stackSize;
                    }
                    worldIn.setBlockState(pos, state.withProperty(FLUID, Integer.valueOf(1)) , 2);
                    worldIn.updateComparatorOutputLevel(pos, this);
                }

                return true;
            }
            else if (item == Items.BUCKET)
            {
                if (i == 3 && !worldIn.isRemote && f ==0)
                {
                    if (!playerIn.capabilities.isCreativeMode)
                    {
                        --heldItem.stackSize;

                        if (heldItem.stackSize == 0)
                        {
                            playerIn.setHeldItem(hand, new ItemStack(Items.WATER_BUCKET));
                        }
                        else if (!playerIn.inventory.addItemStackToInventory(new ItemStack(Items.WATER_BUCKET)))
                        {
                            playerIn.dropItem(new ItemStack(Items.WATER_BUCKET), false);
                        }
                    }

                    this.setFluidLevel(worldIn, pos, state, 0);
                }else
                	if (i == 3 && !worldIn.isRemote && f ==1)
                    {
                        if (!playerIn.capabilities.isCreativeMode)
                        {
                            --heldItem.stackSize;

                            if (heldItem.stackSize == 0)
                            {
                                playerIn.setHeldItem(hand, new ItemStack(ItemList.TANNIN_BUCKET));
                            }
                            else if (!playerIn.inventory.addItemStackToInventory(new ItemStack(ItemList.TANNIN_BUCKET)))
                            {
                                playerIn.dropItem(new ItemStack(ItemList.TANNIN_BUCKET), false);
                            }
                        }

                        this.setFluidLevel(worldIn, pos, state, 0);
                    }

                return true;
            }
            else if (item == Items.GLASS_BOTTLE)
            {
                if (i > 0 && !worldIn.isRemote && f ==0)
                {
                    if (!playerIn.capabilities.isCreativeMode)
                    {
                        ItemStack itemstack1 = PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.WATER);

                        if (--heldItem.stackSize == 0)
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

                    this.setFluidLevel(worldIn, pos, state, i - 1);
                }else
                	if (i > 0 && !worldIn.isRemote && f ==1)
                    {
                        if (!playerIn.capabilities.isCreativeMode)
                        {
                            ItemStack itemstack1 = new ItemStack(ItemList.TANNIN_BOTTLE);

                            if (--heldItem.stackSize == 0)
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

                        this.setFluidLevel(worldIn, pos, state, i - 1);
                    }

                return true;
            }
            else
            {
                if (i > 0 && item instanceof ItemArmor && f ==0)
                {
                    ItemArmor itemarmor = (ItemArmor)item;
                    
                    if ((itemarmor.getArmorMaterial() == ItemArmor.ArmorMaterial.LEATHER || itemarmor.getArmorMaterial() == LeatherWorks.DUMMYLEATHER) && itemarmor.hasColor(heldItem) && !worldIn.isRemote)
                    {
                        itemarmor.removeColor(heldItem);
                        this.setFluidLevel(worldIn, pos, state, i - 1);
                        playerIn.addStat(StatList.ARMOR_CLEANED);
                        return true;
                    }
                }
                
                if (i > 0 && (item instanceof ItemPack ) && f ==0)
                {
                	

                    if (item.getMetadata(heldItem) != 0 && !worldIn.isRemote)
                    {
                    	item.setDamage(heldItem, 0);
                        this.setFluidLevel(worldIn, pos, state, i - 1);
                        playerIn.addStat(StatList.ARMOR_CLEANED);
                        return true;
                    }
                }

                //TODO
                if (i > 0 && item instanceof ItemCraftingLeather )
                {
                	
                	int meta = heldItem.getMetadata();
                	
                	if(meta == 0 && f ==0){
                		this.setFluidLevel(worldIn, pos, state, i - 1);
                		if (!playerIn.capabilities.isCreativeMode)
                        {
                            --heldItem.stackSize;
                        }
                		playerIn.inventory.addItemStackToInventory(new ItemStack(ItemList.CRAFTING_LEATHER,1,1));
                        return true;
                	}else
                		if(meta == 1 && f ==1){
                    		this.setFluidLevel(worldIn, pos, state, i - 1);
                    		if (!playerIn.capabilities.isCreativeMode)
                            {
                                --heldItem.stackSize;
                            }
                    		playerIn.inventory.addItemStackToInventory(new ItemStack(ItemList.CRAFTING_LEATHER,1,2));
                            return true;
                    	}
                }
                
                

                if (i > 0 && item instanceof ItemBanner && f ==0)
                {
                    if (TileEntityBanner.getPatterns(heldItem) > 0 && !worldIn.isRemote)
                    {
                        ItemStack itemstack = heldItem.copy();
                        itemstack.stackSize = 1;
                        TileEntityBanner.removeBannerData(itemstack);
                        playerIn.addStat(StatList.BANNER_CLEANED);

                        if (!playerIn.capabilities.isCreativeMode)
                        {
                            --heldItem.stackSize;
                        }

                        if (heldItem.stackSize == 0)
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
                            this.setFluidLevel(worldIn, pos, state, i - 1);
                        }
                    }

                    return true;
                }
                else
                {
                    return false;
                }
            }
        }
    }

	@Override
	public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random)
    {
        this.updateTick(worldIn, pos, state, random);
    }




	public void setFluidLevel(World worldIn, BlockPos pos, IBlockState state, int level)
    {
        worldIn.setBlockState(pos, state.withProperty(LEVEL, Integer.valueOf(MathHelper.clamp_int(level, 0, 3))), 2);
        worldIn.updateComparatorOutputLevel(pos, this);
    }

    /**
     * Called similar to random ticks, but only when it is raining.
     */
    public void fillWithRain(World worldIn, BlockPos pos)
    {

       // if (worldIn.rand.nextInt(2) == 0)
        //{

            float f = worldIn.getBiome(pos).getFloatTemperature(pos);

            if (worldIn.getBiomeProvider().getTemperatureAtHeight(f, pos.getY()) >= 0.15F)
            {
                IBlockState iblockstate = worldIn.getBlockState(pos);

                if (((Integer)iblockstate.getValue(LEVEL)).intValue() < 3 && ((Integer)iblockstate.getValue(FLUID)).intValue() ==0 )
                {
                    worldIn.setBlockState(pos, iblockstate.cycleProperty(LEVEL), 2);
                }
            }
        //}
    }

    /**
     * Get the Item that this Block should drop when harvested.
     */
    @Nullable
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(BlockList.BARREL);
    }

    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return new ItemStack(BlockList.BARREL);
    }

    public boolean hasComparatorInputOverride(IBlockState state)
    {
        return true;
    }

    public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos)
    {
        return ((Integer)blockState.getValue(LEVEL)).intValue();
    }

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    public IBlockState getStateFromMeta(int meta)
    {
        switch(meta){
        case 0:
        	return this.getDefaultState().withProperty(LEVEL, Integer.valueOf(0)).withProperty(FLUID, Integer.valueOf(0));
        case 1:
        	return this.getDefaultState().withProperty(LEVEL, Integer.valueOf(1)).withProperty(FLUID, Integer.valueOf(0));
        case 2:
        	return this.getDefaultState().withProperty(LEVEL, Integer.valueOf(2)).withProperty(FLUID, Integer.valueOf(0));
        case 3:
        	return this.getDefaultState().withProperty(LEVEL, Integer.valueOf(3)).withProperty(FLUID, Integer.valueOf(0));
        case 4:
        	return this.getDefaultState().withProperty(LEVEL, Integer.valueOf(0)).withProperty(FLUID, Integer.valueOf(1));
        case 5:
        	return this.getDefaultState().withProperty(LEVEL, Integer.valueOf(1)).withProperty(FLUID, Integer.valueOf(1));
        case 6:
        	return this.getDefaultState().withProperty(LEVEL, Integer.valueOf(2)).withProperty(FLUID, Integer.valueOf(1));
        case 7:
        	return this.getDefaultState().withProperty(LEVEL, Integer.valueOf(3)).withProperty(FLUID, Integer.valueOf(1));
        }
    	
    	return this.getDefaultState();
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state)
    {
        return ((Integer)state.getValue(LEVEL)).intValue()+4*((Integer)state.getValue(FLUID)).intValue();
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {LEVEL,FLUID});
    }

    public boolean isPassable(IBlockAccess worldIn, BlockPos pos)
    {
        return true;
    }
}