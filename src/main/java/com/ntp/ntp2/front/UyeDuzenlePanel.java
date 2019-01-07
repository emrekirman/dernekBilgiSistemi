/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ntp.ntp2.front;

import com.ntp.ntp2.back.IlDao;
import com.ntp.ntp2.back.IlceDao;
import com.ntp.ntp2.back.UyeDao;
import com.ntp.ntp2.entities.Cinsiyet;
import com.ntp.ntp2.entities.Il;
import com.ntp.ntp2.entities.Ilce;
import com.ntp.ntp2.entities.Mahalle;
import com.ntp.ntp2.entities.Uye;
import com.ntp.ntp2.entities.Yetki;
import com.vaadin.data.Binder;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PopupView;
import com.vaadin.ui.RadioButtonGroup;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import java.sql.Date;
import java.util.ArrayList;
import org.vaadin.inputmask.InputMask;
import org.vaadin.inputmask.client.Alias;

/**
 *
 * @author Emre
 */
public class UyeDuzenlePanel {
    private final Panel panel;
    private HorizontalLayout horizontalLayout;
    private VerticalLayout panelIcerik;
    
    private TextField kisiId;
    private TextField kisiAd;
    private TextField kisiSoyad;
    private TextField kisiTelNumara;
    private DateField kisiDogumTarih;
    private TextField kisiEpostaAdres;
    private RadioButtonGroup<Cinsiyet> radioGroup;
    private Button kisiGuncelleButon;
    private Button kisiSilButon;
    private KisiListesiGrid kisiListesiGrid;
    private ComboBox<Il> il;
    private ComboBox<Ilce> ilce;
    private TextArea mahalle;
    private Il ilNo;
    private Uye islemYapilacakUye;
    private Binder<Uye> binder;
    private Yetki kullaniciYetki;//Güncellenecek üyenin yetkisini çekmek için bubağımlılık oluşturuldu.
    
    public UyeDuzenlePanel() {
        panel=new Panel();
    }
    
