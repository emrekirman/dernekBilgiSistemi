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

import java.sql.Date;
import java.time.LocalDate;

public class EtkinlikDuzenlePanel {
    private final Panel panel;
    private VerticalLayout panelIcerik;
    private TextField etkinlikBaslik;
    private DateField etkinlikTarih;
    private RichTextArea etkinlikIcerik;
    private EtkinlikListesiGrid etkinlikListesiGrid;
    private Etkinlik etkinlik;//bu sınıf etkinlik tablosunda setlenecek çünkü güncelleme veya silme işlemi yapacağımız kayıtın ID'sine ihtiyacımız var.
    private Button silButon;
    private Binder<Etkinlik> binder;

    public EtkinlikDuzenlePanel() {
        panel=new Panel();
    }

    public Panel panelOlustur(){
        panelIcerik=new VerticalLayout();

        etkinlikBaslik=new TextField();
        etkinlikBaslik.setStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        etkinlikBaslik.setIcon(VaadinIcons.PENCIL);
        etkinlikBaslik.setMaxLength(55);
        etkinlikBaslik.setSizeFull();

        etkinlikTarih=new DateField();
        InputMask.addTo(etkinlikTarih, Alias.DATE);
        etkinlikTarih.setDateFormat("dd/MM/yyyy");
        etkinlikTarih.setSizeFull();
        etkinlikTarih.setTextFieldEnabled(false);

        etkinlikIcerik=new RichTextArea();
        etkinlikIcerik.setSizeFull();

        Button guncelleButon=new Button();
        guncelleButon.setCaption("Güncelle");
        guncelleButon.setStyleName(ValoTheme.BUTTON_FRIENDLY);

        guncelleButon.addClickListener(clickEvent -> {
            if(binder.isValid()) {
                Date bugun = Date.valueOf(LocalDate.now());//bugünün tarihini bir değişkene attık.

                Etkinlik guncellenecekEtkinlik = new Etkinlik(etkinlik.getEtkinlikId(), etkinlikBaslik.getValue(), Date.valueOf(etkinlikTarih.getValue()), etkinlikIcerik.getValue(), bugun);
                guncellenecekEtkinlik.setUyeId(OturumYonetimi.uyeSinifi);//etkinliği yayınlayan üyeyi veri tabanına eklemek için oturum açan üyeyi verdik.
                EtkinlikDao etkinlikDao = new EtkinlikDao();
                etkinlikDao.guncelle(guncellenecekEtkinlik);//güncelleme işlemini hallettik.
                etkinlikListesiGrid.getGrid().setItems(etkinlikDao.tumKayitlariGetir());//güncelleme işleminden sonra etkinlik tablosunu yeniledik.
                panel.setVisible(false);//panelimizin görünme özelliğini kapattık.
            }
            else{
                binder.validate();
            }
        });

        silButon=new Button();
        silButon.setCaption("Sil");
        silButon.setStyleName(ValoTheme.BUTTON_DANGER);

        silButon.addClickListener(clickEvent -> {//silme tuşuna basıldıktan sonra çalışacak blok.
            EtkinlikDao etkinlikDao=new EtkinlikDao();
            etkinlik.setUyeId(OturumYonetimi.uyeSinifi);
            etkinlikDao.sil(etkinlik);
            etkinlikListesiGrid.getGrid().setItems(etkinlikDao.tumKayitlariGetir());
            panel.setVisible(false);
        });

        binder=new Binder<>();
        Etkinlik etkinlik=new Etkinlik();
        binder.setBean(etkinlik);

        binder.forField(etkinlikBaslik).withValidator(new StringLengthValidator("Bu alan boş geçilemez!",1,55)).bind(Etkinlik::getEtkinlikBaslik,Etkinlik::setEtkinlikBaslik);
        binder.forField(etkinlikIcerik).withValidator(new StringLengthValidator("Bu alan boş geçilemez ve en fazla 600 karakter girişi yapılabilir !",1,600)).bind(Etkinlik::getEtkinlikIcerik,Etkinlik::setEtkinlikIcerik);

        HorizontalLayout horizontalLayout=new HorizontalLayout();
        horizontalLayout.addComponent(silButon);
        horizontalLayout.addComponent(guncelleButon);

        panelIcerik.addComponent(etkinlikBaslik);
        panelIcerik.addComponent(etkinlikTarih);
        panelIcerik.addComponent(etkinlikIcerik);
        panelIcerik.addComponent(horizontalLayout);

        panelIcerik.setComponentAlignment(horizontalLayout,Alignment.BOTTOM_RIGHT);
        panelIcerik.setSizeFull();

        panel.setContent(panelIcerik);
        panel.setWidth("500px");
        return panel;
    }

    public EtkinlikListesiGrid getEtkinlikListesiGrid() {
        return etkinlikListesiGrid;
    }

    public void setEtkinlikListesiGrid(EtkinlikListesiGrid etkinlikListesiGrid) {
        this.etkinlikListesiGrid = etkinlikListesiGrid;
    }

    public Panel getPanel() {
        return panel;
    }

    public VerticalLayout getPanelIcerik() {
        return panelIcerik;
    }

    public void setPanelIcerik(VerticalLayout panelIcerik) {
        this.panelIcerik = panelIcerik;
    }

    public TextField getEtkinlikBaslik() {
        return etkinlikBaslik;
    }

    public void setEtkinlikBaslik(TextField etkinlikBaslik) {
        this.etkinlikBaslik = etkinlikBaslik;
    }

    public DateField getEtkinlikTarih() {
        return etkinlikTarih;
    }

    public void setEtkinlikTarih(DateField etkinlikTarih) {
        this.etkinlikTarih = etkinlikTarih;
    }

    public RichTextArea getEtkinlikIcerik() {
        return etkinlikIcerik;
    }

    public void setEtkinlikIcerik(RichTextArea etkinlikIcerik) {
        this.etkinlikIcerik = etkinlikIcerik;
    }

    public Etkinlik getEtkinlik() {
        return etkinlik;
    }

    public void setEtkinlik(Etkinlik etkinlik) {
        this.etkinlik = etkinlik;
    }

    public Button getSilButon() {
        return silButon;
    }

    public void setSilButon(Button silButon) {
        this.silButon = silButon;
    }
}
