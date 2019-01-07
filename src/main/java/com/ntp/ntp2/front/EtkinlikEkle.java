/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ntp.ntp2.front;

import com.ntp.ntp2.back.EtkinlikDao;
import com.ntp.ntp2.back.OturumYonetimi;
import com.ntp.ntp2.entities.Etkinlik;
import com.vaadin.data.Binder;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.vaadin.inputmask.InputMask;
import org.vaadin.inputmask.client.Alias;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;


/**
 *
 * @author emreKirman
 */
public class EtkinlikEkle implements Serializable {
    private final Panel etkinlikEklePanel;
    private VerticalLayout etkinlikIcerikLayout;
    private TextField etkinlikBaslik;
    private DateField etkinlikTarih;
    private RichTextArea etkinlikIcerik;
    private EtkinlikListesiGrid etkinlikListesiGrid;
    private TabSheet yoneticiIslemleriTabSheet;//etkinlik eklendikten sonra direkt olarak etkinlik listesi sayfasını açmak için bu bağımlılığı yarattım.
    private Binder<Etkinlik> binder;

    public EtkinlikEkle() {
        etkinlikEklePanel=new Panel();
    }
    
    public Panel etkinlikEklePanelOlustur(){
        etkinlikEklePanel.setStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        etkinlikEklePanel.setIcon(VaadinIcons.PLUS);
        etkinlikEklePanel.setCaption("Etkinlik Ekle");
        etkinlikEklePanel.setWidth("70%");
        etkinlikEklePanel.setResponsive(true);
        
        etkinlikIcerikLayout=new VerticalLayout();
        
        etkinlikBaslik=new TextField();
        etkinlikBaslik.setStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        etkinlikBaslik.setIcon(VaadinIcons.PENCIL);
        etkinlikBaslik.setPlaceholder("Başlık");
        etkinlikBaslik.setMaxLength(55);
        etkinlikBaslik.setSizeFull();
        
        etkinlikTarih=new DateField();
        etkinlikTarih.setSizeFull();
        etkinlikTarih.setPlaceholder("gg/aa/yyyy");
        etkinlikTarih.setDateFormat("dd/MM/yyyy");
        etkinlikTarih.setTextFieldEnabled(false);
        InputMask.addTo(etkinlikTarih, Alias.DATE);
        
        etkinlikIcerik=new RichTextArea();
        etkinlikIcerik.setSizeFull();
        
        Button ekleButton=new Button();
        ekleButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
        ekleButton.setCaption("Ekle");

        ekleButton.addClickListener(clickEvent -> {
            if (binder.isValid()) {
                String etkinlikBaslik = this.etkinlikBaslik.getValue();
                Date etkinlikTarih = Date.valueOf(this.etkinlikTarih.getValue());
                String etkinlikIcerik = this.etkinlikIcerik.getValue();
                Date bugun = Date.valueOf(LocalDate.now());//etkinliğin hangi gün sisteme girildiğini öğrenmek için bugünün tarihini çektim.

                Etkinlik eklenecekEtkinlik = new Etkinlik(etkinlikBaslik, etkinlikTarih, etkinlikIcerik, bugun);//eklenecek etkinlik nesnemizi oluşturduk ve yapılandırıcı metottan değer atamasını yaptık.
                eklenecekEtkinlik.setUyeId(OturumYonetimi.uyeSinifi);//Etkinliği ekleyen kişi oturumu açan kişi olduğu için direkt bu veriyi kullandık.

                EtkinlikDao etkinlikDao = new EtkinlikDao();//veri tabanı işlemi için bu sınıftan nesne ürettik.
                etkinlikDao.ekle(eklenecekEtkinlik);//ekleme işlemini gerçekleştirdik.

                etkinlikListesiGrid.getGrid().setItems(etkinlikDao.tumKayitlariGetir());//kayıt girildikten sonra etkinlik listesi tablosunu yeniledik.
                yoneticiIslemleriTabSheet.setSelectedTab(3);//ekleme işlemi yapıldıktan sonra etkinlik listesini içeren tablonun olduğu sekmeye yönlendirme yapılabilir.
            }
            else{
                binder.validate();
            }

        });

        binder=new Binder<>();
        Etkinlik etkinlik=new Etkinlik();
        binder.setBean(etkinlik);//Girdi kontrolü yapacağımız sınıfı binder sınıfına atadık.

        binder.forField(etkinlikBaslik).withValidator(new StringLengthValidator("Bu alan boş geçilemez !",1,55)).bind(Etkinlik::getEtkinlikBaslik,Etkinlik::setEtkinlikBaslik);
        binder.forField(etkinlikIcerik).withValidator(new StringLengthValidator("Bu alan boş geçilemez ve en fazla 600 karakter girişi yapılabilir !",1,600)).bind(Etkinlik::getEtkinlikIcerik,Etkinlik::setEtkinlikIcerik);


        etkinlikTarih.setValue(LocalDate.now());
        
        etkinlikIcerikLayout.addComponent(etkinlikBaslik);
        etkinlikIcerikLayout.addComponent(etkinlikTarih);
        etkinlikIcerikLayout.addComponent(etkinlikIcerik);
        etkinlikIcerikLayout.addComponent(ekleButton);
        etkinlikIcerikLayout.setComponentAlignment(ekleButton, Alignment.BOTTOM_RIGHT);
        etkinlikEklePanel.setContent(etkinlikIcerikLayout);
        return etkinlikEklePanel;
    }

    public Panel getEtkinlikEklePanel() {
        return etkinlikEklePanel;
    }

    public VerticalLayout getEtkinlikIcerikLayout() {
        return etkinlikIcerikLayout;
    }

    public EtkinlikListesiGrid getEtkinlikListesiGrid() {
        return etkinlikListesiGrid;
    }

    public void setEtkinlikListesiGrid(EtkinlikListesiGrid etkinlikListesiGrid) {
        this.etkinlikListesiGrid = etkinlikListesiGrid;
    }

    public TabSheet getYoneticiIslemleriTabSheet() {
        return yoneticiIslemleriTabSheet;
    }

    public void setYoneticiIslemleriTabSheet(TabSheet yoneticiIslemleriTabSheet) {
        this.yoneticiIslemleriTabSheet = yoneticiIslemleriTabSheet;
    }
}
