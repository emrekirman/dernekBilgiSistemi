/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ntp.ntp2.entities;

import java.io.Serializable;
import java.util.Date;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Emre
 */
@Entity
@Table(name = "uye")
@XmlRootElement
public class Uye implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "uyeId")
    private Integer uyeId;
    @Basic(optional = false)
    @Column(name = "uyeAd")
    private String uyeAd;
    @Basic(optional = false)
    @Column(name = "uyeSoyad")
    private String uyeSoyad;
    @Basic(optional = false)
    @Column(name = "uyeDogumTarih")
    @Temporal(TemporalType.DATE)
    private Date uyeDogumTarih;
    @Basic(optional = false)
    @Column(name = "uyeTelNo")
    private String uyeTelNo;
    @Basic(optional = false)
    @Column(name = "uyeEposta")
    private String uyeEposta;
    @Basic(optional = false)
    @Column(name = "uyeParola")
    private String uyeParola;
    @JoinColumn(name = "cinsiyetNo", referencedColumnName = "cinsiyetNo")
    @ManyToOne(optional = false)
    private Cinsiyet cinsiyetNo;
    @JoinColumn(name = "ilNo", referencedColumnName = "ilNo")
    @ManyToOne(optional = false)
    private Il ilNo;
    @JoinColumn(name = "ilceNo", referencedColumnName = "ilceNo")
    @ManyToOne(optional = false)
    private Ilce ilceNo;
    
    @JoinColumn(name = "mahalleNo", referencedColumnName = "mahalleNo")
    @ManyToOne(optional = false,targetEntity = Mahalle.class)
    private Mahalle mahalleNo;
    
    /*@JoinColumn(name = "semtNo", referencedColumnName = "semtNo")
    @ManyToOne(optional = false)
    private Semt semtNo;*/
    @JoinColumn(name = "yetkiNo", referencedColumnName = "yetkiNo")
    @ManyToOne(optional = false)
    private Yetki yetkiNo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "uyeId")
    private List<Etkinlik> etkinlikList;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER,mappedBy = "uyeId")
    private List<Talep> talepList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "uyeId")
    private List<Duyuru> duyuruList;

    public Uye() {
    }

    public Uye(Integer uyeId) {
        this.uyeId = uyeId;
    }
    
    public Uye(Integer uyeId,String uyeParola){
        this.uyeId=uyeId;
        this.uyeParola=uyeParola;
    }

    public Uye(Integer uyeId, String uyeAd, String uyeSoyad, Date uyeDogumTarih, String uyeTelNo, String uyeEposta, String uyeParola,Cinsiyet cinsiyetNo) {
        this.uyeId = uyeId;
        this.uyeAd = uyeAd;
        this.uyeSoyad = uyeSoyad;
        this.uyeDogumTarih = uyeDogumTarih;
        this.uyeTelNo = uyeTelNo;
        this.uyeEposta = uyeEposta;
        this.uyeParola = uyeParola;
        this.cinsiyetNo=cinsiyetNo;
    }
    public Uye(String uyeAd, String uyeSoyad,String uyeParola, Date uyeDogumTarih, String uyeTelNo, String uyeEposta,Cinsiyet cinsiyetNo) {
        this.uyeAd = uyeAd;
        this.uyeSoyad = uyeSoyad;
        this.uyeParola=uyeParola;
        this.uyeDogumTarih = uyeDogumTarih;
        this.uyeTelNo = uyeTelNo;
        this.uyeEposta = uyeEposta;
        this.cinsiyetNo=cinsiyetNo;
    }

    public Integer getUyeId() {
        return uyeId;
    }

    public void setUyeId(Integer uyeId) {
        this.uyeId = uyeId;
    }

    public String getUyeAd() {
        return uyeAd;
    }

    public void setUyeAd(String uyeAd) {
        this.uyeAd = uyeAd;
    }

    public String getUyeSoyad() {
        return uyeSoyad;
    }

    public void setUyeSoyad(String uyeSoyad) {
        this.uyeSoyad = uyeSoyad;
    }

    public Date getUyeDogumTarih() {
        return uyeDogumTarih;
    }

    public void setUyeDogumTarih(Date uyeDogumTarih) {
        this.uyeDogumTarih = uyeDogumTarih;
    }

    public String getUyeTelNo() {
        return uyeTelNo;
    }

    public void setUyeTelNo(String uyeTelNo) {
        this.uyeTelNo = uyeTelNo;
    }

    public String getUyeEposta() {
        return uyeEposta;
    }

    public void setUyeEposta(String uyeEposta) {
        this.uyeEposta = uyeEposta;
    }

    public String getUyeParola() {
        return uyeParola;
    }

    public void setUyeParola(String uyeParola) {
        this.uyeParola = uyeParola;
    }

    public Cinsiyet getCinsiyetNo() {
        return cinsiyetNo;
    }

    public void setCinsiyetNo(Cinsiyet cinsiyetNo) {
        this.cinsiyetNo = cinsiyetNo;
    }

    public Il getIlNo() {
        return ilNo;
    }

    public void setIlNo(Il ilNo) {
        this.ilNo = ilNo;
    }

    public Ilce getIlceNo() {
        return ilceNo;
    }

    public void setIlceNo(Ilce ilceNo) {
        this.ilceNo = ilceNo;
    }

    public Mahalle getMahalleNo() {
        return mahalleNo;
    }

    public void setMahalleNo(Mahalle mahalleNo) {
        this.mahalleNo = mahalleNo;
    }

    /*public Semt getSemtNo() {
        return semtNo;
    }

    public void setSemtNo(Semt semtNo) {
        this.semtNo = semtNo;
    }*/

    public Yetki getYetkiNo() {
        return yetkiNo;
    }

    public void setYetkiNo(Yetki yetkiNo) {
        this.yetkiNo = yetkiNo;
    }
