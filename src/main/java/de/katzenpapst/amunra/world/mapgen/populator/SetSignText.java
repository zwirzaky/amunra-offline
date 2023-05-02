package de.katzenpapst.amunra.world.mapgen.populator;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.world.World;

public class SetSignText extends AbstractPopulator {

    private final String[] signText = { "", "", "", "" };

    public SetSignText(final int x, final int y, final int z, final String text) {
        this(x, y, z, text.split("\n", 4));
    }

    public SetSignText(final int x, final int y, final int z, final String[] signText) {
        super(x, y, z);

        for (int i = 0; i < signText.length && i < 4; i++) {
            if (signText[i] != null) {
                this.signText[i] = signText[i];
            }
        }

    }

    @Override
    public boolean populate(final World world) {
        final Block curBlock = world.getBlock(x, y, z);
        if (curBlock == Blocks.standing_sign || curBlock == Blocks.wall_sign) {
            final TileEntitySign sign = (TileEntitySign) world.getTileEntity(x, y, z);

            if (sign != null) {
                sign.signText = this.signText;
                sign.markDirty();
                world.markBlockForUpdate(x, y, z);
                return true;
            }
        }
        return false;
    }

}
