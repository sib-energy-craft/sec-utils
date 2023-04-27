package com.github.sib_energy_craft.sec_utils.load;

/**
 * @since 0.0.3
 * @author sibmaks
 */
public interface ModRegistrar {

    /**
     * Called each time when registrar is load.<br/>
     * By default, no additional actions required, so implementation is empty.
     */
    default void load() {

    }

}
