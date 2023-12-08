package ru.itis.gengine.utils;

import java.util.Locale;

public final class OsCheck {
    /**
     * types of Operating Systems
     */


    // cached result of OS detection
    protected static OsType detectedOS;

    /**
     * detect the operating system from the os.name System property and cache
     * the result
     *
     * @return - the operating system detected
     */
    public static OsType getOperatingSystemType() {
        if (detectedOS == null) {
            String OS = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
            if ((OS.contains("mac")) || (OS.contains("darwin"))) {
                detectedOS = OsType.MacOS;
            } else if (OS.contains("win")) {
                detectedOS = OsType.Windows;
            } else if (OS.contains("nux")) {
                detectedOS = OsType.Linux;
            } else {
                detectedOS = OsType.Other;
            }
        }
        return detectedOS;
    }
}
