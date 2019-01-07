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
@Table(name = "talep")
@XmlRootElement
public class Talep implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "talepId")
    private Integer talepId;
    @Basic(optional = false)
    @Column(name = "talepBaslik")
    private String talepBaslik;
    @Basic(optional = false)
    @Lob
    @Column(name = "talepIcerik")
    private String talepIcerik;
    @Basic(optional = false)
    @Column(name = "talepTarih")
    @Temporal(TemporalType.DATE)
    private Date talepTarih;
    @JoinColumn(name = "uyeId", referencedColumnName = "uyeId")
    @ManyToOne(optional = false)
    private Uye uyeId;

    public Talep() {
    }

    public Talep(Integer talepId) {
        this.talepId = talepId;
    }

    public Talep(Integer talepId, String talepBaslik, String talepIcerik, Date talepTarih) {
        this.talepId = talepId;
        this.talepBaslik = talepBaslik;
        this.talepIcerik = talepIcerik;
        this.talepTarih = talepTarih;
    }

    public Talep( String talepBaslik, String talepIcerik, Date talepTarih) {
        this.talepBaslik = talepBaslik;
        this.talepIcerik = talepIcerik;
        this.talepTarih = talepTarih;
    }

    public Integer getTalepId() {
        return talepId;
    }

    public void setTalepId(Integer talepId) {
        this.talepId = talepId;
    }

    public String getTalepBaslik() {
        return talepBaslik;
    }

    public void setTalepBaslik(String talepBaslik) {
        this.talepBaslik = talepBaslik;
    }

    public String getTalepIcerik() {
        return talepIcerik;
    }

    public void setTalepIcerik(String talepIcerik) {
        this.talepIcerik = talepIcerik;
    }

    public Date getTalepTarih() {
        return talepTarih;
    }

    public void setTalepTarih(Date talepTarih) {
        this.talepTarih = talepTarih;
    }

    public Uye getUyeId() {
        return uyeId;
    }

    public void setUyeId(Uye uyeId) {
        this.uyeId = uyeId;
    }

    //gridde istenilen verileri listelemek için bu get metotolarını kullandık.
    public String getUyeAdSoyad(){
        return uyeId.getUyeAd()+" "+uyeId.getUyeSoyad();
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (talepId != null ? talepId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Talep)) {
            return false;
        }
        Talep other = (Talep) object;
        if ((this.talepId == null && other.talepId != null) || (this.talepId != null && !this.talepId.equals(other.talepId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ntp.ntp2.entities.Talep[ talepId=" + talepId + " ]";
    }
    
}
