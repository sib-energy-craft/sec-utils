package com.github.sib_energy_craft.sec_utils.load;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @since 0.0.3
 * @author sibmaks
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LoadUtils {


    /**
     * Load all mod registrars in package of root class<br/>
     * Method handle all exceptions due to no app crash on mod loading
     *
     * @param root root class
     * @param mod mod code for logs
     * @see ModRegistrar mod registrar
     */
    public static void load(Class<?> root, String mod) {
        var classLoader = root.getClassLoader();
        var packageName = root.getPackageName();
        load(classLoader, mod, packageName);
    }

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
        var stream = classLoader.getResourceAsStream(packageName.replace('.', '/'));
        if(stream == null) {
            return Collections.emptySet();
        }
        var reader = new BufferedReader(new InputStreamReader(stream));
        return reader.lines()
                .filter(it -> it.endsWith(".class"))
                .map(it -> getClass(it, packageName))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    private static Class<? extends ModRegistrar> getClass(String className, String packageName) {
        try {
            var type = Class.forName(packageName + "."
                    + className.substring(0, className.lastIndexOf('.')));
            if(ModRegistrar.class.isAssignableFrom(type)) {
                return (Class<? extends ModRegistrar>) type;
            }
        } catch (ClassNotFoundException e) {
            log.error("Error get class", e);
        }
        return null;
    }

}
