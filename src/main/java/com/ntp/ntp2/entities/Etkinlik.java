/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ntp.ntp2.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Emre
 */
@Entity
@Table(name = "etkinlik")
@XmlRootElement
public class Etkinlik implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "etkinlikId")
    private Integer etkinlikId;
    @Basic(optional = false)
    @Column(name = "etkinlikBaslik")
    private String etkinlikBaslik;
    @Basic(optional = false)
    @Column(name = "etkinlikTarih")
    @Temporal(TemporalType.DATE)
    private Date etkinlikTarih;
    @Basic(optional = false)
    @Lob
    @Column(name = "etkinlikIcerik")
    private String etkinlikIcerik;
    @Basic(optional = false)
    @Column(name = "sistemGirisTarih")
    @Temporal(TemporalType.DATE)
    private Date sistemGirisTarih;
    @JoinColumn(name = "uyeId", referencedColumnName = "uyeId")
    @ManyToOne(optional = false)
    private Uye uyeId;

    public Etkinlik() {
    }

    public Etkinlik(Integer etkinlikId) {
        this.etkinlikId = etkinlikId;
    }

    public Etkinlik(Integer etkinlikId, String etkinlikBaslik, Date etkinlikTarih, String etkinlikIcerik, Date sistemGirisTarih) {
        this.etkinlikId = etkinlikId;
        this.etkinlikBaslik = etkinlikBaslik;
        this.etkinlikTarih = etkinlikTarih;
        this.etkinlikIcerik = etkinlikIcerik;
        this.sistemGirisTarih = sistemGirisTarih;
    }

    public Etkinlik(String etkinlikBaslik, Date etkinlikTarih, String etkinlikIcerik, Date sistemGirisTarih) {
        this.etkinlikBaslik = etkinlikBaslik;
        this.etkinlikTarih = etkinlikTarih;
        this.etkinlikIcerik = etkinlikIcerik;
        this.sistemGirisTarih = sistemGirisTarih;
    }

    public Integer getEtkinlikId() {
        return etkinlikId;
    }

    public void setEtkinlikId(Integer etkinlikId) {
        this.etkinlikId = etkinlikId;
    }

    public String getEtkinlikBaslik() {
        return etkinlikBaslik;
    }

    public void setEtkinlikBaslik(String etkinlikBaslik) {
        this.etkinlikBaslik = etkinlikBaslik;
    }

    public Date getEtkinlikTarih() {
        return etkinlikTarih;
    }

    public void setEtkinlikTarih(Date etkinlikTarih) {
        this.etkinlikTarih = etkinlikTarih;
    }

    public String getEtkinlikIcerik() {
        return etkinlikIcerik;
    }

    public void setEtkinlikIcerik(String etkinlikIcerik) {
        this.etkinlikIcerik = etkinlikIcerik;
    }

    public Date getSistemGirisTarih() {
        return sistemGirisTarih;
    }

    public void setSistemGirisTarih(Date sistemGirisTarih) {
        this.sistemGirisTarih = sistemGirisTarih;
    }

    public Uye getUyeId() {
        return uyeId;
    }

    public void setUyeId(Uye uyeId) {
        this.uyeId = uyeId;
    }

    //etkinlik listesi tablo üzerinde kullanıcı bilgilerini göstermek için bu metotları yazıyorum
    public String getUyeIdUyeAd(){
        return this.uyeId.getUyeAd();
    }

    public String getUyeIdUyeSoyad(){
        return this.uyeId.getUyeSoyad();
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (etkinlikId != null ? etkinlikId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Etkinlik)) {
            return false;
        }
        Etkinlik other = (Etkinlik) object;
        if ((this.etkinlikId == null && other.etkinlikId != null) || (this.etkinlikId != null && !this.etkinlikId.equals(other.etkinlikId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ntp.ntp2.entities.Etkinlik[ etkinlikId=" + etkinlikId + " ]";
    }
    
}
