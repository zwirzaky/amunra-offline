package de.katzenpapst.amunra.client.fx;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import micdoodle8.mods.galacticraft.api.vector.Vector3;

public class EntityFXMothershipJetFire extends EntityFX {

    public EntityFXMothershipJetFire(final World world, final Vector3 pos, final Vector3 motion) {
        super(world, pos.x, pos.y, pos.z, motion.x, motion.y, motion.z);

        this.particleScale = 2.0F;
        // setRBGColorF(0x88, 0x00, 0x88);

        // this is needed because the vanilla code adds a y component
        this.motionX = motion.x + (double) ((float) (Math.random() * 2.0D - 1.0D) * 0.4F);
        this.motionY = motion.y + (double) ((float) (Math.random() * 2.0D - 1.0D) * 0.4F);
        this.motionZ = motion.z + (double) ((float) (Math.random() * 2.0D - 1.0D) * 0.4F);
        final float f = (float) (Math.random() + Math.random() + 1.0D) * 0.15F;
        final float f1 = MathHelper.sqrt_double(
                this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ) / 9.0F;
        this.motionX = this.motionX / (double) f1 * (double) f * 0.4000000059604645D;
        this.motionY = this.motionY / (double) f1 * (double) f * 0.4000000059604645D;
        this.motionZ = this.motionZ / (double) f1 * (double) f * 0.4000000059604645D;

        // stealing stuff from GC
        this.particleRed = 255F / 255F;
        this.particleGreen = 120F / 255F + this.rand.nextFloat() / 3;
        this.particleBlue = 55F / 255F;

        this.particleMaxAge = (int) (Math.ceil(this.particleMaxAge) * 2.0F);

        this.noClip = true; // for now
    }

    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        if (this.particleAge++ >= this.particleMaxAge) {
            this.setDead();
            return;
        }

        // after a while, vary my other coordinates slightly

        this.setParticleTextureIndex(7 - this.particleAge * 8 / this.particleMaxAge);
        this.moveEntity(this.motionX, this.motionY, this.motionZ);

        this.particleGreen += 0.01F;

    }

    @Override
    public void renderParticle(final Tessellator par1Tessellator, final float par2, final float par3, final float par4, final float par5, final float par6,
            final float par7) {
        GL11.glDepthMask(false);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        float var8 = (this.particleAge + par2) / this.particleMaxAge * 32.0F;

        if (var8 < 0.0F) {
            var8 = 0.0F;
        }

        if (var8 > 1.0F) {
            var8 = 1.0F;
        }

        // this.particleScale = this.smokeParticleScale * var8;
        super.renderParticle(par1Tessellator, par2, par3, par4, par5, par6, par7);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
    }

    @Override
    public int getBrightnessForRender(final float par1) {
        return 15728880;
    }

    @Override
    public float getBrightness(final float par1) {
        return 1.0F;
    }
}
