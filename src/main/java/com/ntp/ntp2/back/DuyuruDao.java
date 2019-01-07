package com.ntp.ntp2.back;

import com.ntp.ntp2.entities.Duyuru;
import com.ntp.ntp2.entities.HibernateSetup;
import com.ntp.ntp2.entities.Uye;
import com.ntp.ntp2.interfaces.Dao;
import org.hibernate.*;

import java.util.ArrayList;
import java.util.List;

public class DuyuruDao implements Dao<Duyuru> {
    //Bu sınıf View katmanı (Görüntü katmanı) ile Model katmanı(Veri tabanı) arasında kontrolu(Controller) sağlayan sınıftır.
    private SessionFactory sessionFactory;

    public DuyuruDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public DuyuruDao() {
        this.sessionFactory= HibernateSetup.getSessionFactory();
    }

    @Override
    public void ekle(Duyuru entity) {//parametre olarak girilen duyuru sınıfını veri tabanına ekler.
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
            if(session!=null){session.close();}
        }
    }

    @Override
    public List<Duyuru> tumKayitlariGetir() {
        List<Duyuru> duyuruListe=null;
        Session session=null;
        try{
            session=sessionFactory.openSession();
            duyuruListe=(List<Duyuru>) session.createCriteria(Duyuru.class).list();
        }catch (HibernateException ex){
            ex.printStackTrace();
        }finally {
            if(session!=null){session.close();}
        }
        return duyuruListe;
    }

    @Override
    public void guncelle(Duyuru entity) {
        Session session=null;
        Transaction tx=null;
        try{
            session=sessionFactory.openSession();
            tx=session.beginTransaction();
            session.update(entity);
            session.getTransaction().commit();
        }catch (HibernateException ex){
            ex.printStackTrace();
            if (tx!=null){session.getTransaction().rollback();}
        }finally {
            if(session!=null){session.close();}
        }
    }

    @Override
    public void sil(Duyuru entity) {
        Session session=null;
        Transaction tx=null;
        try {
            session=sessionFactory.openSession();
            tx=session.beginTransaction();
            session.delete(entity);
            session.getTransaction().commit();
        }catch (HibernateException ex){
            ex.printStackTrace();
            if(tx!=null){session.getTransaction().rollback();}
        }finally {
            if(session!=null){session.close();}
        }

    }

    public List<Duyuru> uyeIdyeGoreDuyuruGetir(Uye entity){//özel sorgu yazmak için Hibernat'in kendi içerisindeki Query dilini bilmek gerekir.
        List<Duyuru> duyuruListe=null;
        Session session=null;
        try{
            session=sessionFactory.openSession();
            duyuruListe=new ArrayList<>();
            String hql="SELECT d FROM Duyuru d WHERE d.uyeId=:entity ORDER BY uyeId DESC";
            Query query=session.createQuery(hql);
            query.setParameter("entity",entity);
            duyuruListe=query.list();
        }catch (HibernateException ex){
            ex.printStackTrace();
        }finally {
            if (session!=null){session.close();}
        }
        return duyuruListe;
    }
}
