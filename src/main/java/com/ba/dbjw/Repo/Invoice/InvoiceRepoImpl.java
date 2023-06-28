package com.ba.dbjw.Repo.Invoice;

import com.ba.dbjw.Entity.Invoice.Invoice;
import com.ba.dbjw.Utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import java.util.ArrayList;
import java.util.List;

public class InvoiceRepoImpl implements InvoiceRepo<Invoice> {
    @Override
    public Invoice getInvoiceByCode(String code) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.find(Invoice.class, code);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Invoice> getAllInvoices() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Invoice", Invoice.class).list();
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public boolean saveInvoice(Invoice data) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(data);
            transaction.commit();
            return transaction.getStatus() == TransactionStatus.COMMITTED;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
        return false;
    }
}
