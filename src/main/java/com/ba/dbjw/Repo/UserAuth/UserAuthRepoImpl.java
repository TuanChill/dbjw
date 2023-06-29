package com.ba.dbjw.Repo.UserAuth;

import com.ba.dbjw.Entity.UserAuth.UserAuth;
import com.ba.dbjw.Utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UserAuthRepoImpl implements UserAuthRepo<UserAuth> {

    @Override
    public void saveUser(UserAuth user) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }


    @Override
    public UserAuth findByUserName(String userName) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try (session) {
            return session.find(UserAuth.class, userName);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
}
