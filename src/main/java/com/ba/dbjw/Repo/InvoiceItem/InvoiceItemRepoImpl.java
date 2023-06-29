package com.ba.dbjw.Repo.InvoiceItem;

import com.ba.dbjw.Entity.Invoice.InvoiceItem;
import com.ba.dbjw.Utils.HibernateUtil;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class InvoiceItemRepoImpl implements InvoiceItemRepo<InvoiceItem> {
    @Override
    public List<InvoiceItem> getAllInvoiceItemByCode(String code) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<InvoiceItem> query = builder.createQuery(InvoiceItem.class);
            Root<InvoiceItem> root = query.from(InvoiceItem.class);

            query.select(root)
                    .where(builder.like(builder.lower(root.get("invoice").get("code")), "%" + code.toUpperCase() + "%"));
            return session.createQuery(query).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
