package com.github.sib_energy_craft.sec_utils.screen.slot;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import org.jetbrains.annotations.NotNull;

/**
 * Builder for {@link SlotGroupMeta}
 *
 * @author sibmaks
 * @since 0.0.5
 */
public final class SlotGroupMetaBuilder {
    private final SlotType slotType;
    private int minGlobalIndex = Integer.MAX_VALUE;
    private int maxGlobalIndex = Integer.MIN_VALUE;
    private int minLocalIndex = Integer.MAX_VALUE;
    private int maxLocalIndex = Integer.MIN_VALUE;
    private final BiMap<Integer, Integer> globalToLocalIndex;

    private SlotGroupMetaBuilder(@NotNull SlotType slotType) {
        this.slotType = slotType;
        this.globalToLocalIndex = HashBiMap.create();
    }

    /**
     * Method factory of builder
     *
     * @param slotType slot type
     * @return builder instance
     */
    public static @NotNull SlotGroupMetaBuilder builder(@NotNull SlotType slotType) {
        return new SlotGroupMetaBuilder(slotType);
    }

    /**
     * Add slot into group
     * @param globalIndex global slot index
     * @param localIndex local slot index
     * @return self-reference
     */
    public @NotNull SlotGroupMetaBuilder addSlot(int globalIndex, int localIndex) {
        globalToLocalIndex.put(globalIndex, localIndex);
        this.minGlobalIndex = Math.min(this.minGlobalIndex, globalIndex);
        this.maxGlobalIndex = Math.max(this.maxGlobalIndex, globalIndex);
        this.minLocalIndex = Math.min(this.minLocalIndex, localIndex);
        this.maxLocalIndex = Math.max(this.maxLocalIndex, localIndex);
        return this;
    }

    /**
     * Build slot group meta object based on builder data
     * @return built object
     */
    public @NotNull SlotGroupMeta build() {
        return new SlotGroupMeta(
                slotType,
                new SlotRange(minGlobalIndex, maxGlobalIndex),
                new SlotRange(minLocalIndex, maxLocalIndex),
                globalToLocalIndex
        );
    }
}
