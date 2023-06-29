package com.ba.dbjw.Helpers.CurrentEntity;

public class CurrentInvoice {
    private static String code;

    private CurrentInvoice() {
    }

    public static String getCurrentInvoice() {
        return code;
    }

    public static void setCurrentInvoice(String codeInput) {
        code = codeInput;
    }
}
