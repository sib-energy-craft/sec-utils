package com.github.sib_energy_craft.sec_utils.load.sub;

import com.github.sib_energy_craft.sec_utils.load.ModRegistrar;

/**
 * @author sibmaks
 */
public class SubStubRegistrar implements ModRegistrar {
    public static boolean initialized;

    @Override
    public void load() {
        initialized = true;
    }
}
