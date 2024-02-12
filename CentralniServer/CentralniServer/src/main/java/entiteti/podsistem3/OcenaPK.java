/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entiteti.podsistem3;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Rale
 */
@Embeddable
public class OcenaPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "IdKor")
    private int idKor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IdVS")
    private int idVS;

    public OcenaPK() {
    }

    public OcenaPK(int idKor, int idVS) {
        this.idKor = idKor;
        this.idVS = idVS;
    }

    public int getIdKor() {
        return idKor;
    }

    public void setIdKor(int idKor) {
        this.idKor = idKor;
    }

    public int getIdVS() {
        return idVS;
    }

    public void setIdVS(int idVS) {
        this.idVS = idVS;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idKor;
        hash += (int) idVS;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OcenaPK)) {
            return false;
        }
        OcenaPK other = (OcenaPK) object;
        if (this.idKor != other.idKor) {
            return false;
        }
        if (this.idVS != other.idVS) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.podsistem3.OcenaPK[ idKor=" + idKor + ", idVS=" + idVS + " ]";
    }
    
}
