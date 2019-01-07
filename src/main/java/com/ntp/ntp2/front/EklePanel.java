package com.ntp.ntp2.front;
import com.ntp.ntp2.back.IlDao;
import com.ntp.ntp2.back.IlceDao;
import com.ntp.ntp2.back.MahalleDao;
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
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.Button;
import com.vaadin.ui.TextField;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.icons.VaadinIcons;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import org.vaadin.inputmask.InputMask;
import org.vaadin.inputmask.client.Alias;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 *
 * @author emreKirman
 */
public class EklePanel implements Serializable {
    private final Panel panel;
    private TextField kisiId;
    private TextField kisiAd;
    private TextField kisiSoyad;
    private PasswordField kisiParola;
    private TextField kisiTelNumara;
    private DateField kisiDogumTarih;
    private TextField kisiEpostaAdres;
    private RadioButtonGroup<Cinsiyet> radioGroup;
    private Button kisiEkleButton;
    private KisiListesiGrid kisiListesiGrid;
    private ComboBox<Il> il;
    private ComboBox<Ilce> ilce;
    private TextArea mahalle;
    private Binder<Uye> binder;
    private TabSheet yoneticiIslemleriTabSheet;//üye eklendikten sonra üye listesi sayfasına direkt geçiş için bu bağımlılık oluşturuldu.
    //bu sınıf yönetici üyelerin yeni üyeler eklemesi için oluşturuldu.
    

    public EklePanel(String panelAdi) {
        panel=new Panel(panelAdi);
    }
    
