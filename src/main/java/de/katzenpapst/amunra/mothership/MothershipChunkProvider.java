package de.katzenpapst.amunra.mothership;

import java.util.Arrays;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.init.Blocks;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderGenerate;

import micdoodle8.mods.galacticraft.core.world.gen.BiomeGenBaseOrbit;

public class MothershipChunkProvider extends ChunkProviderGenerate { // for now, just like this

    protected final Random rand;
    // ...sigh...
    protected final World worldObjNonPrivate;

    public MothershipChunkProvider(final World par1World, final long par2, final boolean par4) {
        super(par1World, par2, par4);
        this.rand = new Random(par2);
        worldObjNonPrivate = par1World;
    }

    @Override
    public boolean unloadQueuedChunks() {
        return false;
    }

    @Override
    public int getLoadedChunkCount() {
        return 0;
    }

    @Override
    public boolean saveChunks(final boolean var1, final IProgressUpdate var2) {
        return true;
    }

    @Override
    public boolean canSave() {
        return true;
    }

    @Override
    public Chunk provideChunk(final int chunkX, final int chunkZ) {
        this.rand.setSeed(chunkX * 341873128712L + chunkZ * 132897987541L);
        final Block[] ids = new Block[32768];
        Arrays.fill(ids, Blocks.air);
        final byte[] meta = new byte[32768];

        final Chunk chunk = new Chunk(this.worldObjNonPrivate, ids, meta, chunkX, chunkZ);

        final byte[] biomesArray = chunk.getBiomeArray();
        for (int i = 0; i < biomesArray.length; ++i) {
            biomesArray[i] = (byte) BiomeGenBaseOrbit.space.biomeID;
        }

        chunk.generateSkylightMap();
        return chunk;
    }

    @Override
    public boolean chunkExists(final int par1, final int par2) {
        return true;
    }

    @Override
    public void populate(final IChunkProvider par1IChunkProvider, final int chunkX, final int chunkZ) {

        BlockFalling.fallInstantly = true;
        final int blockX = chunkX * 16;
        final int blockZ = chunkZ * 16;
        this.rand.setSeed(this.worldObjNonPrivate.getSeed());
        final long seed1 = this.rand.nextLong() / 2L * 2L + 1L;
        final long seed2 = this.rand.nextLong() / 2L * 2L + 1L;
        this.rand.setSeed(chunkX * seed1 + chunkZ * seed2 ^ this.worldObjNonPrivate.getSeed());
        if (blockX == 0 && blockZ == 0) {
            // this generates the basis structure

            new MothershipWorldGen().generate(this.worldObjNonPrivate, this.rand, 0, 64, 0);
        }
        BlockFalling.fallInstantly = false;
    }
}
