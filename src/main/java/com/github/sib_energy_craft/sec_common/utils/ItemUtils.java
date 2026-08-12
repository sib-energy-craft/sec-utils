package com.github.sib_energy_craft.sec_common.utils;

import com.github.sib_energy_craft.sec_common.common.Identified;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

/**
 * @author sibmaks
 * @since 0.0.1
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ItemUtils {

    /**
     * Build and register item in {@link BuiltInRegistries#ITEM}
     *
     * @param itemGroup   item group registry key
     * @param block       identified block
     * @param itemCreator item factory
     * @param <B>         block type
     * @param <R>         block item type
     * @return registered item
     */
    public static <B extends Block, R extends BlockItem> R register(@NotNull ResourceKey<CreativeModeTab> itemGroup,
                                                                    @NotNull Identified<? extends B> block,
                                                                    @NotNull Function<B, R> itemCreator) {
        var entity = block.entity();
        var identifier = block.identifier();
        var blockItem = itemCreator.apply(entity);
        return register(itemGroup, identifier, blockItem);
    }

    /**
     * Register item in {@link BuiltInRegistries#ITEM}
     *
     * @param itemGroup  item group registry key
     * @param identifier item identifier
     * @param item       item instance
     * @param <T>        item type
     * @return registered item
     */
    public static <T extends Item> T register(@NotNull ResourceKey<CreativeModeTab> itemGroup,
                                              @NotNull Identifier identifier,
                                              @NotNull T item) {
        CreativeModeTabEvents.modifyOutputEvent(itemGroup).register(entries -> entries.accept(item));
        return Registry.register(BuiltInRegistries.ITEM, identifier, item);
    }

    /**
     * Create and register item in {@link BuiltInRegistries#ITEM}
     *
     * @param itemGroup  item group registry key
     * @param identifier item identifier
     * @param settings   item settings
     * @return registered item
     */
    public static Item register(@NotNull ResourceKey<CreativeModeTab> itemGroup,
                                @NotNull Identifier identifier,
                                @NotNull Item.Properties settings) {
        settings.setId(keyOf(identifier));
        var item = new Item(settings);
        return register(itemGroup, identifier, item);
    }

    /**
     * Register block item in {@link BuiltInRegistries#ITEM}
     *
     * @param itemGroup item group registry key
     * @param block     identified and registered block
     * @return registered item
     */
    public static BlockItem registerBlockItem(@NotNull ResourceKey<CreativeModeTab> itemGroup,
                                              @NotNull Identified<? extends Block> block) {
        var entity = block.entity();
        var identifier = block.identifier();
        var settings = new Item.Properties()
                .setId(keyOf(identifier))
                .useBlockDescriptionPrefix();
        var item = new BlockItem(entity, settings);
        return register(itemGroup, identifier, item);
    }

    /**
     * Get item registry key by identifier
     *
     * @param identifier item identifier
     * @return item registry key
     */
    public static ResourceKey<Item> keyOf(Identifier identifier) {
        return ResourceKey.create(Registries.ITEM, identifier);
    }
}