    public Panel panelOlustur(){
        panelIcerik=new VerticalLayout();
        
        kisiAd=new TextField();
        kisiAd.setPlaceholder("Ad");
        kisiAd.setStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        kisiAd.setIcon(VaadinIcons.USER);
        kisiAd.setSizeFull();
        
        kisiSoyad=new TextField();
        kisiSoyad.setPlaceholder("Soyad");
        kisiSoyad.setStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        kisiSoyad.setIcon(VaadinIcons.USER);
        kisiSoyad.setSizeFull();
        
        kisiDogumTarih=new DateField();
        kisiDogumTarih.setPlaceholder("Doğum Tarihi");
        kisiDogumTarih.setDateFormat("dd/MM/yyyy");
        InputMask.addTo(kisiDogumTarih,Alias.DATE);
        kisiDogumTarih.setSizeFull();
        kisiDogumTarih.setTextFieldEnabled(false);
        
        kisiTelNumara=new TextField();
        kisiTelNumara.setPlaceholder("Telefon Numarası");
        kisiTelNumara.setStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        kisiTelNumara.setIcon(VaadinIcons.PHONE);
        //InputMask.addTo(kisiTelNumara,"+\\90 999-999-99-99");
        InputMask.addTo(kisiTelNumara,"999-999-99-99");
        kisiTelNumara.setSizeFull();
        
        kisiEpostaAdres=new TextField();
        kisiEpostaAdres.setStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        kisiEpostaAdres.setIcon(VaadinIcons.ENVELOPE);
        kisiEpostaAdres.setPlaceholder("E-posta Adresi");
        InputMask.addTo(kisiEpostaAdres, Alias.EMAIL);
        kisiEpostaAdres.setSizeFull();
        
        ArrayList<Cinsiyet> cinsiyetList=new ArrayList<>();//üye cinsiyetlerini tutmak için bir liste oluşturduk.
        cinsiyetList.add(new Cinsiyet(1, "Kadın"));
        cinsiyetList.add(new Cinsiyet(2, "Erkek"));
        
        radioGroup=new RadioButtonGroup<>("Cinsiyet");
        radioGroup.setItems(cinsiyetList);
        radioGroup.setItemCaptionGenerator(Cinsiyet::getCinsiyetAd);
        
        il=new ComboBox<>("Adres");
        il.setSizeFull();
        il.setItemCaptionGenerator(Il::getIlAd);
        il.setPlaceholder("Şehir Seçiniz");
        il.setEmptySelectionAllowed(false);
        
        il.addSelectionListener(listener->{
            IlDao ilDao=new IlDao();
            ArrayList<Il> ilListe=(ArrayList<Il>) ilDao.tumIlleriGetir();
            il.setItems(ilListe);
            ilNo=listener.getValue();
            
            IlceDao ilceDao=new IlceDao();
            ArrayList<Ilce> ilceListe=(ArrayList<Ilce>) ilceDao.ileGoreIlceGetir(ilNo);
            ilce.setItems(ilceListe);
        });
        
        ilce=new ComboBox<>();
        ilce.setSizeFull();
        ilce.setItemCaptionGenerator(Ilce::getIlceAd);
        ilce.setPlaceholder("İlçe Seçiniz");
        ilce.setEmptySelectionAllowed(false);
        
        mahalle=new TextArea();
        mahalle.setPlaceholder("Semt ve Mahalle");
        mahalle.setSizeFull();
        
        kisiGuncelleButon=new Button();
        kisiGuncelleButon.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        kisiGuncelleButon.setCaption("Güncelle");
        
        
        kisisGuncelleButonClick();
        
        kisiSilButon=new Button();
        kisiSilButon.setStyleName(ValoTheme.BUTTON_DANGER);
        kisiSilButon.setCaption("Sil");
        
        kisiSilButon.addClickListener(listener->{
            String kisiAd=this.kisiAd.getValue();
            String kisiSoyad=this.kisiSoyad.getValue();
            Date kisiDogumTarih=Date.valueOf(this.kisiDogumTarih.getValue());
            String kisiTelNo=this.kisiTelNumara.getValue();
            String kisiEposta=this.kisiEpostaAdres.getValue();
            Cinsiyet islemYapilacakCinsiyet=this.radioGroup.getValue();
            Il islemYapilacakIl=this.il.getValue();
            Ilce islemYapilacakIlce=this.ilce.getValue();
            
            Mahalle islemYapilacakMahalle=new Mahalle(kisiListesiGrid.getDuzenlePanelUye().getMahalleNo().getMahalleNo(),this.mahalle.getValue());/*
            Güncelleme yaptığımız için kayıt id'si gerekti ekleme yapsak identity olduğu için sorun çıkmayacaktı. */
            islemYapilacakMahalle.setIlNo(islemYapilacakIl);
            
            Yetki islemYapilacakYetki=kisiListesiGrid.getDuzenlePanelUye().getYetkiNo();
            
            this.islemYapilacakUye=new Uye(kisiAd,kisiSoyad,kisiListesiGrid.getDuzenlePanelUye().getUyeParola(),kisiDogumTarih,kisiTelNo,kisiEposta,islemYapilacakCinsiyet);
            islemYapilacakUye.setUyeId(kisiListesiGrid.getDuzenlePanelUye().getUyeId());
            islemYapilacakUye.setIlNo(islemYapilacakIl);
            islemYapilacakUye.setIlceNo(islemYapilacakIlce);
            islemYapilacakUye.setMahalleNo(islemYapilacakMahalle);
            islemYapilacakUye.setYetkiNo(islemYapilacakYetki);
            
            
            UyeSilOnayPanel uyeSilOnayPanel=new UyeSilOnayPanel();
            uyeSilOnayPanel.setUyeDuzenlePanel(this);
            
            PopupView onaylaPopup=new PopupView(null, uyeSilOnayPanel.panelOlustur());
            onaylaPopup.setPopupVisible(true);
            onaylaPopup.setHideOnMouseOut(false);
            
            AnaPencere.layout.addComponent(AnaPencere.absoluteLayout);//popup'ı yukarıda göstermek için css kullanmak lazım bunun için absolute layout kullandım.
            //AnaPencere.bilesenleriKaldir metodu tüm herseyi temizlediği için absoluteLayout'u burada ekledim.
            AnaPencere.absoluteLayout.addComponent(onaylaPopup,"top:-300px;left:50%");
        });

        binder=new Binder();
        binder.setBean(islemYapilacakUye);


        binder.forField(kisiAd).withValidator(new RegexpValidator("Sadece Harf Girişi Yapınız ve Boş Geçmeyiniz!", "[a-zA-z](\\D)+[a-zA-z]")).bind(Uye::getUyeAd,Uye::setUyeAd);

        binder.forField(kisiSoyad).withValidator(new RegexpValidator("Sadece Harf Yazınız ve Boş Geçmeyiniz!", "[a-zA-z](\\D)+[a-zA-z]")).bind(Uye::getUyeSoyad,Uye::setUyeSoyad);
        binder.forField(mahalle).withValidator(new StringLengthValidator("Bu Ala Boş Geçilemez ve En Fazla 600 Karakter Yazılabilir !",1,600)).bind(Uye::getMahalleNoAd,Uye::setMahalleNoAd);

        binder.forField(kisiTelNumara).withValidator(new RegexpValidator("Hatalı giriş!", "[0-9].+[0-9]")).bind(Uye::getUyeTelNo,Uye::setUyeTelNo);

        HorizontalLayout h1=new HorizontalLayout();
        h1.addComponent(kisiSilButon);
        h1.addComponent(kisiGuncelleButon);
        
        panelIcerik.addComponent(kisiAd);
        panelIcerik.addComponent(kisiSoyad);
        panelIcerik.addComponent(kisiDogumTarih);
        panelIcerik.addComponent(kisiTelNumara);
        panelIcerik.addComponent(kisiEpostaAdres);
        panelIcerik.addComponent(radioGroup);
        panelIcerik.addComponent(il);
        panelIcerik.addComponent(ilce);
        panelIcerik.addComponent(mahalle);
        panelIcerik.addComponent(h1);
        panelIcerik.setComponentAlignment(h1, Alignment.TOP_RIGHT);
        
        panel.setWidth("288px");
        panel.setContent(panelIcerik);
        return panel;
    }

