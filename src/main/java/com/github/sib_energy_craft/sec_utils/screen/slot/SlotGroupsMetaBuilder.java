package com.github.sib_energy_craft.sec_utils.screen.slot;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Builder for {@link SlotGroupsMeta}
 *
 * @author sibmaks
 * @since 0.0.5
 */
public final class SlotGroupsMetaBuilder {
    private final Map<Integer, SlotGroupMeta> globalSlotGroupMetas;
    private final Map<SlotType, Map<Integer, Integer>> slotTypeToGlobalIndex;

    private SlotGroupsMetaBuilder() {
        this.globalSlotGroupMetas = new HashMap<>();
        this.slotTypeToGlobalIndex = new HashMap<>();
    }

    /**
     * Method factory of builder
     *
     * @return builder instance
     */
    public static @NotNull SlotGroupsMetaBuilder builder() {
        return new SlotGroupsMetaBuilder();
    }

    /**
     * Add group into collection
     *
     * @param slotGroupMeta slot group
     * @return self-reference
     */
    public @NotNull SlotGroupsMetaBuilder add(@NotNull SlotGroupMeta slotGroupMeta) {
        var localToGlobals = new HashMap<Integer, Integer>();
        var globalToLocalIndex = slotGroupMeta.getGlobalToLocalIndex();
        for (var entry : globalToLocalIndex.entrySet()) {
            var global = entry.getKey();
            var local = entry.getValue();
            localToGlobals.put(local, global);
            this.globalSlotGroupMetas.put(global, slotGroupMeta);
        }
        this.slotTypeToGlobalIndex.put(slotGroupMeta.getSlotType(), localToGlobals);
        return this;
    }

    /**
     * Build slot groups object based on builder data
     * @return built object
     */
    public @NotNull SlotGroupsMeta build() {
        return new SlotGroupsMeta(
                globalSlotGroupMetas,
                slotTypeToGlobalIndex
        );
    }
}
