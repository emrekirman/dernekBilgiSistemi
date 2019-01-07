package com.ntp.ntp2.front;

import com.ntp.ntp2.entities.Talep;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.time.LocalDate;

public class UyeBilgileriTalepDuzenlePanel {
    private final Panel panel;
    private Talep islemYapilacakTalep;//güncelleme veya silme işlemi yapılacak talebi almak için bu bağımlılığı oluşturduk.
    private TextField talepBaslik;
    private Label talepIcerik;
    private DateField talepTarih;
    private VerticalLayout layout;

    public UyeBilgileriTalepDuzenlePanel() {
        panel=new Panel();
    }

    public Panel panelOlustur(){
        talepBaslik=new TextField();
        talepBaslik.setMaxLength(55);
        talepBaslik.setSizeFull();
        talepBaslik.setStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        talepBaslik.setIcon(VaadinIcons.PENCIL);
        talepBaslik.setEnabled(false);
        talepBaslik.setValue(islemYapilacakTalep.getTalepBaslik());//tabloda kayıda tıklanan talebin başlığını ekledik.

        talepTarih=new DateField();
        talepTarih.setTextFieldEnabled(false);
        talepTarih.setEnabled(false);
        talepTarih.setSizeFull();
        talepTarih.setValue(LocalDate.parse(islemYapilacakTalep.getTalepTarih().toString()));//tabloda kayıda tıklanan talebin tarihini ekledik.

        talepIcerik=new Label();
        talepIcerik.setContentMode(ContentMode.HTML);
        talepIcerik.setSizeFull();
        talepIcerik.setValue(islemYapilacakTalep.getTalepIcerik());//tabloda kayıda tıklanan talebin içeriğini görüntüledik.

        layout=new VerticalLayout();
        layout.addComponent(talepBaslik);
        layout.addComponent(talepTarih);
        layout.addComponent(talepIcerik);

        panel.setCaption("Talep Düzenle");
        panel.setContent(layout);
        panel.setWidth("500px");
        return panel;
    }

    public Panel getPanel() {
        return panel;
    }

    public Talep getIslemYapilacakTalep() {
        return islemYapilacakTalep;
    }

    public void setIslemYapilacakTalep(Talep islemYapilacakTalep) {
        this.islemYapilacakTalep = islemYapilacakTalep;
    }
}
