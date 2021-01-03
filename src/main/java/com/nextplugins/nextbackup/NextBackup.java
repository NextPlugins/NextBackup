package com.nextplugins.nextbackup;

import com.nextplugins.nextbackup.runnable.TimedBackupTask;
import com.nextplugins.nextbackup.service.BackupService;
import io.github.eikefs.minecraft.lib.ConfigValue;
import io.github.eikefs.minecraft.lib.api.mapper.ConfigMapper;
import lombok.Getter;
import me.bristermitten.pdm.PluginDependencyManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class NextBackup extends JavaPlugin {

    @Getter private BackupService backupService;

    @Override
    public void onEnable() {
        PluginDependencyManager.of(this).loadAllDependencies().thenRun(() -> {
            new TimedBackupTask(getBackupService(), this).runTaskTimer(this, 60, 20);
        });

        this.backupService = new BackupService(this);

        saveDefaultConfig();

        File backupDir = new File(getDataFolder(), "backups");

        if (!backupDir.exists()) backupDir.mkdir();
    }

}
