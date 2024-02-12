/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entiteti.podsistem2;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Rale
 */
@Entity
@Table(name = "video_snimak")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VideoSnimak.findAll", query = "SELECT v FROM VideoSnimak v"),
    @NamedQuery(name = "VideoSnimak.findByIdVS", query = "SELECT v FROM VideoSnimak v WHERE v.idVS = :idVS"),
    @NamedQuery(name = "VideoSnimak.findByNaziv", query = "SELECT v FROM VideoSnimak v WHERE v.naziv = :naziv"),
    @NamedQuery(name = "VideoSnimak.findByTrajanje", query = "SELECT v FROM VideoSnimak v WHERE v.trajanje = :trajanje"),
    @NamedQuery(name = "VideoSnimak.findByDatumIVremePostavljanja", query = "SELECT v FROM VideoSnimak v WHERE v.datumIVremePostavljanja = :datumIVremePostavljanja")})
public class VideoSnimak implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IdVS")
    private Integer idVS;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Naziv")
    private String naziv;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Trajanje")
    @Temporal(TemporalType.TIME)
    private Date trajanje;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DatumIVremePostavljanja")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumIVremePostavljanja;
    @ManyToMany(mappedBy = "videoSnimakList")
    private List<Kategorija> kategorijaList;
    @JoinColumn(name = "IdKor", referencedColumnName = "IdKor")
    @ManyToOne(optional = false)
    private Korisnik idKor;

    public VideoSnimak() {
    }

    public VideoSnimak(Integer idVS) {
        this.idVS = idVS;
    }

    public VideoSnimak(Integer idVS, String naziv, Date trajanje, Date datumIVremePostavljanja) {
        this.idVS = idVS;
        this.naziv = naziv;
        this.trajanje = trajanje;
        this.datumIVremePostavljanja = datumIVremePostavljanja;
    }

    public Integer getIdVS() {
        return idVS;
    }

    public void setIdVS(Integer idVS) {
        this.idVS = idVS;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Date getTrajanje() {
        return trajanje;
    }

    public void setTrajanje(Date trajanje) {
        this.trajanje = trajanje;
    }

    public Date getDatumIVremePostavljanja() {
        return datumIVremePostavljanja;
    }

    public void setDatumIVremePostavljanja(Date datumIVremePostavljanja) {
        this.datumIVremePostavljanja = datumIVremePostavljanja;
    }

    @XmlTransient
    public List<Kategorija> getKategorijaList() {
        return kategorijaList;
    }

    public void setKategorijaList(List<Kategorija> kategorijaList) {
        this.kategorijaList = kategorijaList;
    }

    public Korisnik getIdKor() {
        return idKor;
    }

    public void setIdKor(Korisnik idKor) {
        this.idKor = idKor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVS != null ? idVS.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VideoSnimak)) {
            return false;
        }
        VideoSnimak other = (VideoSnimak) object;
        if ((this.idVS == null && other.idVS != null) || (this.idVS != null && !this.idVS.equals(other.idVS))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.podsistem2.VideoSnimak[ idVS=" + idVS + " ]";
    }
    
}
