package com.ba.dbjw.Helpers.UpdateStatus;

public final class UpdateStatusProduct {

    private UpdateStatusProduct() {

    }

    private static boolean isProductAdded;
    public static boolean isProductAdded() {
        return isProductAdded;
    }

    public static void setIsProductAdded(boolean isProduct) {
        UpdateStatusProduct.isProductAdded = isProduct;
    }
}
