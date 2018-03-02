package panda.leatherworks.common.eventhandler;

import java.util.List;
import java.util.Random;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityPolarBear;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import panda.leatherworks.init.LWItems;

public class LivingDropsHandler {

	@SubscribeEvent
	public void onLivingDrops(LivingDropsEvent event) {
		Random rand = event.getEntityLiving().world.rand;
		int fortune = event.getLootingLevel();
		
		if (event.getEntityLiving() instanceof EntityCow && !(event.getEntityLiving() instanceof EntityMooshroom)) {
			replaceDrops(event,LWItems.RAWHIDE_COW);
		}else 
		if (event.getEntityLiving() instanceof EntityPig) {
			addLeatherDrops(event,rand.nextInt(2)*modifyDrops(fortune,rand));
			replaceDrops(event,LWItems.RAWHIDE_PIG);
		}else 
		if (event.getEntityLiving() instanceof EntityHorse) {
			replaceDrops(event,LWItems.RAWHIDE_HORSE);
		}else 
			
		if (event.getEntityLiving() instanceof EntityMooshroom) {
			replaceDrops(event,LWItems.RAWHIDE_MOOSHROOM);
		}else 
		if (event.getEntityLiving() instanceof EntityWolf) {
			addLeatherDrops(event,modifyDrops(fortune,rand));
			replaceDrops(event,LWItems.RAWHIDE_WOLF);
		}else 
		if (event.getEntityLiving() instanceof EntityPolarBear) {
			addLeatherDrops(event,rand.nextInt(2)*modifyDrops(fortune,rand));
			replaceDrops(event,LWItems.RAWHIDE_POLARBEAR);
		}
	}
	
	private void replaceDrops(LivingDropsEvent e, Item item){
		List<EntityItem> drops = e.getDrops();
		for(int i = 0; i < drops.size(); i++){
			EntityItem entity = drops.get(i);
			if (entity.getItem().getItem() == Items.LEATHER)
			{
				entity.setItem(new ItemStack(item));
			}
		}
	}
	
	private void addLeatherDrops(LivingDropsEvent e,int m){
		int i;
		for(i = 0; i<e.getDrops().size(); i++){

			if (e.getDrops().get(i).getItem().getItem() == Items.LEATHER)
			{
				return;
			}
		}
		BlockPos pos = e.getEntity().getPosition();
		World world = e.getEntityLiving().world;
		e.getDrops().add(i, new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(Items.LEATHER,m)));
	}
	
	private int modifyDrops(int fortune,Random random){
		if (fortune > 0 )
		{
			int i = random.nextInt(fortune + 2) - 1;

			if (i < 0)
			{
				i = 0;
			}

			return i + 1;
		}
		return 1;
	}
}
