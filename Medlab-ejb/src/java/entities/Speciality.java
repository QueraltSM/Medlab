/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author QSM
 */
@Entity
@Table(name = "SPECIALITY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Speciality.findAll", query = "SELECT s FROM Speciality s"),
    @NamedQuery(name = "Speciality.findByType", query = "SELECT s FROM Speciality s WHERE s.type = :type")})
public class Speciality implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "speciality")
    private Collection<Researches> researchesCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "TYPE")
    private String type;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "speciality")
    private Collection<Discussions> discussionsCollection;

    public Speciality() {
    }

    public Speciality(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @XmlTransient
    public Collection<Discussions> getDiscussionsCollection() {
        return discussionsCollection;
    }

    public void setDiscussionsCollection(Collection<Discussions> discussionsCollection) {
        this.discussionsCollection = discussionsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (type != null ? type.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Speciality)) {
            return false;
        }
        Speciality other = (Speciality) object;
        if ((this.type == null && other.type != null) || (this.type != null && !this.type.equals(other.type))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Speciality[ type=" + type + " ]";
    }

    @XmlTransient
    public Collection<Researches> getResearchesCollection() {
        return researchesCollection;
    }

    public void setResearchesCollection(Collection<Researches> researchesCollection) {
        this.researchesCollection = researchesCollection;
    }
    
}
