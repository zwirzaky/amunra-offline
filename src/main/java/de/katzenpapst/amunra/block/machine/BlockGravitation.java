package de.katzenpapst.amunra.block.machine;

import java.util.Random;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.katzenpapst.amunra.AmunRa;
import de.katzenpapst.amunra.GuiIds;
import de.katzenpapst.amunra.block.BlockMachineMeta;
import de.katzenpapst.amunra.block.SubBlockMachine;
import de.katzenpapst.amunra.helper.CoordHelper;
import de.katzenpapst.amunra.proxy.ARSidedProxy.ParticleType;
import de.katzenpapst.amunra.tile.TileEntityGravitation;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;

public class BlockGravitation extends SubBlockMachine {

    private final String backTexture;
    private final String sideTexture;
    private final String activeTexture;

    private IIcon backIcon = null;
    private IIcon sideIcon = null;
    private IIcon activeIcon = null;

    public BlockGravitation(final String name, final String frontInactiveTexture, final String activeTexture, final String sideTexture,
            final String backTexture) {
        super(name, frontInactiveTexture);

        this.backTexture = backTexture;
        this.sideTexture = sideTexture;
        this.activeTexture = activeTexture;
    }

    @Override
    public void registerBlockIcons(final IIconRegister par1IconRegister) {
        super.registerBlockIcons(par1IconRegister);
        this.backIcon = par1IconRegister.registerIcon(this.backTexture);
        this.sideIcon = par1IconRegister.registerIcon(this.sideTexture);
        this.activeIcon = par1IconRegister.registerIcon(this.activeTexture);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int side, final int meta) {
        final int realMeta = ((BlockMachineMeta) this.parent).getRotationMeta(meta);

        final ForgeDirection front = CoordHelper.rotateForgeDirection(ForgeDirection.SOUTH, realMeta);
        final ForgeDirection back = CoordHelper.rotateForgeDirection(ForgeDirection.NORTH, realMeta);

        if (side == front.ordinal()) {
            return this.blockIcon;
        }
        if (side == back.ordinal()) {
            return this.backIcon;
        }
        return this.sideIcon;
    }

    @Override
    public TileEntity createTileEntity(final World world, final int metadata) {
        return new TileEntityGravitation();
    }

    @Override
    public boolean hasTileEntity(final int metadata) {
        return true;
    }

    @Override
    public boolean onMachineActivated(final World world, final int x, final int y, final int z, final EntityPlayer entityPlayer, final int side, final float hitX,
            final float hitY, final float hitZ) {
        entityPlayer.openGui(AmunRa.instance, GuiIds.GUI_GRAVITY, world, x, y, z);
        return true;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(final World par1World, final int x, final int y, final int z, final Random rand) {
        final boolean test = true;
        if (par1World.getTileEntity(x, y, z) instanceof TileEntityGravitation) {
            final TileEntityGravitation tile = (TileEntityGravitation) par1World.getTileEntity(x, y, z);
            if (tile.isRunning()) {
                for (int particleCount = 0; particleCount < 10; particleCount++) {
                    double x2 = x + rand.nextFloat();
                    final double y2 = y + rand.nextFloat();
                    double z2 = z + rand.nextFloat();
                    double mX = 0.0D;
                    double mY = 0.0D;
                    double mZ = 0.0D;
                    final int dir = rand.nextInt(2) * 2 - 1;
                    mX = 0;// (rand.nextFloat() - 0.5D) * 0.5D;
                    mY = (rand.nextFloat() - 0.5D) * 0.5D;
                    mZ = 0;// (rand.nextFloat() - 0.5D) * 0.5D;

                    final int var2 = par1World.getBlockMetadata(x, y, z);

                    if (var2 == 3 || var2 == 2) {
                        x2 = x + 0.5D + 0.25D * dir;
                        mX = rand.nextFloat() * 2.0F * dir;
                    } else {
                        z2 = z + 0.5D + 0.25D * dir;
                        mZ = rand.nextFloat() * 2.0F * dir;
                    }

                    if (test) {
                        AmunRa.proxy.spawnParticles(
                                ParticleType.PT_GRAVITY_DUST,
                                par1World,
                                new Vector3(x + 0.5, y + 0.5, z + 0.5),
                                new Vector3(mX, tile.getGravityForce(), mZ));
                    } else {
                        GalacticraftCore.proxy.spawnParticle(
                                "oxygen",
                                new Vector3(x2, y2, z2),
                                new Vector3(mX, mY, mZ),
                                new Object[] { new Vector3(0.7D, 0.7D, 1.0D) });
                    }
                }
            }
        }
    }
}
