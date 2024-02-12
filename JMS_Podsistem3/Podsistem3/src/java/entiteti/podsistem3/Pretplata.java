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
@Table(name = "pretplata")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pretplata.findAll", query = "SELECT p FROM Pretplata p"),
    @NamedQuery(name = "Pretplata.findByIdPret", query = "SELECT p FROM Pretplata p WHERE p.idPret = :idPret"),
    @NamedQuery(name = "Pretplata.findByDatumIVremePocetka", query = "SELECT p FROM Pretplata p WHERE p.datumIVremePocetka = :datumIVremePocetka"),
    @NamedQuery(name = "Pretplata.findByTrenCena", query = "SELECT p FROM Pretplata p WHERE p.trenCena = :trenCena")})
public class Pretplata implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IdPret")
    private Integer idPret;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DatumIVremePocetka")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumIVremePocetka;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TrenCena")
    private int trenCena;
    @JoinColumn(name = "IdKor", referencedColumnName = "IdKor")
    @ManyToOne(optional = false)
    private Korisnik idKor;
    @JoinColumn(name = "IdPak", referencedColumnName = "IdPak")
    @ManyToOne(optional = false)
    private Paket idPak;

    public Pretplata() {
    }

    public Pretplata(Integer idPret) {
        this.idPret = idPret;
    }

    public Pretplata(Integer idPret, Date datumIVremePocetka, int trenCena) {
        this.idPret = idPret;
        this.datumIVremePocetka = datumIVremePocetka;
        this.trenCena = trenCena;
    }

    public Integer getIdPret() {
        return idPret;
    }

    public void setIdPret(Integer idPret) {
        this.idPret = idPret;
    }

    public Date getDatumIVremePocetka() {
        return datumIVremePocetka;
    }

    public void setDatumIVremePocetka(Date datumIVremePocetka) {
        this.datumIVremePocetka = datumIVremePocetka;
    }

    public int getTrenCena() {
        return trenCena;
    }

    public void setTrenCena(int trenCena) {
        this.trenCena = trenCena;
    }

    public Korisnik getIdKor() {
        return idKor;
    }

    public void setIdKor(Korisnik idKor) {
        this.idKor = idKor;
    }

    public Paket getIdPak() {
        return idPak;
    }

    public void setIdPak(Paket idPak) {
        this.idPak = idPak;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPret != null ? idPret.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pretplata)) {
            return false;
        }
        Pretplata other = (Pretplata) object;
        if ((this.idPret == null && other.idPret != null) || (this.idPret != null && !this.idPret.equals(other.idPret))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.podsistem3.Pretplata[ idPret=" + idPret + " ]";
    }
    
}
