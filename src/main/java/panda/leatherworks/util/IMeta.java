package panda.leatherworks.util;

import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;

/**
 * Item renderers could be registered explicitly like vanilla Minecraft does.
 * Locking variant models behind this interface would then be unnecessary.
 */
@Deprecated
public interface IMeta {

	@Nonnull
	int getMaxMeta();

	@Nonnull
	List<ModelResourceLocation> getMetaModelLocations(List<ModelResourceLocation> map);

}
