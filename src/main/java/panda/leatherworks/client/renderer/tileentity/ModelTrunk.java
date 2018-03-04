package panda.leatherworks.client.renderer.tileentity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelTrunk extends ModelBase {
    public ModelRenderer chestLid;
    public ModelRenderer chestBelow;

    public ModelTrunk() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.chestBelow = new ModelRenderer(this, 0, 19);
        this.chestBelow.setRotationPoint(1.0F, 6.0F, 1.0F);
        this.chestBelow.addBox(0.0F, 0.0F, 0.0F, 14, 11, 14, 0.0F);
        this.chestLid = new ModelRenderer(this, 0, 0);
        this.chestLid.setRotationPoint(1.0F, 8.0F, 15.0F);
        this.chestLid.addBox(0.0F, -5.0F, -14.0F, 14, 4, 14, 0.0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.chestBelow.render(f5);
        this.chestLid.render(f5);
    }
    
    public void renderAll()
    {
        this.chestLid.render(0.0625F);
        this.chestBelow.render(0.0625F);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}

