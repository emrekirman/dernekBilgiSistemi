/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ntp.ntp2.front;

import com.ntp.ntp2.back.OturumYonetimi;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author emreKirman
 */
public class YoneticiIslemleri {
    private final TabSheet tabSheet;
    //bu sınıf tüm yönetici işlemlerini sekmeler halinde tek bir sayfa içerisinde tutmak için tasarlandı.
    //yöneticinin gerçekleştirebileceği tüm işlemler bu sınıftan sağlanır.

    public YoneticiIslemleri() {
        tabSheet=new TabSheet();
    }
    
    public TabSheet tabSheetOlustur(){
        tabSheet.setSizeFull();
        tabSheet.addStyleName(ValoTheme.TABSHEET_FRAMED);
        
        VerticalLayout tab1EklePanel=new VerticalLayout();
        VerticalLayout tab2KisiListesi=new VerticalLayout();
        VerticalLayout tab4EtkinlikDuzenle=new VerticalLayout();
        VerticalLayout tab3EtkinlikEkle=new VerticalLayout();
        VerticalLayout tab5TalepListesi=new VerticalLayout();
        VerticalLayout tab6DuyuruEklePanel=new VerticalLayout();

        tab2KisiListesi.setMargin(false);
        tab4EtkinlikDuzenle.setMargin(false);
        tab5TalepListesi.setMargin(false);
        
        EklePanel eklePanel=new EklePanel("Üye Ekle");//üye ekleme paneli
        KisiListesiGrid kisiListesiGrid=new KisiListesiGrid();//tüm üyelerin listelendiği tablo
        eklePanel.setKisiListesiGrid(kisiListesiGrid);//üye ekleme sayfasına üye listesi tablomuzu ekledik çünkü üye ekleme işlemi yapıldıktan sonra tablo güncellenecek.
        eklePanel.setYoneticiIslemleriTabSheet(tabSheet);//sekme geçişleri sağlayan bileşenimizi panele ekledik çünkü üye ekleme işleminin ardından üye listesi sekmesine geçilecek.
        
        EtkinlikEkle etkinlikEkle=new EtkinlikEkle();//etkinlik ekleme paneli
        EtkinlikListesiGrid etkinlikListesiGrid=new EtkinlikListesiGrid();//etkinlik listesi tablosu
        etkinlikEkle.setEtkinlikListesiGrid(etkinlikListesiGrid);//etkinlik eklemesi yapıldıktan sonra etkinlik listesi tablosunu yenilemek için
        etkinlikEkle.setYoneticiIslemleriTabSheet(tabSheet);//etkinlikEkle sınıfında kayıt eklendikten sonra etkinlik listesi sayfasına geçiş için oluşturuldu.

        TalepListesiGrid talepListesiGrid=new TalepListesiGrid();//talep tablosu

        DuyuruEklePanel duyuruEklePanel=new DuyuruEklePanel();//duyuru ekleme paneli
        duyuruEklePanel.setDuyuruYapacakUye(OturumYonetimi.uyeSinifi);//oturum açan üyeyi panele ekledik çünkü duyuru yapan üyenin kim olduğuna ihtiyacımız var.
        
        tab1EklePanel.addComponent(eklePanel.panelOlustur());//bileşenlerimizi sekme geçişleri yapmamızı sağlayan TabSheet bileşenimize ekliyoruz.
        tab1EklePanel.setComponentAlignment(eklePanel.panelOlustur(), Alignment.TOP_CENTER);

        tab2KisiListesi.addComponent(kisiListesiGrid.gridOlustur());

        tab3EtkinlikEkle.addComponent(etkinlikEkle.etkinlikEklePanelOlustur());
        tab3EtkinlikEkle.setComponentAlignment(etkinlikEkle.getEtkinlikEklePanel(), Alignment.TOP_CENTER);

        tab4EtkinlikDuzenle.addComponent(etkinlikListesiGrid.gridOlustur());

        tab5TalepListesi.addComponent(talepListesiGrid.gridOlustur());

        tab6DuyuruEklePanel.addComponent(duyuruEklePanel.panelOlustur());
        tab6DuyuruEklePanel.setComponentAlignment(duyuruEklePanel.getPanel(),Alignment.TOP_CENTER);
        
        
        tabSheet.addTab(tab1EklePanel, "Üye Ekle",VaadinIcons.PLUS);
        tabSheet.addTab(tab2KisiListesi,"Üye Listesi",VaadinIcons.LIST);
        tabSheet.addTab(tab3EtkinlikEkle, "Etkinlik Ekle", VaadinIcons.PLUS);
        tabSheet.addTab(tab4EtkinlikDuzenle, "Etkinlik Listesi",VaadinIcons.LIST);
        tabSheet.addTab(tab5TalepListesi, "Talep/Şikayet Listesi",VaadinIcons.LIST);
        tabSheet.addTab(tab6DuyuruEklePanel, "Duyuru Ekle",VaadinIcons.PLUS);
        
        return tabSheet;
    }

    public TabSheet getTabSheet() {
        return tabSheet;
    }   
}
