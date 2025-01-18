package com.github.sib_energy_craft.sec_utils.utils;

import com.github.sib_energy_craft.sec_utils.common.Identified;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.ExperienceDroppingBlock;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.IntProvider;
import org.jetbrains.annotations.NotNull;

/**
 * @author sibmaks
 * @since 0.0.1
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BlockUtils {

    /**
     * Create and register block into {@link Registries#BLOCK} registry.
     *
     * @param identifier block identifier
     * @param settings   block settings
     * @return registered block
     */
    public static Identified<Block> register(@NotNull Identifier identifier,
                                             @NotNull AbstractBlock.Settings settings) {
        settings.registryKey(keyOf(identifier));
        var block = new Block(settings);
        return register(identifier, block);
    }

    /**
     * Create and register experience dropping block into {@link Registries#BLOCK} registry.
     *
     * @param identifier         block identifier
     * @param experienceProvider experience provider
     * @param settings           block settings
     * @return registered block
     */
    public static Identified<Block> registerExperienceDroppingBlock(@NotNull Identifier identifier,
                                                                    @NotNull IntProvider experienceProvider,
                                                                    @NotNull AbstractBlock.Settings settings) {
        settings.registryKey(keyOf(identifier));
        var block = new ExperienceDroppingBlock(experienceProvider, settings);
        return register(identifier, block);
    }

    /**
     * Register block into {@link Registries#BLOCK} registry.
     *
     * @param identifier block identifier
     * @param block      block to register
     * @param <T>        block type
     * @return registered block
     */
    public static <T extends Block> Identified<T> register(@NotNull Identifier identifier,
                                                           @NotNull T block) {
        block = Registry.register(Registries.BLOCK, identifier, block);
        return new Identified<>(identifier, block);
    }

    /**
     * Get block registry key by identifier
     *
     * @param identifier block identifier
     * @return block registry key
     */
    public static RegistryKey<Block> keyOf(Identifier identifier) {
        return RegistryKey.of(RegistryKeys.BLOCK, identifier);
    }
}
