package de.katzenpapst.amunra.mothership;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.storage.MapStorage;

public class MothershipWorldProviderSaveFile extends WorldSavedData {

    final static String saveFileId = "mothershipData";

    public NBTTagCompound data = null;

    public MothershipWorldProviderSaveFile(final String p_i2141_1_) {
        super(p_i2141_1_);

        data = new NBTTagCompound();
    }

    @Override
    public void readFromNBT(final NBTTagCompound nbt) {
        data = nbt.getCompoundTag("data");
    }

    @Override
    public void writeToNBT(final NBTTagCompound nbt) {
        nbt.setTag("data", data);
    }

    public static MothershipWorldProviderSaveFile getSaveFile(final World world) {
        final MapStorage storage = world.perWorldStorage;
        MothershipWorldProviderSaveFile result = (MothershipWorldProviderSaveFile) storage
                .loadData(MothershipWorldProviderSaveFile.class, saveFileId);
        if (result == null) {
            result = new MothershipWorldProviderSaveFile(saveFileId);
            storage.setData(saveFileId, result);
        }
        return result;
    }

}
