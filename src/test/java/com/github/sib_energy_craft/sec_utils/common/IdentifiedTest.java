package com.github.sib_energy_craft.sec_utils.common;

import net.minecraft.util.Identifier;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

/**
 * @author sibmaks
 * @since 2023-04-28
 */
class IdentifiedTest {

    @Test
    void testConstruct() {
        var identifier = mock(Identifier.class);
        var entity = UUID.randomUUID().toString();
        var identified = new Identified<>(identifier, entity);

        assertEquals(identifier, identified.identifier());
        assertEquals(entity, identified.entity());
    }

}