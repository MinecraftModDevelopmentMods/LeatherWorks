package panda.leatherworks.util.recipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import panda.leatherworks.util.registry.ItemList;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;


public class DryingRecipes {

	private static Map<Item, DryingRecipe> MultiList = new HashMap<Item, DryingRecipe>();

	public static void initDryingRecipes() {

		addDryingRecipe(new ItemStack(ItemList.CRAFTING_LEATHER,1,2), Items.LEATHER,60,Items.ROTTEN_FLESH,0.05f);
	}

	public static void addDryingRecipe(ItemStack input, ItemStack output,int ticks,ItemStack failed,float failureChance) {

		Item item = input.getItem();
		int meta = input.getMetadata();
		int amount = input.stackSize;
		ItemStack stack = new ItemStack(output.getItem(), amount, meta);
		DryingRecipe recipe = new DryingRecipe(input,output,ticks,failed,failureChance);
		
		if (getAllDryingResults(item) == null) { 
			
			MultiList.put(item, recipe);
			return;
		}
		/*
		DryingRecipe retrieved = getAllDryingResults(item);
		if (retrieved != null && retrieved.getOutput() == stack) {
			MultiList.replace(item, retrieved);
		}*/

	}

	public static void addDryingRecipe(Item input, ItemStack output,int ticks,ItemStack failed,float failureChance) {
		addDryingRecipe(new ItemStack(input), output,ticks,failed,failureChance);
	}

	public static void addDryingRecipe(ItemStack input, Item output,int ticks,ItemStack failed,float failureChance) {
		addDryingRecipe(input, new ItemStack(output),ticks,failed,failureChance);
	}
	
	public static void addDryingRecipe(Item input, ItemStack output,int ticks,Item failed,float failureChance) {
		addDryingRecipe(new ItemStack(input), output,ticks,new ItemStack(failed),failureChance);
	}

	public static void addDryingRecipe(ItemStack input, Item output,int ticks,Item failed,float failureChance) {
		addDryingRecipe(input, new ItemStack(output),ticks,new ItemStack(failed),failureChance);
	}

	public static void addDryingRecipe(Item input, Item output,int ticks,ItemStack failed,float failureChance) {
		addDryingRecipe(new ItemStack(input), new ItemStack(output),ticks,failed,failureChance);
	}

	private static DryingRecipe getAllDryingResults(Item input) {
		return MultiList.get(input);
	}
	public static boolean containsRecipe(Item input){
		//System.out.println(input);
		return MultiList.containsKey(input);
		
	}

	// getAllDryingResults is internal for THIS CLASS only! DO NOT USE IT!

	private static DryingRecipe getAllDryingResults(ItemStack input) {
		return getAllDryingResults(input.getItem());
	}

	
	@Nullable
	public static DryingRecipe getDryingResults(ItemStack input) {
		DryingRecipe result = getAllDryingResults(input);
		//DryingRecipe returned = new ArrayList<ItemStack>();
		if (result != null) {
				if (result.input.getMetadata() == input.getMetadata()) {
					return result;
				}
			
		}
		return null;
	}

}



