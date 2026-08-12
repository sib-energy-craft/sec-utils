package com.github.sib_energy_craft.sec_common.utils;

import com.github.sib_energy_craft.sec_common.common.Identified;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

/**
 * @author sibmaks
 * @since 0.0.1
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class EntityUtils {

    /**
     * Register block entity in {@link BuiltInRegistries#BLOCK_ENTITY_TYPE}
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
        FabricBlockEntityTypeBuilder.Factory<E> blockFactory = (pos, state) -> factory.create(pos, state, block.entity());
        return register(block, blockFactory);
    }

    /**
     * Register block entity in {@link BuiltInRegistries#BLOCK_ENTITY_TYPE}
     *
     * @param block   identified block
     * @param factory entity factory
     * @param <B>     type of block
     * @param <E>     type of entity
     * @return registered block entity
     */
    public static <B extends Block,
            E extends BlockEntity> BlockEntityType<E> register(@NotNull Identified<B> block,
                                                               @NotNull FabricBlockEntityTypeBuilder.Factory<E> factory) {
        var identifier = block.identifier();
        var entity = block.entity();

        var energySupplierEntityType = FabricBlockEntityTypeBuilder
                .create(factory, entity)
                .build();

        return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, identifier, energySupplierEntityType);
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
