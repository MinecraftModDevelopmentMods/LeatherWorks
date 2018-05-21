package panda.leatherworks.common.eventhandler;

import panda.leatherworks.LeatherWorks;
import panda.leatherworks.common.block.BlockDebarkedLog;
import panda.leatherworks.common.registries.BarkRegistry;
import panda.leatherworks.init.LWSoundEvents;
import akka.japi.Pair;
import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;

public class DebarkHandler {
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onPlayerInteract(PlayerInteractEvent.RightClickBlock event) {
		
		EntityPlayer player = event.getEntityPlayer();
		World world = player.getEntityWorld();
		
		BlockPos pos = event.getPos().offset(event.getFace());
		IBlockState state = world.getBlockState(event.getPos());

		if ( !(state.getBlock() instanceof BlockLog) || state.getBlock() instanceof BlockDebarkedLog) {
			return;
		}
		
		if(!world.isRemote){
			ItemStack heldStack = player.getHeldItemMainhand();
			Pair pair = new Pair(state.getBlock(),state.getBlock().getMetaFromState(state) % 4);
			
			if (BarkRegistry.hasBark(pair) && !heldStack.isEmpty() && (heldStack.getItem() == Items.FLINT && world.rand.nextInt(20) == 0)
					|| checkOres("toolKnife",heldStack) && world.rand.nextInt(10) == 0  || checkOres("toolAxe",heldStack) && world.rand.nextInt(14) == 0)
			{
				IBlockState newState = BarkRegistry.getDebarkedLog(pair).getDefaultState();
				if(newState != null){
					world.setBlockState(event.getPos(), newState, 3);
					ItemStack stackOut = BarkRegistry.getBark(pair);
					if (!stackOut.isEmpty()) {
						EntityItem entityitem = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), stackOut);
						world.spawnEntity(entityitem);
						if(!player.isCreative()){
							heldStack.attemptDamageItem(1, world.rand, null);
						}
						if (!(player instanceof FakePlayer)) {
							entityitem.onCollideWithPlayer(player);
						}
					}
				}
			}
		}else
		{
			ItemStack heldStack = player.getHeldItemMainhand();

			if (!heldStack.isEmpty() && (heldStack.getItem() == Items.FLINT || checkOres("toolKnife",heldStack) || checkOres("toolAxe",heldStack) ))
			{
				world.playSound(player, player.posX, player.posY, player.posZ, LWSoundEvents.TOOL_SCRAPE, SoundCategory.PLAYERS, 0.4F, 1.0F);
				player.swingArm(EnumHand.MAIN_HAND);	
			}
		}
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
