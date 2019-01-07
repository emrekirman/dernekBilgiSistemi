package com.ntp.ntp2.front;

import com.ntp.ntp2.back.DuyuruDao;
import com.ntp.ntp2.entities.Duyuru;
import com.vaadin.data.Binder;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.sql.Date;
import java.time.LocalDate;

public class UyeBilgileriDuyurularimDuzenlePanel {
    private final Panel panel;
    private VerticalLayout layout;
    private TextField duyuruBaslik;
    private RichTextArea duyuruIcerik;
    private Button guncelleButon;
    private Button silButon;
    private Duyuru islemYapilacakDuyuru;//güncellenecek duyuruyu alıp değiştirip işlem yapıcaz.
    private UyeBilgileriDuyurularimGrid uyeBilgileriDuyurularimGrid;//bu bağımlılığı işlemler yapıldıktan sonra tablomuzu yenilemek için kullanıcaz.
    private Binder<Duyuru> binder;
    //bu sınıf yönetici üyelerin yaptığı duyuruların düzenlenmesi gerektiği zaman kullanılmak için oluşturuldu.

    public UyeBilgileriDuyurularimDuzenlePanel() {
        this.panel=new Panel();
    }

    public Panel panelOLustur(){
        duyuruBaslik=new TextField();
        duyuruBaslik.setStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        duyuruBaslik.setIcon(VaadinIcons.PENCIL);
        duyuruBaslik.setSizeFull();
        duyuruBaslik.setMaxLength(55);


        duyuruIcerik=new RichTextArea();
        duyuruIcerik.setSizeFull();


        guncelleButon=new Button("Güncelle");
        guncelleButon.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        guncelleButon.addClickListener(clickEvent -> {
            if (binder.isValid()) {
                islemYapilacakDuyuru.setDuyuruBaslik(duyuruBaslik.getValue());//yeni girilen verileri atadık.
                islemYapilacakDuyuru.setDuyuruIcerik(duyuruIcerik.getValue());
                islemYapilacakDuyuru.setDuyuruTarih(Date.valueOf(LocalDate.now()));

                DuyuruDao duyuruDao = new DuyuruDao();
                duyuruDao.guncelle(islemYapilacakDuyuru);//değişen verilerle güncelleme işlemi yaptık.

                uyeBilgileriDuyurularimGrid.getGrid().setItems(duyuruDao.uyeIdyeGoreDuyuruGetir(islemYapilacakDuyuru.getUyeId()));//işlemden sonra tabloyu yeniledik.
            }
            else{
                binder.validate();
            }
        });


        silButon=new Button("Sil");
        silButon.setStyleName(ValoTheme.BUTTON_DANGER);
        silButon.addClickListener(clickEvent -> {
           DuyuruDao duyuruDao=new DuyuruDao();
           duyuruDao.sil(islemYapilacakDuyuru);

            uyeBilgileriDuyurularimGrid.getGrid().setItems(duyuruDao.uyeIdyeGoreDuyuruGetir(islemYapilacakDuyuru.getUyeId()));//işlemden sonra tabloyu yeniledik.
            panel.setVisible(false);
        });

        HorizontalLayout horizontalLayout=new HorizontalLayout();
        horizontalLayout.addComponent(silButon);
        horizontalLayout.addComponent(guncelleButon);

        binder=new Binder<>();
        binder.setBean(islemYapilacakDuyuru);

        binder.forField(duyuruBaslik).withValidator(new StringLengthValidator("Bu alan boş geçilemez!",1,55)).bind(Duyuru::getDuyuruBaslik,Duyuru::setDuyuruBaslik);
        binder.forField(duyuruIcerik).withValidator(new StringLengthValidator("Bu alan boş geçilemez ve en fazla 600 karakter girişi yapılabilir !",1,600)).bind(Duyuru::getDuyuruIcerik,Duyuru::setDuyuruIcerik);


        duyuruBaslik.setValue(islemYapilacakDuyuru.getDuyuruBaslik());
        duyuruIcerik.setValue(islemYapilacakDuyuru.getDuyuruIcerik());

        layout=new VerticalLayout();
        layout.addComponent(duyuruBaslik);
        layout.addComponent(duyuruIcerik);
        layout.addComponent(horizontalLayout);
        layout.setComponentAlignment(horizontalLayout,Alignment.BOTTOM_RIGHT);

        panel.setContent(layout);
        panel.setCaption("Duyuru Düzenle");
        return panel;
    }

    public Panel getPanel() {
        return panel;
    }

    public Duyuru getIslemYapilacakDuyuru() {
        return islemYapilacakDuyuru;
    }

    public void setIslemYapilacakDuyuru(Duyuru islemYapilacakDuyuru) {
        this.islemYapilacakDuyuru = islemYapilacakDuyuru;
    }

    public UyeBilgileriDuyurularimGrid getUyeBilgileriDuyurularimGrid() {
        return uyeBilgileriDuyurularimGrid;
    }

    public void setUyeBilgileriDuyurularimGrid(UyeBilgileriDuyurularimGrid uyeBilgileriDuyurularimGrid) {
        this.uyeBilgileriDuyurularimGrid = uyeBilgileriDuyurularimGrid;
    }
}
