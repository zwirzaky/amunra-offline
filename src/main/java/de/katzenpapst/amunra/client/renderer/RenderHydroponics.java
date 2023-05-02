package de.katzenpapst.amunra.client.renderer;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import org.lwjgl.opengl.GL11;

import de.katzenpapst.amunra.client.renderer.model.ModelHydroponics;
import de.katzenpapst.amunra.tile.TileEntityHydroponics;
import micdoodle8.mods.galacticraft.api.transmission.tile.IOxygenReceiver;
import micdoodle8.mods.galacticraft.core.util.OxygenUtil;

public class RenderHydroponics extends TileEntitySpecialRenderer {

    private final ModelHydroponics model;

    public RenderHydroponics() {
        this.model = new ModelHydroponics();
    }

    @Override
    public void renderTileEntityAt(final TileEntity te, final double x, final double y, final double z, final float partialTicks) {
        if (te instanceof TileEntityHydroponics tile) {
            GL11.glPushMatrix();
            GL11.glTranslatef((float) x, (float) y, (float) z);

            final TileEntity[] connections = OxygenUtil.getAdjacentOxygenConnections(tile);

            // meh
            for (int i = 0; i < connections.length; i++) {
                final TileEntity cur = connections[i];
                final ForgeDirection direction = ForgeDirection.values()[i];

                if ((cur instanceof IOxygenReceiver) && (((IOxygenReceiver) cur).getOxygenRequest(direction.getOpposite()) <= 0)) {
                    connections[i] = null;
                }
            }

            final boolean hasNorth = connections[ForgeDirection.NORTH.ordinal()] != null;
            final boolean hasSouth = connections[ForgeDirection.SOUTH.ordinal()] != null;
            final boolean hasWest = connections[ForgeDirection.WEST.ordinal()] != null;
            final boolean hasEast = connections[ForgeDirection.EAST.ordinal()] != null;

            final Tessellator tess = Tessellator.instance;
            this.model.render(tess, tile.getPlantGrowthStatus(), hasNorth, hasSouth, hasWest, hasEast);

            GL11.glPopMatrix();
        }

    }

}
