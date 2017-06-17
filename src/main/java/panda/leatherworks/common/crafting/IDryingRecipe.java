package panda.leatherworks.common.crafting;

import java.util.Random;
import net.minecraft.item.ItemStack;

public interface IDryingRecipe {
	boolean matches(ItemStack input);

	int getDurationTicks(ItemStack input);

	ItemStack getOutput(ItemStack input, Random random);
}
