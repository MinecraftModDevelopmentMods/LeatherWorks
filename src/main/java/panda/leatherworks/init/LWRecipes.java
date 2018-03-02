package panda.leatherworks.init;

import java.util.Iterator;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;
import panda.leatherworks.LeatherWorks;
import panda.leatherworks.common.crafting.DryingRecipes;
import panda.leatherworks.common.crafting.FakeRecipe;
import panda.leatherworks.common.crafting.RepairKitRecipe;

@EventBusSubscriber
public class LWRecipes {
	
	private LWRecipes(){LeatherWorks.logger.info("Registering Recipes");}

	public static void register() {
		removeRecipe(new ResourceLocation("minecraft:leather_leggings"));
		removeRecipe(new ResourceLocation("minecraft:leather_helmet"));
		removeRecipe(new ResourceLocation("minecraft:leather_chestplate"));
		removeRecipe(new ResourceLocation("minecraft:leather_boots"));
		
		DryingRecipes.addDryingRecipe(new ItemStack(LWItems.LEATHER_SOAKED), Items.LEATHER, 60, Items.ROTTEN_FLESH, 0.05f);
	}
	
	@SubscribeEvent
	  public static void registerRecipes(Register<IRecipe> event) {
	    IForgeRegistry<IRecipe> registry = event.getRegistry();

	    registry.register(new RepairKitRecipe(LWItems.BROKEN_LEATHER_CHESTPLATE, LWItems.LEATHER_CHESTPLATE, LWItems.REPAIR_KIT, 20));
	    registry.register(new RepairKitRecipe(LWItems.BROKEN_LEATHER_HELMET, LWItems.LEATHER_HELMET, LWItems.REPAIR_KIT, 20));
	    registry.register(new RepairKitRecipe(LWItems.BROKEN_LEATHER_BOOTS, LWItems.LEATHER_BOOTS, LWItems.REPAIR_KIT, 20));
	    registry.register(new RepairKitRecipe(LWItems.BROKEN_LEATHER_LEGGINGS, LWItems.LEATHER_LEGGINGS, LWItems.REPAIR_KIT, 20));
	    
	    registry.register(new RepairKitRecipe(LWItems.LEATHER_BOOTS, LWItems.LEATHER_BOOTS, LWItems.REPAIR_KIT, 20));
	    registry.register(new RepairKitRecipe(LWItems.LEATHER_LEGGINGS, LWItems.LEATHER_LEGGINGS, LWItems.REPAIR_KIT, 20));
	    registry.register(new RepairKitRecipe(LWItems.LEATHER_HELMET, LWItems.LEATHER_HELMET, LWItems.REPAIR_KIT, 20));
	    registry.register(new RepairKitRecipe(LWItems.LEATHER_CHESTPLATE, LWItems.LEATHER_CHESTPLATE, LWItems.REPAIR_KIT, 20));
	  }

	public static void removeFurnaceRecipe(ItemStack resultItem) {
		Map<ItemStack, ItemStack> recipes = FurnaceRecipes.instance().getSmeltingList();
		for (Iterator<Map.Entry<ItemStack, ItemStack>> entries = recipes.entrySet().iterator(); entries.hasNext();) {
			Map.Entry<ItemStack, ItemStack> entry = entries.next();
			ItemStack result = entry.getValue();
			if (ItemStack.areItemStacksEqual(result, resultItem)) {
				entries.remove();
			}
		}
	}
	
	  public static void removeRecipe(ResourceLocation name)
	  {
	    IRecipe recipe = CraftingManager.getRecipe(name);
	    if (recipe != null)
	    {
	    	LeatherWorks.logger.info("Removing Recipe: " + name);
	      ForgeRegistries.RECIPES.register(new FakeRecipe(recipe));
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
}