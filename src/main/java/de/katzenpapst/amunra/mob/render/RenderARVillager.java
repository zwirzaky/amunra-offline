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

    private static final ResourceLocation villagerTexture = new ResourceLocation(
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
    public void doRender(final EntityLiving par1EntityLiving, final double par2, final double par4, final double par6,
            final float par8, final float par9) {
        this.renderVillager((EntityARVillager) par1EntityLiving, par2, par4, par6, par8, par9);
    }

    @Override
    protected void preRenderCallback(final EntityLivingBase par1EntityLivingBase, final float par2) {
        this.preRenderVillager((EntityARVillager) par1EntityLivingBase, par2);
    }

    @Override
    protected int shouldRenderPass(final EntityLivingBase par1EntityLivingBase, final int par2, final float par3) {
        return this.shouldVillagerRenderPass((EntityARVillager) par1EntityLivingBase, par2, par3);
    }

    @Override
    protected void renderEquippedItems(final EntityLivingBase par1EntityLivingBase, final float par2) {
        this.renderVillagerEquipedItems((EntityARVillager) par1EntityLivingBase, par2);
    }

    @Override
    public void doRender(final EntityLivingBase par1EntityLivingBase, final double par2, final double par4,
            final double par6, final float par8, final float par9) {
        this.renderVillager((EntityARVillager) par1EntityLivingBase, par2, par4, par6, par8, par9);
    }

    @Override
    protected ResourceLocation getEntityTexture(final Entity par1Entity) {
        return RenderARVillager.villagerTexture;
    }

    @Override
    public void doRender(final Entity par1Entity, final double par2, final double par4, final double par6,
            final float par8, final float par9) {
        this.renderVillager((EntityARVillager) par1Entity, par2, par4, par6, par8, par9);
    }
}
