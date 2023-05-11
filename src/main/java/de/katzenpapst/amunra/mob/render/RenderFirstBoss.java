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

    private static final ResourceLocation TEXTURE = new ResourceLocation(
            AmunRa.ASSETPREFIX,
            "textures/entity/mummy.png");

    public RenderFirstBoss() {
        super(new ModelFirstBoss(), 1.0F);
    }

    @Override
    protected void preRenderCallback(EntityLivingBase p_77041_1_, float p_77041_2_) {
        GL11.glScalef(2.5F, 2.5F, 2.5F);
    }

    @Override
    public void doRender(EntityLiving p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
        BossStatus.setBossStatus((IBossDisplayData) p_76986_1_, false);

        super.doRender(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityLiving p_110775_1_) {
        return TEXTURE;
    }
}
