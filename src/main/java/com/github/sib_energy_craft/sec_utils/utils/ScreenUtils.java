package com.github.sib_energy_craft.sec_utils.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.fabricmc.fabric.api.menu.v1.ExtendedMenuType;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.NotNull;

/**
 * @author sibmaks
 * @since 0.0.2
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ScreenUtils {

    /**
     * Register screen handler in {@link BuiltInRegistries#MENU}
     *
     * @param identifier  screen handler identifier
     * @param factory     screen factory
     * @param packetCodec packet codec instance
     * @param <T>         type of screen handler
     * @param <D>         packet codec type
     * @return registered screen handler
     */
    public static <T extends AbstractContainerMenu, D> @NotNull MenuType<T> registerHandler(
            @NotNull Identifier identifier,
            @NotNull ExtendedMenuType.ExtendedFactory<T, D> factory,
            @NotNull StreamCodec<? super RegistryFriendlyByteBuf, D> packetCodec) {
        var type = new ExtendedMenuType<>(factory, packetCodec);
        return Registry.register(BuiltInRegistries.MENU, identifier, type);
    }

    /**
     * Register screen handler in {@link BuiltInRegistries#MENU}
     *
     * @param screenHandlerType screen handler type
     * @param provider          handled screen provider
     * @param <T>               type of screen handler
     * @param <S>               type of screen and provided screen
     * @since 0.0.16
     */
    public static <T extends AbstractContainerMenu, S extends Screen & MenuAccess<T>> void registerScreen(
            @NotNull MenuType<T> screenHandlerType,
            @NotNull MenuScreens.ScreenConstructor<T, S> provider) {
        MenuScreens.register(screenHandlerType, provider);
    }
}
