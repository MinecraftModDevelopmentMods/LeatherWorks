package panda.leatherworks.common.crafting;

import java.util.HashMap;
import java.util.Map;

import panda.leatherworks.init.LWItems;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;


public class DryingRecipes {

	private static Map<Item, DryingRecipe> MultiList = new HashMap<Item, DryingRecipe>();

	public static void initDryingRecipes() {

		addDryingRecipe(new ItemStack(LWItems.CRAFTING_LEATHER,1,2), Items.LEATHER,60,Items.ROTTEN_FLESH,0.05f);
	}

	public static void addDryingRecipe(ItemStack input, ItemStack output,int ticks,ItemStack failed,float failureChance) {

		Item item = input.getItem();
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
		addDryingRecipe(new ItemStack(input,1), output,ticks,failed,failureChance);
	}

	public static void addDryingRecipe(ItemStack input, Item output,int ticks,ItemStack failed,float failureChance) {
		addDryingRecipe(input, new ItemStack(output,1),ticks,failed,failureChance);
	}
	
	public static void addDryingRecipe(Item input, ItemStack output,int ticks,Item failed,float failureChance) {
		addDryingRecipe(new ItemStack(input,1), output,ticks,new ItemStack(failed,1),failureChance);
	}

	public static void addDryingRecipe(ItemStack input, Item output,int ticks,Item failed,float failureChance) {
		addDryingRecipe(input, new ItemStack(output,1),ticks,new ItemStack(failed,1),failureChance);
	}

	public static void addDryingRecipe(Item input, Item output,int ticks,ItemStack failed,float failureChance) {
		addDryingRecipe(new ItemStack(input,1), new ItemStack(output,1),ticks,failed,failureChance);
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



