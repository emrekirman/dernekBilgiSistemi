package com.ntp.ntp2.front;

import com.ntp.ntp2.back.IlDao;
import com.ntp.ntp2.back.IlceDao;
import com.ntp.ntp2.back.OturumYonetimi;
import com.ntp.ntp2.back.UyeDao;
import com.ntp.ntp2.entities.*;
import com.vaadin.data.Binder;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.vaadin.inputmask.InputMask;
import org.vaadin.inputmask.client.Alias;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

public class UyeBilgileriPanel implements Serializable {
    private final Panel panel;
    private VerticalLayout panelIcerik;

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

    public UyeBilgileriPanel() {
        panel=new Panel();
    }

    public Panel panelOlsutur(){

        kisiAd=new TextField();
        kisiAd.setStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        kisiAd.setIcon(VaadinIcons.USER);
        kisiAd.setSizeFull();


        kisiSoyad=new TextField();
        kisiSoyad.setStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        kisiSoyad.setIcon(VaadinIcons.USER);
        kisiSoyad.setSizeFull();


        kisiDogumTarih=new DateField();
        kisiDogumTarih.setDateFormat("dd/MM/yyyy");
        kisiDogumTarih.setPlaceholder("gg/aa/yyyy");
        kisiDogumTarih.setTextFieldEnabled(false);
        kisiDogumTarih.setSizeFull();


        kisiTelNumara=new TextField();
        kisiTelNumara.setStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        kisiTelNumara.setIcon(VaadinIcons.PHONE);
        InputMask.addTo(kisiTelNumara,"999-999-99-99");
        kisiTelNumara.setSizeFull();


        kisiEpostaAdres=new TextField();
        kisiEpostaAdres.setStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        kisiEpostaAdres.setIcon(VaadinIcons.ENVELOPE);
        InputMask.addTo(kisiEpostaAdres, Alias.EMAIL);
        kisiEpostaAdres.setPlaceholder("E-posta Adresi");
        kisiEpostaAdres.setSizeFull();


        ArrayList<Cinsiyet> cinsiyetListe=new ArrayList<>();
        cinsiyetListe.add(new Cinsiyet(1,"Kadın"));
        cinsiyetListe.add(new Cinsiyet(2,"Erkek"));

        radioGroup=new RadioButtonGroup<>("Cinsiyet");
        radioGroup.setItems(cinsiyetListe);
        radioGroup.setItemCaptionGenerator(Cinsiyet::getCinsiyetAd);
        radioGroup.setSelectedItem(islemYapilacakUye.getCinsiyetNo());

        il=new ComboBox<>("Adres");
       il.setSizeFull();
        il.setItemCaptionGenerator(Il::getIlAd);
        il.setPlaceholder("Şehir Seçiniz:");
        il.setEmptySelectionAllowed(false);
        il.setValue(islemYapilacakUye.getIlNo());//üyemizin kayıtlı ilini göstermek için kullandık.

        IlDao ilDao=new IlDao();
        ArrayList<Il> ilListe=(ArrayList<Il>) ilDao.tumIlleriGetir();
        il.setItems(ilListe);
        ilNo=il.getSelectedItem().get();//seçilen ili bir değişkene attık çünkü ilçe seçiminde filtreli bir şekilde şehire göre ilçe listelemesi yapmamız gerek.
        //il comboBoxunu doldurduk

        il.addSelectionListener(singleSelectionEvent -> {
            IlceDao ilceDao=new IlceDao();
            ArrayList<Ilce> ilceListe=(ArrayList<Ilce>) ilceDao.ileGoreIlceGetir(singleSelectionEvent.getValue());//şehir verileri değiştiğinde ilçe verilerininde değişmesini sağladık.
            ilce.setSelectedItem(ilceListe.get(0));
            ilce.setItems(ilceListe);
        });

        ilce=new ComboBox<>();
        ilce.setSizeFull();
        ilce.setEmptySelectionAllowed(false);
        ilce.setItemCaptionGenerator(Ilce::getIlceAd);
        ilce.setValue(islemYapilacakUye.getIlceNo());

        IlceDao ilceDao=new IlceDao();
        ArrayList<Ilce> ilceListe=(ArrayList<Ilce>) ilceDao.ileGoreIlceGetir(ilNo);
        ilce.setItems(ilceListe);

        mahalle=new TextArea();
        mahalle.setSizeFull();
        mahalle.setValue(islemYapilacakUye.getMahalleNo().getMahalleAd());

        kisiGuncelleButon=new Button();
        kisiGuncelleButon.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        kisiGuncelleButon.setCaption("Güncelle");

        kisiGuncelleButon.addClickListener(clickEvent -> {
            if(binder.isValid()) {//boş veya yanlış girilen veri yoksa if bloğu çalışıyor.
                int uyeId = islemYapilacakUye.getUyeId();
                String uyeAd = kisiAd.getValue();
                String uyeSoyad = kisiSoyad.getValue();
                Date uyeDogumTarih = Date.valueOf(kisiDogumTarih.getValue());
                String uyeTelNo = kisiTelNumara.getValue();
                String uyeEpostaAdres = kisiEpostaAdres.getValue();

                Cinsiyet uyeCinsiyetNo = radioGroup.getValue();
                Il uyeIlNo = il.getValue();
                Ilce uyeIlceNo = ilce.getValue();
                Mahalle uyeMahalleNo = new Mahalle(islemYapilacakUye.getMahalleNo().getMahalleNo(), mahalle.getValue());//mahalle güncellemesi için mahalle ıdsine ihtiyaç var.
                uyeMahalleNo.setIlNo(uyeIlNo);
                Yetki uyeYetkiNo = islemYapilacakUye.getYetkiNo();

                islemYapilacakUye = new Uye(uyeId, uyeAd, uyeSoyad, uyeDogumTarih, uyeTelNo, uyeEpostaAdres, OturumYonetimi.uyeSinifi.getUyeParola(), uyeCinsiyetNo);//üyenin parola kısmını oturum sınıfından aldık zaten aynı üyeler.
                islemYapilacakUye.setIlNo(uyeIlNo);
                islemYapilacakUye.setIlceNo(uyeIlceNo);
                islemYapilacakUye.setMahalleNo(uyeMahalleNo);
                islemYapilacakUye.setYetkiNo(uyeYetkiNo);

                UyeDao uyeDao = new UyeDao();
                uyeDao.guncelle(islemYapilacakUye);

                ArrayList<Uye> oturumGuncelleUyeList;//üye bilgileri güncellendikten sonra oturum üzerinde kalan eski üye bilgilerini de düzenlememiz gerekiyor.
                oturumGuncelleUyeList = (ArrayList<Uye>) uyeDao.idSifreyeGoreUyeGetir(islemYapilacakUye);
                OturumYonetimi.oturumBaslat(oturumGuncelleUyeList.get(0));


                UyeBilgileriPanel uyeBilgileriPanel = new UyeBilgileriPanel();
                uyeBilgileriPanel.setIslemYapilacakUye(OturumYonetimi.uyeSinifi);
                AnaPencere.bilesenleriKaldir();
                AnaPencere.bilesenEkle(uyeBilgileriPanel.panelOlsutur());
                AnaPencere.bilesenHizala(uyeBilgileriPanel.getPanel(), Alignment.TOP_CENTER);
            }
            else{//eğer boş bırakılan alan veya yanlış giriş yapılan alan varsa burası çalışacak ve bileşenlerin kızarması sağlanacak.
                binder.validate();
            }
        });

        binder=new Binder<>();
        binder.setBean(islemYapilacakUye);

        binder.forField(kisiAd).withValidator(new RegexpValidator("Sadece harf girişi yapınız.","[a-zA-z](\\D)+[a-zA-z]")).bind(Uye::getUyeAd,Uye::setUyeAd);
        binder.forField(kisiSoyad).withValidator(new RegexpValidator("Sadece harf girişi yapınız.","[a-zA-z](\\D)+[a-zA-z]")).bind(Uye::getUyeSoyad,Uye::setUyeSoyad);
        binder.forField(kisiTelNumara).withValidator(new StringLengthValidator("13 Karakter Girmek Zorundasınız.",10,10)).bind(Uye::getUyeTelNo,Uye::setUyeTelNo);
        binder.forField(mahalle).withValidator(new StringLengthValidator("Bu alan boş geçilemez.",1,200)).bind(Uye::getMahalleNoAd,Uye::setMahalleNoAd);

        kisiAd.setValue(islemYapilacakUye.getUyeAd());
        kisiSoyad.setValue(islemYapilacakUye.getUyeSoyad());
        kisiDogumTarih.setValue(LocalDate.parse(islemYapilacakUye.getUyeDogumTarih().toString()));
        kisiTelNumara.setValue(islemYapilacakUye.getUyeTelNo());
        kisiEpostaAdres.setValue(islemYapilacakUye.getUyeEposta());
        mahalle.setValue(islemYapilacakUye.getMahalleNo().getMahalleAd());


        panelIcerik=new VerticalLayout();
        panelIcerik.addComponent(kisiAd);
        panelIcerik.addComponent(kisiSoyad);
        panelIcerik.addComponent(kisiDogumTarih);
        panelIcerik.addComponent(kisiTelNumara);
        panelIcerik.addComponent(kisiEpostaAdres);
        panelIcerik.addComponent(radioGroup);
        panelIcerik.addComponent(il);
        panelIcerik.addComponent(ilce);
        panelIcerik.addComponent(mahalle);
        panelIcerik.addComponent(kisiGuncelleButon);
        panelIcerik.setComponentAlignment(kisiGuncelleButon,Alignment.BOTTOM_RIGHT);

        panel.setCaption("Üye Bilgileri "+"| Sıra No:"+islemYapilacakUye.getUyeId().toString());
        panel.setWidth("288px");
        panel.setContent(panelIcerik);
        return panel;
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

    public Button getKisiGuncelleButon() {
        return kisiGuncelleButon;
    }

    public void setKisiGuncelleButon(Button kisiGuncelleButon) {
        this.kisiGuncelleButon = kisiGuncelleButon;
    }

    public Button getKisiSilButon() {
        return kisiSilButon;
    }

    public void setKisiSilButon(Button kisiSilButon) {
        this.kisiSilButon = kisiSilButon;
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

    public Il getIlNo() {
        return ilNo;
    }

    public void setIlNo(Il ilNo) {
        this.ilNo = ilNo;
    }

    public Uye getIslemYapilacakUye() {
        return islemYapilacakUye;
    }

    public void setIslemYapilacakUye(Uye islemYapilacakUye) {
        this.islemYapilacakUye = islemYapilacakUye;
    }
}
