package com.ntp.ntp2.entities;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Hibernate oturum olusturma
 *
 * @author Emre Kirman
 * @since 1.0
 */
public class HibernateSetup {
    //Hibernate konfigüre verilerini içeren xml dosyası aracılığı ile veri tabanı bağlantısı sağlar.
    private static final SessionFactory sessionFactory;

    static {
        try {
            sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        } catch (Exception e) {
            System.err.println("Session Factory Olusturma Hatası." + e);
            throw new ExceptionInInitializerError(e);
        }
    }


    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

}
