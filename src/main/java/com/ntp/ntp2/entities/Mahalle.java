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
import javax.persistence.Lob;
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
@Table(name = "mahalle")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mahalle.findAll", query = "SELECT m FROM Mahalle m")
    , @NamedQuery(name = "Mahalle.findByMahalleNo", query = "SELECT m FROM Mahalle m WHERE m.mahalleNo = :mahalleNo")})
public class Mahalle implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "mahalleNo")
    private Integer mahalleNo;
    @Basic(optional = false)
    @Lob
    @Column(name = "mahalleAd")
    private String mahalleAd;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mahalleNo")
    private List<Uye> uyeList;
    @JoinColumn(name = "ilNo", referencedColumnName = "ilNo")
    @ManyToOne(optional = false)
    private Il ilNo;
    /*@JoinColumn(name = "semtNo", referencedColumnName = "semtNo")
    @ManyToOne(optional = false)
    private Semt semtNo;*/

    public Mahalle() {
    }

    public Mahalle(Integer mahalleNo) {
        this.mahalleNo = mahalleNo;
    }

    public Mahalle(Integer mahalleNo, String mahalleAd) {
        this.mahalleNo = mahalleNo;
        this.mahalleAd = mahalleAd;
    }
    public Mahalle(String mahalleAd){
        this.mahalleAd=mahalleAd;
    }

    public Integer getMahalleNo() {
        return mahalleNo;
    }

    public void setMahalleNo(Integer mahalleNo) {
        this.mahalleNo = mahalleNo;
    }

    public String getMahalleAd() {
        return mahalleAd;
    }

    public void setMahalleAd(String mahalleAd) {
        this.mahalleAd = mahalleAd;
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

    /*public Semt getSemtNo() {
        return semtNo;
    }

    public void setSemtNo(Semt semtNo) {
        this.semtNo = semtNo;
    }*/

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mahalleNo != null ? mahalleNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Mahalle)) {
            return false;
        }
        Mahalle other = (Mahalle) object;
        if ((this.mahalleNo == null && other.mahalleNo != null) || (this.mahalleNo != null && !this.mahalleNo.equals(other.mahalleNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ntp.ntp2.entities.Mahalle[ mahalleNo=" + mahalleNo + " ]";
    }
    
}
