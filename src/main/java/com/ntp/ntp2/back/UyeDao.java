/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ntp.ntp2.back;

import com.ntp.ntp2.entities.Etkinlik;
import com.ntp.ntp2.entities.HibernateSetup;
import com.ntp.ntp2.entities.Uye;
import com.ntp.ntp2.interfaces.Dao;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author Emre
 */
public class UyeDao implements Dao<Uye>{
    private SessionFactory sessionFactory;

    public UyeDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public UyeDao() {
        sessionFactory=HibernateSetup.getSessionFactory();
    }
    
    public List<Uye> tumUyeleriGetir(){
        List<Uye> uyeListe=null;
        Session session=null;
        try{
            session=sessionFactory.openSession();
            uyeListe=(List<Uye>) session.createCriteria(Uye.class).list();
        }catch(HibernateException ex){
            ex.printStackTrace();
        }finally{
            session.close();
        }
        return uyeListe;
    }

    public List<Uye> idSifreyeGoreUyeGetir(Uye entity){
        List<Uye> uyeListesi=null;
        Session session=null;
        try{
            uyeListesi=new ArrayList<>();
            session=sessionFactory.openSession();
            String hql="SELECT u FROM Uye u WHERE u.uyeId=:entityUyeId AND u.uyeParola=:entityUyeParola";
            Query query=session.createQuery(hql);
            query.setParameter("entityUyeId", entity.getUyeId());
            query.setParameter("entityUyeParola", entity.getUyeParola());
            uyeListesi=query.list();
            session.close();
        }catch(HibernateException ex){
            ex.printStackTrace();
            
        }
        return uyeListesi; 
    }
    
    public void uyeGuncelle(Uye entity){
        Session session=null;
        Transaction tx=null;
        try{
            session=sessionFactory.openSession();
            tx=session.beginTransaction();
            session.update(entity);
            session.getTransaction().commit();
        }catch(HibernateException ex){
            ex.printStackTrace();
            if(tx!=null){tx.rollback();}
        }finally{
            if(session!=null){session.close();}
        }
    }
    
    public void uyeSil(Uye entity){
        Session session=null;
        Transaction tx=null;
        try{
            session=sessionFactory.openSession();
            tx=session.beginTransaction();
            session.delete(entity);
            session.getTransaction().commit();
        }catch(HibernateException ex){
            ex.printStackTrace();
            if(tx!=null){tx.rollback();}
        }finally{
            if(session!=null){session.close();}
        }
    }

    @Override
    public void ekle(Uye entity) {
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

    @Override
    public List<Uye> tumKayitlariGetir() {
        return null;
    }

    @Override
    public void guncelle(Uye entity) {
        Session session=null;
        Transaction tx=null;
        try{
            session=sessionFactory.openSession();
            tx=session.beginTransaction();
            session.update(entity);
            session.getTransaction().commit();
        }catch(HibernateException ex){
            ex.printStackTrace();
            if(tx!=null){tx.rollback();}
        }finally{
            if(session!=null){session.close();}
        }
    }

    @Override
    public void sil(Uye entity) {

    }
}
