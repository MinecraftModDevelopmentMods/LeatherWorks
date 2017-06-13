package panda.leatherworks;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.ExistingSubstitutionException;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry.Type;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.RecipeSorter;
import panda.leatherworks.gui.GuiHandler;
import panda.leatherworks.items.armor.ItemBrokenArmor;
import panda.leatherworks.util.events.BucketHandler;
import panda.leatherworks.util.events.DebarkHandler;
import panda.leatherworks.util.events.LivingDropsHandler;
import panda.leatherworks.util.network.PacketRequestUpdateRack;
import panda.leatherworks.util.network.PacketUpdateRack;
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

	public static Logger log = LogManager.getLogger(NAME);
//Add tanner to village?
	@EventHandler
	public void preinit(FMLPreInitializationEvent event) throws ExistingSubstitutionException {
		wrapper = NetworkRegistry.INSTANCE.newSimpleChannel(LeatherWorks.MODID);
		wrapper.registerMessage(new PacketUpdateRack.Handler(), PacketUpdateRack.class, 0, Side.CLIENT);
		wrapper.registerMessage(new PacketRequestUpdateRack.Handler(), PacketRequestUpdateRack.class, 1, Side.SERVER);
		
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
		
		registerColorHandlers(event);
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
	
	@SideOnly(Side.CLIENT)
	public static void registerColorHandlers(FMLInitializationEvent e) {
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new IItemColor() {
			public int getColorFromItemstack(ItemStack stack, int tintIndex) {
				return tintIndex > 0 ? -1
						: ((ItemBrokenArmor) (stack.getItem())).getColor(stack);
			}
		}, ItemList.BROKEN_LEATHER_CHESTPLATE);
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new IItemColor() {
			public int getColorFromItemstack(ItemStack stack, int tintIndex) {
				return tintIndex > 0 ? -1
						: ((ItemBrokenArmor) (stack.getItem())).getColor(stack);
			}
		}, ItemList.BROKEN_LEATHER_HELMET);
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new IItemColor() {
			public int getColorFromItemstack(ItemStack stack, int tintIndex) {
				return tintIndex > 0 ? -1
						: ((ItemBrokenArmor) (stack.getItem())).getColor(stack);
			}
		}, ItemList.BROKEN_LEATHER_LEGGINGS);
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new IItemColor() {
			public int getColorFromItemstack(ItemStack stack, int tintIndex) {
				return tintIndex > 0 ? -1
						: ((ItemBrokenArmor) (stack.getItem())).getColor(stack);
			}
		}, ItemList.BROKEN_LEATHER_BOOTS);
	}
	
	

}