    public Panel panelOlustur(){
        VerticalLayout panelIcerik=new VerticalLayout();
        
        kisiAd=new TextField();
        kisiAd.setPlaceholder("Ad");
        kisiAd.setStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        kisiAd.setIcon(VaadinIcons.USER);
        kisiAd.setMaxLength(55);
        kisiAd.setSizeFull();
        
        kisiSoyad=new TextField();
        kisiSoyad.setPlaceholder("Soyad");
        kisiSoyad.setStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        kisiSoyad.setIcon(VaadinIcons.USER);
        kisiSoyad.setMaxLength(55);
        kisiSoyad.setSizeFull();
        
        kisiParola=new PasswordField();
        kisiParola.setPlaceholder("Parola");
        kisiParola.setStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        kisiParola.setIcon(VaadinIcons.KEY);
        kisiParola.setMaxLength(55);
        kisiParola.setSizeFull();
        
        kisiDogumTarih=new DateField();
        kisiDogumTarih.setPlaceholder("Doğum Tarihi");
        kisiDogumTarih.setDateFormat("dd/MM/yyyy");
        InputMask.addTo(kisiDogumTarih,Alias.DATE);
        kisiDogumTarih.setSizeFull();
        
        kisiTelNumara=new TextField();
        kisiTelNumara.setPlaceholder("Telefon Numarası");
        kisiTelNumara.setStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        kisiTelNumara.setIcon(VaadinIcons.PHONE);
        //InputMask.addTo(kisiTelNumara,"+\\90 999-999-99-99");
        InputMask.addTo(kisiTelNumara,"999-999-99-99");
        kisiTelNumara.setMaxLength(55);
        kisiTelNumara.setSizeFull();
        
        kisiEpostaAdres=new TextField();
        kisiEpostaAdres.setStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        kisiEpostaAdres.setIcon(VaadinIcons.ENVELOPE);
        kisiEpostaAdres.setPlaceholder("E-posta Adresi");
        InputMask.addTo(kisiEpostaAdres, Alias.EMAIL);
        kisiEpostaAdres.setMaxLength(55);
        kisiEpostaAdres.setSizeFull();
        
        ArrayList<Cinsiyet> cinsiyetList=new ArrayList<>();
        cinsiyetList.add(new Cinsiyet(1, "Kadın"));
        cinsiyetList.add(new Cinsiyet(2, "Erkek"));
        
        radioGroup=new RadioButtonGroup<>("Cinsiyet");
        radioGroup.setItems(cinsiyetList);
        radioGroup.setItemCaptionGenerator(Cinsiyet::getCinsiyetAd);
        radioGroup.setSelectedItem(cinsiyetList.get(0));
        
        radioGroup.addSelectionListener(listener->{
            Notification.show(listener.getSelectedItem().get().getCinsiyetNo()+" "+radioGroup.getSelectedItem().get().getCinsiyetAd());
        });
        
        ilce=new ComboBox<>();
        ilce.setSizeFull();
        ilce.setItemCaptionGenerator(Ilce::getIlceAd);
        ilce.setPlaceholder("İlçe Seçiniz");
        ilce.setEmptySelectionAllowed(false);
        
        IlceDao ilceDao=new IlceDao();
        
        IlDao ilDao=new IlDao();
        List<Il> ilListe=ilDao.tumIlleriGetir();
        
        il=new ComboBox<>("Adres");
        il.setSizeFull();
        il.setItemCaptionGenerator(Il::getIlAd);
        il.setItems(ilListe);
        il.setPlaceholder("Şehir Seçiniz");
        il.setEmptySelectionAllowed(false);
        il.setSelectedItem(ilListe.get(0));
        
        il.addSelectionListener(listener->{
            Il ilceParametreIl=il.getSelectedItem().get();
            List<Ilce> ilceListe=ilceDao.ileGoreIlceGetir(ilceParametreIl);
            ilce.setItems(ilceListe);
            ilce.setSelectedItem(ilceListe.get(0));
            panelIcerik.addComponent(ilce,8);
            panelIcerik.addComponent(mahalle,9);

        });
        
        mahalle=new TextArea();
        mahalle.setPlaceholder("Semt ve Mahalle");
        mahalle.setSizeFull();
        
        /*ilce.addSelectionListener(listener->{
            Notification.show(ilce.getValue().getIlceAd());
            panelIcerik.addComponent(mahalle,8);
        });*/
         
        kisiEkleButton=new Button("Ekle");
        kisiEkleButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
        kisiEkleButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        kisiEkleClick();
        
        binder=new Binder();
        Uye k=new Uye();
        k.setMahalleNo(new Mahalle(1,"Şimşir sokak."));
        binder.setBean(k);
        
        //BINDER BASLANGIC
        binder.forField(kisiParola).withValidator(new StringLengthValidator("En az 8 karakter girişi yapmanız gerek.", 8, 16)).bind(Uye::getUyeParola,Uye::setUyeParola);
        binder.forField(kisiAd).withValidator(new RegexpValidator("Sadece Harf Girişi Yapınız ve Boş Geçmeyiniz!", "[a-zA-z](\\D)+[a-zA-z]")).bind(Uye::getUyeAd,Uye::setUyeAd);
        binder.forField(kisiSoyad).withValidator(new RegexpValidator("Sadece Harf Yazınız ve Boş Geçmeyiniz!", "[a-zA-z](\\D)+[a-zA-z]")).bind(Uye::getUyeSoyad,Uye::setUyeSoyad);
        binder.forField(mahalle).withValidator(new StringLengthValidator("Bu Ala Boş Geçilemez ve En Fazla 600 Karakter Yazılabilir !",1,600)).bind(Uye::getMahalleNoAd,Uye::setMahalleNoAd);
        binder.forField(kisiTelNumara).withValidator(new RegexpValidator("Hatalı giriş!", "[0-9].+[0-9]")).bind(Uye::getUyeTelNo,Uye::setUyeTelNo);

        kisiDogumTarih.setValue(LocalDate.now());//örnek veri girişi için.
        
        panelIcerik.addComponent(kisiAd);
        panelIcerik.addComponent(kisiSoyad);
        panelIcerik.addComponent(kisiParola);
        panelIcerik.addComponent(kisiDogumTarih);
        panelIcerik.addComponent(kisiTelNumara);
        panelIcerik.addComponent(kisiEpostaAdres);
        panelIcerik.addComponent(radioGroup);
        panelIcerik.addComponent(il);
        
        panelIcerik.addComponent(kisiEkleButton);
        panelIcerik.setComponentAlignment(kisiEkleButton, Alignment.BOTTOM_RIGHT);

        panel.setStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        panel.setIcon(VaadinIcons.SIGN_IN);
        panel.setSizeUndefined();
        panel.setStyleName(ValoTheme.PANEL_WELL);
        panel.setWidth("288px");
        panel.setResponsive(true);//ekran boyutu ufaldığında nesnemizin bu duruma duyarlı olması sağlandı.
        panel.setContent(panelIcerik);//panel içerisine ızgara ataması yapıldı.
        
        return panel;
    }    

