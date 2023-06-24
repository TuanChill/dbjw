package com.ba.dbjw.Helpers.UpdateStatus;

public final class UpdateStatusEmployee {

    private UpdateStatusEmployee() {

    }

    private static boolean isEmployeeAdded;
    public static boolean isEmployeeAdded() {
        return isEmployeeAdded;
    }

    public static void setIsEmployeeAdded(boolean isEmployee) {
        UpdateStatusEmployee.isEmployeeAdded = isEmployee;
    }
}
