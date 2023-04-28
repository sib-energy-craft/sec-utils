package com.github.sib_energy_craft.sec_utils.screen;

import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author sibmaks
 * @since 2023-04-28
 */
class PropertyMapTest {

    @Test
    void testReadonlyProperty() {
        var keyAValue = UUID.randomUUID().hashCode();

        var propertyMap = new PropertyMap<>(FixtureProperties.class);

        propertyMap.add(FixtureProperties.KEY_A, () -> keyAValue);
        assertEquals(1, propertyMap.size());

        int actual = propertyMap.get(FixtureProperties.KEY_A.ordinal());
        assertEquals(keyAValue, actual);

        var keyAOtherValue = UUID.randomUUID().hashCode();
        propertyMap.set(FixtureProperties.KEY_A.ordinal(), keyAOtherValue);

        // property not changed
        actual = propertyMap.get(FixtureProperties.KEY_A.ordinal());
        assertEquals(keyAValue, actual);
    }

    @Test
    void testProperty() {
        var startValue = UUID.randomUUID().hashCode();
        var keyAValue = new AtomicInteger(startValue);

        var propertyMap = new PropertyMap<>(FixtureProperties.class);

        propertyMap.add(FixtureProperties.KEY_A, keyAValue::get, keyAValue::set);
        assertEquals(1, propertyMap.size());

        int actual = propertyMap.get(FixtureProperties.KEY_A.ordinal());
        assertEquals(startValue, actual);

        var keyAOtherValue = UUID.randomUUID().hashCode();
        propertyMap.set(FixtureProperties.KEY_A.ordinal(), keyAOtherValue);

        // property not changed
        actual = propertyMap.get(FixtureProperties.KEY_A.ordinal());
        assertEquals(keyAOtherValue, actual);
    }

}