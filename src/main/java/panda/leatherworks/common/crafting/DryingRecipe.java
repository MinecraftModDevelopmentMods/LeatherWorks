package panda.leatherworks.common.crafting;

import net.minecraft.item.ItemStack;

public class DryingRecipe{
	
	ItemStack input,output,failed;
	int ticks;
	float failureChance;
	
	public DryingRecipe(ItemStack in, ItemStack out,int tick,ItemStack fail,float chance){
		this.input = in;
		this.output = out;
		this.failed = fail;
		this.ticks = tick;
		this.failureChance = chance;
		
	}
	
	public ItemStack getInput() {
		return input;
	}

	public void setInput(ItemStack input) {
		this.input = input;
	}

	public ItemStack getOutput() {
		return output;
	}

	public void setOutput(ItemStack output) {
		this.output = output;
	}

	public ItemStack getFailed() {
		return failed;
	}

	public void setFailed(ItemStack failed) {
		this.failed = failed;
	}

	public int getTicks() {
		return ticks;
	}

	public void setTicks(int ticks) {
		this.ticks = ticks;
	}

	public float getFailureChance() {
		return failureChance;
	}

	public void setFailureChance(float failureChance) {
		this.failureChance = failureChance;
	}

	
}
