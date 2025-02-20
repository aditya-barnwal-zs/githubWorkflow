package com.zs.assignments.assignment1;


public class SystemInfo {
    public static void main(String[] args) {
        SystemDetails systemDetails = new SystemDetails();

        String currentUser = systemDetails.getUser();
        String homeDirectory = systemDetails.getHomeDirectory();
        long systemMemory = systemDetails.getSystemMemory();
        int systemCores = systemDetails.getSystemCores();
        long systemDiskSize = systemDetails.getSystemDiskSize();
        String osBuild = systemDetails.getOsBuild();
        String osVersion = systemDetails.getOsVersion();

        System.out.println("Name of user is: " + currentUser);
        System.out.println("Home Directory is: " + homeDirectory);
        System.out.println("System memory size is: " + systemMemory + " GB");
        System.out.println("System cores count is: " + systemCores);
        System.out.println("System disk size is: " + systemDiskSize + " GB");
        System.out.println("OS Build is: " + osBuild);
        System.out.println("OS Version is: " + osVersion);
    }
}
