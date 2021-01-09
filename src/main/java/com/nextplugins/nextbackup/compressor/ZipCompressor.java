package com.nextplugins.nextbackup.compressor;

import java.io.File;
import java.io.FileInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public final class ZipCompressor {

    public static void zip(File fileToZip, String fileName, ZipOutputStream zipOut) throws Exception {
        if (fileToZip.isDirectory()) {

            if (fileName.endsWith("/")) {
                zipOut.putNextEntry(new ZipEntry(fileName));
            } else {
                zipOut.putNextEntry(new ZipEntry(fileName + "/"));
            }

            zipOut.closeEntry();

            File[] children = fileToZip.listFiles();

            for (File childFile : children) {
                zip(childFile, fileName + "/" + childFile.getName(), zipOut);
            }
            return;
        }

        zipFile(fileToZip, fileName, zipOut);
    }

    public static void zipFile(File target, String name, ZipOutputStream zipOutputStream) {
         try (FileInputStream fileInput = new FileInputStream(target)) {
             ZipEntry entry = new ZipEntry(name);

             zipOutputStream.putNextEntry(entry);

             byte[] bytes = new byte[1024];
             int length;

             while ((length = fileInput.read(bytes)) >= 0) {
                 zipOutputStream.write(bytes, 0, length);
             }

             zipOutputStream.closeEntry();
         } catch (Exception exception) {
             exception.printStackTrace();
         }
    }

}
