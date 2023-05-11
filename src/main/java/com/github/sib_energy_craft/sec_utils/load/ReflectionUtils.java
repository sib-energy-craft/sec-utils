package com.github.sib_energy_craft.sec_utils.load;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.function.Predicate;
import java.util.zip.ZipFile;

/**
 * Reflections utils for getting class in packages, get class annotations and so on.
 *
 * @author sibmaks
 * @since 0.0.10
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ReflectionUtils {
    private static final String CLASS_EXT = ".class";


    /**
     * Get filtered classes in specific package. Not recursive
     *
     * @param classLoader class loader
     * @param packageName package name
     * @param condition filter condition
     * @return set of class
     * @param <T> type of class
     * @throws IOException read resource exception
     */
    public static<T> Set<Class<T>> getClasses(@NotNull ClassLoader classLoader,
                                              @NotNull String packageName,
                                              @NotNull Predicate<Class<T>> condition) throws IOException {
        var path = packageName.replace('.', '/');
        var resources = classLoader.getResources(path);
        var dirs = new HashMap<String, List<URL>>();
        dirs.put("jar", new ArrayList<>());
        dirs.put("file", new ArrayList<>());
        while (resources.hasMoreElements()) {
            var resource = resources.nextElement();
            var files = dirs.get(resource.getProtocol());
            if(files == null) {
                log.warn("Protocol {} is unsupported", resource.getProtocol());
                continue;
            }
            files.add(resource);
        }
        var classes = new HashSet<Class<T>>();
        for (var directory : dirs.get("file")) {
            classes.addAll(findClassesByFileProtocol(new File(directory.getFile()), packageName, condition));
        }
        for (var directory : dirs.get("jar")) {
            classes.addAll(findClassesByJarProtocol(directory, condition));
        }
        return classes;
    }

    private static<T> Set<Class<T>> findClassesByJarProtocol(@NotNull URL directory,
                                                             @NotNull Predicate<Class<T>> condition) {
        var parts = directory.getFile().split("!");
        var jarFileName = parts[0];
        var innerPath = parts[1].substring(1);
        try {
            URL jarURL = new URL(jarFileName);
            var jarFile = new File(jarURL.getFile());
            if (!jarFile.exists()) {
                return Collections.emptySet();
            }
            var classInfos = new HashSet<Class<T>>();
            try (var zipFile = new ZipFile(jarURL.getFile())) {
                var entries = zipFile.entries();
                while (entries.hasMoreElements()) {
                    var zipEntry = entries.nextElement();
                    var name = zipEntry.getName();
                    if (!name.startsWith(innerPath) && name.endsWith(CLASS_EXT)) {
                        continue;
                    }
                    var className = name.substring(0, name.length() - CLASS_EXT.length()).replace("/", ".");
                    Optional.ofNullable(buildClassInfo(condition, className)).ifPresent(classInfos::add);
                }
            }
            return classInfos;
        } catch (Exception e) {
            log.error("Get classes from jar exception", e);
        }
        return Collections.emptySet();
    }

    private static<T> Class<T> buildClassInfo(@NotNull Predicate<Class<T>> condition,
                                                        @NotNull String className) {
        try {
            var clazz = (Class<T>) Class.forName(className);
            if (condition.test(clazz)) {
                return clazz;
            }
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    private static<T> Set<Class<T>> findClassesByFileProtocol(@NotNull File directory,
                                                              @NotNull String packageName,
                                                              @NotNull Predicate<Class<T>> condition) {
        if (!directory.exists()) {
            return Collections.emptySet();
        }
        var files = directory.listFiles();
        if (files == null) {
            return Collections.emptySet();
        }
        var classes = new HashSet<Class<T>>();
        for (var file : files) {
            if (file.getName().endsWith(CLASS_EXT)) {
                try {
                    var clazz = (Class<T>) Class.forName(packageName + '.' +
                            file.getName().substring(0, file.getName().length() - 6));
                    if (condition.test(clazz)) {
                        classes.add(clazz);
                    }
                } catch (ClassNotFoundException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
        return classes;
    }

}