package com.github.sib_energy_craft.sec_utils.utils;

import com.github.sib_energy_craft.sec_utils.common.Identified;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;

/**
 * @author sibmaks
 * Created at 19-12-2022
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class EntityUtils {

    /**
     * Register block entity in {@link Registries#BLOCK_ENTITY_TYPE}
     *
     * @param block identified and registered block
     * @param factory entity factory
     * @return registered block entity
     * @param <B> type of block
     * @param <E> type of entity
     */
    public static<B extends Block,
                  E extends BlockEntity> BlockEntityType<E> register(@NotNull Identified<B> block,
                                                                     @NotNull Factory<B, E> factory) {
        return register(block, (blockPos, blockState) -> factory.create(blockPos, blockState, block.entity()));
    }

    /**
     * Register block entity in {@link Registries#BLOCK_ENTITY_TYPE}
     *
     * @param block identified block
     * @param factory entity factory
     * @return registered block entity
     * @param <B> type of block
     * @param <E> type of entity
     */
    public static<B extends  Block,
                  E extends BlockEntity> BlockEntityType<E> register(@NotNull Identified<B> block,
                                                                     @NotNull FabricBlockEntityTypeBuilder.Factory<E> factory) {
        var identifier = block.identifier();
        var entity = block.entity();

        var energySupplierEntityType = FabricBlockEntityTypeBuilder
                .create(factory, entity)
                .build(null);

        return Registry.register(Registries.BLOCK_ENTITY_TYPE, identifier, energySupplierEntityType);
    }

    public interface Factory<B extends Block, E extends BlockEntity> {
        E create(BlockPos blockPos, BlockState blockState, B block);
    }
}
