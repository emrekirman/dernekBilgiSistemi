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
@Table(name = "duyuru")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Duyuru.findAll", query = "SELECT d FROM Duyuru d")
    , @NamedQuery(name = "Duyuru.findByDuyuruId", query = "SELECT d FROM Duyuru d WHERE d.duyuruId = :duyuruId")
    , @NamedQuery(name = "Duyuru.findByDuyuruBaslik", query = "SELECT d FROM Duyuru d WHERE d.duyuruBaslik = :duyuruBaslik")
    , @NamedQuery(name = "Duyuru.findByDuyuruTarih", query = "SELECT d FROM Duyuru d WHERE d.duyuruTarih = :duyuruTarih")})
public class Duyuru implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "duyuruId")
    private Integer duyuruId;
    @Basic(optional = false)
    @Column(name = "duyuruBaslik")
    private String duyuruBaslik;
    @Basic(optional = false)
    @Lob
    @Column(name = "duyuruIcerik")
    private String duyuruIcerik;
    @Basic(optional = false)
    @Column(name = "duyuruTarih")
    @Temporal(TemporalType.DATE)
    private Date duyuruTarih;
    @JoinColumn(name = "uyeId", referencedColumnName = "uyeId")
    @ManyToOne(optional = false)
    private Uye uyeId;

    public Duyuru() {
    }

    public Duyuru(Integer duyuruId) {
        this.duyuruId = duyuruId;
    }

    public Duyuru(Integer duyuruId, String duyuruBaslik, String duyuruIcerik, Date duyuruTarih) {
        this.duyuruId = duyuruId;
        this.duyuruBaslik = duyuruBaslik;
        this.duyuruIcerik = duyuruIcerik;
        this.duyuruTarih = duyuruTarih;
    }

    public Integer getDuyuruId() {
        return duyuruId;
    }

    public void setDuyuruId(Integer duyuruId) {
        this.duyuruId = duyuruId;
    }

    public String getDuyuruBaslik() {
        return duyuruBaslik;
    }

    public void setDuyuruBaslik(String duyuruBaslik) {
        this.duyuruBaslik = duyuruBaslik;
    }

    public String getDuyuruIcerik() {
        return duyuruIcerik;
    }

    public void setDuyuruIcerik(String duyuruIcerik) {
        this.duyuruIcerik = duyuruIcerik;
    }

    public Date getDuyuruTarih() {
        return duyuruTarih;
    }

    public void setDuyuruTarih(Date duyuruTarih) {
        this.duyuruTarih = duyuruTarih;
    }

    public Uye getUyeId() {
        return uyeId;
    }

    public void setUyeId(Uye uyeId) {
        this.uyeId = uyeId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (duyuruId != null ? duyuruId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Duyuru)) {
            return false;
        }
        Duyuru other = (Duyuru) object;
        if ((this.duyuruId == null && other.duyuruId != null) || (this.duyuruId != null && !this.duyuruId.equals(other.duyuruId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ntp.ntp2.entities.Duyuru[ duyuruId=" + duyuruId + " ]";
    }
    
}
