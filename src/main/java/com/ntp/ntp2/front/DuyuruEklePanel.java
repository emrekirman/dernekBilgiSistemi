package com.ntp.ntp2.front;

import com.ntp.ntp2.back.DuyuruDao;
import com.ntp.ntp2.entities.Duyuru;
import com.ntp.ntp2.entities.Uye;
import com.vaadin.data.Binder;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.sql.Date;
import java.time.LocalDate;

public class DuyuruEklePanel {
    private final Panel panel;
    private VerticalLayout layout;
    private TextField duyuruBaslik;
    private RichTextArea duyuruIcerik;
    private Button ekleButon;
    private Uye duyuruYapacakUye;//Duyuru yapan üyeyi veritananına eklemek için bu bağımlılığı oluşturdum.
    private Binder<Duyuru> binder;
    //Bu sınıf dernek yöneticilerinin duyuru paylaşması için oluştruldu.

    public DuyuruEklePanel() {
        this.panel = new Panel();
    }

    public Panel panelOlustur(){
        duyuruBaslik=new TextField();
        duyuruBaslik.setStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        duyuruBaslik.setIcon(VaadinIcons.PENCIL);
        duyuruBaslik.setMaxLength(55);
        duyuruBaslik.setPlaceholder("Duyuru Başlığı");
        duyuruBaslik.setSizeFull();

        duyuruIcerik=new RichTextArea();
        duyuruIcerik.setSizeFull();

        ekleButon=new Button("Ekle");
        ekleButon.setStyleName(ValoTheme.BUTTON_PRIMARY);

        ekleButon.addClickListener(clickEvent -> {//butona tıklandığında çalıştılacak alan.
            if (binder.isValid()) {//bileşenlere(components) herhangi yanlış veri girdisi veya boş bırakma sorunu yoksa bu koşul true olur ve aşağıdaki blok çalışır.
                Duyuru duyuru = new Duyuru();
                duyuru.setDuyuruBaslik(this.duyuruBaslik.getValue());
                duyuru.setDuyuruIcerik(this.duyuruIcerik.getValue());
                Date bugun = Date.valueOf(LocalDate.now());
                duyuru.setDuyuruTarih(bugun);
                duyuru.setUyeId(duyuruYapacakUye);

                DuyuruDao duyuruDao = new DuyuruDao();
                duyuruDao.ekle(duyuru);

                DuyuruPanel duyuruPanel=new DuyuruPanel();
                AnaPencere.bilesenleriKaldir();
                AnaPencere.bilesenEkle(duyuruPanel.accordionOlustur());
                AnaPencere.bilesenHizala(duyuruPanel.getAccordion(),Alignment.TOP_CENTER);
            }
            else{
                binder.validate();
            }
        });

        //binder alanlara hatalı veri girdisi ve boş bırakma gibi sorunları ortadan kaldırmak için kullanılır.
        binder=new Binder<>();
        Duyuru duyuru=new Duyuru();
        binder.setBean(duyuru);

        binder.forField(duyuruBaslik).withValidator(new StringLengthValidator("Bu alan boş geçilemez !",1,55)).bind(Duyuru::getDuyuruBaslik,Duyuru::setDuyuruBaslik);
        binder.forField(duyuruIcerik).withValidator(new StringLengthValidator("Bu alan boş geçilemez ve en fazla 600 karakter girilebilir !",1,600)).bind(Duyuru::getDuyuruIcerik,Duyuru::setDuyuruIcerik);

        layout=new VerticalLayout();
        layout.addComponent(duyuruBaslik);
        layout.addComponent(duyuruIcerik);
        layout.addComponent(ekleButon);
        layout.setComponentAlignment(ekleButon,Alignment.BOTTOM_RIGHT);

        panel.setContent(layout);
        panel.setStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        panel.setIcon(VaadinIcons.PLUS);
        panel.setCaption("Duyuru Ekle");
        panel.setWidth("70%");
        return panel;
    }

    public Uye getDuyuruYapacakUye() {
        return duyuruYapacakUye;
    }

    public void setDuyuruYapacakUye(Uye duyuruYapacakUye) {
        this.duyuruYapacakUye = duyuruYapacakUye;
    }

    public Panel getPanel() {
        return panel;
    }
}
