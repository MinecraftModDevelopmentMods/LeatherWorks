package panda.leatherworks.common.eventhandler;

import panda.leatherworks.LeatherWorks;
import panda.leatherworks.common.block.BlockDebarkedLog;
import panda.leatherworks.init.LWBlocks;
import panda.leatherworks.init.LWItems;
import panda.leatherworks.init.LWSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockLog.EnumAxis;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;

public class DebarkHandler {
	@SubscribeEvent(priority=net.minecraftforge.fml.common.eventhandler.EventPriority.LOWEST)
	public void onPlayerInteract(PlayerInteractEvent.RightClickBlock event) {
		EntityPlayer player = event.getEntityPlayer();
		World world = player.getEntityWorld();

		IBlockState state = world.getBlockState(event.getPos());
		BlockPos pos = event.getPos().offset(event.getFace());
		
		if ( !(state.getBlock() instanceof BlockLog)|| state.getBlock() instanceof BlockDebarkedLog) {
			return;
		}

		

		if(!world.isRemote){
			ItemStack heldStack = player.getHeldItemMainhand();
			if (!heldStack.isEmpty() && heldStack.getItem() == Items.FLINT && world.rand.nextInt(20) == 0)
			{
				
				IBlockState newState = findCorrectState(state);
				if(newState != null){
					world.setBlockState(event.getPos(), newState, 3);
					ItemStack stackOut = findCorrectStack(state);
					if (!stackOut.isEmpty()) {
						
						EntityItem entityitem = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), stackOut);
						world.spawnEntity(entityitem);

						if (!(player instanceof FakePlayer)) {
							entityitem.onCollideWithPlayer(player);
						}
					}
				}
			}else{
				
				
				if(!heldStack.isEmpty() && checkOres("toolKnife",heldStack) && world.rand.nextInt(10) == 0){
					IBlockState newState = findCorrectState(state);
					if(newState != null){
						world.setBlockState(event.getPos(), newState, 3);
						ItemStack stackOut = findCorrectStack(state);
						if (!stackOut.isEmpty()) {
							EntityItem entityitem = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), stackOut);
							world.spawnEntity(entityitem);
							heldStack.attemptDamageItem(1, world.rand, null);
							if (!(player instanceof FakePlayer)) {
								entityitem.onCollideWithPlayer(player);
							}
						}
					}
				}
			}
		}else
		{
			ItemStack heldStack = player.getHeldItemMainhand();

			if (!heldStack.isEmpty() && (heldStack.getItem() == Items.FLINT || checkOres("toolKnife",heldStack) ))
			{
				
				world.playSound(player, player.posX, player.posY, player.posZ, LWSoundEvents.TOOL_SCRAPE, SoundCategory.PLAYERS, 0.4F, 1.0F);
				player.swingArm(EnumHand.MAIN_HAND);	
			}
		}
	}

	private IBlockState findCorrectState(IBlockState state) {
		Block block = state.getBlock();
		int meta;

		EnumAxis axis = state.getValue(BlockLog.LOG_AXIS);


		if( block== Blocks.LOG){
			meta = block.getMetaFromState(state);

			switch(meta % 4){
			case 1:
				return LWBlocks.DEBARKED_LOG_SPRUCE.getDefaultState().withProperty(BlockDebarkedLog.LOG_AXIS,axis );
			case 2:
				return LWBlocks.DEBARKED_LOG_BIRCH.getDefaultState().withProperty(BlockDebarkedLog.LOG_AXIS,axis );
			case 3:
				return LWBlocks.DEBARKED_LOG_JUNGLE.getDefaultState().withProperty(BlockDebarkedLog.LOG_AXIS,axis );
			default:
				return LWBlocks.DEBARKED_LOG_OAK.getDefaultState().withProperty(BlockDebarkedLog.LOG_AXIS,axis );
			}
		}else 
			if( block== Blocks.LOG2){
				meta = block.getMetaFromState(state);
				if(meta % 4 == 0){
					return LWBlocks.DEBARKED_LOG_ACACIA.getDefaultState().withProperty(BlockDebarkedLog.LOG_AXIS,axis );
				}else
				if(meta % 4 == 1){
					return LWBlocks.DEBARKED_LOG_DARKOAK.getDefaultState().withProperty(BlockDebarkedLog.LOG_AXIS,axis );
				}
			}
		return null;
	}

	private ItemStack findCorrectStack(IBlockState state) {
		Block block = state.getBlock();
		int meta;
		if( block== Blocks.LOG){
			meta = block.getMetaFromState(state);
			switch(meta%4){
			case 1:
				return new ItemStack(LWItems.BARK_SPRUCE);
			case 2:
				return new ItemStack(LWItems.BARK_BIRCH);
			case 3:
				return new ItemStack(LWItems.BARK_JUNGLE);
			default:
				return new ItemStack(LWItems.BARK_OAK);
			}

		}else 
			if( block== Blocks.LOG2){
				meta = block.getMetaFromState(state);
				if(meta % 4 == 0){
					return new ItemStack(LWItems.BARK_ACACIA);
				}else
				if(meta % 4 == 1){
					return new ItemStack(LWItems.BARK_DARKOAK);
				}
			}
		return ItemStack.EMPTY;
	}
	
	private boolean checkOres(String key,ItemStack stack){
		for(ItemStack stack2 : OreDictionary.getOres(key)){
			if(stack2.getItem() == stack.getItem()){
				return true;
			}
		}
		return false;
	}
}
