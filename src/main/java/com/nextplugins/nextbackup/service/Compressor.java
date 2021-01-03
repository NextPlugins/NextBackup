package com.nextplugins.nextbackup.service;

import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZOutputFile;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.util.Calendar;

public class Compressor {

    public static void compress(File target, File folder) {
        final String fileName = fileName() + ".7z";

        try (SevenZOutputFile outputFile = new SevenZOutputFile(new File(folder, fileName))) {
            Files.walk(target.toPath()).forEach(path -> {
                File file = path.toFile();

                if (!file.isDirectory()) {
                    try (FileInputStream inputStream = new FileInputStream(file)) {
                        SevenZArchiveEntry entry = outputFile.createArchiveEntry(file, file.toString());

                        outputFile.putArchiveEntry(entry);
                        outputFile.write(Files.readAllBytes(file.toPath()));
                        outputFile.closeArchiveEntry();
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            });

            outputFile.finish();
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
