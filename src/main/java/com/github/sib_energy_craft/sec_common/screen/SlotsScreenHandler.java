package com.github.sib_energy_craft.sec_common.screen;

import com.github.sib_energy_craft.sec_common.screen.slot.SlotGroupsMeta;
import com.github.sib_energy_craft.sec_common.screen.slot.SlotType;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author sibmaks
 * @since 0.0.14
 */
public abstract class SlotsScreenHandler extends AbstractContainerMenu {

    protected SlotsScreenHandler(@Nullable MenuType<?> type,
                                 int syncId) {
        super(type, syncId);
    }

    /**
     * Insert item into a specific slot type or in otherwise one
     *
     * @param slotGroupsMeta slot groups meta data
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
     * @param slotGroupsMeta slot groups meta data
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
        return moveItemStackTo(slotStack, globalRange.minIndex(), globalRange.maxIndex() + 1, false);
    }
}
