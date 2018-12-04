package com.fusionjack.adhell3.utils;

import android.content.pm.PackageManager;
import android.os.Build;

import java.util.ArrayList;
import java.util.List;

public final class AppPermissionUtils {

    private AppPermissionUtils() {
    }

    public static boolean isDangerousLevel(int level) {
        level = level & android.content.pm.PermissionInfo.PROTECTION_MASK_BASE;
        return level == android.content.pm.PermissionInfo.PROTECTION_DANGEROUS;
    }

    public static List<String> getSiblingPermissions(String permissionName) {
        List<String> permissionNames = new ArrayList<>();
        PackageManager packageManager = AdhellFactory.getInstance().getPackageManager();
        try {
            android.content.pm.PermissionInfo info = packageManager.getPermissionInfo(permissionName, PackageManager.GET_META_DATA);
            List<android.content.pm.PermissionInfo> permissions = packageManager.queryPermissionsByGroup(info.group, PackageManager.GET_META_DATA);
            for (android.content.pm.PermissionInfo permission : permissions) {
                permissionNames.add(permission.name);
            }
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        return permissionNames;
    }

    public static String getProtectionLevelLabel(int level) {
        String protLevel = "未知";
        switch (level & android.content.pm.PermissionInfo.PROTECTION_MASK_BASE) {
            case android.content.pm.PermissionInfo.PROTECTION_DANGEROUS:
                protLevel = "危險";
                break;
            case android.content.pm.PermissionInfo.PROTECTION_NORMAL:
                protLevel = "一般";
                break;
            case android.content.pm.PermissionInfo.PROTECTION_SIGNATURE:
                protLevel = "簽名";
                break;
            case android.content.pm.PermissionInfo.PROTECTION_SIGNATURE_OR_SYSTEM:
                protLevel = "簽名或系統";
                break;
        }
        if ((level & android.content.pm.PermissionInfo.PROTECTION_FLAG_PRIVILEGED) != 0) {
            protLevel += "|特權";
        }
        if ((level & android.content.pm.PermissionInfo.PROTECTION_FLAG_DEVELOPMENT) != 0) {
            protLevel += "|開發";
        }
        if ((level & android.content.pm.PermissionInfo.PROTECTION_FLAG_APPOP) != 0) {
            protLevel += "|appop";
        }
        if ((level & android.content.pm.PermissionInfo.PROTECTION_FLAG_PRE23) != 0) {
            protLevel += "|pre23";
        }
        if ((level & android.content.pm.PermissionInfo.PROTECTION_FLAG_INSTALLER) != 0) {
            protLevel += "|安裝器";
        }
        if ((level & android.content.pm.PermissionInfo.PROTECTION_FLAG_VERIFIER) != 0) {
            protLevel += "|驗證";
        }
        if ((level & android.content.pm.PermissionInfo.PROTECTION_FLAG_PREINSTALLED) != 0) {
            protLevel += "|內建";
        }
        if ((level & android.content.pm.PermissionInfo.PROTECTION_FLAG_SETUP) != 0) {
            protLevel += "|設定";
        }
        if ((level & android.content.pm.PermissionInfo.PROTECTION_FLAG_RUNTIME_ONLY) != 0) {
            protLevel += "|執行";
        }
        return protLevel;
    }

    public static int fixProtectionLevel(int level) {
        if (level == android.content.pm.PermissionInfo.PROTECTION_SIGNATURE_OR_SYSTEM) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                level = android.content.pm.PermissionInfo.PROTECTION_SIGNATURE | android.content.pm.PermissionInfo.PROTECTION_FLAG_PRIVILEGED;
            }
        }
        return level;
    }
}
