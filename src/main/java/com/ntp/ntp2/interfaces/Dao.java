/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ntp.ntp2.interfaces;

import java.util.List;

/**
 *
 * @author Emre
 */
public interface Dao<T> {
    //bu arayüz Dao sınıflarının içerisinde sürekli tekrarlayan metotlar için oluşturuldu.
    //daha esnek bir yapı katıyor.
    void ekle(T entity);
    List<T> tumKayitlariGetir();
    void guncelle(T entity);
    void sil(T entity);
}
