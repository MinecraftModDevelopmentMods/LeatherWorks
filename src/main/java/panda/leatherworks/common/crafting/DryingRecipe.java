package panda.leatherworks.common.crafting;

import java.util.Random;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class DryingRecipe implements IDryingRecipe {
	private final ItemStack input, successOutput, failureOutput;
	private int durationTicks;
	private float failureChance;
	
	public DryingRecipe(ItemStack in, ItemStack out, int tick, ItemStack fail, float chance) {
		this.input = in;
		this.successOutput = out;
		this.failureOutput = fail;
		this.durationTicks = tick;
		this.failureChance = chance;
	}

	@Override
	public boolean matches(ItemStack input) {
		return OreDictionary.itemMatches(this.input, input, false);
	}

	@Override
	public int getDurationTicks(ItemStack input) {
		return durationTicks;
	}

	@Override
	public ItemStack getOutput(ItemStack input, Random random) {
		if (random.nextFloat() > failureChance) {
			return successOutput.copy();
		} else {
			return failureOutput.copy();
		}
	}
}
