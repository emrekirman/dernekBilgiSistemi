package com.ntp.ntp2.front;

import com.ntp.ntp2.back.TalepDao;
import com.ntp.ntp2.entities.Talep;
import com.vaadin.ui.Grid;
import com.vaadin.ui.PopupView;
import java.util.List;

public class TalepListesiGrid {
    private final Grid<Talep> grid;
    private List<Talep> talepListe;
    //bu sınıf tüm taleplerin listelendiği bir tablo oluşturmak için tasarlandı.

    public TalepListesiGrid() {
        grid=new Grid<>();
    }

    public Grid<Talep> gridOlustur(){
        TalepDao talepDao=new TalepDao();
        talepListe=talepDao.tumKayitlariGetir();//tüm talepleri veri tabanından çekerek bir liste içerisine attık.


        grid.setSizeFull();//ekran ölçülerine göre tablonun büyüklüğüünü tam yaptık.
        grid.setHeight("100%");
        grid.setItems(talepListe);//tablo içerisinde görüntülenecek verileri atadık.
        grid.addColumn(Talep::getTalepId).setCaption("No");//tablodaki sütunları ayarladık.
        grid.addColumn(Talep::getTalepBaslik).setCaption("Talep Başlığı");
        grid.addColumn(Talep::getTalepIcerik).setCaption("Talep İçerik");
        grid.addColumn(Talep::getTalepTarih).setCaption("Talep Tarih");
        grid.addColumn(Talep::getUyeAdSoyad).setCaption("Talep Eden Kişi");

        grid.addItemClickListener(itemClick -> {//tablo üzerinde bir kayıda tıklandığında çalışacak metot.
            TalepDuzenlePanel talepDuzenlePanel=new TalepDuzenlePanel();
            talepDuzenlePanel.setIslemYapilacakTalep(itemClick.getItem());//çıkan ekran üzerinden seçili kayıt ile işlem yapmak için bu atamayı yaptık.

            PopupView popupView=new PopupView(null,talepDuzenlePanel.panelOlustur());//kayıda tıklandığında düzenleme ekranın çıkması için bu işlemi yaptık.
            popupView.setPopupVisible(true);
            popupView.setHideOnMouseOut(false);


            AnaPencere.layout.addComponent(AnaPencere.absoluteLayout);//popup'ı yukarıda göstermek için css kullanmak lazım bunun için absolute layout kullandım.
            //AnaPencere.bilesenleriKaldir metodu tüm herseyi temizlediği için absoluteLayout'u burada ekledim.
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
}
