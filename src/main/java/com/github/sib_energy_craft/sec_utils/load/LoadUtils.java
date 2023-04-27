package com.github.sib_energy_craft.sec_utils.load;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ConfigurationBuilder;

import java.util.Set;

/**
 * @since 0.0.3
 * @author sibmaks
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LoadUtils {

    /**
     * Load all mod registrars in passed package<br/>
     * Method handle all exceptions due to no app crash on mod loading
     *
     * @param classLoader class load for registrar loading
     * @param mod mod code for logs
     * @param packageName package path
     * @see ModRegistrar mod registrar
     */
    public static void load(ClassLoader classLoader, String mod, String packageName) {
        log.debug("Load: {}", mod);
        try {
            var registrars = findAllRegistrars(classLoader, packageName);
            for (var type : registrars) {
                try {
                    var defaultConstructor = type.getDeclaredConstructor();
                    var registrar = defaultConstructor.newInstance();
                    registrar.load();
                    log.debug("Registrar loaded: {}#{}", mod, Class.forName(type.getName()));
                } catch (Exception e) {
                    // don't crash app in case if registrar creation failed
                    log.error("Can't load registrar %s mod %s".formatted(type.getName(), mod), e);
                }
            }
            log.debug("Loaded: {}", mod);
        } catch (Exception e) {
            // don't crash app in case if mod load failed
            log.error("Can't load mod %s".formatted(mod), e);
        }
    }

    private static Set<Class<? extends ModRegistrar>> findAllRegistrars(ClassLoader classLoader, String packageName) {
        var configurationBuilder = ConfigurationBuilder.build()
                .forPackages(packageName)
                .addClassLoaders(classLoader)
                .addScanners(Scanners.SubTypes);

        var reflections = new Reflections(configurationBuilder);
        return reflections.getSubTypesOf(ModRegistrar.class);
    }

}
