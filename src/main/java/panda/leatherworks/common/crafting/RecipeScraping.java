package panda.leatherworks.common.crafting;

import java.util.List;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import net.minecraftforge.registries.IForgeRegistryEntry;
import panda.leatherworks.LeatherWorks;

public class RecipeScraping implements IRecipeFactory{

	@Override
	public IRecipe parse(JsonContext context, JsonObject json) {
		ShapelessOreRecipe recipe = ShapelessOreRecipe.factory(context, json);

        return new ShapelessScrapingRecipe( recipe.getRecipeOutput(), recipe.getIngredients());
    }

    public static class ShapelessScrapingRecipe extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {
    	/** Is the ItemStack that you get when craft the recipe. */
        private final ItemStack recipeOutput;
        /** Is a List of ItemStack that composes the recipe. */
        public final NonNullList<Ingredient> recipeItems;
        private final String group;

        public ShapelessScrapingRecipe(ItemStack output, NonNullList<Ingredient> list)
        {
            this.group = LeatherWorks.MODID;
            this.recipeOutput = output;
            this.recipeItems = list;
        }
        
        @Override
        public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv)
        {
            return NonNullList.<ItemStack>withSize(inv.getSizeInventory(), ItemStack.EMPTY);
        }

        @Override
        public boolean matches(InventoryCrafting inv, World worldIn)
        {
            List<Ingredient> list = Lists.newArrayList(this.recipeItems);

            for (int i = 0; i < inv.getHeight(); ++i)
            {
                for (int j = 0; j < inv.getWidth(); ++j)
                {
                    ItemStack itemstack = inv.getStackInRowAndColumn(j, i);

                    if (!itemstack.isEmpty())
                    {
                        boolean flag = false;

                        for (Ingredient ingredient : list)
                        {
                            if (ingredient.apply(itemstack))
                            {
                                flag = true;
                                list.remove(ingredient);
                                break;
                            }
                        }
                        if (!flag)
                        {
                            return false;
                        }
                    }
                }
            }

            return list.isEmpty();
        }

        public static ShapelessRecipes deserialize(JsonObject json)
        {
            String s = JsonUtils.getString(json, "group", "");
            NonNullList<Ingredient> nonnulllist = deserializeIngredients(JsonUtils.getJsonArray(json, "ingredients"));

            if (nonnulllist.isEmpty())
            {
                throw new JsonParseException("No ingredients for shapeless recipe");
            }
            else if (nonnulllist.size() > 9)
            {
                throw new JsonParseException("Too many ingredients for shapeless recipe");
            }
            else
            {
                ItemStack itemstack = ShapedRecipes.deserializeItem(JsonUtils.getJsonObject(json, "result"), true);
                return new ShapelessRecipes(s, itemstack, nonnulllist);
            }
        }

        private static NonNullList<Ingredient> deserializeIngredients(JsonArray array)
        {
            NonNullList<Ingredient> nonnulllist = NonNullList.<Ingredient>create();

            for (int i = 0; i < array.size(); ++i)
            {
                Ingredient ingredient = ShapedRecipes.deserializeIngredient(array.get(i));

                if (ingredient != Ingredient.EMPTY)
                {
                    nonnulllist.add(ingredient);
                }
            }

            return nonnulllist;
        }

        public boolean canFit(int width, int height)
        {
            return width * height >= this.recipeItems.size();
        }
        
        
        @Override
        public String getGroup(){return this.group;}
        public ItemStack getRecipeOutput(){return this.recipeOutput;}
        public ItemStack getCraftingResult(InventoryCrafting inv){return this.recipeOutput.copy();}
        @Override
        public NonNullList<Ingredient> getIngredients(){return this.recipeItems;}
    } 
}