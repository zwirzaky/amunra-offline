package de.katzenpapst.amunra.world.mapgen.volcano;

import java.util.Random;

import net.minecraft.util.MathHelper;

import de.katzenpapst.amunra.world.mapgen.BaseStructureStart;
import de.katzenpapst.amunra.world.mapgen.StructureGenerator;
import micdoodle8.mods.galacticraft.api.prefab.core.BlockMetaPair;

public class VolcanoGenerator extends StructureGenerator {

    protected final BlockMetaPair fluid;
    protected final BlockMetaPair mountainMaterial;
    protected final BlockMetaPair shaftMaterial;
    protected final int maxDepth;
    protected final boolean createMagmaChamber;

    public VolcanoGenerator(final BlockMetaPair fluid, final BlockMetaPair mountainMaterial,
            final BlockMetaPair shaftMaterial, final int maxDepth, final boolean magmaChamber) {
        this.fluid = fluid;
        this.mountainMaterial = mountainMaterial;
        this.shaftMaterial = shaftMaterial;
        this.maxDepth = maxDepth;
        this.createMagmaChamber = magmaChamber;
    }

    @Override
    protected long getSalt() {
        return 84375932847598L;
    }

    @Override
    protected boolean canGenerateHere(final int chunkX, final int chunkZ, final Random rand) {
        final int rangeShift = 4;
        final int range = 1 << rangeShift;
        final int superchunkX = chunkX >> rangeShift;
        final int superchunkZ = chunkZ >> rangeShift;

        final int chunkStartX = superchunkX << rangeShift;
        final int chunkStartZ = superchunkZ << rangeShift;
        final int chunkEndX = chunkStartX + range - 1;
        final int chunkEndZ = chunkStartZ + range - 1;
        // this square of chunk coords superchunkX,superchunkX+range-1 and superchunkZ,superchunkZ+range-1
        // now could contain a village
        this.rand.setSeed(this.worldObj.getSeed() ^ this.getSalt() ^ superchunkX ^ superchunkZ);

        final int actualVillageX = MathHelper.getRandomIntegerInRange(this.rand, chunkStartX, chunkEndX);
        final int actualVillageZ = MathHelper.getRandomIntegerInRange(this.rand, chunkStartZ, chunkEndZ);

        return chunkX == actualVillageX && chunkZ == actualVillageZ;
    }

    @Override
    protected BaseStructureStart createNewStructure(final int xChunkCoord, final int zChunkCoord) {
        final Random rand = new Random(this.worldObj.getSeed() ^ xChunkCoord ^ zChunkCoord ^ this.getSalt());
        final Volcano v = new Volcano(this.worldObj, xChunkCoord, zChunkCoord, rand);
        v.setFluid(this.fluid);
        v.setMaxDepth(this.maxDepth);
        v.setMountainMaterial(this.mountainMaterial);
        v.setShaftMaterial(this.shaftMaterial);
        v.setHasMagmaChamber(this.createMagmaChamber);
        return v;
    }

    @Override
    public String getName() {
        return "Volcano";
    }

}
