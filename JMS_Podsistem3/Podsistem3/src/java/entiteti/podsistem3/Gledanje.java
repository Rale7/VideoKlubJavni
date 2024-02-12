/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entiteti.podsistem3;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Rale
 */
@Entity
@Table(name = "gledanje")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Gledanje.findAll", query = "SELECT g FROM Gledanje g"),
    @NamedQuery(name = "Gledanje.findByIdGle", query = "SELECT g FROM Gledanje g WHERE g.idGle = :idGle"),
    @NamedQuery(name = "Gledanje.findByDatumVremePocetka", query = "SELECT g FROM Gledanje g WHERE g.datumVremePocetka = :datumVremePocetka"),
    @NamedQuery(name = "Gledanje.findByZapocetoOd", query = "SELECT g FROM Gledanje g WHERE g.zapocetoOd = :zapocetoOd"),
    @NamedQuery(name = "Gledanje.findBySekundiOdlgedano", query = "SELECT g FROM Gledanje g WHERE g.sekundiOdlgedano = :sekundiOdlgedano")})
public class Gledanje implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IdGle")
    private Integer idGle;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DatumVremePocetka")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumVremePocetka;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ZapocetoOd")
    private int zapocetoOd;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SekundiOdlgedano")
    private int sekundiOdlgedano;
    @JoinColumn(name = "IdKor", referencedColumnName = "IdKor")
    @ManyToOne(optional = false)
    private Korisnik idKor;
    @JoinColumn(name = "IdVS", referencedColumnName = "IdVS")
    @ManyToOne(optional = false)
    private VideoSnimak idVS;

    public Gledanje() {
    }

    public Gledanje(Integer idGle) {
        this.idGle = idGle;
    }

    public Gledanje(Integer idGle, Date datumVremePocetka, int zapocetoOd, int sekundiOdlgedano) {
        this.idGle = idGle;
        this.datumVremePocetka = datumVremePocetka;
        this.zapocetoOd = zapocetoOd;
        this.sekundiOdlgedano = sekundiOdlgedano;
    }

    public Integer getIdGle() {
        return idGle;
    }

    public void setIdGle(Integer idGle) {
        this.idGle = idGle;
    }

    public Date getDatumVremePocetka() {
        return datumVremePocetka;
    }

    public void setDatumVremePocetka(Date datumVremePocetka) {
        this.datumVremePocetka = datumVremePocetka;
    }

    public int getZapocetoOd() {
        return zapocetoOd;
    }

    public void setZapocetoOd(int zapocetoOd) {
        this.zapocetoOd = zapocetoOd;
    }

    public int getSekundiOdlgedano() {
        return sekundiOdlgedano;
    }

    public void setSekundiOdlgedano(int sekundiOdlgedano) {
        this.sekundiOdlgedano = sekundiOdlgedano;
    }

    public Korisnik getIdKor() {
        return idKor;
    }

    public void setIdKor(Korisnik idKor) {
        this.idKor = idKor;
    }

    public VideoSnimak getIdVS() {
        return idVS;
    }

    public void setIdVS(VideoSnimak idVS) {
        this.idVS = idVS;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGle != null ? idGle.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Gledanje)) {
            return false;
        }
        Gledanje other = (Gledanje) object;
        if ((this.idGle == null && other.idGle != null) || (this.idGle != null && !this.idGle.equals(other.idGle))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.podsistem3.Gledanje[ idGle=" + idGle + " ]";
    }
    
}
