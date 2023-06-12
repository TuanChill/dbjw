package com.ba.dbjw.Helpers;

public final class UpdateStatus {

    private UpdateStatus() {

    }

    private static boolean isProductAdded;
    public static boolean isProductAdded() {
        return isProductAdded;
    }

    public static void setIsProductAdded(boolean isProduct) {
        UpdateStatus.isProductAdded = isProduct;
    }
}
