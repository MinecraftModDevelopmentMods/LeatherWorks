package panda.leatherworks.entity;

import panda.leatherworks.LeatherWorks;
import panda.leatherworks.util.network.PacketUpdateRack;
import panda.leatherworks.util.recipe.DryingRecipe;
import panda.leatherworks.util.recipe.DryingRecipes;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityDryingRack extends TileEntityItemRack implements ITickable{
private static int tickCounter = 0;
private static long currentTime = 0;
private static long endTime = 0;

//DryingRecipes.containsRecipe(heldItem.getItem())

public ItemStackHandler inventory = new ItemStackHandler(1) {
		@Override
		protected void onContentsChanged(int slot) {
			World world = Minecraft.getMinecraft().theWorld;

			LeatherWorks.wrapper.sendToAllAround(new PacketUpdateRack(TileEntityDryingRack.this), new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 64));
			
		}
		@Override
		protected int getStackLimit(int slot, ItemStack stack)
	    {
	        return 1;
	    }
		
		@Override
	    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate)
	    {
	        if (stack == null || stack.stackSize == 0)
	            return null;
	        //if(DryingRecipes.containsRecipe(stack.getItem())){
	        validateSlotIndex(slot);

	        ItemStack existing = this.stacks[slot];

	        int limit = getStackLimit(slot, stack);

	        if (existing != null)
	        {
	            if (!ItemHandlerHelper.canItemStacksStack(stack, existing))
	                return stack;

	            limit -= existing.stackSize;
	        }

	        if (limit <= 0)
	            return stack;

	        boolean reachedLimit = stack.stackSize > limit;

	        if (!simulate)
	        {
	            if (existing == null)
	            {
	                this.stacks[slot] = reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, limit) : stack;
	            }
	            else
	            {
	                existing.stackSize += reachedLimit ? limit : stack.stackSize;
	            }
	            onContentsChanged(slot);
	        }

	        return reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, stack.stackSize - limit) : null;
	    //}return null;
	    }
	};
	
	public TileEntityDryingRack(){
		this.tickCounter = 0;
		this.currentTime = 0;
		this.endTime = 0;
	}
	

	@Override
	public void update() {
		World world = this.getWorld();
		
		if(!world.isRemote)
		{
			++tickCounter;
			
			if(tickCounter == 20){
				
				IItemHandler cap = this.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
				ItemStack stack = cap.getStackInSlot(0);
				tickCounter = 0;
				currentTime = world.getTotalWorldTime();
				//endTime = currentTime +
				System.out.println(this.endTime);
				if(stack != null && this.endTime>0) {
					
					//System.out.println(currentTime+":"+endTime);
					System.out.println(stack);
					DryingRecipe recipe = DryingRecipes.getDryingResults(stack);
					
					if(recipe != null){
						if(world.rand.nextFloat()< recipe.getFailureChance()){
							System.out.println("ROT:"+stack);
							//if(cap.extractItem(0, 1, true) != null){
							cap.extractItem(0, 1, false);
							//System.out.println(cap.getStackInSlot(0));
							//	}
							cap.insertItem(0, new ItemStack(Items.ROTTEN_FLESH), false);
							//System.out.println(cap.getStackInSlot(0));

							this.endTime = 0;
						}else{
							System.out.println(currentTime+":"+endTime);
						if(endTime > 0 && currentTime >= endTime){
							System.out.println("LEATHER:"+stack);
							//if(cap.extractItem(0, 1, true) != null){
							cap.extractItem(0, 1, false);
							
							//}
							cap.insertItem(0, new ItemStack(Items.LEATHER), false);
							this.endTime = 0;
						}
						}
						
					}else{
						System.out.println("Recipe was null!");
					}

				}
			}
		}
	} 
	//when we collect, all endTimes are set to zero
		
	
	
	public void setEndTime(long time){
		//if(inventory.getStackInSlot(0) != null){
			
			this.endTime = time;
		//}
		
	}
	
	public long getEndTime(){
		//if(inventory.getStackInSlot(0) != null){
			
			return this.endTime;
		//}
		
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setTag("inventory", inventory.serializeNBT());
		compound.setLong("end_time", endTime);
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		inventory.deserializeNBT(compound.getCompoundTag("inventory"));
		endTime = compound.getLong("end_time");
		super.readFromNBT(compound);
	}

}
