/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ntp.ntp2.front;

import com.vaadin.ui.*;

/**
 *
 * @author Emre
 */
public class AnaPencere {
    public static VerticalLayout layout;
    public static AbsoluteLayout absoluteLayout=new AbsoluteLayout();
    //Bu sınıf ana sınıfım tüm modül yönelndirmelerini buradan yapıyoruz.
    
    public static VerticalLayout anaPencereOlustur(){
        AnaPencere.layout = new VerticalLayout();//dikey ızgara oluşturduk.
        DuyuruPanel duyuruPanel=new DuyuruPanel();//anasayfamızda yayınlanan duyurular olacağı için duyuruların listelendiği sınıfımızdan nesne türettik.

        MenuNavigasyon menuNavigasyon=new MenuNavigasyon();//yönlendirmeler için menü çubuğumuzu oluşturduk.
        
        layout.addComponent(menuNavigasyon.menuOlustur());//menu nesnemizi ızgaraya ekledik.
        
        layout.setMargin(false);//ızgarada bileşenler arası boşluk olamasını sağladık.
        layout.addComponent(duyuruPanel.accordionOlustur());//ızgaraya duyurularımızı listeleyecek olan metodumuzu çağırdık.
        layout.setComponentAlignment(duyuruPanel.getAccordion(), Alignment.MIDDLE_CENTER);//duyuru listemizi sayfa içerisinde ortaladık.
        return layout;
    }
     public static void bilesenleriKaldir(){//bu metot bir modül ile işimiz bittiğinde veya sayfa yönlendirmesi yapacağımızda kullanılmak için yazıldı.
         MenuNavigasyon menuNavigasyon=new MenuNavigasyon();
         layout.removeAllComponents();
         layout.addComponent(menuNavigasyon.menuOlustur());
         //layout.addComponent(absoluteLayout);
     }

     
     public static void bilesenEkle(Component bilesen){
         layout.addComponent(bilesen);
     }
     public static void bilesenEkle(Component bilesen,int index){
         layout.addComponent(bilesen,index);
     }
     
     public static void bilesenHizala(Component bilesen,Alignment hiza){
         layout.setComponentAlignment(bilesen, hiza);
     }
     
     public static VerticalLayout aktifSayfa(){
         
         return layout;
     }
}
