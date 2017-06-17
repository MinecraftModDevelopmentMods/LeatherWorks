package panda.leatherworks.init;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.IForgeRegistry;
import panda.leatherworks.common.item.ItemBark;
import panda.leatherworks.common.item.ItemBase;
import panda.leatherworks.common.item.ItemBucketTannin;
import panda.leatherworks.common.item.ItemCraftingLeather;
import panda.leatherworks.common.item.ItemEnderPack;
import panda.leatherworks.common.item.ItemPack;
import panda.leatherworks.common.item.ItemRawhide;
import panda.leatherworks.common.item.armor.ItemBrokenArmor;
import panda.leatherworks.common.item.armor.ItemLeatherworksArmor;


@EventBusSubscriber
public final class LWItems {

	public static final ItemArmor.ArmorMaterial DUMMYLEATHER = EnumHelper
		.addArmorMaterial("leather", "leatherworks:leather", 5, new int[]{0,0,0,0}, 15, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F);

	/*
	 * Declare items here and then subsequently add them to getList(); They will
	 * be automatically moved to registration and model loading.
	 */
	public static final Item RAWHIDE = new ItemRawhide();
	public static final Item BARK = new ItemBark();

	public static final Item TANNIN_BOTTLE = new ItemBase("tannin_bottle");
	public static final Item TANNIN_BALL = new ItemBase("tannin_ball");
	public static final Item REPAIR_KIT = new ItemBase("repair_kit").setMaxStackSize(16);

	public static final Item TANNIN_BUCKET = new ItemBucketTannin();

	public static final Item CRAFTING_LEATHER = new ItemCraftingLeather();
	
	public static final Item PACK = new ItemPack();
	public static final Item ENDER_PACK = new ItemEnderPack();
	public static final Item LEATHER_STRIP = new ItemBase("leather_strip");
	public static final Item LEATHER_SHEET = new ItemBase("leather_sheet");
	
	public static final Item BROKEN_LEATHER_HELMET = new ItemBrokenArmor(EntityEquipmentSlot.HEAD).setRegistryName("broken_leather_helmet");
	public static final Item BROKEN_LEATHER_CHESTPLATE = new ItemBrokenArmor(EntityEquipmentSlot.CHEST).setRegistryName("broken_leather_chestplate");
	public static final Item BROKEN_LEATHER_LEGGINGS = new ItemBrokenArmor(EntityEquipmentSlot.LEGS).setRegistryName("broken_leather_leggings");
	public static final Item BROKEN_LEATHER_BOOTS = new ItemBrokenArmor(EntityEquipmentSlot.FEET).setRegistryName("broken_leather_boots");
	
	
	public static final Item LEATHER_HELMET = new ItemLeatherworksArmor(ItemArmor.ArmorMaterial.LEATHER, EntityEquipmentSlot.HEAD, BROKEN_LEATHER_HELMET).setUnlocalizedName("helmetCloth").setRegistryName("leather_helmet");
	public static final Item LEATHER_CHESTPLATE = new ItemLeatherworksArmor(ItemArmor.ArmorMaterial.LEATHER, EntityEquipmentSlot.CHEST , BROKEN_LEATHER_CHESTPLATE).setUnlocalizedName("chestplateCloth").setRegistryName("leather_chestplate");
	public static final Item LEATHER_LEGGINGS = new ItemLeatherworksArmor(ItemArmor.ArmorMaterial.LEATHER, EntityEquipmentSlot.LEGS, BROKEN_LEATHER_LEGGINGS).setUnlocalizedName("leggingsCloth").setRegistryName("leather_leggings");
	public static final Item LEATHER_BOOTS = new ItemLeatherworksArmor(ItemArmor.ArmorMaterial.LEATHER, EntityEquipmentSlot.FEET, BROKEN_LEATHER_BOOTS).setUnlocalizedName("bootsCloth").setRegistryName("leather_boots");
	
	

	public static List<Item> getList() {
		List<Item> list = new ArrayList<Item>();

		list.add(TANNIN_BOTTLE);
		list.add(TANNIN_BUCKET);
		list.add(RAWHIDE);
		list.add(CRAFTING_LEATHER);
		list.add(BARK);
		list.add(TANNIN_BALL);
		list.add(LEATHER_SHEET);
		list.add(LEATHER_STRIP);
		list.add(PACK);
		list.add(ENDER_PACK);
		list.add(REPAIR_KIT);
		
		list.add(BROKEN_LEATHER_HELMET);
		list.add(BROKEN_LEATHER_CHESTPLATE);
		list.add(BROKEN_LEATHER_LEGGINGS);
		list.add(BROKEN_LEATHER_BOOTS);

		return list;
	}

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Item> event) {
		IForgeRegistry<Item> registry = event.getRegistry();

		registerItemBlock(registry, LWBlocks.DEBARKED_LOG_OAK);
		registerItemBlock(registry, LWBlocks.DEBARKED_LOG_ACACIA);
		registerItemBlock(registry, LWBlocks.DEBARKED_LOG_BIRCH);
		registerItemBlock(registry, LWBlocks.DEBARKED_LOG_SPRUCE);
		registerItemBlock(registry, LWBlocks.DEBARKED_LOG_DARKOAK);
		registerItemBlock(registry, LWBlocks.DEBARKED_LOG_JUNGLE);
		registerItemBlock(registry, LWBlocks.BARREL);
		registerItemBlock(registry, LWBlocks.DRYING_RACK);
		registerItemBlock(registry, LWBlocks.SEALED_BARREL);
	}

	private static void registerItemBlock(IForgeRegistry<Item> registry, Block block) {
		registry.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
	}
}
