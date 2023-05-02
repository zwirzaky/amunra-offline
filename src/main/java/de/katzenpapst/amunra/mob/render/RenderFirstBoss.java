package de.katzenpapst.amunra.mob.render;

import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import de.katzenpapst.amunra.AmunRa;
import de.katzenpapst.amunra.mob.model.ModelFirstBoss;

public class RenderFirstBoss extends RenderBiped {

    private static final ResourceLocation zombieTextures = new ResourceLocation(
            AmunRa.ASSETPREFIX,
            "textures/entity/mummy.png");

    public RenderFirstBoss() {
        super(new ModelFirstBoss(), 1.0F);
    }

    @Override
    protected void preRenderCallback(final EntityLivingBase par1EntityLiving, final float par2) {
        GL11.glScalef(2.5F, 2.5F, 2.5F);
    }

    @Override
    public void doRender(final EntityLiving par1EntityLiving, final double par2, final double par4, final double par6, final float par8, final float par9) {
        BossStatus.setBossStatus((IBossDisplayData) par1EntityLiving, false);

        super.doRender(par1EntityLiving, par2, par4, par6, par8, par9);
    }

    @Override
    protected ResourceLocation getEntityTexture(final EntityLiving e) {
        return zombieTextures;
    }
}
