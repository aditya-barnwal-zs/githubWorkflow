package com.zs.assignments.assignment1;

import java.io.File;

import java.lang.management.ManagementFactory;

import com.sun.management.OperatingSystemMXBean;

public class SystemDetails {
    OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
    File root = new File("/");

    public String getUser() {
        return System.getProperty("user.name");
    }

    public String getHomeDirectory() {
        return System.getProperty("user.home");
    }

    public String getOsVersion() {
        return System.getProperty("os.version");
    }

    public String getOsBuild() {
        return System.getProperty("os.name");
    }

    public long getSystemMemory() {
        long totalMemoryInBytes = osBean.getTotalPhysicalMemorySize();
        long totalMemoryInGB = (totalMemoryInBytes / (1024 * 1024 * 1024));
        return totalMemoryInGB;
    }

    public long getSystemDiskSize() {
        long totalDiskSpaceInBytes = root.getTotalSpace();
        long totalDiskSpaceInGB = (int) (totalDiskSpaceInBytes / (1024 * 1024 * 1024));
        return totalDiskSpaceInGB;
    }

    public int getSystemCores() {
        return osBean.getAvailableProcessors();
    }
}
