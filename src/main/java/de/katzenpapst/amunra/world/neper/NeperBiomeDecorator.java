package de.katzenpapst.amunra.world.neper;

import java.util.List;

import net.minecraft.init.Blocks;
import net.minecraft.world.gen.feature.WorldGenTallGrass;
import net.minecraft.world.gen.feature.WorldGenerator;
import de.katzenpapst.amunra.AmunRa;
import de.katzenpapst.amunra.block.ARBlocks;
import de.katzenpapst.amunra.world.AmunraBiomeDecorator;
import de.katzenpapst.amunra.world.WorldGenOre;
import micdoodle8.mods.galacticraft.api.prefab.core.BlockMetaPair;

public class NeperBiomeDecorator extends AmunraBiomeDecorator {

    protected WorldGenerator grassGen = new WorldGenTallGrass(Blocks.tallgrass, 1);
    private int grassPerChunk = 10;

    @Override
    protected List<WorldGenOre> getOreGenerators() {
        List<WorldGenOre> list = super.getOreGenerators();
        
        if(AmunRa.config.generateOres) {
            BlockMetaPair stone = new BlockMetaPair(Blocks.stone, (byte) 0);
            
            list.add(new WorldGenOre(new BlockMetaPair(Blocks.diamond_ore, (byte) 0), 4, stone, 8, 0, 12));
            list.add(new WorldGenOre(new BlockMetaPair(Blocks.emerald_ore, (byte) 0), 4, stone, 4, 8, 32));
            list.add(new WorldGenOre(new BlockMetaPair(Blocks.iron_ore, (byte) 0), 8, stone, 16, 2, 70));
            list.add(new WorldGenOre(new BlockMetaPair(Blocks.gold_ore, (byte) 0), 8, stone, 8, 2, 40));

            list.add(new WorldGenOre(ARBlocks.blockOldConcrete, 64, stone, 16, 30, 70));
            list.add(new WorldGenOre(ARBlocks.oreSteelConcrete, 10, ARBlocks.blockOldConcrete, 16, 30, 70));
            list.add(new WorldGenOre(ARBlocks.oreBoneConcrete, 6, ARBlocks.blockOldConcrete, 12, 30, 70));
        }
        return list;
    }

    @Override
    protected void decorate() {
        super.decorate();
        for (int i = 0; i < this.grassPerChunk; ++i) {
            int x = this.chunkX + this.mWorld.rand.nextInt(16) + 8;
            int z = this.chunkZ + this.mWorld.rand.nextInt(16) + 8;
            int y = mWorld.rand.nextInt(this.mWorld.getHeightValue(x, z) * 2);

            grassGen.generate(this.mWorld, this.mWorld.rand, x, y, z);
        }
    }

}
