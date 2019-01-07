/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ntp.ntp2.back;

import com.ntp.ntp2.entities.HibernateSetup;
import com.ntp.ntp2.entities.Mahalle;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author Emre
 */
public class MahalleDao {
    private SessionFactory sessionFactory;

    public MahalleDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public MahalleDao() {
        sessionFactory=HibernateSetup.getSessionFactory();
    }
    
    public void mahalleEkle(Mahalle entity){
        Transaction transaction=null;
        Session session=null;
        try{
            session=sessionFactory.openSession();
            transaction=session.beginTransaction();
            session.persist(entity);
            session.getTransaction().commit();
        }catch(HibernateException ex){
            ex.printStackTrace();
            if(transaction!=null){
                session.getTransaction().rollback();
            }
        }finally{
            if(session!=null){
                session.close();
            }
        }
    }
    
}
