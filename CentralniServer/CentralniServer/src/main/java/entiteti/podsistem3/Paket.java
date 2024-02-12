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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "paket")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Paket.findAll", query = "SELECT p FROM Paket p"),
    @NamedQuery(name = "Paket.findByIdPak", query = "SELECT p FROM Paket p WHERE p.idPak = :idPak"),
    @NamedQuery(name = "Paket.findByMesecnaCena", query = "SELECT p FROM Paket p WHERE p.mesecnaCena = :mesecnaCena")})
public class Paket implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IdPak")
    private Integer idPak;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MesecnaCena")
    private int mesecnaCena;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPak")
    private List<Pretplata> pretplataList;

    public Paket() {
    }

    public Paket(Integer idPak) {
        this.idPak = idPak;
    }

    public Paket(Integer idPak, int mesecnaCena) {
        this.idPak = idPak;
        this.mesecnaCena = mesecnaCena;
    }

    public Integer getIdPak() {
        return idPak;
    }

    public void setIdPak(Integer idPak) {
        this.idPak = idPak;
    }

    public int getMesecnaCena() {
        return mesecnaCena;
    }

    public void setMesecnaCena(int mesecnaCena) {
        this.mesecnaCena = mesecnaCena;
    }

    @XmlTransient
    public List<Pretplata> getPretplataList() {
        return pretplataList;
    }

    public void setPretplataList(List<Pretplata> pretplataList) {
        this.pretplataList = pretplataList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPak != null ? idPak.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Paket)) {
            return false;
        }
        Paket other = (Paket) object;
        if ((this.idPak == null && other.idPak != null) || (this.idPak != null && !this.idPak.equals(other.idPak))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.podsistem3.Paket[ idPak=" + idPak + " ]";
    }
    
}
