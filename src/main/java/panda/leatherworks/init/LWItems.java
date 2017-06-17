package panda.leatherworks.init;

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
import panda.leatherworks.LeatherWorks;
import panda.leatherworks.common.item.ItemBark;
import panda.leatherworks.common.item.ItemBucketTannin;
import panda.leatherworks.common.item.ItemCraftingLeather;
import panda.leatherworks.common.item.ItemEnderPack;
import panda.leatherworks.common.item.ItemPack;
import panda.leatherworks.common.item.ItemRawhide;
import panda.leatherworks.common.item.armor.ItemBrokenArmor;
import panda.leatherworks.common.item.armor.ItemLeatherworksArmor;


@EventBusSubscriber
public final class LWItems {

	public static final ItemArmor.ArmorMaterial DUMMYLEATHER = EnumHelper.addArmorMaterial("leather", "leatherworks:leather", 5, new int[]{0,0,0,0}, 15, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F);

	public static final Item RAWHIDE = simply(new ItemRawhide(), "rawhide").setCreativeTab(LeatherWorks.LeatherTab);
	public static final Item BARK = simply(new ItemBark(), "bark").setCreativeTab(LeatherWorks.LeatherTab);

	public static final Item TANNIN_BOTTLE = simply(new Item(), "tannin_bottle").setCreativeTab(LeatherWorks.LeatherTab);
	public static final Item TANNIN_BALL = simply(new Item(), "tannin_ball").setCreativeTab(LeatherWorks.LeatherTab);
	public static final Item REPAIR_KIT = simply(new Item(), "repair_kit").setMaxStackSize(16).setCreativeTab(LeatherWorks.LeatherTab);

	public static final Item TANNIN_BUCKET = simply(new ItemBucketTannin(), "tannin_bucket").setCreativeTab(LeatherWorks.LeatherTab);

	public static final Item CRAFTING_LEATHER = simply(new ItemCraftingLeather(), "crafting_leather").setCreativeTab(LeatherWorks.LeatherTab);
	
	public static final Item PACK = simply(new ItemPack(), "pack").setCreativeTab(LeatherWorks.LeatherTab);
	public static final Item ENDER_PACK = simply(new ItemEnderPack(), "ender_pack").setCreativeTab(LeatherWorks.LeatherTab);
	public static final Item LEATHER_STRIP = simply(new Item(), "leather_strip").setCreativeTab(LeatherWorks.LeatherTab);
	public static final Item LEATHER_SHEET = simply(new Item(), "leather_sheet").setCreativeTab(LeatherWorks.LeatherTab);
	
	public static final Item BROKEN_LEATHER_HELMET = simply(new ItemBrokenArmor(EntityEquipmentSlot.HEAD), "broken_leather_helmet").setCreativeTab(LeatherWorks.LeatherTab);
	public static final Item BROKEN_LEATHER_CHESTPLATE = simply(new ItemBrokenArmor(EntityEquipmentSlot.CHEST), "broken_leather_chestplate").setCreativeTab(LeatherWorks.LeatherTab);
	public static final Item BROKEN_LEATHER_LEGGINGS = simply(new ItemBrokenArmor(EntityEquipmentSlot.LEGS), "broken_leather_leggings").setCreativeTab(LeatherWorks.LeatherTab);
	public static final Item BROKEN_LEATHER_BOOTS = simply(new ItemBrokenArmor(EntityEquipmentSlot.FEET), "broken_leather_boots").setCreativeTab(LeatherWorks.LeatherTab);
	
	
	public static final Item LEATHER_HELMET = new ItemLeatherworksArmor(ItemArmor.ArmorMaterial.LEATHER, EntityEquipmentSlot.HEAD, BROKEN_LEATHER_HELMET).setUnlocalizedName("helmetCloth").setRegistryName("leather_helmet");
	public static final Item LEATHER_CHESTPLATE = new ItemLeatherworksArmor(ItemArmor.ArmorMaterial.LEATHER, EntityEquipmentSlot.CHEST , BROKEN_LEATHER_CHESTPLATE).setUnlocalizedName("chestplateCloth").setRegistryName("leather_chestplate");
	public static final Item LEATHER_LEGGINGS = new ItemLeatherworksArmor(ItemArmor.ArmorMaterial.LEATHER, EntityEquipmentSlot.LEGS, BROKEN_LEATHER_LEGGINGS).setUnlocalizedName("leggingsCloth").setRegistryName("leather_leggings");
	public static final Item LEATHER_BOOTS = new ItemLeatherworksArmor(ItemArmor.ArmorMaterial.LEATHER, EntityEquipmentSlot.FEET, BROKEN_LEATHER_BOOTS).setUnlocalizedName("bootsCloth").setRegistryName("leather_boots");

	private static Item simply(Item item, String name) {
		return item.setRegistryName(LeatherWorks.MODID, name).setUnlocalizedName(LeatherWorks.MODID + "." + name);
	}

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Item> event) {
		IForgeRegistry<Item> registry = event.getRegistry();

		registry.register(RAWHIDE);
		registry.register(BARK);
		registry.register(TANNIN_BOTTLE);
		registry.register(TANNIN_BALL);
		registry.register(REPAIR_KIT);
		registry.register(TANNIN_BUCKET);
		registry.register(CRAFTING_LEATHER);
		registry.register(PACK);
		registry.register(ENDER_PACK);
		registry.register(LEATHER_STRIP);
		registry.register(LEATHER_SHEET);
		registry.register(BROKEN_LEATHER_HELMET);
		registry.register(BROKEN_LEATHER_CHESTPLATE);
		registry.register(BROKEN_LEATHER_LEGGINGS);
		registry.register(BROKEN_LEATHER_BOOTS);

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
