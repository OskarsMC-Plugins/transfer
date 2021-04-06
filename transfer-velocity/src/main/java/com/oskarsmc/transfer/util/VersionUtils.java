package com.oskarsmc.transfer.util;

import com.oskarsmc.transfer.configuration.TransferSettings;

public class VersionUtils {

    public static final double CONFIG_VERSION = 0.1;

    public static boolean isLatestConfigVersion(TransferSettings transferSettings) {
        if (transferSettings.getConfigVersion() == null) {
            return false;
        }
        return transferSettings.getConfigVersion() == CONFIG_VERSION;
    }
}