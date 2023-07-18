package com.ba.dbjw.Service.Invoice;

import com.ba.dbjw.Entity.Invoice.Invoice;
import com.ba.dbjw.Repo.Invoice.InvoiceRepo;
import com.ba.dbjw.Repo.Invoice.InvoiceRepoImpl;

import java.util.List;

public class InvoiceServiceImpl implements InvoiceService<Invoice> {

    private final InvoiceRepo<Invoice> invoiceRepo = new InvoiceRepoImpl();

    @Override
    public Invoice getInvoiceByCode(String code) {
        if(code != null) {
            return invoiceRepo.getInvoiceByCode(code);
        }
        return null;
    }

    @Override
    public List<Invoice> getAllInvoices() {
        return invoiceRepo.getAllInvoices();
    }

    @Override
    public boolean saveInvoice(Invoice data) {
        if(data != null) {
            return invoiceRepo.saveInvoice(data);
        }
        return false;
    }
}
