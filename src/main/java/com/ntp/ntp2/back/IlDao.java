/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ntp.ntp2.back;

import com.ntp.ntp2.entities.HibernateSetup;
import com.ntp.ntp2.entities.Il;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
/**
 *
 * @author Emre
 */
public class IlDao {
    public EntityManagerFactory emf=null;
    public List<Il> ilListe;
    private SessionFactory sessionFactory;

    public IlDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public IlDao() {
        sessionFactory=HibernateSetup.getSessionFactory();
    }
    
    
    
    public EntityManager getEntityManager(){
        if(this.emf==null){
            this.emf=Persistence.createEntityManagerFactory("hbnt");
        }
        EntityManager em=emf.createEntityManager();
        return em;
    }
    
    public List<Il> tumIlleriGetir(){
        List<Il> ilListe=null;
        Session session=null;
        try{
            session=sessionFactory.openSession();
            ilListe=(List<Il>) session.createCriteria(Il.class).list();
        }catch(HibernateException ex){
            ex.printStackTrace();
        }finally{
            if(session!=null)
                session.close();
        }
        return ilListe;
    }
    
    public void tumIlleriGetir(Il il){
        try{
            EntityManager em=getEntityManager();
            String sorgu="SELECT i FROM Il i";
            Query q=em.createQuery(sorgu);
            this.ilListe=q.getResultList();
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }

    public List<Il> getIlListe() {
        return ilListe;
    }

    public void setIlListe(List<Il> ilListe) {
        this.ilListe = ilListe;
    }
    
    
}
