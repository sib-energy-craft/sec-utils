package com.github.sib_energy_craft.sec_utils.load;

import com.github.sib_energy_craft.sec_utils.load.sub.SubStubRegistrar;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author sibmaks
 */
class LoadUtilsTest {

    @Test
    void testGetRegistrar() {
        String mod = UUID.randomUUID().toString();
        boolean loaded = LoadUtils.load(LoadUtilsTest.class, mod);
        assertTrue(loaded);

        assertTrue(StubRegistrar.initialized);
        assertFalse(SubStubRegistrar.initialized);
    }

}