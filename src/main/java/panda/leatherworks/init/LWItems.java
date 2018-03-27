package panda.leatherworks.init;

import net.minecraft.block.Block;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import panda.leatherworks.ConfigLeatherWorks;
import panda.leatherworks.LeatherWorks;
import panda.leatherworks.common.item.ItemBucketTannin;
import panda.leatherworks.common.item.ItemCraftingLeather;
import panda.leatherworks.common.item.ItemEnderPack;
import panda.leatherworks.common.item.ItemPack;
import panda.leatherworks.common.item.armor.ItemBrokenArmor;
import panda.leatherworks.common.item.armor.ItemLeatherworksArmor;

@EventBusSubscriber
public final class LWItems {
	
	private LWItems(){LeatherWorks.logger.info("Registering Items");}

	public static final ItemArmor.ArmorMaterial DUMMYLEATHER = EnumHelper.addArmorMaterial("leather", "leatherworks:leather", 5, new int[]{0,0,0,0}, 0, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F);

	public static final Item RAWHIDE_COW = simply(new Item(), "rawhide_cow");
	public static final Item RAWHIDE_PIG = simply(new Item(), "rawhide_pig");
	public static final Item RAWHIDE_HORSE = simply(new Item(), "rawhide_horse");
	public static final Item RAWHIDE_WOLF = simply(new Item(), "rawhide_wolf");
	public static final Item RAWHIDE_POLARBEAR = simply(new Item(), "rawhide_polarbear");
	public static final Item RAWHIDE_MOOSHROOM = simply(new Item(), "rawhide_mooshroom");
	public static final Item RAWHIDE_LLAMA =  simply(new Item(), "rawhide_llama");
	public static final Item RAWHIDE_MULE = simply(new Item(), "rawhide_mule");
	public static final Item RAWHIDE_DONKEY = simply(new Item(), "rawhide_donkey");
	
	public static final Item BARK_OAK = simply(new Item(), "bark_oak");
	public static final Item BARK_SPRUCE = simply(new Item(), "bark_spruce");
	public static final Item BARK_BIRCH = simply(new Item(), "bark_birch");
	public static final Item BARK_JUNGLE = simply(new Item(), "bark_jungle");
	public static final Item BARK_ACACIA = simply(new Item(), "bark_acacia");
	public static final Item BARK_DARKOAK = simply(new Item(), "bark_darkoak");

	public static final Item TANNIN_BOTTLE = simply(new Item(), "tannin_bottle");
	public static final Item TANNIN_BALL = simply(new Item(), "tannin_ball");
	public static final Item REPAIR_KIT = simply(new Item(), "repair_kit").setMaxStackSize(16);

	public static final Item TANNIN_BUCKET = simply(new ItemBucketTannin(), "tannin_bucket");

	public static final Item LEATHER_SCRAPED = simply(new ItemCraftingLeather(), "crafting_leather_scraped");
	public static final Item LEATHER_WASHED = simply(new ItemCraftingLeather(), "crafting_leather_washed");
	public static final Item LEATHER_SOAKED = simply(new ItemCraftingLeather(), "crafting_leather_soaked");
	
	public static final Item PACK_RED = simply(new ItemPack(), "pack_"+EnumDyeColor.RED.getName().toLowerCase());
	public static final Item PACK_BLACK = simply(new ItemPack(), "pack_"+EnumDyeColor.BLACK.getName().toLowerCase());
	public static final Item PACK_BLUE = simply(new ItemPack(), "pack_"+EnumDyeColor.BLUE.getName().toLowerCase());
	public static final Item PACK_BROWN = simply(new ItemPack(), "pack_"+EnumDyeColor.BROWN.getName().toLowerCase());
	public static final Item PACK_CYAN = simply(new ItemPack(), "pack_"+EnumDyeColor.CYAN.getName().toLowerCase());
	public static final Item PACK_GRAY = simply(new ItemPack(), "pack_"+EnumDyeColor.GRAY.getName().toLowerCase());
	public static final Item PACK_GREEN = simply(new ItemPack(), "pack_"+EnumDyeColor.GREEN.getName().toLowerCase());
	public static final Item PACK_LIGHT_BLUE = simply(new ItemPack(), "pack_"+EnumDyeColor.LIGHT_BLUE.getName().toLowerCase());
	public static final Item PACK_LIME = simply(new ItemPack(), "pack_"+EnumDyeColor.LIME.getName().toLowerCase());
	public static final Item PACK_MAGENTA = simply(new ItemPack(), "pack_"+EnumDyeColor.MAGENTA.getName().toLowerCase());
	public static final Item PACK_ORANGE = simply(new ItemPack(), "pack_"+EnumDyeColor.ORANGE.getName().toLowerCase());
	public static final Item PACK_PINK = simply(new ItemPack(), "pack_"+EnumDyeColor.PINK.getName().toLowerCase());
	public static final Item PACK_PURPLE = simply(new ItemPack(), "pack_"+EnumDyeColor.PURPLE.getName().toLowerCase());
	public static final Item PACK_YELLOW = simply(new ItemPack(), "pack_"+EnumDyeColor.YELLOW.getName().toLowerCase());
	public static final Item PACK_SILVER = simply(new ItemPack(), "pack_"+EnumDyeColor.SILVER.getName().toLowerCase());

