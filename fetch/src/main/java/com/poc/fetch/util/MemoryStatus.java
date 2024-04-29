package com.poc.fetch.util;

public class MemoryStatus {
    private static final long MB = 1048576;

    private MemoryStatus() {
    }

    public static void showMemoryStatus() {
        long totalMemory = Runtime.getRuntime().totalMemory() / MB;
        long freeMemory = Runtime.getRuntime().freeMemory() / MB;
        System.out.println(String.format("Memoria total: %s \nMemoria usada: %s \nMemoria libre. %s", totalMemory, (totalMemory - freeMemory), freeMemory));
        System.out.println("------------------------------------------------------------------------------------------------------------------------");
    }
}
