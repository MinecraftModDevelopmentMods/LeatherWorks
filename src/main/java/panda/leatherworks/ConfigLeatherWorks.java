package panda.leatherworks;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.OreDictionary;
import panda.leatherworks.init.LWItems;

//Modified from
//https://github.com/Shadows-of-Fire/EnderBags/blob/master/src/main/java/gigabit101/EnderBags/config/ConfigEnderBag.java
public class ConfigLeatherWorks {
	
	protected static final List<Pair<Item, Integer>> BLACKLISTREGISTRY = new ArrayList<>();
	private static String[] blacklist;
	public static boolean disableAllPacks;
	public static boolean disableColoredPacks;
	public static boolean disableEnderPack;
	public static boolean disableTrunk;
	public static int leatherDryingTime;
	public static float leatherFailureChance;
	public static boolean allowBatchProcessing;
	public static boolean disableColoredTrunks;
	public static boolean consumeFlint;
	public static boolean singleBarkItem;
	
	private ConfigLeatherWorks(){LeatherWorks.logger.info("Loading Config");}
	
	public static void load(Configuration config) {
		config.load();
		
		leatherDryingTime = config.getInt("leatherDryingTime", "general", 9600, 1, 2147483647, "Time in ticks to dry leather. 1 second is 20 ticks. Default is 8 mins");
		leatherFailureChance = config.getFloat("leatherFailureChance", "general", 5f, 0f, 100f, "Percent chance that drying leather will fail and return rotten flesh");
		
		disableAllPacks = config.getBoolean("disableAllPacks", "general", false, "Remove all packs from the game");
		disableColoredPacks = config.getBoolean("disableColoredPacks", "general", false, "Remove colored packs from the game");
		disableEnderPack = config.getBoolean("disableEnderPack", "general", false, "Remove Ender packs from the game");
		disableTrunk = config.getBoolean("disableTrunk", "general", false,  "Remove the Leather Trunks from the game");
		disableColoredTrunks = config.getBoolean("disableTrunk", "general", false,  "Remove colored Leather Trunks from the game");
		
		allowBatchProcessing = config.getBoolean("allowBatchProcessing", "general", true, "If disabled, 1 unit of water or tannin will be required to process each hide.");
		consumeFlint = config.getBoolean("consumeFlint", "general", false, "If enabled, Scraping hides will consume flint");
		singleBarkItem = config.getBoolean("singleBarkItem", "general", false, "If enabled, Leatherworks will only use one bark item");
		
		blacklist = config.getStringList("blacklist", "blacklist", new String[] {
			LWItems.PACK_BLACK.getRegistryName().toString() + ":" + OreDictionary.WILDCARD_VALUE,
			LWItems.PACK_BLUE.getRegistryName().toString() + ":" + OreDictionary.WILDCARD_VALUE,
			LWItems.PACK_BROWN.getRegistryName().toString() + ":" + OreDictionary.WILDCARD_VALUE,
			LWItems.PACK_CYAN.getRegistryName().toString() + ":" + OreDictionary.WILDCARD_VALUE,
			LWItems.PACK_GRAY.getRegistryName().toString() + ":" + OreDictionary.WILDCARD_VALUE,
			LWItems.PACK_GREEN.getRegistryName().toString() + ":" + OreDictionary.WILDCARD_VALUE,
			LWItems.PACK_LIGHT_BLUE.getRegistryName().toString() + ":" + OreDictionary.WILDCARD_VALUE,
			LWItems.PACK_LIME.getRegistryName().toString() + ":" + OreDictionary.WILDCARD_VALUE,
			LWItems.PACK_MAGENTA.getRegistryName().toString() + ":" + OreDictionary.WILDCARD_VALUE,
			LWItems.PACK_ORANGE.getRegistryName().toString() + ":" + OreDictionary.WILDCARD_VALUE,
			LWItems.PACK_PINK.getRegistryName().toString() + ":" + OreDictionary.WILDCARD_VALUE,
			LWItems.PACK_PURPLE.getRegistryName().toString() + ":" + OreDictionary.WILDCARD_VALUE,
			LWItems.PACK_RED.getRegistryName().toString() + ":" + OreDictionary.WILDCARD_VALUE,
			LWItems.PACK_SILVER.getRegistryName().toString() + ":" + OreDictionary.WILDCARD_VALUE,
			LWItems.PACK_YELLOW.getRegistryName().toString() + ":" + OreDictionary.WILDCARD_VALUE,
			LWItems.ENDER_PACK.getRegistryName().toString() + ":" + OreDictionary.WILDCARD_VALUE,
			Blocks.ANVIL.getRegistryName().toString() + ":" + OreDictionary.WILDCARD_VALUE,
			Blocks.BLACK_SHULKER_BOX.getRegistryName().toString() + ":" + OreDictionary.WILDCARD_VALUE,
			Blocks.BLUE_SHULKER_BOX.getRegistryName().toString() + ":" + OreDictionary.WILDCARD_VALUE,
			Blocks.BROWN_SHULKER_BOX.getRegistryName().toString() + ":" + OreDictionary.WILDCARD_VALUE,
			Blocks.CYAN_SHULKER_BOX.getRegistryName().toString() + ":" + OreDictionary.WILDCARD_VALUE,
			Blocks.GRAY_SHULKER_BOX.getRegistryName().toString() + ":" + OreDictionary.WILDCARD_VALUE,
			Blocks.GREEN_SHULKER_BOX.getRegistryName().toString() + ":" + OreDictionary.WILDCARD_VALUE,
			Blocks.LIME_SHULKER_BOX.getRegistryName().toString() + ":" + OreDictionary.WILDCARD_VALUE,
			Blocks.MAGENTA_SHULKER_BOX.getRegistryName().toString() + ":" + OreDictionary.WILDCARD_VALUE,
			Blocks.ORANGE_SHULKER_BOX.getRegistryName().toString() + ":" + OreDictionary.WILDCARD_VALUE,
			Blocks.PINK_SHULKER_BOX.getRegistryName().toString() + ":" + OreDictionary.WILDCARD_VALUE,
			Blocks.PURPLE_SHULKER_BOX.getRegistryName().toString() + ":" + OreDictionary.WILDCARD_VALUE,
			Blocks.RED_SHULKER_BOX.getRegistryName().toString() + ":" + OreDictionary.WILDCARD_VALUE,
			Blocks.SILVER_SHULKER_BOX.getRegistryName().toString() + ":" + OreDictionary.WILDCARD_VALUE,
			Blocks.YELLOW_SHULKER_BOX.getRegistryName().toString() + ":" + OreDictionary.WILDCARD_VALUE,
			Blocks.LIGHT_BLUE_SHULKER_BOX.getRegistryName().toString() + ":" + OreDictionary.WILDCARD_VALUE,
			Blocks.WHITE_SHULKER_BOX.getRegistryName().toString() + ":" + OreDictionary.WILDCARD_VALUE
			}, "A list of item registry names that are not allowed in packs.  Format is modid:name:meta, and using a meta of 32767 blocks all metas.");

		if (config.hasChanged()) config.save();

	}

	public static void parseBlacklist() {
		for (String s : blacklist) {
			String[] split = s.split(":");
			if (split.length != 3) {
				LeatherWorks.logger.error("Found invalid blacklist entry: " + s + ", it will be ignored.");
				continue;
			}

			Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(split[0], split[1]));
			BLACKLISTREGISTRY.add(Pair.of(item, Integer.parseInt(split[2])));
		}
	}

	public static boolean isBlacklistedFromBag(ItemStack stack) {
		for (Pair<Item, Integer> p : BLACKLISTREGISTRY) {
			if (p.getLeft() == stack.getItem() && (p.getRight() == OreDictionary.WILDCARD_VALUE || p.getRight() == stack.getMetadata())) return true;
		}
		return false;
	}
}
