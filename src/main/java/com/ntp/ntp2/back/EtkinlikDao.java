package com.ntp.ntp2.back;

import com.ntp.ntp2.entities.Etkinlik;
import com.ntp.ntp2.entities.HibernateSetup;
import com.ntp.ntp2.entities.Uye;
import com.ntp.ntp2.interfaces.Dao;
import org.hibernate.*;
import java.util.ArrayList;
import java.util.List;

public class EtkinlikDao implements Dao<Etkinlik> {
    //Bu sınıf View katmanı (Görüntü katmanı) ile Model katmanı(Veri tabanı) arasında kontrolu(Controller) sağlayan sınıftır.
    private SessionFactory sessionFactory;

    public EtkinlikDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public EtkinlikDao() {
        this.sessionFactory= HibernateSetup.getSessionFactory();
    }

    @Override
    public void ekle(Etkinlik entity) {//parametre olarak girilen etkinlik sınıfını veri tabanına ekler.
        Transaction tx=null;
        Session session=null;
        try{
            session=sessionFactory.openSession();
            tx=session.beginTransaction();
            session.persist(entity);
            session.getTransaction().commit();
        }catch (HibernateException ex){
            ex.printStackTrace();
            if (tx!=null){session.getTransaction().rollback();}
        }finally {
            if (session!=null){session.close();}
        }
    }

    @Override
    public List<Etkinlik> tumKayitlariGetir() {
        List<Etkinlik> etkinlikListe=null;
        Session session=null;
        try{
            session=sessionFactory.openSession();
            etkinlikListe=(List<Etkinlik>) session.createCriteria(Etkinlik.class).list();
        }catch (HibernateException ex){
            ex.printStackTrace();
        }finally {
            if(session!=null){session.close();}
        }

        return etkinlikListe;
    }

    @Override
    public void guncelle(Etkinlik entity) {
        Session session=null;
        Transaction tx=null;
        try{
            session=sessionFactory.openSession();
            tx=session.beginTransaction();
            session.update(entity);
            session.getTransaction().commit();
        }catch (HibernateException ex){
            ex.printStackTrace();
            if(tx!=null){tx.rollback();}
        }finally {
            if(session!=null){session.close();}
        }
    }

    @Override
    public void sil(Etkinlik entity) {
        Session session=null;
        Transaction tx=null;
        try{
            session=sessionFactory.openSession();
            tx=session.beginTransaction();
            session.delete(entity);
            session.getTransaction().commit();
        }catch (HibernateException ex){
            ex.printStackTrace();
            if(tx!=null){tx.rollback();}
        }finally {
            if(session!=null){session.close();}
        }
    }

    public List<Etkinlik> uyeIdyeGoreEtkinlikGetir(Uye entity){
        List<Etkinlik> etkinlikListe=null;
        Session session=null;
        try {
            etkinlikListe=new ArrayList<>();
            session=sessionFactory.openSession();
            String hql="SELECT e FROM Etkinlik e WHERE e.uyeId=:entity";
            Query query=session.createQuery(hql);
            query.setParameter("entity",entity);
            etkinlikListe=query.list();
        }catch (HibernateException ex){
            ex.printStackTrace();
        }finally {
            if(session!=null){session.close();}
        }
        return etkinlikListe;
    }
}
