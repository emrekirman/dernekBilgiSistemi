package com.ntp.ntp2.front;


import com.ntp.ntp2.back.OturumYonetimi;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.*;

import java.io.File;
import java.io.Serializable;

/**
 *
 * @author EmreKirman
 */
public class MenuNavigasyon implements Serializable {
    private final MenuBar menuBar;
    private Window window;
    //Bu sınıf menü üzerinden modüllerin ekrana getirilmesi ve kaldırılması işlerini halletmek için oluşturuldu. Kısacası sayfa yönlendirme işlerini halletmekte denebilir.

    public MenuNavigasyon() {
        menuBar=new MenuBar();
    }
    
    public MenuBar menuOlustur(){
        menuBar.setSizeFull();
        menuBar.setResponsive(true);
        
        MenuBar.Command yonlendir=menuYonlendir();
        
        menuBar.addItem("Anasayfa",VaadinIcons.HOME,yonlendir);
        
        MenuBar.MenuItem etkinlik;
        etkinlik=menuBar.addItem("Etkinlik",VaadinIcons.CALENDAR_CLOCK,null);//etkinlik adında bir menü elemanı oluşturduk.
        etkinlik.addItem("Etkinlik Listesi",VaadinIcons.LIST,yonlendir);
        
        if(OturumYonetimi.girisDurumu && OturumYonetimi.uyeYetki.equals("Standart")){//oturum açan üyenin yetkisine göre işlemler yaparak sayfa erişim kısıtlamaları oluşturduk.
            MenuBar.MenuItem talepSikayet;
            talepSikayet=menuBar.addItem("Talepte Bulun",VaadinIcons.PLUS,yonlendir);
        }
        if(OturumYonetimi.girisDurumu && OturumYonetimi.uyeYetki.equals("Yönetici")){
            MenuBar.MenuItem uye;
            uye = menuBar.addItem("Yönetici İşlemleri",VaadinIcons.COG,yonlendir);
            
        }
        if(!OturumYonetimi.girisDurumu){
            menuBar.addItem("Giriş Yap",VaadinIcons.SIGN_IN,yonlendir);
        }

        if(OturumYonetimi.girisDurumu){
            MenuBar.MenuItem kullaniciIslemleri;
            kullaniciIslemleri=menuBar.addItem(OturumYonetimi.uyeAd+" "+OturumYonetimi.uyeSoyad,VaadinIcons.USER,null);
            kullaniciIslemleri.addItem("Üye Bilgileri",VaadinIcons.USER_CARD,yonlendir);
            if (OturumYonetimi.uyeYetki.equals("Standart")){
                kullaniciIslemleri.addItem("Taleplerim",VaadinIcons.LIST,yonlendir);
            }
            if(OturumYonetimi.uyeYetki.equals("Yönetici")){
                kullaniciIslemleri.addItem("Etkinlik Listem",VaadinIcons.LIST,yonlendir);
                kullaniciIslemleri.addItem("Duyurularım",VaadinIcons.LIST,yonlendir);
            }
            kullaniciIslemleri.addSeparator();
            kullaniciIslemleri.addItem("Çıkış Yap",VaadinIcons.SIGN_OUT,yonlendir);
        }

        return menuBar;
    }

