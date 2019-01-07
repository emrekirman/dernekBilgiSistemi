package com.ntp.ntp2.front;

import com.ntp.ntp2.back.EtkinlikDao;
import com.ntp.ntp2.entities.Etkinlik;
import com.ntp.ntp2.entities.Uye;
import com.vaadin.ui.Grid;
import com.vaadin.ui.PopupView;

import java.util.List;

public class UyeBilgileriEtkinlikListesiGrid {
    private final Grid<Etkinlik> grid;
    private List<Etkinlik> etkinlikListe;
    private Uye etkinlikGetirilecekUye;//bu değişkene oturum açan üye sınfı atanır çünkü bu üyeye ait etkinlikler listelenir.
    //bu sınıf üye bilgileri sekmesi altında yöneticilerin üyelerin kendi etkinliklerini görmesi amacıyla yapılmıştır.

    public UyeBilgileriEtkinlikListesiGrid() {
        grid=new Grid<>();
    }

    public Grid<Etkinlik> gridOlustur(){
        EtkinlikDao etkinlikDao=new EtkinlikDao();
        etkinlikListe=etkinlikDao.uyeIdyeGoreEtkinlikGetir(etkinlikGetirilecekUye);//üye varlığımıza göre içindeki etkinlikleri listeye attık.

        grid.setSizeFull();
        grid.setHeight("100%");
        grid.setItems(etkinlikListe);
        grid.addColumn(Etkinlik::getEtkinlikBaslik).setCaption("Başlık");
        grid.addColumn(Etkinlik::getEtkinlikTarih).setCaption("Tarih");
        grid.addColumn(Etkinlik::getEtkinlikIcerik).setCaption("İçerik");
        grid.addColumn(Etkinlik::getSistemGirisTarih).setCaption("Sisteme Girme Tarihi");

        grid.addItemClickListener(itemClick -> {
            UyeBilgileriEtkinlikDuzenlePanel uyeBilgileriEtkinlikDuzenlePanel=new UyeBilgileriEtkinlikDuzenlePanel();
            uyeBilgileriEtkinlikDuzenlePanel.setIslemYapilacakEtkinik(itemClick.getItem());
            uyeBilgileriEtkinlikDuzenlePanel.setUyeBilgileriEtkinlikListesiGrid(this);

            PopupView popupView=new PopupView(null,uyeBilgileriEtkinlikDuzenlePanel.panelOlustur());
            popupView.setPopupVisible(true);
            popupView.setHideOnMouseOut(false);

            AnaPencere.bilesenEkle(AnaPencere.absoluteLayout);
            AnaPencere.absoluteLayout.addComponent(popupView,"top:-300px;left:50%");
        });
        return grid;
    }

    public Grid<Etkinlik> getGrid() {
        return grid;
    }

    public List<Etkinlik> getEtkinlikListe() {
        return etkinlikListe;
    }

    public void setEtkinlikListe(List<Etkinlik> etkinlikListe) {
        this.etkinlikListe = etkinlikListe;
    }

    public Uye getEtkinlikGetirilecekUye() {
        return etkinlikGetirilecekUye;
    }

    public void setEtkinlikGetirilecekUye(Uye etkinlikGetirilecekUye) {
        this.etkinlikGetirilecekUye = etkinlikGetirilecekUye;
    }
}
