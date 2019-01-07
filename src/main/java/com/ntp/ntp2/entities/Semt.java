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
@Table(name = "semt")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Semt.findAll", query = "SELECT s FROM Semt s")
    , @NamedQuery(name = "Semt.findBySemtNo", query = "SELECT s FROM Semt s WHERE s.semtNo = :semtNo")
    , @NamedQuery(name = "Semt.findBySemtAd", query = "SELECT s FROM Semt s WHERE s.semtAd = :semtAd")})
public class Semt implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "semtNo")
    private Integer semtNo;
    @Basic(optional = false)
    @Column(name = "semtAd")
    private String semtAd;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "semtNo")
    private List<Uye> uyeList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "semtNo")
    private List<Mahalle> mahalleList;
    @JoinColumn(name = "ilNo", referencedColumnName = "ilNo")
    @ManyToOne(optional = false)
    private Il ilNo;
    @JoinColumn(name = "ilceNo", referencedColumnName = "ilceNo")
    @ManyToOne(optional = false)
    private Ilce ilceNo;

    public Semt() {
    }

    public Semt(Integer semtNo) {
        this.semtNo = semtNo;
    }

    public Semt(Integer semtNo, String semtAd) {
        this.semtNo = semtNo;
        this.semtAd = semtAd;
    }

    public Integer getSemtNo() {
        return semtNo;
    }

    public void setSemtNo(Integer semtNo) {
        this.semtNo = semtNo;
    }

    public String getSemtAd() {
        return semtAd;
    }

    public void setSemtAd(String semtAd) {
        this.semtAd = semtAd;
    }

    @XmlTransient
    public List<Uye> getUyeList() {
        return uyeList;
    }

    public void setUyeList(List<Uye> uyeList) {
        this.uyeList = uyeList;
    }

    @XmlTransient
    public List<Mahalle> getMahalleList() {
        return mahalleList;
    }

    public void setMahalleList(List<Mahalle> mahalleList) {
        this.mahalleList = mahalleList;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (semtNo != null ? semtNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Semt)) {
            return false;
        }
        Semt other = (Semt) object;
        if ((this.semtNo == null && other.semtNo != null) || (this.semtNo != null && !this.semtNo.equals(other.semtNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ntp.ntp2.entities.Semt[ semtNo=" + semtNo + " ]";
    }
    
}
