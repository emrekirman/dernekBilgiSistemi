/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ntp.ntp2.front;

import com.ntp.ntp2.back.IlceDao;
import com.ntp.ntp2.back.UyeDao;
import com.ntp.ntp2.entities.Il;
import com.ntp.ntp2.entities.Ilce;
import com.ntp.ntp2.entities.Uye;
import com.vaadin.ui.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author emrekirman
 */
public class KisiListesiGrid implements Serializable {
    private final Grid<Uye> grid;
    private List<Uye> kisiler;
    private Il il;
    private Ilce ilce;
    private Uye duzenlePanelUye;
    //bu sınıf üye listesinin bulunduğu tabloyu tasarlamak için oluşturuldu.
    public KisiListesiGrid() {
        grid=new Grid<>();
    }
    
    public Grid gridOlustur(){
        kisiler=new ArrayList<>();//üyelerin tutulacağı listeyi oluşturduk.
        grid.setSizeFull();//tablonun boyutunu ekran ölçeğine göre maksimuma getirdik.
        grid.setHeight("100%");
        grid.setItems(kisiler);//tablo içerisine üyelerin tutulduğu listeyi atadık.
        grid.addColumn(Uye::getUyeId).setCaption("No");//Tabloda gözükecek sütunları ekledik.
        grid.addColumn(Uye::getUyeAd).setCaption("Ad");
        grid.addColumn(Uye::getUyeSoyad).setCaption("Soyad");
        grid.addColumn(Uye::getUyeDogumTarih).setCaption("Doğum Tarih");
        grid.addColumn(Uye::getUyeTelNo).setCaption("Telefon Numarası");
        grid.addColumn(Uye::getIlNoAd).setCaption("İl");
        grid.addColumn(Uye::getIlceNoAd).setCaption("İlçe");
        grid.addColumn(Uye::getCinsiyetNoAd).setCaption("Cinsiyet");
        grid.addColumn(Uye::getYetkiNoAd).setCaption("Yetki");
        
        
        UyeDao uyeDao=new UyeDao();
        kisiler=uyeDao.tumUyeleriGetir();//veri tabanımızdaki tüm üyeleri listeye atadık.
        
        grid.setItems(kisiler);//listeyi tablo içerisinde gözükmesi için atıyoruz.
        
        
        grid.addItemClickListener(itemClick -> {//tablo üzerinde herhangi kayıta tıklandığında çalışacak metot.
            UyeDuzenlePanel uyeDuzenlePanel=new UyeDuzenlePanel();
            uyeDuzenlePanel.getPanel().setCaption("Üye Sıra No: "+itemClick.getItem().getUyeId().toString());
            uyeDuzenlePanel.setKisiListesiGrid(this);//kayıda tıkladığımızda karşımaza çıkan detaylı bilgi ekranında kullanıdığımız sınıfa tablo sınıfını atıyoruz.
            uyeDuzenlePanel.setKullaniciYetki(itemClick.getItem().getYetkiNo());//kullanıcı yetkisi sabit kalacağı için kayıtla gelen kullanıcının yetkisinide bilgi ekranına atıyoruz.
            
            PopupView uyeListesiPopup=new PopupView(null,uyeDuzenlePanel.panelOlustur());//bilgi ekranının karşımıza çıkması için gerekli işlemleri yapıyoruz.
            uyeListesiPopup.setPopupVisible(true);
            uyeListesiPopup.setHideOnMouseOut(false);
            
            uyeDuzenlePanel.getKisiAd().setValue(itemClick.getItem().getUyeAd());//paneldeki bileşenlerin değerlerini değiştiriyoruz.
            uyeDuzenlePanel.getKisiSoyad().setValue(itemClick.getItem().getUyeSoyad());
            uyeDuzenlePanel.getKisiDogumTarih().setValue(LocalDate.parse(itemClick.getItem().getUyeDogumTarih().toString()));
            uyeDuzenlePanel.getKisiTelNumara().setValue(itemClick.getItem().getUyeTelNo());
            uyeDuzenlePanel.getKisiEpostaAdres().setValue(itemClick.getItem().getUyeEposta());
            uyeDuzenlePanel.getRadioGroup().setSelectedItem(itemClick.getItem().getCinsiyetNo());
            uyeDuzenlePanel.getIl().setValue(itemClick.getItem().getIlNo());
            uyeDuzenlePanel.getIlce().setValue(itemClick.getItem().getIlceNo());
            uyeDuzenlePanel.getMahalle().setValue(itemClick.getItem().getMahalleNo().getMahalleAd());
            
            duzenlePanelUye=itemClick.getItem();/*Bu değişken popup penceresinde update işlemi yaparken kişinin parolasına ve ıdsini öğrenme 
            imkanımızı sağlar.*/
            
            uyeDuzenlePanel.getIlce().addSelectionListener(listener->{//popup panelindeki ilce combobox'ının seçilme olayını tanımladık.
                IlceDao ilceDao=new IlceDao();
                ArrayList<Ilce> ilceListe=(ArrayList<Ilce>) ilceDao.ileGoreIlceGetir(il);
                uyeDuzenlePanel.getIlce().setItems(ilceListe);
            });
            
            AnaPencere.layout.addComponent(AnaPencere.absoluteLayout);//popup'ı yukarıda göstermek için css kullanmak lazım bunun için absolute layout kullandım.
            //AnaPencere.bilesenleriKaldir metodu tüm herseyi temizlediği için absoluteLayout'u burada ekledim.
            AnaPencere.absoluteLayout.addComponent(uyeListesiPopup,"top:-300px;left:50%");
        });
        
        
        return grid;
    }

    public List<Uye> getKisiler() {
        return kisiler;
    }

    public void setKisiler(List<Uye> kisiler) {
        this.kisiler = kisiler;
    }

    public Grid<Uye> getGrid() {
        return grid;
    }

    public Uye getDuzenlePanelUye() {
        return duzenlePanelUye;
    }

    public void setDuzenlePanelUye(Uye duzenlePanelUye) {
        this.duzenlePanelUye = duzenlePanelUye;
    }
}
