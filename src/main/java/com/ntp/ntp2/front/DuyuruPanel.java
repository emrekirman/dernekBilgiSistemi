/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ntp.ntp2.front;

import com.ntp.ntp2.back.DuyuruDao;
import com.ntp.ntp2.entities.Duyuru;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author Emre
 */
public class DuyuruPanel {
    private Accordion accordion;
    private Label panelİcerik;
    //Bu sınıf yöneticilerin girmiş olduğu duyuruları tüm kullanıcılara göstermek için oluşturuldu.

    public DuyuruPanel() {
        accordion=new Accordion();//değişken final olarak tanımlandığı için yapılandırıcı metot içerisinde nesne ürettik.
    }

    public Accordion accordionOlustur(){
        accordion.setWidth("70%");
        DuyuruDao duyuruDao=new DuyuruDao();

        List<Duyuru> duyuruListe=duyuruDao.tumKayitlariGetir();//veritabanından tüm duyurular liste şeklinde çekildi.
        for(Duyuru i:duyuruListe){
            if(i!=null){
                etkinlikEkle(i);//liste içerisinde ki tüm duyurular herkesin görebileceği şekilde listelendi.
            }
            else{
                continue;
            }
        }
        return accordion;
    }

    public void etkinlikEkle(Duyuru entity){//veritabanından çekilen her bir duyuru kaydını bu sınıftan üretilecek nesneler içerisine dolduruyoruz.
        Layout icerikLayout=new VerticalLayout();
        icerikLayout.setSizeFull();

        Label icerikLabel=new Label();
        icerikLabel.setSizeFull();
        icerikLabel.setContentMode(ContentMode.HTML);
        icerikLabel.setValue(entity.getDuyuruIcerik());

        Label ekleyenUye=new Label();
        ekleyenUye.setContentMode(ContentMode.HTML);
        ekleyenUye.setValue("<b><font size=-1>Paylaşan Üye: </b>"+entity.getUyeId().getUyeAd()+" "+entity.getUyeId().getUyeSoyad()+"</font>");

        Label eklenenTarih=new Label();
        eklenenTarih.setContentMode(ContentMode.HTML);
        LocalDate duyuruTarih=LocalDate.parse(entity.getDuyuruTarih().toString());
        String tarih=duyuruTarih.format((DateTimeFormatter.ofPattern("dd MM yyyy")));//tarihi istediğimiz şekilde ekranda göstermeye yarar.
        eklenenTarih.setValue("<b><font size=-1>Eklenen Tarih: </b>"+tarih);

        Label yatayCizgi=new Label();
        yatayCizgi.setContentMode(ContentMode.HTML);//label içeriğine html kodları ile müdahale etmemizi sağlar.
        yatayCizgi.setValue("<hr>");
        yatayCizgi.setSizeFull();

        HorizontalLayout horizontalLayout=new HorizontalLayout();
        horizontalLayout.addComponent(eklenenTarih);
        horizontalLayout.addComponent(ekleyenUye);
        horizontalLayout.setSizeFull();
        horizontalLayout.setComponentAlignment(ekleyenUye,Alignment.BOTTOM_RIGHT);

        icerikLayout.addComponent(icerikLabel);
        icerikLayout.addComponent(yatayCizgi);
        icerikLayout.addComponent(horizontalLayout);

        accordion.addTab(icerikLayout,entity.getDuyuruBaslik(),VaadinIcons.BOOKMARK);
    }

    public Label getPanelİcerik() {
        return panelİcerik;
    }

    public void setPanelİcerik(Label panelİcerik) {
        this.panelİcerik = panelİcerik;
    }

    public Accordion getAccordion() {
        return accordion;
    }

    public void setAccordion(Accordion accordion) {
        this.accordion = accordion;
    }
}