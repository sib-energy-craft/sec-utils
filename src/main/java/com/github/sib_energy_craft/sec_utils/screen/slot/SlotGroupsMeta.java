package com.github.sib_energy_craft.sec_utils.screen.slot;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

/**
 * @author sibmaks
 * @since 0.0.5
 */
@EqualsAndHashCode
@AllArgsConstructor
public class SlotGroupsMeta {
    private final Map<Integer, SlotGroupMeta> globalSlotGroupMetas;
    private final Map<SlotType, SlotGroupMeta> slotTypeToGlobalIndex;

    /**
     * Get slot group meta in formation by global slot index
     *
     * @param index global index
     * @return slot group meta or null if not found
     */
    @Nullable
    public SlotGroupMeta getByGlobalSlotIndex(int index) {
        return globalSlotGroupMetas.get(index);
    }

    /**
     * Get local slot index by global index
     *
     * @param index global index
     * @return local slot index or null
     */
    @Nullable
    public Integer getLocalIndexByGlobal(int index) {
        var slotGroupMeta = globalSlotGroupMetas.get(index);
        if(slotGroupMeta == null) {
            return null;
        }
        return slotGroupMeta.getLocalIndex(index);
    }

    /**
     * Get global slot index by local index
     *
     * @param index local index
     * @return global slot index or null
     */
    @Nullable
    public Integer getGlobalIndexByLocal(@NotNull SlotType slotType, int index) {
        var slotGroupMeta = slotTypeToGlobalIndex.get(slotType);
        if(slotGroupMeta == null) {
            return null;
        }
        return slotGroupMeta.getGlobalIndex(index);
    }

    /**
     * Get slot group meta by slot type
     *
     * @return global slot index or null
     */
    @Nullable
    public SlotGroupMeta getSlotGroupMeta(@NotNull SlotType slotType) {
        return slotTypeToGlobalIndex.get(slotType);
    }
}
