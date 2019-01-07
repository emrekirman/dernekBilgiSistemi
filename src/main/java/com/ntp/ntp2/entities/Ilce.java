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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Emre
 */
@Entity
@Table(name = "ilce")
@XmlRootElement
public class Ilce implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ilceNo")
    private Integer ilceNo;
    @Basic(optional = false)
    @Column(name = "ilceAd")
    private String ilceAd;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ilceNo")
    private List<Uye> uyeList;
    @JoinColumn(name = "ilNo", referencedColumnName = "ilNo")
    @ManyToOne(optional = false)
    private Il ilNo;
    /*@OneToMany(cascade = CascadeType.ALL, mappedBy = "ilceNo")
    private List<Semt> semtList;*/

    public Ilce() {
    }

    public Ilce(Integer ilceNo) {
        this.ilceNo = ilceNo;
    }

    public Ilce(Integer ilceNo, String ilceAd) {
        this.ilceNo = ilceNo;
        this.ilceAd = ilceAd;
    }

    public Integer getIlceNo() {
        return ilceNo;
    }

    public void setIlceNo(Integer ilceNo) {
        this.ilceNo = ilceNo;
    }

    public String getIlceAd() {
        return ilceAd;
    }

    public void setIlceAd(String ilceAd) {
        this.ilceAd = ilceAd;
    }

    @XmlTransient
    public List<Uye> getUyeList() {
        return uyeList;
    }

    public void setUyeList(List<Uye> uyeList) {
        this.uyeList = uyeList;
    }

    public Il getIlNo() {
        return ilNo;
    }

    public void setIlNo(Il ilNo) {
        this.ilNo = ilNo;
    }

   /* @XmlTransient
    public List<Semt> getSemtList() {
        return semtList;
    }

    public void setSemtList(List<Semt> semtList) {
        this.semtList = semtList;
    }*/

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ilceNo != null ? ilceNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ilce)) {
            return false;
        }
        Ilce other = (Ilce) object;
        if ((this.ilceNo == null && other.ilceNo != null) || (this.ilceNo != null && !this.ilceNo.equals(other.ilceNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ntp.ntp2.entities.Ilce[ ilceNo=" + ilceNo + " ]";
    }
    
}
