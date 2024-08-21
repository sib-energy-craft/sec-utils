package com.github.sib_energy_craft.sec_utils.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.gui.screen.ingame.ScreenHandlerProvider;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

/**
 * @author sibmaks
 * @since 0.0.2
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ScreenUtils {

    /**
     * Register screen handler in {@link Registries#SCREEN_HANDLER}
     *
     * @param identifier  screen handler identifier
     * @param factory     screen factory
     * @param packetCodec packet codec instance
     * @param <T>         type of screen handler
     * @param <D>         packet codec type
     * @return registered screen handler
     */
    public static <T extends ScreenHandler, D> @NotNull ScreenHandlerType<T> registerHandler(
            @NotNull Identifier identifier,
            @NotNull ExtendedScreenHandlerType.ExtendedFactory<T, D> factory,
            @NotNull PacketCodec<? super RegistryByteBuf, D> packetCodec) {
        var type = new ExtendedScreenHandlerType<>(factory, packetCodec);
        return Registry.register(Registries.SCREEN_HANDLER, identifier, type);
    }

    /**
     * Register screen handler in {@link Registries#SCREEN_HANDLER}
     *
     * @param screenHandlerType screen handler type
     * @param provider          handled screen provider
     * @param <T>               type of screen handler
     * @param <S>               type of screen and provided screen
     * @since 0.0.16
     */
    public static <T extends ScreenHandler, S extends Screen & ScreenHandlerProvider<T>> void registerScreen(
            @NotNull ScreenHandlerType<T> screenHandlerType,
            @NotNull HandledScreens.Provider<T, S> provider) {
        HandledScreens.register(screenHandlerType, provider);
    }
}
