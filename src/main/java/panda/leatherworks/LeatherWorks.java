package panda.leatherworks;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.ExistingSubstitutionException;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry.Type;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.RecipeSorter;
import panda.leatherworks.common.CommonProxy;
import panda.leatherworks.common.GuiHandler;
import panda.leatherworks.common.eventhandler.BucketHandler;
import panda.leatherworks.common.eventhandler.DebarkHandler;
import panda.leatherworks.common.eventhandler.LivingDropsHandler;
import panda.leatherworks.util.recipe.RecipeRepair;
import panda.leatherworks.util.recipe.RecipeRepairLeatherArmor;
import panda.leatherworks.util.recipe.ScrapingRecipe;
import panda.leatherworks.util.recipe.ShearsRecipe;
import panda.leatherworks.util.registry.ItemList;
import panda.leatherworks.util.registry.MasterRegistrar;

@Mod(modid = LeatherWorks.MODID, name = LeatherWorks.NAME, version = LeatherWorks.VERSION)

public class LeatherWorks {
	public static final String MODID = "leatherworks";
	public static final String VERSION = "1.46.0";
	public static final String NAME = "Leather Works";
	public static ArmorMaterial DUMMYLEATHER;
	public static SimpleNetworkWrapper wrapper;
	@Mod.Instance(MODID)
	public static LeatherWorks INSTANCE;

	@SidedProxy(
			clientSide = "panda.leatherworks.client.ClientProxy",
			serverSide = "panda.leatherworks.server.ServerProxy"
	)
	public static CommonProxy PROXY;

	public static Logger log = LogManager.getLogger(NAME);
//Add tanner to village?
	@EventHandler
	public void preinit(FMLPreInitializationEvent event) throws ExistingSubstitutionException {
		wrapper = NetworkRegistry.INSTANCE.newSimpleChannel(LeatherWorks.MODID);
		PROXY.registerMessageHandlers(wrapper);

		DUMMYLEATHER = EnumHelper.addArmorMaterial("leather", "leatherworks:leather", 5, new int[]{0,0,0,0}, 15, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F);
		MasterRegistrar.callRegistry(event);
		MinecraftForge.EVENT_BUS.register(new LivingDropsHandler());
		MinecraftForge.EVENT_BUS.register(new BucketHandler());
		MinecraftForge.EVENT_BUS.register(new DebarkHandler());
		
		GameRegistry.addSubstitutionAlias("minecraft:leather_chestplate", Type.ITEM, ItemList.LEATHER_CHESTPLATE);
		GameRegistry.addSubstitutionAlias("minecraft:leather_boots",      Type.ITEM, ItemList.LEATHER_BOOTS);
		GameRegistry.addSubstitutionAlias("minecraft:leather_helmet", 	  Type.ITEM, ItemList.LEATHER_HELMET);
		GameRegistry.addSubstitutionAlias("minecraft:leather_leggings",   Type.ITEM, ItemList.LEATHER_LEGGINGS);
		//GameRegistry.findItem("gotwood", name);
		
		
		NetworkRegistry.INSTANCE.registerGuiHandler(INSTANCE, new GuiHandler());
		
	}

	@EventHandler
	public void init(FMLInitializationEvent event) throws ExistingSubstitutionException {
		
		RecipeSorter.INSTANCE.register("leatherworks:scrapingrecipe", ScrapingRecipe.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
		RecipeSorter.INSTANCE.register("leatherworks:shearsrecipe", ShearsRecipe.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
		RecipeSorter.INSTANCE.register("leatherworks:repairleatherarmorrecipe", RecipeRepairLeatherArmor.class,RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
		RecipeSorter.INSTANCE.register("leatherworks:repairrecipe", RecipeRepair.class,RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");

		PROXY.registerColorHandlers();

		CraftingManager.getInstance().addRecipe(new ScrapingRecipe(new ItemStack(ItemList.CRAFTING_LEATHER,1,0),new ArrayList<ItemStack>(Arrays.asList(new ItemStack(ItemList.RAWHIDE,1,OreDictionary.WILDCARD_VALUE), new ItemStack(Items.FLINT)))));
		CraftingManager.getInstance().addRecipe(new ScrapingRecipe(new ItemStack(ItemList.CRAFTING_LEATHER,1,0),new ArrayList<ItemStack>(Arrays.asList(new ItemStack(Items.RABBIT_HIDE), new ItemStack(Items.FLINT)))));
		
		CraftingManager.getInstance().addRecipe(new ShearsRecipe(new ItemStack(ItemList.LEATHER_STRIP,4),new ArrayList<ItemStack>(Arrays.asList(new ItemStack(Items.LEATHER), new ItemStack(Items.SHEARS,1,OreDictionary.WILDCARD_VALUE)))));
		CraftingManager.getInstance().addRecipe(new ShearsRecipe(new ItemStack(ItemList.LEATHER_STRIP,8),new ArrayList<ItemStack>(Arrays.asList(new ItemStack(ItemList.LEATHER_SHEET), new ItemStack(Items.SHEARS,1,OreDictionary.WILDCARD_VALUE)))));
		CraftingManager.getInstance().addRecipe( new RecipeRepairLeatherArmor());
		CraftingManager.getInstance().addRecipe( new RecipeRepair());
		
		
	
	}
	
	public static final CreativeTabs LeatherTab = new CreativeTabs(LeatherWorks.MODID) {
		@Override
		public Item getTabIconItem() {
			return ItemList.RAWHIDE;
		}

	};
	
}