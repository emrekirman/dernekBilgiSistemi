/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ntp.ntp2.front;

import com.ntp.ntp2.back.EtkinlikDao;
import com.ntp.ntp2.entities.Etkinlik;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author emreKirman
 */
public class EtkinlikListesi {
    private final Accordion accordion;
    private List<Etkinlik> etkinlikListe;

    public EtkinlikListesi() {
        accordion=new Accordion();
    }
    
    public Accordion etkinlikListesiOlustur(){
        etkinlikListe=new ArrayList<>();//etkinlileri içerisine atacağımız liste.
        EtkinlikDao etkinlikDao=new EtkinlikDao();
        etkinlikListe=(ArrayList<Etkinlik>)etkinlikDao.tumKayitlariGetir();//veritabanından tüm etkinlikleri getiren işlem.

        for(int i = 0; i<etkinlikListe.size(); i++){//burada foreach yerine for kullanılmasının sebebi bu şekilde de çözüm olacağını göstermektir.
            if (!etkinlikListe.get(i).getEtkinlikBaslik().isEmpty()){//liste boş değilse aşağıdaki bloğu çalıştır.
                LocalDate etkinlikTarihi=LocalDate.parse(etkinlikListe.get(i).getEtkinlikTarih().toString());
                String tarhi=etkinlikTarihi.format(DateTimeFormatter.ofPattern("dd MM yyyy"));
                etkinlikEkle(etkinlikListe.get(i).getEtkinlikBaslik()," | "+tarhi,etkinlikListe.get(i).getEtkinlikIcerik());
            }
            else{
                continue;
            }
        }

        accordion.setWidth("80%");//etkinlik listesini içeren bileşenin sayfa içerisinde ki boyutu.
        return accordion;
    }
    
    public void etkinlikEkle(String baslik,String tarih,String icerik){//for döngüsü içerisinde çağırılan metot.
        //bu metot for döngüsünde her bir etkinlik nesnesini panel bileşeni içerisine yerleştirir ve gösterir.
        Layout icerikLayout=new VerticalLayout();
        icerikLayout.setSizeFull();
        
        Label icerikLabel=new Label();
        icerikLabel.setSizeFull();
        
        icerikLabel.setContentMode(ContentMode.HTML);
        icerikLabel.setValue(icerik);
        icerikLayout.addComponent(icerikLabel);
        
        accordion.addTab(icerikLayout,baslik+" "+tarih,VaadinIcons.CALENDAR);
    }

    public Accordion getAccordion() {
        return accordion;
    }
    
    
}
