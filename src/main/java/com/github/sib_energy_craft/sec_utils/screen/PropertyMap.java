package com.github.sib_energy_craft.sec_utils.screen;

import net.minecraft.screen.PropertyDelegate;
import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @since 0.0.1
 * @author sibmaks
 */
public class PropertyMap<K extends Enum<K>> implements PropertyDelegate {
    private final K[] enumConstants;
    private final EnumMap<K, Supplier<Integer>> supplierMap;
    private final EnumMap<K, Consumer<Integer>> consumerMap;

    public PropertyMap(@NotNull Class<K> type) {
        this.enumConstants = type.getEnumConstants();
        this.supplierMap = new EnumMap<>(type);
        this.consumerMap = new EnumMap<>(type);
    }

    /**
     * Add read-write property into map
     *
     * @param property property ket
     * @param supplier property supplier
     * @param consumer property consumer
     */
    public void add(@NotNull K property,
                    @NotNull Supplier<Integer> supplier,
                    @NotNull Consumer<Integer> consumer) {
        this.supplierMap.put(property, supplier);
        this.consumerMap.put(property, consumer);
    }

    /**
     * Add readonly property into map
     *
     * @param property property ket
     * @param supplier property supplier
     */
    public void add(@NotNull K property,
                    @NotNull Supplier<Integer> supplier) {
        this.supplierMap.put(property, supplier);
        this.consumerMap.put(property, it -> {});
    }

    @Override
    public int get(int index) {
        var prop = enumConstants[index];
        return supplierMap.get(prop).get();
    }

    @Override
    public void set(int index, int value) {
        var prop = enumConstants[index];
        consumerMap.get(prop).accept(value);
    }

    @Override
    public int size() {
        return consumerMap.size();
    }
}
