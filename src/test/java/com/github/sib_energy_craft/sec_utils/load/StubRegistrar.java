package com.github.sib_energy_craft.sec_utils.load;

/**
 * @author sibmaks
 */
public class StubRegistrar implements ModRegistrar {
    public static boolean initialized;

    @Override
    public void load() {
        initialized = true;
    }
}
