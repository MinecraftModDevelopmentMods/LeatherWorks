package panda.leatherworks.common.crafting;

import java.util.ArrayList;
import java.util.List;

import panda.leatherworks.init.LWItems;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;


public class DryingRecipes {

	private static List<IDryingRecipe> recipes = new ArrayList<>();

	public static void initDryingRecipes() {
		addDryingRecipe(new ItemStack(LWItems.CRAFTING_LEATHER,1,2), Items.LEATHER, 60, Items.ROTTEN_FLESH, 0.05f);
	}

	public static void addDryingRecipe(ItemStack input, ItemStack output, int ticks, ItemStack failed, float failureChance) {
		DryingRecipe recipe = new DryingRecipe(input, output, ticks, failed, failureChance);
		recipes.add(recipe);
	}

	public static void addDryingRecipe(Item input, ItemStack output, int ticks, ItemStack failed, float failureChance) {
		addDryingRecipe(new ItemStack(input,1), output,ticks,failed,failureChance);
	}

	public static void addDryingRecipe(ItemStack input, Item output, int ticks, ItemStack failed, float failureChance) {
		addDryingRecipe(input, new ItemStack(output,1),ticks,failed,failureChance);
	}
	
	public static void addDryingRecipe(Item input, ItemStack output, int ticks, Item failed, float failureChance) {
		addDryingRecipe(new ItemStack(input,1), output,ticks,new ItemStack(failed,1),failureChance);
	}

	public static void addDryingRecipe(ItemStack input, Item output, int ticks, Item failed, float failureChance) {
		addDryingRecipe(input, new ItemStack(output,1),ticks,new ItemStack(failed,1),failureChance);
	}

	public static void addDryingRecipe(Item input, Item output, int ticks, ItemStack failed, float failureChance) {
		addDryingRecipe(new ItemStack(input,1), new ItemStack(output,1),ticks,failed,failureChance);
	}

	public static boolean hasRecipe(ItemStack input) {
		return getDryingRecipe(input) != null;
	}

	public static IDryingRecipe getDryingRecipe(ItemStack input) {
		for (IDryingRecipe recipe : recipes) {
			if (recipe.matches(input)) {
				return recipe;
			}
		}
		return null;
	}

}



