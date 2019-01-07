package com.ntp.ntp2.back;

import com.ntp.ntp2.entities.HibernateSetup;
import com.ntp.ntp2.entities.Talep;
import com.ntp.ntp2.entities.Uye;
import com.ntp.ntp2.interfaces.Dao;
import org.hibernate.*;

import java.util.ArrayList;
import java.util.List;

public class TalepDao implements Dao<Talep> {
    private SessionFactory sessionFactory;

    public TalepDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public TalepDao() {
        sessionFactory= HibernateSetup.getSessionFactory();
    }

    @Override
    public void ekle(Talep entity) {
        Transaction tx=null;
        Session session=null;
        try{
            session=sessionFactory.openSession();
            tx=session.beginTransaction();
            session.persist(entity);
            session.getTransaction().commit();
        }catch (HibernateException ex){
            ex.printStackTrace();
            if(tx!=null){session.getTransaction().rollback();}
        }finally {
            if (session!=null){session.close();}
        }
    }

    @Override
    public List tumKayitlariGetir()
    {
        List<Talep> talepListe=null;
        Session session=null;
        try{
            talepListe=new ArrayList<>();
            session=sessionFactory.openSession();
            talepListe=(List<Talep>) session.createCriteria(Talep.class).list();
        }catch(HibernateException ex){
            ex.printStackTrace();
        }finally{
            session.close();
        }
        return talepListe;
    }

    @Override
    public void guncelle(Talep entity) {

    }

    @Override
    public void sil(Talep entity) {

    }

    public List<Talep> uyeIdyeGoreTalepGetir(Uye entity){
        List<Talep> talepListe=null;
        Session session=null;

        try{
            talepListe=new ArrayList<>();
            session=sessionFactory.openSession();
            String hql="SELECT t FROM Talep t WHERE t.uyeId=:entityUyeId";
            Query query=session.createQuery(hql);
            query.setParameter("entityUyeId",entity);
            talepListe=query.list();
        }catch (HibernateException ex){
            ex.printStackTrace();
        }finally {
            if (session!=null){session.close();}
        }
        return talepListe;
    }
}
