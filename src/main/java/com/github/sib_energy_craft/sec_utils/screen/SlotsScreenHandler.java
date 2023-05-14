package com.github.sib_energy_craft.sec_utils.screen;

import com.github.sib_energy_craft.sec_utils.screen.slot.SlotGroupsMeta;
import com.github.sib_energy_craft.sec_utils.screen.slot.SlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author sibmaks
 * @since 0.0.14
 */
public abstract class SlotsScreenHandler extends ScreenHandler {

    protected SlotsScreenHandler(@Nullable ScreenHandlerType<?> type,
                                 int syncId) {
        super(type, syncId);
    }

    /**
     * Insert item into specific slot type or in otherwise one
     *
     * @param slotStack slot to insert
     * @param to most priority slot type
     * @param otherwise other slot types to insert
     * @return true - slot was insert, false - otherwise
     */
    public boolean insertItem(@NotNull SlotGroupsMeta slotGroupsMeta,
                              @NotNull ItemStack slotStack,
                              @NotNull SlotType to,
                              @NotNull SlotType ... otherwise) {
        if(insertItem(slotGroupsMeta, slotStack, to)) {
            return true;
        }
        for (var slotType : otherwise) {
            if(insertItem(slotGroupsMeta, slotStack, slotType)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Insert item into specific slot type
     *
     * @param slotStack slot to insert
     * @param to slot type
     * @return true - slot was insert, false - otherwise
     */
    public boolean insertItem(@NotNull SlotGroupsMeta slotGroupsMeta,
                              @NotNull ItemStack slotStack,
                              @NotNull SlotType to) {
        var slotGroupMeta = slotGroupsMeta.getSlotGroupMeta(to);
        if(slotGroupMeta == null) {
            return false;
        }
        var globalRange = slotGroupMeta.getGlobalRange();
        return insertItem(slotStack, globalRange.minIndex(), globalRange.maxIndex() + 1, false);
    }
}