//Grid sütun listelemek için kullanılan metotlar
    public String getIlNoAd(){
        return ilNo.getIlAd().toUpperCase();
    }
    
    public String getIlceNoAd(){
        return ilceNo.getIlceAd();
    }
    
    public String getCinsiyetNoAd(){
        return this.cinsiyetNo.getCinsiyetAd();
    }
    
    public String getYetkiNoAd(){
        return this.yetkiNo.getYetkiAd();
    }



    //Binder ile validate yapmak için bu metotları kullanıcaz.
    public String getMahalleNoAd(){
        return mahalleNo.getMahalleAd();
    }
    public void setMahalleNoAd(String mahalleAd){
        this.mahalleNo.setMahalleAd(mahalleAd);
    }
    public LocalDate getUyeDogumTarihLocalDate(){
        return LocalDate.parse(uyeDogumTarih.toString());
    }
    public void setUyeDogumTarihLocalDate(LocalDate tarih){
        //this.uyeDogumTarih=Date.from(Instant.parse(tarih.toString()));
    }
//SON
    
    @XmlTransient
    public List<Etkinlik> getEtkinlikList() {
        return etkinlikList;
    }

    public void setEtkinlikList(List<Etkinlik> etkinlikList) {
        this.etkinlikList = etkinlikList;
    }

    @XmlTransient
    public List<Talep> getTalepList() {
        return talepList;
    }

    public void setTalepList(List<Talep> talepList) {
        this.talepList = talepList;
    }

    @XmlTransient
    public List<Duyuru> getDuyuruList() {
        return duyuruList;
    }

    public void setDuyuruList(List<Duyuru> duyuruList) {
        this.duyuruList = duyuruList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (uyeId != null ? uyeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Uye)) {
            return false;
        }
        Uye other = (Uye) object;
        if ((this.uyeId == null && other.uyeId != null) || (this.uyeId != null && !this.uyeId.equals(other.uyeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ntp.ntp2.entities.Uye[ uyeId=" + uyeId + " ]";
    }
    
}
