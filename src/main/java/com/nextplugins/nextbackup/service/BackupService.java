package com.nextplugins.nextbackup.service;

import com.nextplugins.nextbackup.service.filter.PluginFilenameFilter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@AllArgsConstructor
public final class BackupService {

    private final JavaPlugin plugin;

    public File getPluginsFolder() {
        return plugin.getDataFolder().getParentFile();
    }

    public List<File> getPluginsFolders() {
        List<File> fileList = new ArrayList<>();

        for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
            if (!plugin.getName().equalsIgnoreCase("NextBackup"))
            fileList.add(plugin.getDataFolder());
        }

        return fileList;
    }

    public List<File> getWorldsFolders() {
        List<File> fileList = new ArrayList<>();

        for (World world : Bukkit.getWorlds()) {
            fileList.add(world.getWorldFolder());
        }

        return fileList;
    }

    public List<File> getPluginsFiles() {
        return Arrays.asList(getPluginsFolder().listFiles(new PluginFilenameFilter()));
    }

    @SneakyThrows
    public File getBackupFile() {
        File directory = new File("Backup");
        File worldDirectory = new File(directory, "Worlds");
        File pluginDirectory = new File(directory, "Plugins");

        directory.mkdir();
        worldDirectory.mkdir();
        pluginDirectory.mkdir();

        for (File pluginFile : getPluginsFiles()) {
            File backupPluginFile = new File(pluginDirectory, pluginFile.getName());

            FileUtils.copyFile(pluginFile, backupPluginFile);
        }

        for (File pluginDir : getPluginsFolders()) {
            File backupPluginDir = new File(pluginDirectory, pluginDir.getName());

            FileUtils.copyDirectory(pluginDir, backupPluginDir);
        }

        for (File worldDir : getWorldsFolders()) {
            File backupWorldDir = new File(worldDirectory, worldDir.getName());

            FileUtils.copyDirectory(worldDir, backupWorldDir);
        }

        return directory;
    }

}
