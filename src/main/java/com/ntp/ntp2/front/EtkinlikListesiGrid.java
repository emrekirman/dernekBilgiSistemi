package com.ntp.ntp2.front;

import com.ntp.ntp2.back.EtkinlikDao;
import com.ntp.ntp2.entities.Etkinlik;
import com.vaadin.ui.Grid;
import com.vaadin.ui.PopupView;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EtkinlikListesiGrid {
    private final Grid<Etkinlik> grid;//etkinliklerimizin gözükeceği tablo.
    private List<Etkinlik> etkinlikListe;//etkinliklerin tutulacağı liste.

    public EtkinlikListesiGrid() {
        this.grid = new Grid<>();
    }

    public Grid gridOlustur(){
        etkinlikListe=new ArrayList<>();
        grid.setSizeFull();//tablomuzun büyüklüğünü fulledik.
        grid.setHeight("100%");
        //sütunları eklemek için aşağıdaki komutlar kullanılır.
        grid.addColumn(Etkinlik::getEtkinlikId).setCaption("No");
        grid.addColumn(Etkinlik::getEtkinlikBaslik).setCaption("Başlık");
        grid.addColumn(Etkinlik::getEtkinlikTarih).setCaption("Etkinlik Tarihi");
        grid.addColumn(Etkinlik::getUyeIdUyeAd).setCaption("Ekleyen Üye Adı");
        grid.addColumn(Etkinlik::getUyeIdUyeSoyad).setCaption("Ekleyen Üye Soyadı");
        grid.addColumn(Etkinlik::getSistemGirisTarih).setCaption("Sisteme Eklenme Tarihi");

        EtkinlikDao etkinlikDao=new EtkinlikDao();
        etkinlikListe=etkinlikDao.tumKayitlariGetir();//tüm etkinlikeri liste içerisine atadık.
        grid.setItems(etkinlikListe);//tablo içerisine etkinliklerimizin olduğu listeyi atadık.

        grid.addItemClickListener(itemClick -> {//tablo içerisinde herhangi bir kayıda tıklandığında çalışacak metot.
            EtkinlikDuzenlePanel etkinlikDuzenlePanel=new EtkinlikDuzenlePanel();//katıda tıklandığında karşımıza çıkacak ekran için tasarlanan nesneyi oluşturduk.
            etkinlikDuzenlePanel.setEtkinlikListesiGrid(this);//karşımıza çıkan ekranda güncelleme veya silme işlemi yapıldıktan sonra tablo üzerinde yenileme yapmak için bu atama işlemini yaptık.
            etkinlikDuzenlePanel.setEtkinlik(itemClick.getItem());//güncelleme veya silme işlemine uğrayacak sınıfı karşımaza çıkan ekrana atadık.

            PopupView popupView=new PopupView(null,etkinlikDuzenlePanel.panelOlustur());//nesnenin kayıda tıklandığında karşımıza çıkması için kullandığımız yönetem.
            popupView.setPopupVisible(true);//görünme özelliğini doğru yaptık.
            popupView.setHideOnMouseOut(false);//mouse oynadığında kaybolma özelliğini kapattık.

            etkinlikDuzenlePanel.getEtkinlikBaslik().setValue(itemClick.getItem().getEtkinlikBaslik());//karşımıza gelecek ekrandaki bileşenlere tıklanan kayıtttaki verileri atıyoruz.
            etkinlikDuzenlePanel.getEtkinlikTarih().setValue(LocalDate.parse(itemClick.getItem().getEtkinlikTarih().toString()));
            etkinlikDuzenlePanel.getEtkinlikIcerik().setValue(itemClick.getItem().getEtkinlikIcerik());


            AnaPencere.layout.addComponent(AnaPencere.absoluteLayout);//popup'ı yukarıda göstermek için css kullanmak lazım bunun için absolute layout kullandım.
            //AnaPencere.bilesenleriKaldir metodu tüm herseyi temizlediği için absoluteLayout'u burada ekledim.
            AnaPencere.absoluteLayout.addComponent(popupView,"top:-300px;left:50%");

        });

        return grid;
    }

    public Grid<Etkinlik> getGrid() {
        return grid;
    }
}
