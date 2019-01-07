package com.ntp.ntp2.front;

import com.ntp.ntp2.back.EtkinlikDao;
import com.ntp.ntp2.back.OturumYonetimi;
import com.ntp.ntp2.entities.Etkinlik;
import com.vaadin.data.Binder;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.sql.Date;
import java.time.LocalDate;

public class UyeBilgileriEtkinlikDuzenlePanel {
    private final Panel panel;
    private VerticalLayout layout;
    private TextField etkinlikBaslik;
    private DateField etkinlikTarih;
    private RichTextArea etkinlikIcerik;
    private Button guncelleButon;
    private Button silButon;
    private Binder<Etkinlik> binder;
    private Etkinlik islemYapilacakEtkinik;//güncelleme veya silme işlemi yapılacak üyeyi buraya bağımlılık verdik.
    private UyeBilgileriEtkinlikListesiGrid uyeBilgileriEtkinlikListesiGrid;//silme veya güncelleme işlemi yapıldıktan sonra tablo içerisnde ki verileri yenilemek için bu bağımlılık var.

    public UyeBilgileriEtkinlikDuzenlePanel() {
        this.panel = new Panel();
    }

    public Panel panelOlustur(){
        etkinlikBaslik=new TextField();
        etkinlikBaslik.setStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        etkinlikBaslik.setIcon(VaadinIcons.PENCIL);
        etkinlikBaslik.setPlaceholder("Etkinlik Başlığı");
        etkinlikBaslik.setMaxLength(55);
        etkinlikBaslik.setSizeFull();

        etkinlikTarih=new DateField();
        etkinlikTarih.setTextFieldEnabled(false);
        etkinlikTarih.setSizeFull();
        etkinlikTarih.setDateFormat("dd/MM/yyyy");

        etkinlikIcerik=new RichTextArea();
        etkinlikIcerik.setSizeFull();

        guncelleButon=new Button("Güncelle");
        guncelleButon.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        guncelleButon.addClickListener(clickEvent -> {
            if (binder.isValid()) {
                islemYapilacakEtkinik.setEtkinlikBaslik(etkinlikBaslik.getValue());
                islemYapilacakEtkinik.setEtkinlikIcerik(etkinlikIcerik.getValue());
                islemYapilacakEtkinik.setEtkinlikTarih(Date.valueOf(etkinlikTarih.getValue()));
                islemYapilacakEtkinik.setUyeId(OturumYonetimi.uyeSinifi);

                EtkinlikDao etkinlikDao = new EtkinlikDao();
                etkinlikDao.guncelle(islemYapilacakEtkinik);
                panel.setVisible(false);

                uyeBilgileriEtkinlikListesiGrid.getGrid().setItems(etkinlikDao.tumKayitlariGetir());
            }
            else{
                binder.validate();
            }
        });

        silButon=new Button("Sil");
        silButon.setStyleName(ValoTheme.BUTTON_DANGER);
        silButon.addClickListener(clickEvent -> {
            if (binder.isValid()) {
                EtkinlikDao etkinlikDao = new EtkinlikDao();
                etkinlikDao.sil(islemYapilacakEtkinik);

                panel.setVisible(false);
                uyeBilgileriEtkinlikListesiGrid.getGrid().setItems(etkinlikDao.tumKayitlariGetir());
            }
            else{
                binder.validate();
            }
        });


        binder=new Binder<>();
        binder.setBean(islemYapilacakEtkinik);

        binder.forField(etkinlikBaslik).withValidator(new StringLengthValidator("Bu alan boş geçilemez ve en fazla 55 karakter girilebilir !",1,55)).bind(Etkinlik::getEtkinlikBaslik,Etkinlik::setEtkinlikBaslik);
        binder.forField(etkinlikIcerik).withValidator(new StringLengthValidator("Bu alan boş geçilemez ve en fazla 600 karakter girilebilir !",1,600)).bind(Etkinlik::getEtkinlikIcerik,Etkinlik::setEtkinlikIcerik);

        etkinlikBaslik.setValue(islemYapilacakEtkinik.getEtkinlikBaslik());//bağımlılık verilen üyenin içerisindeki verileri bileşenlere aktardık.
        etkinlikTarih.setValue(LocalDate.parse(islemYapilacakEtkinik.getEtkinlikTarih().toString()));
        etkinlikIcerik.setValue(islemYapilacakEtkinik.getEtkinlikIcerik());

        HorizontalLayout horizontalLayout=new HorizontalLayout();
        horizontalLayout.addComponent(silButon,0);
        horizontalLayout.addComponent(guncelleButon,1);

        layout=new VerticalLayout();
        layout.addComponent(etkinlikBaslik);
        layout.addComponent(etkinlikTarih);
        layout.addComponent(etkinlikIcerik);
        layout.addComponent(horizontalLayout);
        layout.setComponentAlignment(horizontalLayout,Alignment.BOTTOM_RIGHT);

        panel.setCaption("Etkinlik Düzenle");
        panel.setContent(layout);
        return panel;
    }

    public Panel getPanel() {
        return panel;
    }

    public Etkinlik getIslemYapilacakEtkinik() {
        return islemYapilacakEtkinik;
    }

    public void setIslemYapilacakEtkinik(Etkinlik islemYapilacakEtkinik) {
        this.islemYapilacakEtkinik = islemYapilacakEtkinik;
    }

    public UyeBilgileriEtkinlikListesiGrid getUyeBilgileriEtkinlikListesiGrid() {
        return uyeBilgileriEtkinlikListesiGrid;
    }

    public void setUyeBilgileriEtkinlikListesiGrid(UyeBilgileriEtkinlikListesiGrid uyeBilgileriEtkinlikListesiGrid) {
        this.uyeBilgileriEtkinlikListesiGrid = uyeBilgileriEtkinlikListesiGrid;
    }
}
