/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ntp.ntp2.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Emre
 */
@Entity
@Table(name = "cinsiyet")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cinsiyet.findAll", query = "SELECT c FROM Cinsiyet c")
    , @NamedQuery(name = "Cinsiyet.findByCinsiyetNo", query = "SELECT c FROM Cinsiyet c WHERE c.cinsiyetNo = :cinsiyetNo")
    , @NamedQuery(name = "Cinsiyet.findByCinsiyetAd", query = "SELECT c FROM Cinsiyet c WHERE c.cinsiyetAd = :cinsiyetAd")})
public class Cinsiyet implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cinsiyetNo")
    private Integer cinsiyetNo;
    @Basic(optional = false)
    @Column(name = "cinsiyetAd")
    private String cinsiyetAd;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cinsiyetNo")
    private List<Uye> uyeList;

    public Cinsiyet() {
    }

    public Cinsiyet(Integer cinsiyetNo) {
        this.cinsiyetNo = cinsiyetNo;
    }

    public Cinsiyet(Integer cinsiyetNo, String cinsiyetAd) {
        this.cinsiyetNo = cinsiyetNo;
        this.cinsiyetAd = cinsiyetAd;
    }

    public Integer getCinsiyetNo() {
        return cinsiyetNo;
    }

    public void setCinsiyetNo(Integer cinsiyetNo) {
        this.cinsiyetNo = cinsiyetNo;
    }

    public String getCinsiyetAd() {
        return cinsiyetAd;
    }

    public void setCinsiyetAd(String cinsiyetAd) {
        this.cinsiyetAd = cinsiyetAd;
    }

    @XmlTransient
    public List<Uye> getUyeList() {
        return uyeList;
    }

    public void setUyeList(List<Uye> uyeList) {
        this.uyeList = uyeList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cinsiyetNo != null ? cinsiyetNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cinsiyet)) {
            return false;
        }
        Cinsiyet other = (Cinsiyet) object;
        if ((this.cinsiyetNo == null && other.cinsiyetNo != null) || (this.cinsiyetNo != null && !this.cinsiyetNo.equals(other.cinsiyetNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ntp.ntp2.entities.Cinsiyet[ cinsiyetNo=" + cinsiyetNo + " ]";
    }
    
}
