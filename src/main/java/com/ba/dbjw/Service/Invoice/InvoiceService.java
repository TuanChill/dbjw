package com.ba.dbjw.Service.Invoice;

import java.util.List;

public interface InvoiceService<T> {
    T getInvoiceByCode(String code);
    List<T> getAllInvoices();
    boolean saveInvoice(T data);
}
