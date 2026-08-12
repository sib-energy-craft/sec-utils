package com.github.sib_energy_craft.sec_common.screen.property;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;

/**
 * @author sibmaks
 * @since 0.0.7-1.21.4
 */
public interface TypedScreenProperty<B extends ByteBuf, T> {

    /**
     * Get property identifier.
     *
     * @return property identifier
     */
    int id();

    /**
     * Get property codec.
     *
     * @return property codec
     */
    @NotNull
    StreamCodec<B, T> codec();

    /**
     * Get property value.
     *
     * @return property value
     */
    T value();

}
