package com.github.sib_energy_craft.sec_utils.utils;

import com.github.sib_energy_craft.sec_utils.common.Identified;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

/**
 * @since 0.0.1
 * @author sibmaks
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BlockUtils {

    /**
     * Create and register block into {@link Registries#BLOCK} registry.
     *
     * @param identifier block identifier
     * @param settings block settings
     * @return registered block
     */
    public static Identified<Block> register(@NotNull Identifier identifier,
                                             @NotNull AbstractBlock.Settings settings) {
        var block = new Block(settings);
        return register(identifier, block);
    }

    /**
     * Register block into {@link Registries#BLOCK} registry.
     *
     * @param identifier block identifier
     * @param block block to register
     * @return registered block
     */
    public static<T extends Block> Identified<T> register(@NotNull Identifier identifier,
                                                          @NotNull T block) {
        block = Registry.register(Registries.BLOCK, identifier, block);
        return new Identified<>(identifier, block);
    }
}