	public static final Item ENDER_PACK = simply(new ItemEnderPack(), "ender_pack");
	public static final Item LEATHER_STRIP = simply(new Item(), "leather_strip");
	public static final Item LEATHER_SHEET = simply(new Item(), "leather_sheet");
	
	public static final Item BROKEN_LEATHER_HELMET = simply(new ItemBrokenArmor(EntityEquipmentSlot.HEAD), "broken_leather_helmet");
	public static final Item BROKEN_LEATHER_CHESTPLATE = simply(new ItemBrokenArmor(EntityEquipmentSlot.CHEST), "broken_leather_chestplate");
	public static final Item BROKEN_LEATHER_LEGGINGS = simply(new ItemBrokenArmor(EntityEquipmentSlot.LEGS), "broken_leather_leggings");
	public static final Item BROKEN_LEATHER_BOOTS = simply(new ItemBrokenArmor(EntityEquipmentSlot.FEET), "broken_leather_boots");
	
	public static final Item LEATHER_HELMET = new ItemLeatherworksArmor(ItemArmor.ArmorMaterial.LEATHER, EntityEquipmentSlot.HEAD, BROKEN_LEATHER_HELMET).setUnlocalizedName("minecraft.leather_helmet").setRegistryName("minecraft:leather_helmet");
	public static final Item LEATHER_CHESTPLATE = new ItemLeatherworksArmor(ItemArmor.ArmorMaterial.LEATHER, EntityEquipmentSlot.CHEST , BROKEN_LEATHER_CHESTPLATE).setUnlocalizedName("minecraft.leather_chestplate").setRegistryName("minecraft:leather_chestplate");
	public static final Item LEATHER_LEGGINGS = new ItemLeatherworksArmor(ItemArmor.ArmorMaterial.LEATHER, EntityEquipmentSlot.LEGS, BROKEN_LEATHER_LEGGINGS).setUnlocalizedName("minecraft.leather_leggings").setRegistryName("minecraft:leather_leggings");
	public static final Item LEATHER_BOOTS = new ItemLeatherworksArmor(ItemArmor.ArmorMaterial.LEATHER, EntityEquipmentSlot.FEET, BROKEN_LEATHER_BOOTS).setUnlocalizedName("minecraft.leather_boots").setRegistryName("minecraft:leather_boots");

	

	

	private static Item simply(Item item, String name) {
		return item.setRegistryName(LeatherWorks.MODID, name).setUnlocalizedName(LeatherWorks.MODID + "." + name).setCreativeTab(LeatherWorks.LeatherTab);
	}

