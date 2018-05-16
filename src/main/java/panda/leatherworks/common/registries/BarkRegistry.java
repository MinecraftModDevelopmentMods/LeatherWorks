package panda.leatherworks.common.registries;

import java.util.HashMap;
import java.util.Map;

import akka.japi.Pair;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import panda.leatherworks.init.LWBlocks;
import panda.leatherworks.init.LWItems;

public class BarkRegistry {
	
	private static Map<Pair<Block, Integer>,Pair<ItemStack, Block>> barkTable = new HashMap<>();
	
	public static void initBarks(){
		addBark(Blocks.LOG,0, LWItems.BARK_OAK, LWBlocks.DEBARKED_LOG_OAK);
		addBark(Blocks.LOG,1, LWItems.BARK_SPRUCE, LWBlocks.DEBARKED_LOG_SPRUCE);
		addBark(Blocks.LOG,2, LWItems.BARK_BIRCH, LWBlocks.DEBARKED_LOG_BIRCH);
		addBark(Blocks.LOG,3, LWItems.BARK_JUNGLE, LWBlocks.DEBARKED_LOG_JUNGLE);
		
		addBark(Blocks.LOG2,0, LWItems.BARK_ACACIA, LWBlocks.DEBARKED_LOG_ACACIA);
		addBark(Blocks.LOG2,1, LWItems.BARK_DARKOAK, LWBlocks.DEBARKED_LOG_DARKOAK);
		
	}
	
	public static void addBark(Block input, int i, Item output, Block debarked) {
		addBark(new Pair(input, i),new Pair(new ItemStack(output,1), debarked));
	}

	public static void addBark(Block input, ItemStack output, Block debarked) {
		addBark(new Pair(input, 0), new Pair(output,debarked));
	}

	public static void addBark(Block input, Item output,Block debarked) {
		addBark(new Pair(input, 0), new Pair(new ItemStack(output),debarked));
	}
	
	public static void addBark(Pair input, Pair output) {

		if(!barkTable.containsKey(input)){
			barkTable.put(input,output);
		}
	}
	
	public static boolean hasBark(Pair input){
		return barkTable.containsKey(input);
	}
	
	public static ItemStack getBark(Pair input) {
		Pair<ItemStack, Block> result = barkTable.get(input);
		if (result == null)
			return ItemStack.EMPTY;
		return result.first();
	}
	
	public static Block getDebarkedLog(Pair input) {
		Pair<ItemStack, Block> result = barkTable.get(input);
		if (result == null)
			return Blocks.AIR;
		return result.second();
	}

}
