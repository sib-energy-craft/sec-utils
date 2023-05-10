package com.github.sib_energy_craft.sec_utils.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.TagKey;
import org.jetbrains.annotations.NotNull;

/**
 * @author sibmaks
 * @since 0.0.5
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TagUtils {

    /**
     * Check has item stack passed tag or not
     *
     * @param tag required tag
     * @param itemStack item stack to check
     * @return true - item stack has tag, false - otherwise
     */
    public static boolean hasTag(@NotNull TagKey<Item> tag,
                                 @NotNull ItemStack itemStack) {
        return itemStack.streamTags().anyMatch((it) -> it.equals(tag));
    }

    /**
     * Check has block passed tag or not
     *
     * @param tag required tag
     * @param blockState block to check
     * @return true - block has tag, false - otherwise
     */
    public static boolean hasTag(@NotNull TagKey<Block> tag,
                                 @NotNull BlockState blockState) {
        return blockState.streamTags().anyMatch((it) -> it.equals(tag));
    }
}
