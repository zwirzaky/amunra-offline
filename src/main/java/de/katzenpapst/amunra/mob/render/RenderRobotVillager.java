package de.katzenpapst.amunra.mob.render;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import de.katzenpapst.amunra.AmunRa;
import de.katzenpapst.amunra.mob.RobotVillagerProfession;
import de.katzenpapst.amunra.mob.entity.EntityRobotVillager;
import de.katzenpapst.amunra.mob.model.ModelRobotVillager;

public class RenderRobotVillager extends RenderLiving {

    private static final ResourceLocation villagerTexture = new ResourceLocation(
            AmunRa.ASSETPREFIX,
            "textures/entity/robotvillager.png");

    protected ModelRobotVillager villagerModel;

    public RenderRobotVillager() {
        super(new ModelRobotVillager(0.0F), 0.5F);
        this.villagerModel = (ModelRobotVillager) this.mainModel;
    }

    protected int shouldVillagerRenderPass(final EntityRobotVillager par1EntityVillager, final int par2, final float par3) {
        return -1;
    }

    public void renderVillager(final EntityRobotVillager par1EntityVillager, final double par2, final double par4, final double par6,
            final float par8, final float par9) {
        super.doRender(par1EntityVillager, par2, par4, par6, par8, par9);
    }

    protected void renderVillagerEquipedItems(final EntityRobotVillager par1EntityVillager, final float par2) {
        super.renderEquippedItems(par1EntityVillager, par2);

        // try some stuff
        renderFrontPlate(par1EntityVillager);
    }

    private void renderFrontPlate(final EntityRobotVillager par1EntityVillager) {

        final RobotVillagerProfession prof = RobotVillagerProfession.getProfession(par1EntityVillager.getProfession());
        if (prof == null) {

            // something weird happened
            return;
        }
        //
        GL11.glPushMatrix();
        // texturemanager.bindTexture(texturemanager.getResourceLocation(p_78443_2_.getItemSpriteNumber()));

        // this.bindTexture(new ResourceLocation(AmunRa.instance.ASSETPREFIX,
        // "textures/entity/icons/refinery_front.png"));
        this.bindTexture(prof.getIcon());
        TextureUtil.func_152777_a(false, false, 1.0F);
        final Tessellator tessellator = Tessellator.instance;

        final float scale = 0.45F;

        // float f4 = -0.25F;

        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glTranslatef(-scale / 2, 0.10F, -0.20F);

        GL11.glScalef(scale, scale, scale);

        //////
        // p_78439_0_ is a tesselator
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, 1.0F);

        tessellator.addVertexWithUV(0.0D, 0.0D, 0.0D, 0, 0);
        tessellator.addVertexWithUV(1.0D, 0.0D, 0.0D, 1, 0);
        tessellator.addVertexWithUV(1.0D, 1.0D, 0.0D, 1, 1);
        tessellator.addVertexWithUV(0.0D, 1.0D, 0.0D, 0, 1);
        tessellator.draw();
        /// and stuff
        GL11.glPopMatrix();
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        TextureUtil.func_147945_b();
    }

    protected void preRenderVillager(final EntityRobotVillager par1EntityVillager, final float par2) {
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
    public void doRender(final EntityLiving par1EntityLiving, final double par2, final double par4, final double par6, final float par8, final float par9) {
        this.renderVillager((EntityRobotVillager) par1EntityLiving, par2, par4, par6, par8, par9);
    }

    @Override
    protected void preRenderCallback(final EntityLivingBase par1EntityLivingBase, final float par2) {
        this.preRenderVillager((EntityRobotVillager) par1EntityLivingBase, par2);
    }

    @Override
    protected int shouldRenderPass(final EntityLivingBase par1EntityLivingBase, final int par2, final float par3) {
        return this.shouldVillagerRenderPass((EntityRobotVillager) par1EntityLivingBase, par2, par3);
    }

    @Override
    protected void renderEquippedItems(final EntityLivingBase par1EntityLivingBase, final float par2) {
        this.renderVillagerEquipedItems((EntityRobotVillager) par1EntityLivingBase, par2);
    }

    @Override
    public void doRender(final EntityLivingBase par1EntityLivingBase, final double par2, final double par4, final double par6, final float par8,
            final float par9) {
        this.renderVillager((EntityRobotVillager) par1EntityLivingBase, par2, par4, par6, par8, par9);
    }

    @Override
    protected ResourceLocation getEntityTexture(final Entity par1Entity) {
        return RenderRobotVillager.villagerTexture;
    }

    @Override
    public void doRender(final Entity par1Entity, final double par2, final double par4, final double par6, final float par8, final float par9) {
        this.renderVillager((EntityRobotVillager) par1Entity, par2, par4, par6, par8, par9);
    }
}
