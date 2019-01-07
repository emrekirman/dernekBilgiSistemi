package com.ntp.ntp2.front;

import com.ntp.ntp2.entities.Talep;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.time.LocalDate;

public class TalepDuzenlePanel {
    private final Panel panel;
    private VerticalLayout layout;
    private TextField talepBaslik;
    private DateField talepTarih;
    private Label talepIcerik;
    private Talep islemYapilacakTalep;//ekranda göstereceğimiz talep bilgileri için bu değişkeni kullanıcaz.
    //Bu sınıf dernek üyelerinin yapmış olduğu talepleri tabloda kayıtlara tıklandığında göstermek için oluşturuldu.

    public TalepDuzenlePanel() {
        panel=new Panel();
    }

    public Panel panelOlustur(){
        panel.setCaption("Talep No: "+islemYapilacakTalep.getTalepId().toString());//talep numarasını panelin başlık kısmına yazdık.

        talepBaslik=new TextField();
        talepBaslik.setEnabled(false);
        talepBaslik.setStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        talepBaslik.setIcon(VaadinIcons.PENCIL);
        talepBaslik.setValue(islemYapilacakTalep.getTalepBaslik());//tablodan gelen Talep sınıfını bu sınıfa değişken olarak atadık çünkü içerisindeki verilere erişmek için.
        talepBaslik.setMaxLength(55);//talep başlığına girilebilecek karakter limiti.
        talepBaslik.setSizeFull();

        talepTarih=new DateField();
        talepTarih.setEnabled(false);
        talepTarih.setDateFormat("dd/MM/yyyy");
        talepTarih.setValue(LocalDate.parse(islemYapilacakTalep.getTalepTarih().toString()));
        talepTarih.setSizeFull();

        talepIcerik=new Label();
        talepIcerik.setValue(islemYapilacakTalep.getTalepIcerik());
        talepIcerik.setContentMode(ContentMode.HTML);
        talepIcerik.setSizeFull();

        layout=new VerticalLayout();
        layout.addComponent(talepBaslik);
        layout.addComponent(talepTarih);
        layout.addComponent(talepIcerik);
        layout.setSizeFull();

        panel.setContent(layout);
        panel.setWidth("500px");
        return panel;
    }

    public Talep getIslemYapilacakTalep() {
        return islemYapilacakTalep;
    }

    public void setIslemYapilacakTalep(Talep islemYapilacakTalep) {
        this.islemYapilacakTalep = islemYapilacakTalep;
    }

    public Panel getPanel() {
        return panel;
    }

    public VerticalLayout getLayout() {
        return layout;
    }

    public void setLayout(VerticalLayout layout) {
        this.layout = layout;
    }

    public TextField getTalepBaslik() {
        return talepBaslik;
    }

    public void setTalepBaslik(TextField talepBaslik) {
        this.talepBaslik = talepBaslik;
    }

    public DateField getTalepTarih() {
        return talepTarih;
    }

    public void setTalepTarih(DateField talepTarih) {
        this.talepTarih = talepTarih;
    }
}
