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
@Table(name = "il")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Il.findAll", query = "SELECT \u0131 FROM Il \u0131")
    , @NamedQuery(name = "Il.findByIlNo", query = "SELECT \u0131 FROM Il \u0131 WHERE \u0131.ilNo = :ilNo")
    , @NamedQuery(name = "Il.findByIlAd", query = "SELECT \u0131 FROM Il \u0131 WHERE \u0131.ilAd = :ilAd")})
public class Il implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ilNo")
    private Integer ilNo;
    @Basic(optional = false)
    @Column(name = "ilAd")
    private String ilAd;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ilNo")
    private List<Uye> uyeList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ilNo")
    private List<Ilce> ilceList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ilNo")
    private List<Mahalle> mahalleList;
    /*@OneToMany(cascade = CascadeType.ALL, mappedBy = "ilNo")
    private List<Semt> semtList;*/

    public Il() {
    }

    public Il(Integer ilNo) {
        this.ilNo = ilNo;
    }

    public Il(Integer ilNo, String ilAd) {
        this.ilNo = ilNo;
        this.ilAd = ilAd;
    }

    public Integer getIlNo() {
        return ilNo;
    }

    public void setIlNo(Integer ilNo) {
        this.ilNo = ilNo;
    }

    public String getIlAd() {
        return ilAd;
    }

    public void setIlAd(String ilAd) {
        this.ilAd = ilAd;
    }

    @XmlTransient
    public List<Uye> getUyeList() {
        return uyeList;
    }

    public void setUyeList(List<Uye> uyeList) {
        this.uyeList = uyeList;
    }

    @XmlTransient
    public List<Ilce> getIlceList() {
        return ilceList;
    }

    public void setIlceList(List<Ilce> ilceList) {
        this.ilceList = ilceList;
    }

    @XmlTransient
    public List<Mahalle> getMahalleList() {
        return mahalleList;
    }

    public void setMahalleList(List<Mahalle> mahalleList) {
        this.mahalleList = mahalleList;
    }

    /*@XmlTransient
    public List<Semt> getSemtList() {
        return semtList;
    }

    public void setSemtList(List<Semt> semtList) {
        this.semtList = semtList;
    }*/

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ilNo != null ? ilNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Il)) {
            return false;
        }
        Il other = (Il) object;
        if ((this.ilNo == null && other.ilNo != null) || (this.ilNo != null && !this.ilNo.equals(other.ilNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ntp.ntp2.entities.Il[ ilNo=" + ilNo + " ]";
    }
    
}