    public void kisiEkleClick() {
        kisiEkleButton.addClickListener((event) -> {
            if(binder.isValid()){
                String kisiAd=this.kisiAd.getValue();
                String kisiSoyad=this.kisiSoyad.getValue();
                String kisiParola=this.kisiParola.getValue();
                Date tarih=Date.valueOf(this.kisiDogumTarih.getValue());
                String kisiTelefonNumara=this.kisiTelNumara.getValue();
                String epostaAdres=this.kisiEpostaAdres.getValue();
                
                Yetki yetkiNo=new Yetki(1, "Standart");
                Cinsiyet cinsiyetNo= radioGroup.getSelectedItem().get();
                Il ilNo=il.getValue();
                Ilce ilceNo=ilce.getValue();
                Mahalle mahalleNo=new Mahalle(mahalle.getValue());
                mahalleNo.setIlNo(ilNo);
                
                Uye eklenecekUye=new Uye();
                eklenecekUye.setUyeAd(kisiAd);
                eklenecekUye.setUyeSoyad(kisiSoyad);
                eklenecekUye.setUyeParola(kisiParola);
                eklenecekUye.setUyeDogumTarih(tarih);
                eklenecekUye.setUyeTelNo(kisiTelefonNumara);
                eklenecekUye.setUyeEposta(epostaAdres);
                eklenecekUye.setYetkiNo(yetkiNo);
                eklenecekUye.setCinsiyetNo(cinsiyetNo);
                eklenecekUye.setIlNo(ilNo);
                eklenecekUye.setIlceNo(ilceNo);
                eklenecekUye.setMahalleNo(mahalleNo);
                
                UyeDao uyeDao=new UyeDao();
                MahalleDao mahalleDao=new MahalleDao();
                try{
                    mahalleDao.mahalleEkle(mahalleNo);
                    uyeDao.ekle(eklenecekUye);
                    ArrayList<Uye> yenilenenUyeler=(ArrayList<Uye>) uyeDao.tumUyeleriGetir();//Kullanıcı Eklendikten sonra tablodaki üyeleri yenilemek için.
                    kisiListesiGrid.getGrid().setItems(yenilenenUyeler);//Kullanıcı Eklendikten sonra tablodaki üyeleri yenilemek için.
                    yoneticiIslemleriTabSheet.setSelectedTab(1);//Kullanıcı Eklendikten sonra Üye listesi tabına geçiş için
                    
                }catch(Exception ex){
                    ex.printStackTrace();
                }
                kisiEkleButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
            }
            else{
                binder.validate();
                kisiEkleButton.setStyleName(ValoTheme.BUTTON_DANGER);
            }
            
        });
    }

    public KisiListesiGrid getKisiListesiGrid() {
        return kisiListesiGrid;
    }

    public void setKisiListesiGrid(KisiListesiGrid kisiListesiGrid) {
        this.kisiListesiGrid = kisiListesiGrid;
    }

    public void setYoneticiIslemleriTabSheet(TabSheet yoneticiIslemleriTabSheet) {
        this.yoneticiIslemleriTabSheet = yoneticiIslemleriTabSheet;
    }

    public TabSheet getYoneticiIslemleriTabSheet() {
        return yoneticiIslemleriTabSheet;
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

    public Button getKisiEkleButton() {
        return kisiEkleButton;
    }

    public void setKisiEkleButton(Button kisiEkleButton) {
        this.kisiEkleButton = kisiEkleButton;
    }

    public Binder<Uye> getBinder() {
        return binder;
    }

    public void setBinder(Binder<Uye> binder) {
        this.binder = binder;
    }

    public Panel getPanel() {
        return panel;
    }
}
