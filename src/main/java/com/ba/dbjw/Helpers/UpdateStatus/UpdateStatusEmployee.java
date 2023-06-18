package com.ba.dbjw.Helpers.UpdateStatus;

public final class UpdateStatusEmployee {

    private UpdateStatusEmployee() {

    }

    private static boolean isCustomerAdded;
    public static boolean isCustomerAdded() {
        return isCustomerAdded;
    }

    public static void setIsCustomerAdded(boolean isCustomer) {
        UpdateStatusEmployee.isCustomerAdded = isCustomer;
    }
}
