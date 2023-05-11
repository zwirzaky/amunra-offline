package de.katzenpapst.amunra.mob.render;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import de.katzenpapst.amunra.AmunRa;
import de.katzenpapst.amunra.mob.entity.EntityARVillager;
import de.katzenpapst.amunra.mob.model.ModelARVillager;

public class RenderARVillager extends RenderLiving {

    private static final ResourceLocation VILLAGER_TEXTURE = new ResourceLocation(
            AmunRa.ASSETPREFIX,
            "textures/entity/villager-2.png");

    protected ModelARVillager villagerModel;

    public RenderARVillager() {
        super(new ModelARVillager(0.0F), 0.5F);
        this.villagerModel = (ModelARVillager) this.mainModel;
    }

    protected int shouldVillagerRenderPass(final EntityARVillager par1EntityVillager, final int par2,
            final float par3) {
        return -1;
    }

    public void renderVillager(final EntityARVillager par1EntityVillager, final double par2, final double par4,
            final double par6, final float par8, final float par9) {
        super.doRender(par1EntityVillager, par2, par4, par6, par8, par9);
    }

    protected void renderVillagerEquipedItems(final EntityARVillager par1EntityVillager, final float par2) {
        super.renderEquippedItems(par1EntityVillager, par2);
    }

    protected void preRenderVillager(final EntityARVillager par1EntityVillager, final float par2) {
        float f1 = 0.9375F;

        if (par1EntityVillager.getGrowingAge() < 0) {
            f1 = (float) (f1 * 0.5D);
            this.shadowSize = 0.25F;
        } else {
            this.shadowSize = 0.5F;
        }

        GL11.glScalef(f1, f1, f1);
    }

    @Override
    public void doRender(EntityLiving p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
        this.renderVillager((EntityARVillager) p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }

    @Override
    protected void preRenderCallback(EntityLivingBase p_77041_1_, float p_77041_2_) {
        this.preRenderVillager((EntityARVillager) p_77041_1_, p_77041_2_);
    }

    @Override
    protected int shouldRenderPass(EntityLivingBase p_77032_1_, int p_77032_2_, float p_77032_3_) {
        return this.shouldVillagerRenderPass((EntityARVillager) p_77032_1_, p_77032_2_, p_77032_3_);
    }

    @Override
    protected void renderEquippedItems(EntityLivingBase p_77029_1_, float p_77029_2_) {
        this.renderVillagerEquipedItems((EntityARVillager) p_77029_1_, p_77029_2_);
    }

    @Override
    public void doRender(EntityLivingBase p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
        this.renderVillager((EntityARVillager) p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
        return RenderARVillager.VILLAGER_TEXTURE;
    }

    @Override
    public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
        this.renderVillager((EntityARVillager) p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
}
