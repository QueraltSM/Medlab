/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author QSM
 */
@Entity
@Table(name = "RESEARCHES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Researches.findAll", query = "SELECT r FROM Researches r"),
    @NamedQuery(name = "Researches.findById", query = "SELECT r FROM Researches r WHERE r.id = :id"),
    @NamedQuery(name = "Researches.findByTitle", query = "SELECT r FROM Researches r WHERE r.title = :title"),
    @NamedQuery(name = "Researches.findByDescription", query = "SELECT r FROM Researches r WHERE r.description = :description"),
    @NamedQuery(name = "Researches.findByDate", query = "SELECT r FROM Researches r WHERE r.date = :date"),
    @NamedQuery(name = "Researches.findByViews", query = "SELECT r FROM Researches r WHERE r.views = :views"),
    @NamedQuery(name = "Researches.findByConclusions", query = "SELECT r FROM Researches r WHERE r.conclusions = :conclusions")})
public class Researches implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "TITLE")
    private String title;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 700)
    @Column(name = "DESCRIPTION")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VIEWS")
    private int views;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 700)
    @Column(name = "CONCLUSIONS")
    private String conclusions;
    @JoinColumn(name = "SPECIALITY", referencedColumnName = "TYPE")
    @ManyToOne(optional = false)
    private Speciality speciality;
    @JoinColumn(name = "AUTHOR", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Users author;

    public Researches() {
    }

    public Researches(Long id) {
        this.id = id;
    }

    public Researches(Long id, String title, String description, Date date, int views, String conclusions) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.views = views;
        this.conclusions = conclusions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public String getConclusions() {
        return conclusions;
    }

    public void setConclusions(String conclusions) {
        this.conclusions = conclusions;
    }

    public Speciality getSpeciality() {
        return speciality;
    }

    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
    }

    public Users getAuthor() {
        return author;
    }

    public void setAuthor(Users author) {
        this.author = author;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Researches)) {
            return false;
        }
        Researches other = (Researches) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Researches[ id=" + id + " ]";
    }
    
}
