package com.github.sib_energy_craft.sec_common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.world.level.block.Block;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Set of mixin hooks, used in mods
 *
 * @author sibmaks
 * @since 0.0.17
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Hooks {
    /**
     * Hook for AxeItem class initialization
     */
    public static Consumer<Map<Block, Block>> AxeItemClassInit;
}
