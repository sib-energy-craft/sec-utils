package com.github.sib_energy_craft.sec_utils.load;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.Set;

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
    public static boolean load(Class<?> root, String mod) {
        var classLoader = root.getClassLoader();
        var packageName = root.getPackageName();
        return load(classLoader, mod, packageName);
    }

    /**
     * Load all mod registrars in passed package<br/>
     * Method handle all exceptions due to no app crash on mod loading
     *
     * @param classLoader class load for registrar loading
     * @param mod mod code for logs
     * @param packageName package path
     * @return true - loaded without error, false - otherwise
     * @see ModRegistrar mod registrar
     */
    public static boolean load(ClassLoader classLoader, String mod, String packageName) {
        log.debug("Load: {}", mod);
        boolean errorHappened = false;
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
                    errorHappened = true;
                }
            }
            log.debug("Loaded: {}", mod);
        } catch (Exception e) {
            // don't crash app in case if mod load failed
            log.error("Can't load mod %s".formatted(mod), e);
            errorHappened = true;
        }
        return !errorHappened;
    }

    private static Set<Class<ModRegistrar>> findAllRegistrars(ClassLoader classLoader, String packageName) throws IOException {
        return ReflectionUtils.getClasses(classLoader, packageName, it ->
                !it.isInterface() && !Modifier.isAbstract(it.getModifiers()) &&
                ModRegistrar.class.isAssignableFrom(it));
    }

}
