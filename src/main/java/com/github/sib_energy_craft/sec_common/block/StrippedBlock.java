package com.github.sib_energy_craft.sec_common.block;

import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

/**
 * Stripped block marker interface.
 *
 * @author sibmaks
 * @since 0.0.17
 */
public interface StrippedBlock {

    /**
     * Get stripped block state
     *
     * @param state base block state
     * @return stripped block state
     */
    @NotNull
    BlockState getStrippedState(@NotNull BlockState state);

}
