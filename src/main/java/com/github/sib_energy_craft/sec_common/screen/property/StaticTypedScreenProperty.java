package com.github.sib_energy_craft.sec_common.screen.property;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;

/**
 * Static typed screen property.
 *
 * @author sibmaks
 * @since 0.0.7-1.21.4
 */
public record StaticTypedScreenProperty<B extends ByteBuf, T>(
        int id,
        @NotNull StreamCodec<B, T> codec,
        @NotNull T value
) implements TypedScreenProperty<B, T> {

}
