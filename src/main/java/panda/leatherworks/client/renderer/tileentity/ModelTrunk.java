package panda.leatherworks.client.renderer.tileentity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelTrunk extends ModelBase {
    public ModelRenderer chestLid = (new ModelRenderer(this, 0, 0)).setTextureSize(64, 64);
    public ModelRenderer chestBelow;

    public ModelTrunk() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.chestLid.setRotationPoint(1.0F, 8.0F, 15.0F);
        this.chestLid.addBox(0.0F, -5.0F, -14.0F, 14, 4, 14, 0.0F);
        this.chestBelow = new ModelRenderer(this, 0, 18);
        this.chestBelow.setRotationPoint(1.0F, 6.0F, 1.0F);
        this.chestBelow.addBox(0.0F, 0.0F, 0.0F, 14, 11, 14, 0.0F);
    }

    public void renderAll()
    {
        this.chestLid.render(0.0625F);
        this.chestBelow.render(0.0625F);
    }
}

