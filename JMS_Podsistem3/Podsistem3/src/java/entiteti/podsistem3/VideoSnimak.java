/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entiteti.podsistem3;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
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
    @NamedQuery(name = "VideoSnimak.findByIdVS", query = "SELECT v FROM VideoSnimak v WHERE v.idVS = :idVS")})
public class VideoSnimak implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IdVS")
    private Integer idVS;
    @JoinColumn(name = "IdKor", referencedColumnName = "IdKor")
    @ManyToOne(optional = false)
    private Korisnik idKor;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idVS")
    private List<Gledanje> gledanjeList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "videoSnimak")
    private List<Ocena> ocenaList;

    public VideoSnimak() {
    }

    public VideoSnimak(Integer idVS) {
        this.idVS = idVS;
    }

    public Integer getIdVS() {
        return idVS;
    }

    public void setIdVS(Integer idVS) {
        this.idVS = idVS;
    }

    public Korisnik getIdKor() {
        return idKor;
    }

    public void setIdKor(Korisnik idKor) {
        this.idKor = idKor;
    }

    @XmlTransient
    public List<Gledanje> getGledanjeList() {
        return gledanjeList;
    }

    public void setGledanjeList(List<Gledanje> gledanjeList) {
        this.gledanjeList = gledanjeList;
    }

    @XmlTransient
    public List<Ocena> getOcenaList() {
        return ocenaList;
    }

    public void setOcenaList(List<Ocena> ocenaList) {
        this.ocenaList = ocenaList;
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
        return "entiteti.podsistem3.VideoSnimak[ idVS=" + idVS + " ]";
    }
    
}
