package com.github.sib_energy_craft.sec_utils.utils;

import com.github.sib_energy_craft.sec_utils.common.Identified;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

/**
 * @since 0.0.1
 * @author sibmaks
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ItemUtils {

    /**
     * Build and register item in {@link Registries#ITEM}
     *
     * @param itemGroup item group
     * @param block identified block
     * @param itemCreator item factory
     * @return registered item
     */
    public static<B extends Block, R extends BlockItem> R register(@NotNull ItemGroup itemGroup,
                                                                   @NotNull Identified<? extends B> block,
                                                                   @NotNull Function<B, R> itemCreator) {
        var entity = block.entity();
        var identifier = block.identifier();
        var blockItem = itemCreator.apply(entity);
        return register(itemGroup, identifier, blockItem);
    }

    /**
     * Register item in {@link Registries#ITEM}
     *
     * @param itemGroup item group
     * @param identifier item identifier
     * @param item item instance
     * @return registered item
     */
    public static<T extends Item> T register(@NotNull ItemGroup itemGroup,
                                             @NotNull Identifier identifier,
                                             @NotNull T item) {
        ItemGroupEvents.modifyEntriesEvent(itemGroup).register(entries -> entries.add(item));
        return Registry.register(Registries.ITEM, identifier, item);
    }

    /**
     * Create and register item in {@link Registries#ITEM}
     *
     * @param itemGroup item group
     * @param identifier item identifier
     * @param settings item settings
     * @return registered item
     */
    public static Item register(@NotNull ItemGroup itemGroup,
                                @NotNull Identifier identifier,
                                @NotNull Item.Settings settings) {
        var item = new Item(settings);
        return register(itemGroup, identifier, item);
    }

    /**
     * Register block item in {@link Registries#ITEM}
     *
     * @param itemGroup item group
     * @param block identified and registered block
     * @return registered item
     */
    public static Item registerBlockItem(@NotNull ItemGroup itemGroup,
                                         @NotNull Identified<? extends Block> block) {
        var entity = block.entity();
        var identifier = block.identifier();
        var settings = new Item.Settings();
        var item = new BlockItem(entity, settings);
        return register(itemGroup, identifier, item);
    }
}
