package com.github.sib_energy_craft.sec_utils.utils;

import com.github.sib_energy_craft.sec_utils.common.Identified;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.BlockEntityType.BlockEntityFactory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;

/**
 * @author sibmaks
 * @since 0.0.1
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class EntityUtils {

    /**
     * Register block entity in {@link Registries#BLOCK_ENTITY_TYPE}
     *
     * @param block   identified and registered block
     * @param factory entity factory
     * @param <B>     type of block
     * @param <E>     type of entity
     * @return registered block entity
     */
    public static <B extends Block,
            E extends BlockEntity> BlockEntityType<E> register(@NotNull Identified<B> block,
                                                               @NotNull Factory<B, E> factory) {
        BlockEntityFactory<E> blockFactory = (pos, state) -> factory.create(pos, state, block.entity());
        return register(block, blockFactory);
    }

    /**
     * Register block entity in {@link Registries#BLOCK_ENTITY_TYPE}
     *
     * @param block   identified block
     * @param factory entity factory
     * @param <B>     type of block
     * @param <E>     type of entity
     * @return registered block entity
     */
    public static <B extends Block,
            E extends BlockEntity> BlockEntityType<E> register(@NotNull Identified<B> block,
                                                               @NotNull BlockEntityType.BlockEntityFactory<E> factory) {
        var identifier = block.identifier();
        var entity = block.entity();

        var energySupplierEntityType = BlockEntityType.Builder
                .create(factory, entity)
                .build(null);

        return Registry.register(Registries.BLOCK_ENTITY_TYPE, identifier, energySupplierEntityType);
    }

    /**
     * Block entity factory
     *
     * @param <B> block type
     * @param <E> block an entity type
     */
    public interface Factory<B extends Block, E extends BlockEntity> {
        /**
         * Factory method for block entity creation
         *
         * @param blockPos   block position
         * @param blockState block state
         * @param block      block
         * @return instance of block entity
         */
        E create(BlockPos blockPos, BlockState blockState, B block);
    }
}
