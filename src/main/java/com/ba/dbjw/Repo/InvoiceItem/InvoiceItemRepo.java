package com.ba.dbjw.Repo.InvoiceItem;


import java.util.List;

public interface InvoiceItemRepo<T> {
    List<T> getAllInvoiceItemByCode(String code);
}
