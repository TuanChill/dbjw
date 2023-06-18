package com.ba.dbjw.Helpers.UpdateStatus;

public final class UpdateStatusCustomer {

    private UpdateStatusCustomer() {

    }

    private static boolean isCustomerAdded;
    public static boolean isCustomerAdded() {
        return isCustomerAdded;
    }

    public static void setIsCustomerAdded(boolean isCustomer) {
        UpdateStatusCustomer.isCustomerAdded = isCustomer;
    }
}