	@SubscribeEvent
	public static void register(RegistryEvent.Register<Item> event) {
		IForgeRegistry<Item> registry = event.getRegistry();

		registry.register(RAWHIDE_COW);
		registry.register(RAWHIDE_PIG);
		registry.register(RAWHIDE_HORSE);
		registry.register(RAWHIDE_WOLF);
		registry.register(RAWHIDE_POLARBEAR);
		registry.register(RAWHIDE_MOOSHROOM);
		registry.register(RAWHIDE_LLAMA);
		registry.register(RAWHIDE_MULE);
		registry.register(RAWHIDE_DONKEY);
		
		registry.register(LEATHER_SCRAPED);
		registry.register(LEATHER_WASHED);
		registry.register(LEATHER_SOAKED);
		registry.register(BARK_OAK);
		registry.register(BARK_SPRUCE);
		registry.register(BARK_BIRCH);
		registry.register(BARK_JUNGLE);
		registry.register(BARK_ACACIA);
		registry.register(BARK_DARKOAK);
		registry.register(TANNIN_BALL);
		registry.register(TANNIN_BOTTLE);
		registry.register(TANNIN_BUCKET);
		registry.register(LEATHER_STRIP);
		registry.register(LEATHER_SHEET);
		registry.register(REPAIR_KIT);
		
		if(!ConfigLeatherWorks.disableAllPacks){
			registry.register(PACK_BROWN);
			if(!ConfigLeatherWorks.disableColoredPacks){
				registry.register(PACK_RED);
				registry.register(PACK_ORANGE);
				registry.register(PACK_YELLOW);
				registry.register(PACK_LIME); 
				registry.register(PACK_GREEN);
				registry.register(PACK_CYAN);
				registry.register(PACK_LIGHT_BLUE);
				registry.register(PACK_BLUE); 
				registry.register(PACK_PURPLE);
				registry.register(PACK_MAGENTA);
				registry.register(PACK_PINK);
				registry.register(PACK_GRAY); 
				registry.register(PACK_SILVER);
				registry.register(PACK_BLACK);
			}
			if(!ConfigLeatherWorks.disableEnderPack){
				registry.register(ENDER_PACK);
			}
		}
		
		registry.register(BROKEN_LEATHER_HELMET);
		registry.register(BROKEN_LEATHER_CHESTPLATE);
		registry.register(BROKEN_LEATHER_LEGGINGS);
		registry.register(BROKEN_LEATHER_BOOTS);
		registry.register(LEATHER_HELMET);
		registry.register(LEATHER_CHESTPLATE);
		registry.register(LEATHER_LEGGINGS);
		registry.register(LEATHER_BOOTS);
		registerItemBlock(registry, LWBlocks.BARREL_OAK);
		registerItemBlock(registry, LWBlocks.BARREL_SPRUCE);
		registerItemBlock(registry, LWBlocks.BARREL_BIRCH);
		registerItemBlock(registry, LWBlocks.BARREL_JUNGLE);
		registerItemBlock(registry, LWBlocks.BARREL_ACACIA);
		registerItemBlock(registry, LWBlocks.BARREL_DARKOAK);
		
		registerItemBlock(registry, LWBlocks.DEBARKED_LOG_OAK);
		registerItemBlock(registry, LWBlocks.DEBARKED_LOG_ACACIA);
		registerItemBlock(registry, LWBlocks.DEBARKED_LOG_BIRCH);
		registerItemBlock(registry, LWBlocks.DEBARKED_LOG_SPRUCE);
		registerItemBlock(registry, LWBlocks.DEBARKED_LOG_DARKOAK);
		registerItemBlock(registry, LWBlocks.DEBARKED_LOG_JUNGLE);
		registerItemBlock(registry, LWBlocks.BARK_OAK);
		registerItemBlock(registry, LWBlocks.BARK_ACACIA);
		registerItemBlock(registry, LWBlocks.BARK_BIRCH);
		registerItemBlock(registry, LWBlocks.BARK_DARKOAK);
		registerItemBlock(registry, LWBlocks.BARK_JUNGLE);
		registerItemBlock(registry, LWBlocks.BARK_SPRUCE);
		registerItemBlock(registry, LWBlocks.SEALED_BARREL_OAK);
		registerItemBlock(registry, LWBlocks.SEALED_BARREL_SPRUCE);
		registerItemBlock(registry, LWBlocks.SEALED_BARREL_BIRCH);
		registerItemBlock(registry, LWBlocks.SEALED_BARREL_JUNGLE);
		registerItemBlock(registry, LWBlocks.SEALED_BARREL_ACACIA);
		registerItemBlock(registry, LWBlocks.SEALED_BARREL_DARKOAK);
		registerItemBlock(registry, LWBlocks.DRYING_RACK_OAK);
		registerItemBlock(registry, LWBlocks.DRYING_RACK_SPRUCE);
		registerItemBlock(registry, LWBlocks.DRYING_RACK_BIRCH);
		registerItemBlock(registry, LWBlocks.DRYING_RACK_JUNGLE);
		registerItemBlock(registry, LWBlocks.DRYING_RACK_ACACIA);
		registerItemBlock(registry, LWBlocks.DRYING_RACK_DARKOAK);
		if(!ConfigLeatherWorks.disableTrunk){
		registerItemBlock(registry, LWBlocks.LEATHER_TRUNK);
		}
	}

	private static void registerItemBlock(IForgeRegistry<Item> registry, Block block) {
		registry.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
	}
}
