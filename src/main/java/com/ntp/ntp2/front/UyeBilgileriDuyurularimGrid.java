package com.ntp.ntp2.front;

import com.ntp.ntp2.back.DuyuruDao;
import com.ntp.ntp2.entities.Duyuru;
import com.ntp.ntp2.entities.Uye;
import com.vaadin.ui.Grid;
import com.vaadin.ui.PopupView;

import java.util.List;

public class UyeBilgileriDuyurularimGrid {
    private final Grid<Duyuru> grid;
    private List<Duyuru> duyuruListe;
    private Uye duyuruGetirilecekUye;
    //bu sınıf yönetici üyelerin yayınladığı duyuruları tablo şeklinde görmek için oluşturuldu.

    public UyeBilgileriDuyurularimGrid() {
        this.grid=new Grid<>();
    }

    public Grid<Duyuru> gridOlustur(){
        grid.setSizeFull();
        grid.setHeight("100%");
        grid.addColumn(Duyuru::getDuyuruBaslik).setCaption("Duyuru Başlığı");//tablo sütunlarını ayarladık.
        grid.addColumn(Duyuru::getDuyuruIcerik).setCaption("Duyuru İçeriği").setMaximumWidth(300);
        grid.addColumn(Duyuru::getDuyuruTarih).setCaption("Duyuru Tarihi");

        DuyuruDao duyuruDao=new DuyuruDao();
        duyuruListe=duyuruDao.uyeIdyeGoreDuyuruGetir(duyuruGetirilecekUye);//tüm duyuruları listeye atadık.
        grid.setItems(duyuruListe);//duyuruların bulunduğu listeyi tablo içerisine atadık.

        grid.addItemClickListener(itemClick -> {
           UyeBilgileriDuyurularimDuzenlePanel uyeBilgileriDuyurularimDuzenlePanel=new UyeBilgileriDuyurularimDuzenlePanel();//tablo üzerinde kayıda tıklandığında ekranda gözükecek ekranı ürettik.
           uyeBilgileriDuyurularimDuzenlePanel.setIslemYapilacakDuyuru(itemClick.getItem());//tablo üzerinde tıklanan duyuruyu ekrana çıkacak panele atadık.
           uyeBilgileriDuyurularimDuzenlePanel.setUyeBilgileriDuyurularimGrid(this);//silme veya güncelleme işlemi yapıldıktan sonra tablonun yenilenmesi için tablomuzu ekrana çıkan panele atadık.

            PopupView popupView=new PopupView(null,uyeBilgileriDuyurularimDuzenlePanel.panelOLustur());//panelin ekranda gözükmesi için yapılan işlem.
            popupView.setPopupVisible(true);
            popupView.setHideOnMouseOut(false);

            AnaPencere.layout.addComponent(AnaPencere.absoluteLayout);
            AnaPencere.absoluteLayout.addComponent(popupView,"top:-300px;left:50%");
        });
        return grid;
    }

    public Uye getDuyuruGetirilecekUye() {
        return duyuruGetirilecekUye;
    }

    public void setDuyuruGetirilecekUye(Uye duyuruGetirilecekUye) {
        this.duyuruGetirilecekUye = duyuruGetirilecekUye;
    }

    public Grid<Duyuru> getGrid() {
        return grid;
    }
}
