package com.nextplugins.nextbackup.service.filter;

import java.io.File;
import java.io.FilenameFilter;

public class PluginFilenameFilter implements FilenameFilter {

    @Override
    public boolean accept(File dir, String name) {
        return name.endsWith(".jar");
    }

}