    public void kisisGuncelleButonClick() {
        kisiGuncelleButon.addClickListener(listener->{
            if (binder.isValid()) {
                String kisiAd = this.kisiAd.getValue();
                String kisiSoyad = this.kisiSoyad.getValue();
                Date kisiDogumTarih = Date.valueOf(this.kisiDogumTarih.getValue());
                String kisiTelNo = this.kisiTelNumara.getValue();
                String kisiEposta = this.kisiEpostaAdres.getValue();
                Cinsiyet islemYapilacakCinsiyet = this.radioGroup.getValue();
                Il islemYapilacakIl = this.il.getValue();
                Ilce islemYapilacakIlce = this.ilce.getValue();

                Mahalle islemYapilacakMahalle = new Mahalle(kisiListesiGrid.getDuzenlePanelUye().getMahalleNo().getMahalleNo(), this.mahalle.getValue());/*
            Güncelleme yaptığımız için kayıt id'si gerekti ekleme yapsak identity olduğu için sorun çıkmayacaktı. */
                islemYapilacakMahalle.setIlNo(islemYapilacakIl);

                Uye islemYapilacakUye = new Uye(kisiAd, kisiSoyad, kisiListesiGrid.getDuzenlePanelUye().getUyeParola(), kisiDogumTarih, kisiTelNo, kisiEposta, islemYapilacakCinsiyet);
                islemYapilacakUye.setUyeId(kisiListesiGrid.getDuzenlePanelUye().getUyeId());
                islemYapilacakUye.setIlNo(islemYapilacakIl);
                islemYapilacakUye.setIlceNo(islemYapilacakIlce);
                islemYapilacakUye.setMahalleNo(islemYapilacakMahalle);
                islemYapilacakUye.setYetkiNo(kullaniciYetki);

                UyeDao guncellenecekUyeDao = new UyeDao();
                guncellenecekUyeDao.uyeGuncelle(islemYapilacakUye);
                kisiListesiGrid.getGrid().setItems(guncellenecekUyeDao.tumUyeleriGetir());//Güncelleme işleminden sonra kullanıcı listesi tablusnu tüm kullanıcıları getirecek sorguyu çalışturdık.
                panel.setVisible(false);
            }
            else{
                binder.validate();
            }
        });
    }

