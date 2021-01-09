package com.nextplugins.nextbackup.runnable;

import com.nextplugins.nextbackup.compressor.ZipCompressor;
import com.nextplugins.nextbackup.service.BackupService;
import com.nextplugins.nextbackup.compressor.Compressor;
import io.github.eikefs.minecraft.lib.ConfigValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipOutputStream;

@AllArgsConstructor
@Getter
public class TimedBackupTask extends BukkitRunnable {

    @ConfigValue("ticks-delay") public static Integer delay;

    private final BackupService service;
    private final JavaPlugin plugin;

    @Override
    public void run() {
        final Logger log = getPlugin().getLogger();

        final File backupFile = service.getBackupFile();
        final File outputFile = new File(plugin.getDataFolder() + "/backups",
                                        Calendar.getInstance().toInstant().toString().replace(":", "-") + ".zip");

        try (
            FileOutputStream outputStream = new FileOutputStream(outputFile);
            ZipOutputStream zipOutput = new ZipOutputStream(outputStream)) {

            final long start = System.currentTimeMillis();

            log.warning("Creating backup! Server may lag a bit...");

            // TODO: Async this
            ZipCompressor.zip(backupFile, backupFile.getName(), zipOutput);

            backupFile.delete();

            log.info(String.format("Backup is done! Time elapsed: %s seconds!", ((System.currentTimeMillis() - start) / 1000)));
        } catch (Exception exception) {
            log.log(Level.SEVERE, "Fatal: " + exception.getMessage());

            cancel();
        }
    }

}
