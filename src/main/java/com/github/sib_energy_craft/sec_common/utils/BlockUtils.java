package com.github.sib_energy_craft.sec_common.utils;

import com.github.sib_energy_craft.sec_common.common.Identified;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.jetbrains.annotations.NotNull;

/**
 * @author sibmaks
 * @since 0.0.1
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BlockUtils {

    /**
     * Create and register block into {@link BuiltInRegistries#BLOCK} registry.
     *
     * @param identifier block identifier
     * @param settings   block settings
     * @return registered block
     */
    public static Identified<Block> register(@NotNull Identifier identifier,
                                             @NotNull BlockBehaviour.Properties settings) {
        settings.setId(keyOf(identifier));
        var block = new Block(settings);
        return register(identifier, block);
    }

    /**
     * Create and register experience dropping block into {@link BuiltInRegistries#BLOCK} registry.
     *
     * @param identifier         block identifier
     * @param experienceProvider experience provider
     * @param settings           block settings
     * @return registered block
     */
    public static Identified<Block> registerExperienceDroppingBlock(@NotNull Identifier identifier,
                                                                    @NotNull IntProvider experienceProvider,
                                                                    @NotNull BlockBehaviour.Properties settings) {
        settings.setId(keyOf(identifier));
        var block = new DropExperienceBlock(experienceProvider, settings);
        return register(identifier, block);
    }

    /**
     * Register block into {@link BuiltInRegistries#BLOCK} registry.
     *
     * @param identifier block identifier
     * @param block      block to register
     * @param <T>        block type
     * @return registered block
     */
    public static <T extends Block> Identified<T> register(@NotNull Identifier identifier,
                                                           @NotNull T block) {
        block = Registry.register(BuiltInRegistries.BLOCK, identifier, block);
        return new Identified<>(identifier, block);
    }

    /**
     * Get block registry key by identifier
     *
     * @param identifier block identifier
     * @return block registry key
     */
    public static ResourceKey<Block> keyOf(Identifier identifier) {
        return ResourceKey.create(Registries.BLOCK, identifier);
    }
}
