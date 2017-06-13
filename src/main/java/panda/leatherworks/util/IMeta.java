package panda.leatherworks.util;

import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;

@Deprecated
public interface IMeta {

	@Nonnull
	int getMaxMeta();

	@Nonnull
	List<ModelResourceLocation> getMetaModelLocations(List<ModelResourceLocation> map);

}
