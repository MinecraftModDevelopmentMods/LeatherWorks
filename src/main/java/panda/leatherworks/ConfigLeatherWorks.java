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
	
	protected static final List<Pair<Item, Integer>> BLACKLIST = new ArrayList<>();
	private static String[] blacklist;
	
	public static void load(Configuration config) {
		config.load();

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
			BLACKLIST.add(Pair.of(item, Integer.parseInt(split[2])));
		}
	}

	public static boolean isBlacklistedFromBag(ItemStack stack) {
		for (Pair<Item, Integer> p : BLACKLIST) {
			if (p.getLeft() == stack.getItem() && (p.getRight() == OreDictionary.WILDCARD_VALUE || p.getRight() == stack.getMetadata())) return true;
		}
		return false;
	}
}
