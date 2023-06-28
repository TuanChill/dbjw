package com.ba.dbjw.Repo.Invoice;

import java.util.List;

public interface InvoiceRepo<T> {
    T getInvoiceByCode(String code);
    List<T> getAllInvoices();
    boolean saveInvoice(T data);
}
