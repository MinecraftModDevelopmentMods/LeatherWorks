package panda.leatherworks.util.registry;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import panda.leatherworks.common.crafting.DryingRecipes;

public class RecipeRegistry {

	public static void register() {
		DryingRecipes.initDryingRecipes();
		
		GameRegistry.addShapelessRecipe(new ItemStack(ItemList.TANNIN_BALL, 2), new ItemStack(ItemList.BARK,1,5));
		GameRegistry.addShapelessRecipe(new ItemStack(ItemList.TANNIN_BALL, 2), new ItemStack(ItemList.BARK,1,4));
		GameRegistry.addShapelessRecipe(new ItemStack(ItemList.TANNIN_BALL, 2), new ItemStack(ItemList.BARK,1,1), new ItemStack(ItemList.BARK,1,1));
		GameRegistry.addShapelessRecipe(new ItemStack(ItemList.TANNIN_BALL, 2), new ItemStack(ItemList.BARK,1,0), new ItemStack(ItemList.BARK,1,0));
		GameRegistry.addShapelessRecipe(new ItemStack(ItemList.TANNIN_BALL, 1), new ItemStack(ItemList.BARK,1,3), new ItemStack(ItemList.BARK,1,3),new ItemStack(ItemList.BARK,1,3));
		GameRegistry.addShapelessRecipe(new ItemStack(ItemList.TANNIN_BALL, 1), new ItemStack(ItemList.BARK,1,2), new ItemStack(ItemList.BARK,1,2),new ItemStack(ItemList.BARK,1,2),new ItemStack(ItemList.BARK,1,2));
		GameRegistry.addShapelessRecipe(new ItemStack(ItemList.TANNIN_BOTTLE),new ItemStack(ItemList.TANNIN_BALL, 1),PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.WATER));
		GameRegistry.addShapelessRecipe(new ItemStack(ItemList.TANNIN_BUCKET),new ItemStack(ItemList.TANNIN_BALL, 1),new ItemStack(Items.WATER_BUCKET));
		
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.PLANKS,4,0), new ItemStack(BlockList.DEBARKED_LOG_OAK));
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.PLANKS,4,1), new ItemStack(BlockList.DEBARKED_LOG_SPRUCE));
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.PLANKS,4,2), new ItemStack(BlockList.DEBARKED_LOG_BIRCH));
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.PLANKS,4,3), new ItemStack(BlockList.DEBARKED_LOG_JUNGLE));
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.PLANKS,4,4), new ItemStack(BlockList.DEBARKED_LOG_ACACIA));
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.PLANKS,4,5), new ItemStack(BlockList.DEBARKED_LOG_DARKOAK));
		
		GameRegistry.addShapedRecipe(new ItemStack(ItemList.LEATHER_SHEET), "SL", "LS", 'S', new ItemStack(Items.LEATHER),
			'L', new ItemStack(Items.STRING));
		
		GameRegistry.addShapedRecipe(new ItemStack(ItemList.PACK), "SLS", "LIL", "SLS", 'S', new ItemStack(ItemList.LEATHER_STRIP),
			'L', new ItemStack(ItemList.LEATHER_SHEET), 'I', new ItemStack(Items.GOLD_INGOT));
		GameRegistry.addShapedRecipe(new ItemStack(ItemList.ENDER_PACK), "EEE", "EPE", "EYE", 'P',
			new ItemStack(ItemList.PACK), 'E', new ItemStack(Items.ENDER_PEARL), 'Y',
			new ItemStack(Items.ENDER_EYE));
		GameRegistry.addShapedRecipe(new ItemStack(BlockList.RACK), "SSS", 'S',
			new ItemStack(Blocks.WOODEN_SLAB,1,OreDictionary.WILDCARD_VALUE));
		
		for(ItemStack stack :OreDictionary.getOres("plankWood")){
			GameRegistry.addShapedRecipe(new ItemStack(BlockList.BARREL), "P P", "P P", "PPP", 'P',
				stack);
			GameRegistry.addShapedRecipe(new ItemStack(BlockList.BARREL_SEALED), "PLP", "P P", "PPP",
				'P', stack,'L', Blocks.WOODEN_PRESSURE_PLATE);
		}
		
		GameRegistry.addShapedRecipe(new ItemStack(BlockList.BARREL_SEALED), "L", "P", 'P',
			BlockList.BARREL,'L', Blocks.WOODEN_PRESSURE_PLATE);
		GameRegistry.addShapelessRecipe(new ItemStack(BlockList.BARREL),new ItemStack(BlockList.BARREL_SEALED));
				
		GameRegistry.addShapelessRecipe(new ItemStack(ItemList.PACK,1,1),new ItemStack(ItemList.PACK), new ItemStack(Items.DYE,1,1));
		GameRegistry.addShapelessRecipe(new ItemStack(ItemList.PACK,1,2),new ItemStack(ItemList.PACK), new ItemStack(Items.DYE,1,14));
		GameRegistry.addShapelessRecipe(new ItemStack(ItemList.PACK,1,3),new ItemStack(ItemList.PACK), new ItemStack(Items.DYE,1,11));
		GameRegistry.addShapelessRecipe(new ItemStack(ItemList.PACK,1,4),new ItemStack(ItemList.PACK), new ItemStack(Items.DYE,1,10));
		GameRegistry.addShapelessRecipe(new ItemStack(ItemList.PACK,1,5),new ItemStack(ItemList.PACK), new ItemStack(Items.DYE,1,2));
		GameRegistry.addShapelessRecipe(new ItemStack(ItemList.PACK,1,6),new ItemStack(ItemList.PACK), new ItemStack(Items.DYE,1,6));
		GameRegistry.addShapelessRecipe(new ItemStack(ItemList.PACK,1,7),new ItemStack(ItemList.PACK), new ItemStack(Items.DYE,1,12));
		GameRegistry.addShapelessRecipe(new ItemStack(ItemList.PACK,1,8),new ItemStack(ItemList.PACK), new ItemStack(Items.DYE,1,4));
		GameRegistry.addShapelessRecipe(new ItemStack(ItemList.PACK,1,9),new ItemStack(ItemList.PACK), new ItemStack(Items.DYE,1,5));
		GameRegistry.addShapelessRecipe(new ItemStack(ItemList.PACK,1,10),new ItemStack(ItemList.PACK), new ItemStack(Items.DYE,1,13));
		GameRegistry.addShapelessRecipe(new ItemStack(ItemList.PACK,1,11),new ItemStack(ItemList.PACK), new ItemStack(Items.DYE,1,9));
		GameRegistry.addShapelessRecipe(new ItemStack(ItemList.PACK,1,12),new ItemStack(ItemList.PACK), new ItemStack(Items.DYE,1,7));
		GameRegistry.addShapelessRecipe(new ItemStack(ItemList.PACK,1,13),new ItemStack(ItemList.PACK), new ItemStack(Items.DYE,1,8));
		GameRegistry.addShapelessRecipe(new ItemStack(ItemList.PACK,1,14),new ItemStack(ItemList.PACK), new ItemStack(Items.DYE,1,0));

		GameRegistry.addShapelessRecipe(new ItemStack(ItemList.REPAIR_KIT), new ItemStack(Items.LEATHER),new ItemStack(Items.STRING));
		
		
	
	}

	public static void removeFurnaceRecipe(ItemStack resultItem) {
		Map<ItemStack, ItemStack> recipes = FurnaceRecipes.instance().getSmeltingList();
		for (Iterator<Map.Entry<ItemStack, ItemStack>> entries = recipes.entrySet().iterator(); entries.hasNext();) {
			Map.Entry<ItemStack, ItemStack> entry = entries.next();
			ItemStack result = entry.getValue();
			if (ItemStack.areItemStacksEqual(result, resultItem)) {												// ItemStack,
				entries.remove();
			}
		}
	}


	public static void addOredicts(String[] oreDictEntries, Block name) {
		addOredicts(oreDictEntries, new ItemStack(name));
	}

	public static void addOredicts(String[] oreDictEntries, Item name) {
		addOredicts(oreDictEntries, new ItemStack(name));
	}

	public static void addOredicts(String[] oreDictEntries, ItemStack itemStackName) {
		for (final String oreDictEntry : oreDictEntries)
			OreDictionary.registerOre(oreDictEntry, itemStackName);
	}

	public static void removeRecipe(Item item) {

		List<IRecipe> recipies = CraftingManager.getInstance().getRecipeList();

		Iterator<IRecipe> remover = recipies.iterator();

		while (remover.hasNext()) {
			ItemStack itemstack = remover.next().getRecipeOutput();

			if (itemstack != null && itemstack.getItem() == item) {
				remover.remove();
			}

		}

	}
}