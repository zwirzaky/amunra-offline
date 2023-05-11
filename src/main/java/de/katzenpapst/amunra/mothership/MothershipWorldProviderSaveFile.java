package de.katzenpapst.amunra.mothership;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.storage.MapStorage;

public class MothershipWorldProviderSaveFile extends WorldSavedData {

    final static String SAVE_FILE_ID = "mothershipData";

    public NBTTagCompound data = null;

    public MothershipWorldProviderSaveFile(final String p_i2141_1_) {
        super(p_i2141_1_);

        this.data = new NBTTagCompound();
    }

    @Override
    public void readFromNBT(NBTTagCompound p_76184_1_) {
        this.data = p_76184_1_.getCompoundTag("data");
    }

    @Override
    public void writeToNBT(NBTTagCompound p_76187_1_) {
        p_76187_1_.setTag("data", this.data);
    }

    public static MothershipWorldProviderSaveFile getSaveFile(final World world) {
        final MapStorage storage = world.perWorldStorage;
        MothershipWorldProviderSaveFile result = (MothershipWorldProviderSaveFile) storage
                .loadData(MothershipWorldProviderSaveFile.class, SAVE_FILE_ID);
        if (result == null) {
            result = new MothershipWorldProviderSaveFile(SAVE_FILE_ID);
            storage.setData(SAVE_FILE_ID, result);
        }
        return result;
    }

}
