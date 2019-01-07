/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ntp.ntp2.front;

import com.ntp.ntp2.back.OturumYonetimi;
import com.ntp.ntp2.back.UyeDao;
import com.ntp.ntp2.entities.Uye;
import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import java.util.ArrayList;

/**
 *
 * @author emreKirman
 */
public class GirisPanel {
    private final Panel girisPanel;
    private VerticalLayout layout;
    private Binder binder;
    private TextField kullaniciId;
    private TextField kullaniciParola;
    //Bu sınıf üye girişi ve oturum başlatma işlerini halletmek için oluşturuldu.

    public GirisPanel() {
        girisPanel=new Panel();
    }
    
    public Panel girisPanelOlustur(){
        girisPanel.setStyleName(ValoTheme.PANEL_WELL);
        girisPanel.setStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        girisPanel.setIcon(VaadinIcons.USER_CARD);
        girisPanel.setCaption("Giriş Yap");
        girisPanel.setWidth("288px");
        
        layout=new VerticalLayout();
        layout.setSizeFull();
        HorizontalLayout horizontalLayout=new HorizontalLayout();
        
        kullaniciId=new TextField();
        kullaniciId.setStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        kullaniciId.setIcon(VaadinIcons.USER);
        kullaniciId.setPlaceholder("Üye No");
        kullaniciId.setSizeFull();

        kullaniciParola=new PasswordField();
        kullaniciParola.setStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        kullaniciParola.setIcon(VaadinIcons.KEY);
        kullaniciParola.setPlaceholder("Parola");
        kullaniciParola.setSizeFull();
        
        Button girisButon=new Button("Giriş");
        girisButon.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        girisButon.setWidth("125px");
        girisButon.setClickShortcut(ShortcutAction.KeyCode.ENTER);//enter tuşuna basıldığında butona basılmış gibi tetiklenir.
        
        girisButon.addClickListener(listener->{
           int uyeId=Integer.parseInt(kullaniciId.getValue());//giriş yapan üyenin girdiği üye noyu bu deişkene attık.
           String uyeParola=kullaniciParola.getValue();//giriş yapan üyenin girdiği şifre.
           Uye uye=new Uye(uyeId,uyeParola);//giriş yapan üyenin bilgilerini bu nesneye attık.
           UyeDao uyeDao=new UyeDao();
           ArrayList<Uye> girisYapanUyeListe= (ArrayList<Uye>) uyeDao.idSifreyeGoreUyeGetir(uye);//bu komutla beraber id ve şifre doğrulaması yaptık.
           if(!girisYapanUyeListe.isEmpty()){//liste boş değilse bloğu çalıştıracak.
               OturumYonetimi.uyeSinifi=girisYapanUyeListe.get(0);//oturum başlatıyoruz.

               OturumYonetimi.oturumBaslat(girisYapanUyeListe.get(0));
               DuyuruPanel duyuruPanel=new DuyuruPanel();
               AnaPencere.bilesenleriKaldir();
               AnaPencere.bilesenEkle(duyuruPanel.accordionOlustur());
               AnaPencere.bilesenHizala(duyuruPanel.getAccordion(), Alignment.TOP_CENTER);
               Notification.show(OturumYonetimi.uyeAd+" "+OturumYonetimi.uyeSoyad);
           }
           else{
               Notification.show("Üye Numarası Veya Şifre Yanlış", Notification.Type.ERROR_MESSAGE);//Üye yoksa bu hata mesajı ekranda çıkar.
           }
        });
        
        Link parolaUnut=new Link("Parola mı unuttum",new ExternalResource("#"));
        
        horizontalLayout.addComponent(parolaUnut);
        horizontalLayout.addComponent(girisButon);
        horizontalLayout.setComponentAlignment(parolaUnut, Alignment.BOTTOM_RIGHT);//bileşenleri hizaladık.
        horizontalLayout.setComponentAlignment(girisButon, Alignment.BOTTOM_RIGHT);
        
        
        layout.addComponent(kullaniciId);
        layout.addComponent(kullaniciParola);
        layout.addComponent(horizontalLayout);
        layout.setComponentAlignment(horizontalLayout, Alignment.BOTTOM_RIGHT);
        girisPanel.setContent(layout);
        return girisPanel;
    }

    public Panel getGirisPanel() {
        return girisPanel;
    }
    
    
}
