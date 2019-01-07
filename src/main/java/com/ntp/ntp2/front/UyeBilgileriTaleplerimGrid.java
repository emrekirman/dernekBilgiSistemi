package com.ntp.ntp2.front;

import com.ntp.ntp2.back.TalepDao;
import com.ntp.ntp2.entities.Talep;
import com.ntp.ntp2.entities.Uye;
import com.vaadin.ui.Grid;
import com.vaadin.ui.PopupView;

import java.util.ArrayList;
import java.util.List;

public class UyeBilgileriTaleplerimGrid {
    private final Grid<Talep> grid;
    private List<Talep> talepListe;
    private Uye talepGetirilecekUye;//Talepleri listelecek üye. Oturumu açan üye

    public UyeBilgileriTaleplerimGrid() {
        grid=new Grid<>();
    }

    public Grid<Talep> gridOlustur(){
        TalepDao talepDao=new TalepDao();
        talepListe=talepDao.uyeIdyeGoreTalepGetir(talepGetirilecekUye);//Oturumu açan üyenin taleplerini bu listeye attık.

        grid.setSizeFull();
        grid.setHeight("100%");
        grid.setItems(talepListe);
        grid.addColumn(Talep::getTalepBaslik).setCaption("Başlık");
        grid.addColumn(Talep::getTalepTarih).setCaption("Gönderilen Tarih");
        grid.addColumn(Talep::getTalepIcerik).setCaption("Talep İçeriği");

        grid.addItemClickListener(itemClick ->{
            UyeBilgileriTalepDuzenlePanel uyeBilgileriTalepDuzenlePanel=new UyeBilgileriTalepDuzenlePanel();
            uyeBilgileriTalepDuzenlePanel.setIslemYapilacakTalep(itemClick.getItem());

            PopupView popupView=new PopupView(null,uyeBilgileriTalepDuzenlePanel.panelOlustur());
            popupView.setPopupVisible(true);
            popupView.setHideOnMouseOut(false);

            AnaPencere.bilesenEkle(AnaPencere.absoluteLayout);
            AnaPencere.absoluteLayout.addComponent(popupView,"top:-300px;left:50%");
        });
        return grid;
    }

    public Grid<Talep> getGrid() {
        return grid;
    }

    public List<Talep> getTalepListe() {
        return talepListe;
    }

    public void setTalepListe(List<Talep> talepListe) {
        this.talepListe = talepListe;
    }

    public Uye getTalepGetirilecekUye() {
        return talepGetirilecekUye;
    }

    public void setTalepGetirilecekUye(Uye talepGetirilecekUye) {
        this.talepGetirilecekUye = talepGetirilecekUye;
    }
}
