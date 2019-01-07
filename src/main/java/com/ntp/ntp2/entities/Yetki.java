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
@Table(name = "yetki")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Yetki.findAll", query = "SELECT y FROM Yetki y")
    , @NamedQuery(name = "Yetki.findByYetkiNo", query = "SELECT y FROM Yetki y WHERE y.yetkiNo = :yetkiNo")
    , @NamedQuery(name = "Yetki.findByYetkiAd", query = "SELECT y FROM Yetki y WHERE y.yetkiAd = :yetkiAd")})
public class Yetki implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "yetkiNo")
    private Integer yetkiNo;
    @Basic(optional = false)
    @Column(name = "yetkiAd")
    private String yetkiAd;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "yetkiNo")
    private List<Uye> uyeList;

    public Yetki() {
    }

    public Yetki(Integer yetkiNo) {
        this.yetkiNo = yetkiNo;
    }

    public Yetki(Integer yetkiNo, String yetkiAd) {
        this.yetkiNo = yetkiNo;
        this.yetkiAd = yetkiAd;
    }

    public Integer getYetkiNo() {
        return yetkiNo;
    }

    public void setYetkiNo(Integer yetkiNo) {
        this.yetkiNo = yetkiNo;
    }

    public String getYetkiAd() {
        return yetkiAd;
    }

    public void setYetkiAd(String yetkiAd) {
        this.yetkiAd = yetkiAd;
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
        hash += (yetkiNo != null ? yetkiNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Yetki)) {
            return false;
        }
        Yetki other = (Yetki) object;
        if ((this.yetkiNo == null && other.yetkiNo != null) || (this.yetkiNo != null && !this.yetkiNo.equals(other.yetkiNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ntp.ntp2.entities.Yetki[ yetkiNo=" + yetkiNo + " ]";
    }
    
}
