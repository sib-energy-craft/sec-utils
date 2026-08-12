package com.github.sib_energy_craft.sec_utils.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.NotNull;

/**
 * Client-side screen registration utilities.
 *
 * @author sibmaks
 * @since 0.0.27
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ClientScreenUtils {

    /**
     * Register a client screen for a menu type.
     *
     * @param screenHandlerType screen handler type
     * @param provider          handled screen provider
     * @param <T>               type of screen handler
     * @param <S>               type of screen and provided screen
     */
    public static <T extends AbstractContainerMenu, S extends Screen & MenuAccess<T>> void registerScreen(
            @NotNull MenuType<T> screenHandlerType,
            @NotNull MenuScreens.ScreenConstructor<T, S> provider) {
        MenuScreens.register(screenHandlerType, provider);
    }
}
