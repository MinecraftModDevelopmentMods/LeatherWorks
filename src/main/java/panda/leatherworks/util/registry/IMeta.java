package panda.leatherworks.util.registry;

import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;

public interface IMeta {

	@Nonnull
	public int getMaxMeta();

	@Nonnull
	public List<ModelResourceLocation> getMetaModelLocations(List<ModelResourceLocation> map);

}
