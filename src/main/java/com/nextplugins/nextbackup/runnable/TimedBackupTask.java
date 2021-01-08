package com.nextplugins.nextbackup.runnable;

import com.nextplugins.nextbackup.service.BackupService;
import com.nextplugins.nextbackup.service.Compressor;
import io.github.eikefs.minecraft.lib.ConfigValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

@AllArgsConstructor
@Getter
public class TimedBackupTask extends BukkitRunnable {

    @ConfigValue("ticks-delay") public static int delay;

    private final BackupService service;
    private final JavaPlugin plugin;

    @SneakyThrows
    @Override
    public void run() {
        final Logger log = getPlugin().getLogger();
        final File backupFile = service.getBackupFile();

        log.warning("Creating backup! Server may lag a bit...");

        Compressor.compress(new File(getPlugin().getDataFolder(), "backups"), backupFile);

        backupFile.delete();

        log.info("Done! Backup created.");
    }

}
