package com.nextplugins.nextbackup.compressor;

import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;
import org.apache.commons.compress.archivers.sevenz.SevenZOutputFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Calendar;

@Deprecated
public final class Compressor {

    public static void compress(File folder, File... files) throws IOException {
        try (SevenZOutputFile out = new SevenZOutputFile(new File(folder, fileName()))){
            for (File file : files){
                addToArchiveCompression(out, file, ".");
            }
        }
    }

    private static void addToArchiveCompression(SevenZOutputFile out, File file, String dir) {
        String name = dir + File.separator + file.getName();

        try {
            if (file.isFile()) {
                SevenZArchiveEntry entry = out.createArchiveEntry(file, name);
                out.putArchiveEntry(entry);

                try (FileInputStream in = new FileInputStream(file)) {
                    byte[] b = new byte[1024];
                    int count = 0;

                    while ((count = in.read(b)) > 0) {
                        out.write(b, 0, count);
                    }

                    out.closeArchiveEntry();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            } else if (file.isDirectory()) {
                File[] children = file.listFiles();

                if (children != null) {
                    for (File child : children) {
                        addToArchiveCompression(out, child, name);
                    }
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private static String fileName() {
        Calendar calendar = Calendar.getInstance();

        return String.format("%s-%s-%s at %s-%s-%s",
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.HOUR),
                calendar.get(Calendar.MINUTE),
                calendar.get(Calendar.SECOND));
    }

}
