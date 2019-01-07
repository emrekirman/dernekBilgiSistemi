/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ntp.ntp2.back;

import com.ntp.ntp2.entities.HibernateSetup;
import com.ntp.ntp2.entities.Il;
import com.ntp.ntp2.entities.Ilce;

import java.io.Serializable;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author Emre
 */
public class IlceDao implements Serializable {
    public List<Ilce> ilceListe;
    private SessionFactory sessionFactory;//veri tabanı bağlantısı başlatmak için kullanılır.

    public IlceDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public IlceDao() {
        sessionFactory=HibernateSetup.getSessionFactory();
    }
    
    
    public List<Ilce> ileGoreIlceGetir(Il il){
        List<Ilce> ilceListe=null;
        Session session=null;//veri tabanı bağlantısını başlatıp atamak için kullanılır.
        try{
            session=sessionFactory.openSession();
            Query query=session.createQuery("from Ilce where ilNo=:id");
            query.setParameter("id", il);
            ilceListe=query.list();
        }catch(HibernateException ex){
            ex.printStackTrace();
        }finally{
            if(session!=null)
                session.close();
        }
        return ilceListe;
    }
    
}
