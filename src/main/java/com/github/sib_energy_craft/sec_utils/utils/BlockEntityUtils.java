package com.github.sib_energy_craft.sec_utils.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * @since 0.0.1
 * @author sibmaks
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BlockEntityUtils {

    /**
     * Check is player close enough to interact with block entity
     *
     * @param blockEntity block entity
     * @param player player
     * @return true - player can interact, false - otherwise
     */
    public static boolean canPlayerUse(@NotNull BlockEntity blockEntity,
                                       @NotNull PlayerEntity player) {
        var world = blockEntity.getWorld();
        if (world == null) {
            return false;
        }
        var pos = blockEntity.getPos();
        if (!Objects.equals(world.getBlockEntity(pos), blockEntity)) {
            return false;
        }
        var distance = player.squaredDistanceTo(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
        return distance <= 64.0;
    }
}