    public HorizontalLayout getHorizontalLayout() {
        return horizontalLayout;
    }

    public Uye getIslemYapilacakUye() {
        return islemYapilacakUye;
    }

    public void setHorizontalLayout(HorizontalLayout horizontalLayout) {
        this.horizontalLayout = horizontalLayout;
    }

    public VerticalLayout getPanelIcerik() {
        return panelIcerik;
    }

    public void setPanelIcerik(VerticalLayout panelIcerik) {
        this.panelIcerik = panelIcerik;
    }

    public TextField getKisiId() {
        return kisiId;
    }

    public void setKisiId(TextField kisiId) {
        this.kisiId = kisiId;
    }

    public TextField getKisiAd() {
        return kisiAd;
    }

    public void setKisiAd(TextField kisiAd) {
        this.kisiAd = kisiAd;
    }

    public TextField getKisiSoyad() {
        return kisiSoyad;
    }

    public void setKisiSoyad(TextField kisiSoyad) {
        this.kisiSoyad = kisiSoyad;
    }

    public TextField getKisiTelNumara() {
        return kisiTelNumara;
    }

    public void setKisiTelNumara(TextField kisiTelNumara) {
        this.kisiTelNumara = kisiTelNumara;
    }

    public DateField getKisiDogumTarih() {
        return kisiDogumTarih;
    }

    public void setKisiDogumTarih(DateField kisiDogumTarih) {
        this.kisiDogumTarih = kisiDogumTarih;
    }

    public TextField getKisiEpostaAdres() {
        return kisiEpostaAdres;
    }

    public void setKisiEpostaAdres(TextField kisiEpostaAdres) {
        this.kisiEpostaAdres = kisiEpostaAdres;
    }

    public RadioButtonGroup<Cinsiyet> getRadioGroup() {
        return radioGroup;
    }

    public void setRadioGroup(RadioButtonGroup<Cinsiyet> radioGroup) {
        this.radioGroup = radioGroup;
    }

    public KisiListesiGrid getKisiListesiGrid() {
        return kisiListesiGrid;
    }

    public void setKisiListesiGrid(KisiListesiGrid kisiListesiGrid) {
        this.kisiListesiGrid = kisiListesiGrid;
    }

    public ComboBox<Il> getIl() {
        return il;
    }

    public void setIl(ComboBox<Il> il) {
        this.il = il;
    }

    public ComboBox<Ilce> getIlce() {
        return ilce;
    }

    public void setIlce(ComboBox<Ilce> ilce) {
        this.ilce = ilce;
    }

    public TextArea getMahalle() {
        return mahalle;
    }

    public void setMahalle(TextArea mahalle) {
        this.mahalle = mahalle;
    }

    public Panel getPanel() {
        return panel;
    }

    public void setIslemYapilacakUye(Uye islemYapilacakUye) {
        this.islemYapilacakUye = islemYapilacakUye;
    }

    public Yetki getKullaniciYetki() {
        return kullaniciYetki;
    }

    public void setKullaniciYetki(Yetki kullaniciYetki) {
        this.kullaniciYetki = kullaniciYetki;
    }
}
