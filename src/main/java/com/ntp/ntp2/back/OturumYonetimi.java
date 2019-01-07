/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ntp.ntp2.back;

import com.ntp.ntp2.entities.Uye;
import com.ntp.ntp2.front.AnaPencere;
import com.ntp.ntp2.front.GirisPanel;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Image;

import java.io.File;

/**
 *
 * @author EmreKirman
 */
public class OturumYonetimi {
    public static Integer uyeId=null;
    public static String uyeAd=null;
    public static String uyeSoyad=null;
    public static boolean girisDurumu=false;
    public static String uyeYetki=null;
    public static Uye uyeSinifi;

    public static void oturumuSonlandir(){
        //Bu sınıf program içerisindeki oturum yönetiminin yürütüldüğü sınıftır. Bu nedenle tüm tanımlamalar static'tir.
        uyeId=null;
        uyeAd=null;
        uyeSoyad=null;
        girisDurumu=false;
        uyeYetki=null;
        uyeSinifi=null;


        GirisPanel girisPanel=new GirisPanel();

        String basepath = VaadinService.getCurrent()
                .getBaseDirectory().getAbsolutePath();

        FileResource resource = new FileResource(new File(basepath +
                "/WEB-INF/dernekLogo.png"));

        Image image = new Image("", resource);
        //image.setWidth("18%");
        image.setWidth("288px");
        AnaPencere.bilesenleriKaldir();

        AnaPencere.bilesenEkle(image);
        AnaPencere.bilesenEkle(girisPanel.girisPanelOlustur());

        AnaPencere.bilesenHizala(image, Alignment.TOP_CENTER);
        AnaPencere.bilesenHizala(girisPanel.getGirisPanel(), Alignment.TOP_CENTER);
    }

    public static void oturumBaslat(Uye entity){
        uyeId=entity.getUyeId();
        uyeAd=entity.getUyeAd();
        uyeSoyad=entity.getUyeSoyad();
        girisDurumu=true;
        uyeYetki=entity.getYetkiNoAd();
        uyeSinifi=entity;
    }
}
