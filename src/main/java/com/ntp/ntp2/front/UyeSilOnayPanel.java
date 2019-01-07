/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ntp.ntp2.front;

import com.ntp.ntp2.back.UyeDao;
import com.ntp.ntp2.entities.Uye;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author EmreKirman
 */
public class UyeSilOnayPanel {
    private final Panel panel;
    private VerticalLayout verticalLayout;
    private UyeDuzenlePanel uyeDuzenlePanel;//düzenleme panelini buraya bağımlılık verdik çünkü silme işleminde evet butonuna basılırsa silinecek üyeye ihtiyacımız var.

    public UyeSilOnayPanel() {
        panel=new Panel();
    }
    
    public Panel panelOlustur(){
        Uye silinecekUye= uyeDuzenlePanel.getIslemYapilacakUye();
        verticalLayout=new VerticalLayout();
        Label labelSoru=new Label("<b>"+silinecekUye.getUyeAd()+" "+silinecekUye.getUyeSoyad()+" </b>İsimli Üyeyi Silmek İstediğinize Emin Misiniz?",ContentMode.HTML);
        verticalLayout.addComponent(labelSoru);
        
        HorizontalLayout horizontalLayout=new HorizontalLayout();
        
        Button butonEvet=new Button();
        butonEvet.setCaption("Evet");
        butonEvet.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        
        butonEvet.addClickListener(listener->{
            UyeDao silinecekUyeDao=new UyeDao();
            silinecekUyeDao.uyeSil(silinecekUye);
            uyeDuzenlePanel.getKisiListesiGrid().getGrid().setItems(silinecekUyeDao.tumUyeleriGetir());//silme işleminden sonra üye tablosunu değişiklikleri görmek için tüm verileri çektik.
            panel.setVisible(false);

            
        });
        
        Button butonHayir=new Button("Hayır");
        butonHayir.setStyleName(ValoTheme.BUTTON_DANGER);
        
        butonHayir.addClickListener(listener->{
            panel.setVisible(false);
        });
        
        horizontalLayout.addComponent(butonEvet);
        horizontalLayout.addComponent(butonHayir);
        verticalLayout.addComponent(horizontalLayout);
        verticalLayout.setComponentAlignment(horizontalLayout, Alignment.BOTTOM_RIGHT);
        
        panel.setContent(verticalLayout);
        return panel;
    }

    public VerticalLayout getVerticalLayout() {
        return verticalLayout;
    }

    public void setVerticalLayout(VerticalLayout verticalLayout) {
        this.verticalLayout = verticalLayout;
    }

    public UyeDuzenlePanel getUyeDuzenlePanel() {
        return uyeDuzenlePanel;
    }

    public void setUyeDuzenlePanel(UyeDuzenlePanel uyeDuzenlePanel) {
        this.uyeDuzenlePanel = uyeDuzenlePanel;
    }     
}
