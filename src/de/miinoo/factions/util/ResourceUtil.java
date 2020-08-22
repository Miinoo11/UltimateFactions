package de.miinoo.factions.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ResourceUtil {

    public static void writeResource(String resource, File file) {
        InputStream source = null;
        OutputStream target = null;
        try {
            if (!file.isFile()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            source = ResourceUtil.class.getResourceAsStream(resource);
            target = new FileOutputStream(file);
            byte[] buf = new byte[2056];
            int n;
            while ((n = source.read(buf)) > 0) {
                target.write(buf, 0, n);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (source != null) {
                try {
                    source.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (target != null) {
                try {
                    target.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static void createAndWrite(String resource, File file) {
        if (!file.isFile()) {
            writeResource(resource, file);
        }
    }

}