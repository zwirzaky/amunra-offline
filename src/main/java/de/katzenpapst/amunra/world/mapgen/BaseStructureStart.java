package de.katzenpapst.amunra.world.mapgen;

import java.util.HashMap;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;

import de.katzenpapst.amunra.AmunRa;
import de.katzenpapst.amunra.helper.CoordHelper;
import de.katzenpapst.amunra.world.mapgen.populator.AbstractPopulator;
import de.katzenpapst.amunra.world.mapgen.populator.SpawnEntity;
import micdoodle8.mods.galacticraft.api.vector.BlockVec3;

abstract public class BaseStructureStart extends BaseStructureComponent {

    protected PopulatorByChunkMap populatorsByChunk;

    public class PopulatorMap extends HashMap<BlockVec3, AbstractPopulator> {

        private static final long serialVersionUID = -1581029941656595874L;
    }

    public class PopulatorByChunkMap extends HashMap<Long, PopulatorMap> {

        private static final long serialVersionUID = 2084699646332356938L;
    }

    protected int chunkX;
    protected int chunkZ;

    protected Random rand;

    protected World worldObj;

    // coords relative to the
    protected int startX;
    // protected int startY;
    protected int startZ;

    public BaseStructureStart(World world, int chunkX, int chunkZ, Random rand) {

        this.chunkX = chunkX;
        this.chunkZ = chunkZ;

        this.worldObj = world;

        this.rand = rand;

        this.startX = this.rand.nextInt(16);
        this.startZ = this.rand.nextInt(16);

        // int startBlockX = chunkX*16 + this.startX;
        // int startBlockZ = chunkZ*16 + this.startZ;

        populatorsByChunk = new PopulatorByChunkMap();
    }

    protected void preparePopulatorListForChunk(int chunkX, int chunkZ) {
        Long key = Long.valueOf(ChunkCoordIntPair.chunkXZ2Int(chunkX, chunkZ));

        if (populatorsByChunk.containsKey(key)) {
            // this is bad, this shouldn't happen
            AmunRa.LOGGER.error(
                    "Tried to prepare populator list for chunk {}/{}. This could mean that the chunk is being generated twice.",
                    chunkX,
                    chunkZ);
            return;
        }

        populatorsByChunk.put(key, new PopulatorMap());
    }

    public World getWorld() {
        return worldObj;
    }

    /**
     * This should be overridden, but then called before anything else happens
     */
    @Override
    public boolean generateChunk(int chunkX, int chunkZ, Block[] arrayOfIDs, byte[] arrayOfMeta) {
        preparePopulatorListForChunk(chunkX, chunkZ);

        return true;
    }

    public void populateChunk(World world, int chunkX, int chunkZ) {

        Long chunkKey = Long.valueOf(ChunkCoordIntPair.chunkXZ2Int(chunkX, chunkZ));
        if (!populatorsByChunk.containsKey(chunkKey)) {
            AmunRa.LOGGER.warn("No populator list for chunk {}/{}", chunkX, chunkZ);
            return;
        }
        PopulatorMap curMap = populatorsByChunk.get(chunkKey);
        populatorsByChunk.remove(chunkKey);// remove it already, at this point, it's too late

        for (AbstractPopulator p : curMap.values()) {
            if (!p.populate(world)) {
                AmunRa.LOGGER.error("Populator {} failed...", p.getClass().getCanonicalName());
            }
        }

        curMap.clear();// I hope that's enough of a hint to make java delete this stuff

    }

    public void addPopulator(AbstractPopulator p) {
        // ok I can't do that
        // this.worldObj.getChunkFromBlockCoords(p_72938_1_, p_72938_2_)
        int chunkX = CoordHelper.blockToChunk(p.getX());// p.getX() >> 4;
        int chunkZ = CoordHelper.blockToChunk(p.getZ());

        // p_72938_1_ >> 4, p_72938_2_ >>
        // 16

        Long chunkKey = Long.valueOf(ChunkCoordIntPair.chunkXZ2Int(chunkX, chunkZ));
        if (!populatorsByChunk.containsKey(chunkKey)) {
            AmunRa.LOGGER.error(
                    "Cannot add populator for {}/{}, offender: {}. Probably it's the wrong chunk",
                    chunkX,
                    chunkZ,
                    p.getClass().getCanonicalName());
            return;
        }
        PopulatorMap curMap = populatorsByChunk.get(chunkKey);

        BlockVec3 key = p.getBlockVec3();
        if (curMap.containsKey(key)) {
            AmunRa.LOGGER.error("Cannot add populator for {}, offender: {}", key, p.getClass().getCanonicalName());
            return;
        }
        // pack the coords
        curMap.put(key, p);
    }

    public void spawnLater(Entity ent, int x, int y, int z) {
        SpawnEntity p = new SpawnEntity(x, y, z, ent);
        addPopulator(p);
    }

    public int getWorldGroundLevel() {
        // ((ChunkProviderSpace)worldObj.getChunkProvider()).g
        // NO IDEA
        return worldObj.provider.getAverageGroundLevel();
    }

}
