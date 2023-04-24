package com.github.sib_energy_craft.sec_utils.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.gui.screen.ingame.ScreenHandlerProvider;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

/**
 * @author sibmaks
 * Created at 19-12-2022
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ScreenUtils {

    /**
     * Register screen handler in {@link Registries#SCREEN_HANDLER}
     *
     * @param identifier screen handler identifier
     * @param factory screen factory
     * @param provider handled screen provider
     * @return registered screen handler
     * @param <T> type of screen handler
     * @param <S> type of screen & provided scren
     */
    public static <T extends ScreenHandler, S extends Screen & ScreenHandlerProvider<T>>
        ScreenHandlerType<T> register(@NotNull Identifier identifier,
                                      @NotNull ExtendedScreenHandlerType.ExtendedFactory<T> factory,
                                      @NotNull HandledScreens.Provider<T, S> provider) {
        var type = new ExtendedScreenHandlerType<>(factory);
        var register = Registry.register(Registries.SCREEN_HANDLER, identifier, type);
        HandledScreens.register(register, provider);
        return register;
    }
}