    private MenuBar.Command menuYonlendir() {
        MenuBar.Command yonlendir=(MenuBar.MenuItem selectedItem) -> {
            switch(selectedItem.getText()){//seçilen menü elemanının içerdiği yazıya göre yönlendirme işlemleri yaptık.
                case("Anasayfa"):
                    DuyuruPanel duyuruPanel=new DuyuruPanel();
                    AnaPencere.bilesenleriKaldir();
                    AnaPencere.bilesenEkle(duyuruPanel.accordionOlustur());
                    AnaPencere.bilesenHizala(duyuruPanel.getAccordion(),Alignment.TOP_CENTER);
                    break;
                case("Giriş Yap"):
                    GirisPanel girisPanel=new GirisPanel();
                    
                    String basepath = VaadinService.getCurrent()
                  .getBaseDirectory().getAbsolutePath();
                    
                    FileResource resource = new FileResource(new File(basepath +
                        "/WEB-INF/dernekLogo.png"));
                    
                    Image image = new Image("", resource);
                    image.setWidth("288px");
                    AnaPencere.bilesenleriKaldir();
                    
                    AnaPencere.bilesenEkle(image);
                    AnaPencere.bilesenEkle(girisPanel.girisPanelOlustur());
                    
                    AnaPencere.bilesenHizala(image, Alignment.TOP_CENTER);
                    AnaPencere.bilesenHizala(girisPanel.getGirisPanel(), Alignment.TOP_CENTER);
                    break;
                case("Yönetici İşlemleri"):
                    YoneticiIslemleri uyeIslemleri=new YoneticiIslemleri();
                    AnaPencere.bilesenleriKaldir();
                    AnaPencere.bilesenEkle(uyeIslemleri.tabSheetOlustur());
                    break;
                case("Etkinlik Listesi"):
                    EtkinlikListesi etkinlikListesi=new EtkinlikListesi();
                    AnaPencere.bilesenleriKaldir();
                    AnaPencere.bilesenEkle(etkinlikListesi.etkinlikListesiOlustur());
                    AnaPencere.bilesenHizala(etkinlikListesi.getAccordion(), Alignment.TOP_CENTER);
                    break;
                case("Çıkış Yap"):
                    OturumYonetimi.oturumuSonlandir();


                    break;
                case("Talepte Bulun"):
                    TalepteBulunPanel talepteBulunPanel=new TalepteBulunPanel();
                    window=new Window();
                    talepteBulunPanel.setWindow(window);
                    window.setContent(talepteBulunPanel.panelOlustur());
                    window.center();
                    window.setModal(true);
                    UI.getCurrent().addWindow(window);
                    break;
                case("Üye Bilgileri"):
                    UyeBilgileriPanel uyeBilgileriPanel=new UyeBilgileriPanel();
                    uyeBilgileriPanel.setIslemYapilacakUye(OturumYonetimi.uyeSinifi);//oturumu açan üye kendi bilgilerini görebileceği için üye sınfını setledik.

                    AnaPencere.bilesenleriKaldir();
                    AnaPencere.bilesenEkle(uyeBilgileriPanel.panelOlsutur());
                    AnaPencere.bilesenHizala(uyeBilgileriPanel.getPanel(),Alignment.TOP_CENTER);
                    break;
                case("Taleplerim"):
                    UyeBilgileriTaleplerimGrid uyeBilgileriTaleplerimGrid=new UyeBilgileriTaleplerimGrid();
                    uyeBilgileriTaleplerimGrid.setTalepGetirilecekUye(OturumYonetimi.uyeSinifi);

                    AnaPencere.bilesenleriKaldir();
                    AnaPencere.bilesenEkle(uyeBilgileriTaleplerimGrid.gridOlustur());
                    AnaPencere.bilesenHizala(uyeBilgileriTaleplerimGrid.getGrid(),Alignment.TOP_CENTER);
                    break;
                case("Etkinlik Listem"):
                    UyeBilgileriEtkinlikListesiGrid uyeBilgileriEtkinlikListesiGrid=new UyeBilgileriEtkinlikListesiGrid();
                    uyeBilgileriEtkinlikListesiGrid.setEtkinlikGetirilecekUye(OturumYonetimi.uyeSinifi);

                    AnaPencere.bilesenleriKaldir();
                    AnaPencere.bilesenEkle(uyeBilgileriEtkinlikListesiGrid.gridOlustur());
                    AnaPencere.bilesenHizala(uyeBilgileriEtkinlikListesiGrid.getGrid(),Alignment.TOP_CENTER);
                    break;
                case("Duyurularım"):
                    UyeBilgileriDuyurularimGrid uyeBilgileriDuyurularimGrid=new UyeBilgileriDuyurularimGrid();
                    uyeBilgileriDuyurularimGrid.setDuyuruGetirilecekUye(OturumYonetimi.uyeSinifi);

                    AnaPencere.bilesenleriKaldir();
                    AnaPencere.bilesenEkle(uyeBilgileriDuyurularimGrid.gridOlustur());
                    AnaPencere.bilesenHizala(uyeBilgileriDuyurularimGrid.getGrid(),Alignment.TOP_CENTER);
                    break;
            }
        };
        return yonlendir;
    }

    public Window getWindow() {
        return window;
    }

    public void setWindow(Window window) {
        this.window = window;
    }
}
