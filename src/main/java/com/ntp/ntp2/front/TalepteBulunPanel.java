package com.ntp.ntp2.front;

import com.ntp.ntp2.back.OturumYonetimi;
import com.ntp.ntp2.back.TalepDao;
import com.ntp.ntp2.entities.Talep;
import com.vaadin.data.Binder;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.sql.Date;
import java.time.LocalDate;

public class TalepteBulunPanel {
    private final Panel panel;
    private VerticalLayout verticalLayout;
    private TextField talepBaslik;
    private RichTextArea talepIcerik;
    private Button gonderButon;
    private Window window;//ekleme işlemi yapıldıktan sonra modal'ı kapatmak için bu bağımlılık oluşturuldu.
    private Binder<Talep> binder;
    //Bu sınıf modal olarak kullanılacak
    //bu sınıf üye tarafından yönetime iletilecek talepleri göndermek için oluşturuldu.

    public TalepteBulunPanel() {
        panel=new Panel();
    }

    public Panel panelOlustur(){
        panel.setStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        panel.setIcon(VaadinIcons.PLUS);
        panel.setCaption("Talepte Bulun");
        panel.setWidth("100%");
        panel.setResponsive(true);

        talepBaslik=new TextField();
        talepBaslik.setStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        talepBaslik.setIcon(VaadinIcons.PENCIL);
        talepBaslik.setSizeFull();
        talepBaslik.setMaxLength(55);
        talepBaslik.setPlaceholder("Talep Başlık");

        talepIcerik=new RichTextArea();
        talepIcerik.setSizeFull();

        gonderButon=new Button();
        gonderButon.setCaption("Gönder");
        gonderButon.setStyleName(ValoTheme.BUTTON_PRIMARY);

        gonderButon.addClickListener(clickEvent -> {//butona tıklandığında çalışacak metot.
            if (binder.isValid()) {
                String talepBaslik = this.talepBaslik.getValue();//bileşenlerde üyenin girdiği verileri değişkenlere atadık.
                String talepIcerik = this.talepIcerik.getValue();

                Date talepTarih = Date.valueOf(LocalDate.now());//bugünün tarihini aldık.

                Talep eklenecekTalep = new Talep(talepBaslik, talepIcerik, talepTarih);//veritabanına kaydedilecek talebi ürettik ve değerleri atadık.
                eklenecekTalep.setUyeId(OturumYonetimi.uyeSinifi);//talep yapan üyeyi ekledik.

                TalepDao talepDao = new TalepDao();
                talepDao.ekle(eklenecekTalep);//talebi veri tabanına ekledik.

                window.close();//ekrana çıkan pencereyi(Modalı) kapattık
                Notification.show("Talebiniz Yönetime iletilmiştir.");
            }
            else{
                binder.validate();
            }
        });

        binder=new Binder<>();
        Talep t=new Talep();
        binder.setBean(t);

        binder.forField(talepBaslik).withValidator(new StringLengthValidator("Bu alan boş geçilemez!",1,55)).bind(Talep::getTalepBaslik,Talep::setTalepBaslik);
        binder.forField(talepIcerik).withValidator(new StringLengthValidator("Bu alan boş geçilemez ve en fazla 600 karakter girilebilir!",1,600)).bind(Talep::getTalepIcerik,Talep::setTalepIcerik);

        verticalLayout=new VerticalLayout();
        verticalLayout.addComponent(talepBaslik);
        verticalLayout.addComponent(talepIcerik);
        verticalLayout.addComponent(gonderButon);
        verticalLayout.setComponentAlignment(gonderButon,Alignment.BOTTOM_RIGHT);

        panel.setContent(verticalLayout);
        return panel;
    }

    public Panel getPanel() {
        return panel;
    }

    public VerticalLayout getVerticalLayout() {
        return verticalLayout;
    }

    public TextField getTalepBaslik() {
        return talepBaslik;
    }


    public RichTextArea getTalepIcerik() {
        return talepIcerik;
    }

    public Button getGonderButon() {
        return gonderButon;
    }

    public void setVerticalLayout(VerticalLayout verticalLayout) {
        this.verticalLayout = verticalLayout;
    }

    public void setTalepBaslik(TextField talepBaslik) {
        this.talepBaslik = talepBaslik;
    }

    public void setTalepIcerik(RichTextArea talepIcerik) {
        this.talepIcerik = talepIcerik;
    }

    public void setGonderButon(Button gonderButon) {
        this.gonderButon = gonderButon;
    }

    public Window getWindow() {
        return window;
    }

    public void setWindow(Window window) {
        this.window = window;
    }
}
