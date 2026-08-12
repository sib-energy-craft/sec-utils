package com.github.sib_energy_craft.sec_common.screen.property;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

/**
 * @author sibmaks
 * @since 0.0.1
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ScreenPropertyRegistry {
    private static final BiMap<Integer, StreamCodec<?, ?>> INDEXED_PROPERTY_TYPE;
    private static final Set<StreamCodec<?, ?>> VALUES;

    static {
        INDEXED_PROPERTY_TYPE = HashBiMap.create();
        VALUES = new HashSet<>(INDEXED_PROPERTY_TYPE.values());

        registry(ScreenPropertyCodecs.SHORT);
        registry(ScreenPropertyCodecs.INTEGER);
        registry(ScreenPropertyCodecs.FLOAT);
        registry(ScreenPropertyCodecs.DOUBLE);
        registry(ScreenPropertyCodecs.ITEM_STACK);
        registry(ScreenPropertyCodecs.BOOLEAN);
        registry(ScreenPropertyCodecs.STRING);
        registry(ScreenPropertyCodecs.ENERGY);
    }

    /**
     * Register a new codec of screen properties into registry
     *
     * @param index                   property codec index
     * @param screenPropertyTypeCodec screen property codec
     */
    public static synchronized void registry(int index,
                                             @NotNull StreamCodec<? extends ByteBuf, ?> screenPropertyTypeCodec) {
        if (INDEXED_PROPERTY_TYPE.containsKey(index)) {
            throw new IllegalStateException("Property codec index %s already registered".formatted(index));
        }
        INDEXED_PROPERTY_TYPE.put(index, screenPropertyTypeCodec);
        if (VALUES.contains(screenPropertyTypeCodec)) {
            throw new IllegalStateException("Property codec %s already registered".formatted(screenPropertyTypeCodec));
        }
        VALUES.add(screenPropertyTypeCodec);
    }

    /**
     * Register a new codec of screen properties into registry
     *
     * @param screenPropertyTypeCodec screen property codec
     * @return property codec index
     */
    public static synchronized int registry(@NotNull StreamCodec<? extends ByteBuf, ?> screenPropertyTypeCodec) {
        var index = INDEXED_PROPERTY_TYPE.keySet()
                .stream()
                .max(Integer::compareTo)
                .orElse(0) + 1;
        INDEXED_PROPERTY_TYPE.put(index, screenPropertyTypeCodec);
        if (VALUES.contains(screenPropertyTypeCodec)) {
            throw new IllegalStateException("Property codec %s already registered".formatted(screenPropertyTypeCodec));
        }
        VALUES.add(screenPropertyTypeCodec);
        return index;
    }

    /**
     * Get all supported property types
     *
     * @return set of property types
     */
    public static Set<StreamCodec<?, ?>> values() {
        return VALUES;
    }

    /**
     * Get property codec index by reference
     *
     * @param type property codec
     * @return index of a property codec
     */
    public static int getIndex(@NotNull StreamCodec<?, ?> type) {
        return INDEXED_PROPERTY_TYPE.inverse().get(type);
    }

    /**
     * Get a property codec by index
     *
     * @param index property codec index
     * @return property codec
     */
    public static <T> StreamCodec<? super ByteBuf, T> findValue(int index) {
        return (StreamCodec<? super ByteBuf, T>) INDEXED_PROPERTY_TYPE.get(index);
    }
}
