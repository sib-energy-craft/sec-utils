package com.github.sib_energy_craft.sec_utils.screen.slot;

import com.google.common.collect.BiMap;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Map;

/**
 * Slot group meta information.<br/>
 * Examples of slot groups:
 * - quick access slot groups (9 slots)
 * - player inventory slot groups (27 slots)
 *
 * @author sibmaks
 * @since 0.0.5
 */
@EqualsAndHashCode
public final class SlotGroupMeta {
    @Getter
    private final @NotNull SlotType slotType;
    @Getter
    private final @NotNull SlotRange globalRange;
    @Getter
    private final @NotNull SlotRange localRange;
    @Getter
    private final @NotNull Map<Integer, Integer> globalToLocalIndex;
    private final @NotNull Map<Integer, Integer> localToGlobalIndex;

    public SlotGroupMeta(@NotNull SlotType slotType,
                         @NotNull SlotRange globalRange,
                         @NotNull SlotRange localRange,
                         @NotNull BiMap<Integer, Integer> globalToLocalIndex) {
        this.slotType = slotType;
        this.globalRange = globalRange;
        this.localRange = localRange;
        this.globalToLocalIndex = Collections.unmodifiableMap(globalToLocalIndex);
        this.localToGlobalIndex = Collections.unmodifiableMap(globalToLocalIndex.inverse());
    }

    /**
     * Get local slot index by global
     *
     * @param globalIndex global index
     * @return local index or null if slot not found
     */
    public Integer getLocalIndex(int globalIndex) {
        return globalToLocalIndex.get(globalIndex);
    }

    /**
     * Get global slot index by local
     *
     * @param localIndex local index
     * @return global index or null if slot not found
     */
    public Integer getGlobalIndex(int localIndex) {
        return localToGlobalIndex.get(localIndex);
    }
}
