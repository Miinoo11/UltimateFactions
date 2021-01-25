package de.miinoo.factions.addon;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class AddonLoader {

   public static Collection<FactionsAddon> loadAddons(File dir) {
        if (!dir.isDirectory()) {
            dir.mkdirs();
        }
        try {
            Collection<FactionsAddon> addons = new ArrayList<>();
            for(Class clazz : loadClasses(dir)) {
                Constructor constructor = clazz.getDeclaredConstructor();
                if(constructor == null){
                    System.err.println("Error loading addon "+clazz.getName()+": constructor not found");
                    continue;
                }
                constructor.setAccessible(true);
                addons.add((FactionsAddon) constructor.newInstance());
            }
            return addons;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.EMPTY_LIST;
    }

    private static Iterable<Class> loadClasses(File dir) throws IOException, ClassNotFoundException {
        List<Class> addons = new ArrayList<>();
        File[] jars = dir.listFiles((file, name) -> name.endsWith(".jar"));
        URL[] locations = new URL[jars.length + 1];
        for (int i = 0; i < jars.length; i++) {
            locations[i] = jars[i].toURI().toURL();
        }
        locations[jars.length] = dir.toURI().toURL();
        URLClassLoader loader = new URLClassLoader(locations, AddonLoader.class.getClassLoader());
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                String name = file.getName();
                if (name.endsWith(".class")) {
                    Class clazz = loader.loadClass(name.substring(0, name.length() - 6));
                    if(FactionsAddon.class.isAssignableFrom(clazz)){
                        addons.add(clazz);
                    }
                } else if (name.endsWith(".jar")) {
                    JarFile jar = new JarFile(file);
                    Enumeration<JarEntry> entries = jar.entries();
                    while (entries.hasMoreElements()) {
                        JarEntry entry = entries.nextElement();
                        if (entry.getName().endsWith(".class")) {
                            Class clazz = loader.loadClass(entry.getName().substring(0, entry.getName().length() - 6).replaceAll("/|\\\\", "\\."));
                            if(FactionsAddon.class.isAssignableFrom(clazz)){
                                addons.add(clazz);
                            }
                        }
                    }
                    jar.close();
                }
            }
        }
        loader.close();
        return addons;
    }

}
